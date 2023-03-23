package com.atguigu.fruit.dao.impl;

import com.atguigu.fruit.dao.FruitDAO;
import com.atguigu.fruit.pojo.Fruit;
import com.atguigu.myssm.basedao.BaseDAO;

import java.util.List;

/**
 * @Author: 刘华昌
 * @DATE: 2022/6/7 16:24 星期二
 * @Operating:
 * @Description:
 */
public class FruitDAOImpl extends BaseDAO<Fruit> implements FruitDAO {

    //重写 FruitDAO接口的 getFruitList()方法
    @Override
    public List<Fruit> getFruitList() {
        //调用 父类BaseDAO 的 executeQuery("sql") 方法, 通过相应的sql语句，从数据库中获取数据
        return super.executeQuery("select * from t_fruit");
    }

    //重写 FruitDAO接口的 getFruitByFid()方法
    @Override
    public Fruit getFruitByFid(Integer fid) {
        //调用 父类BaseDAO 的 load("sql") 方法, 通过相应的sql语句，从数据库中获取数据
        return super.load("select * from t_fruit where fid = ?", fid);
    }

    //重写 FruitDAO接口的 updateFruit()方法
    @Override
    public void updateFruit(Fruit fruit) {
        //调用 父类BaseDAO 的 executeUpdate("sql") 方法, 通过相应的sql语句，修改数据库中数据
        String sql = "update t_fruit set fname = ?, price = ?, fcount = ?, remark = ? where fid = ?";
        super.executeUpdate(sql, fruit.getFname(), fruit.getPrice(), fruit.getFcount(), fruit.getRemark(), fruit.getFid());
    }

    //重写 FruitDAO接口的 delFruit()方法
    @Override
    public void delFruit(Integer fid) {
        //调用 父类BaseDAO 的 executeUpdate("sql") 方法, 通过相应的sql语句，删除数据库中数据
        super.executeUpdate("delete from t_fruit where fid = ?", fid);
    }

    //重写 FruitDAO接口的 addFruit()方法
    @Override
    public void addFruit(Fruit fruit) {
        //调用 父类BaseDAO 的 executeUpdate("sql") 方法, 通过相应的sql语句，添加数据库中数据
        String sql = "insert into t_fruit values(0,?,?,?,?)";
        super.executeUpdate(sql,fruit.getFname(),fruit.getPrice(),fruit.getFcount(),fruit.getRemark());
    }
}
