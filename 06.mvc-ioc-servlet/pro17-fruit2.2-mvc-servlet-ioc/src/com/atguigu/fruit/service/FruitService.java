package com.atguigu.fruit.service;

import com.atguigu.fruit.pojo.Fruit;

import java.util.List;

/**
 * @Author: 刘华昌
 * @DATE: 2022/6/17 9:23 星期五
 * @Operating:
 * @Description:
 */
public interface FruitService {

    //获取指定页面的库存列表信息
    List<Fruit> getFruitList(String keyword, Integer pageNo);
    //添加库存记录信息
    void addFruit(Fruit fruit);
    //删除指定fid的库存记录
    void delFruit(Integer fid);
    //修改的库存记录
    void updateFruit(Fruit fruit);
    //查看指定fid的库存记录
    Fruit getFruitByFid(Integer fid);
    //获取库存总页数
    Integer getPageCount(String keyword);
}
