package com.atguigu.fruit.dao.impl;

import com.atguigu.fruit.dao.FruitDAO;
import com.atguigu.fruit.pojo.Fruit;
import com.atguigu.myssm.base.BaseDAO;

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
        //调用 父类BaseDAO 的 executeQuery("sql") 方法, 通过相应的sql语句，数据库获取数据
        return super.executeQuery("select * from t_fruit");
    }
}
