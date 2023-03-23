package com.atguigu.myssm.basedao;

import java.lang.reflect.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseDAO<T> {

    protected Connection conn ;
    protected PreparedStatement psmt ;
    protected ResultSet rs ;

    //T的Class对象
    private Class entityClass ;

    public BaseDAO(){
        //getClass() 获取Class对象，当前我们执行的是new FruitDAOImpl() , 创建的是FruitDAOImpl的实例
        //那么子类构造方法内部首先会调用父类（BaseDAO）的无参构造方法
        //因此此处的getClass()会被执行，但是getClass获取的是FruitDAOImpl的Class
        //所以getGenericSuperclass()获取到的是BaseDAO的Class
        Type genericType = getClass().getGenericSuperclass();
        //ParameterizedType 参数化类型
        Type[] actualTypeArguments = ((ParameterizedType) genericType).getActualTypeArguments();
        //获取到的<T>中的T的真实的类型
        Type actualType = actualTypeArguments[0];
        try {
            entityClass = Class.forName(actualType.getTypeName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new DAOException("BaseDAO 构造器 出错了...");
        }
    }

    //获取连接
    protected Connection getConn(){
        return ConnUtil.getConn() ;
    }

    //删除 close()
    protected void close(ResultSet rs , PreparedStatement psmt , Connection conn){
    /*    try {
            if (rs != null) {
                rs.close();
            }
            if(psmt!=null){
                psmt.close();
            }
            if(conn!=null && !conn.isClosed()){
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }*/
    }

    //给预处理命令对象设置参数
    private void setParams(PreparedStatement psmt , Object... params) throws SQLException {
        if(params!=null && params.length>0){
            for (int i = 0; i < params.length; i++) {
                psmt.setObject(i+1,params[i]);
            }
        }
    }

    //执行更新，返回影响行数
    protected int executeUpdate(String sql , Object... params){
        boolean insertFlag = false ;
        insertFlag = sql.trim().toUpperCase().startsWith("INSERT");
        try {
            conn = getConn();
            if(insertFlag){
                psmt = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            }else {
                psmt = conn.prepareStatement(sql);
            }
            setParams(psmt,params);
            int count = psmt.executeUpdate() ;

            if(insertFlag){
                rs = psmt.getGeneratedKeys();
                if(rs.next()){
                    return ((Long)rs.getLong(1)).intValue();
                }
            }
            return count ;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException("BaseDAO executeUpdate 出错了...");
        }
    }

    //通过反射技术给obj对象的property属性赋propertyValue值
    private void setValue(Object obj ,  String property , Object propertyValue){
        Class clazz = obj.getClass();
        try {
            //获取property这个字符串对应的属性名 ， 比如 "fid"  去找 obj对象中的 fid 属性
            Field field = clazz.getDeclaredField(property);
            if(field!=null){

                //获取field字段的类型名
                String typeName = field.getType().getName();

                //判断：如果是自定义类型（如Topic类中的author属性（userBasic类型），而在数据库中 我们存储的是一个fid(Integer) 我们就需要将Integer封装成UserBasic），
                // 则需要调用这个自定义类的带一个参数（如fid）的构造方法，创建出这个自定义的实例对象（如userBasic），
                //然后将实例对象赋值给这个属性
                if (isMyType(typeName)){
                    //获取Class类对象
                    Class<?> typeNameClass = Class.forName(typeName);
                    //获取带一个参数的构造方法
                    Constructor<?> constructor = typeNameClass.getDeclaredConstructor(java.lang.Integer.class);
                    //创建对象实例
                    propertyValue = constructor.newInstance(propertyValue);
                }
                field.setAccessible(true);
                field.set(obj,propertyValue);
            }

        } catch (NoSuchFieldException | IllegalAccessException | ClassNotFoundException | NoSuchMethodException e) {
            e.printStackTrace();
            throw new DAOException("BaseDAO setValue 出错了...");
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    //是否 不是自定义类型
    private static boolean isNotMyType(String typeName){
        return "java.lang.Integer".equals(typeName)
                || "java.lang.String".equals(typeName)
                || "java.util.Date".equals(typeName)
                || "java.sql.Date".equals(typeName);
    }
    //是否 是自定义类型
    private static boolean isMyType(String typeName){
        return !isNotMyType(typeName);
    }

    //执行复杂查询，返回例如统计结果
    protected Object[] executeComplexQuery(String sql , Object... params){
        try {
            conn = getConn() ;
            psmt = conn.prepareStatement(sql);
            setParams(psmt,params);
            rs = psmt.executeQuery();

            //通过rs可以获取结果集的元数据
            //元数据：描述结果集数据的数据 , 简单讲，就是这个结果集有哪些列，什么类型等等

            ResultSetMetaData rsmd = rs.getMetaData();
            //获取结果集的列数
            int columnCount = rsmd.getColumnCount();
            Object[] columnValueArr = new Object[columnCount];
            //6.解析rs
            if(rs.next()){
                for(int i = 0 ; i<columnCount;i++){
                    Object columnValue = rs.getObject(i+1);     //33    苹果      5
                    columnValueArr[i]=columnValue;
                }
                return columnValueArr ;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException("BaseDAO executeComplexQuery 出错了...");
        }
        return null ;
    }

    //执行查询，返回单个实体对象
    protected T load(String sql , Object... params){
        try {
            conn = getConn() ;
            psmt = conn.prepareStatement(sql);
            setParams(psmt,params);
            rs = psmt.executeQuery();

            //通过rs可以获取结果集的元数据
            //元数据：描述结果集数据的数据 , 简单讲，就是这个结果集有哪些列，什么类型等等

            ResultSetMetaData rsmd = rs.getMetaData();
            //获取结果集的列数
            int columnCount = rsmd.getColumnCount();
            //6.解析rs
            if(rs.next()){
                T entity = (T)entityClass.newInstance();

                for(int i = 0 ; i<columnCount;i++){
                    String columnName = rsmd.getColumnName(i+1);            //fid   fname   price
                    Object columnValue = rs.getObject(i+1);     //33    苹果      5
                    setValue(entity,columnName,columnValue);
                }
                return entity ;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new DAOException("BaseDAO load 出错了...");
        }
        return null ;
    }


    //执行查询，返回List
    protected List<T> executeQuery(String sql , Object... params){
        List<T> list = new ArrayList<>();
        try {
            conn = getConn() ;
            psmt = conn.prepareStatement(sql);
            setParams(psmt,params);
            rs = psmt.executeQuery();

            //通过rs可以获取结果集的元数据
            //元数据：描述结果集数据的数据 , 简单讲，就是这个结果集有哪些列，什么类型等等

            ResultSetMetaData rsmd = rs.getMetaData();
            //获取结果集的列数
            int columnCount = rsmd.getColumnCount();
            //6.解析rs
            while(rs.next()){
                T entity = (T)entityClass.newInstance();

                for(int i = 0 ; i<columnCount;i++){
                    String columnName = rsmd.getColumnLabel(i+1);            //fid   fname   price
                    Object columnValue = rs.getObject(i+1);     //33    苹果      5
                    setValue(entity,columnName,columnValue);
                }
                list.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new DAOException("BaseDAO executeQuery 出错了...");
        }
        return list ;
    }
}
