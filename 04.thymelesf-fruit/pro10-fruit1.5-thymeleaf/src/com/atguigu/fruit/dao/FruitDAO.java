package com.atguigu.fruit.dao;

import com.atguigu.fruit.pojo.Fruit;

import java.util.List;

/**
 * @Author: 刘华昌
 * @DATE: 2022/6/8 10:13 星期三
 * @Operating:
 * @Description:
 */
public interface FruitDAO {
    //获取所有的库存信息列表
    List<Fruit> getFruitList();

    //根据fid获取指定的水果库存信息
    Fruit getFruitByFid(Integer fid);

    //根据Fruit对象 修改指定的水果库存信息
    void updateFruit(Fruit fruit);

    //根据fid删除指定水果
    void delFruit(Integer fid);

    //添加方法
    void addFruit(Fruit fruit);
}
