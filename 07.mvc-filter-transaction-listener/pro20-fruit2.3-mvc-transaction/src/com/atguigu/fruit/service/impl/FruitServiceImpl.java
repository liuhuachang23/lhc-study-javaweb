package com.atguigu.fruit.service.impl;

import com.atguigu.fruit.service.FruitService;
import com.atguigu.fruit.dao.FruitDAO;
import com.atguigu.fruit.pojo.Fruit;

import java.util.List;

/**
 * @Author: 刘华昌
 * @DATE: 2022/6/17 9:31 星期五
 * @Operating:
 * @Description:
 */
public class FruitServiceImpl implements FruitService {

    //1. 让 fruitDAO对象 属性 改为null （解除FruitServiceImpl 与 fruitDAO的耦合 ）
    //private FruitDAO fruitDAO = new FruitDAOImpl();
    private FruitDAO fruitDAO = null;
    //改完了之后，就要解决 NullPointException
    //2. 在applicationContext.xml配置文件 bean标签中 "关联" FruitServiceImpl 与 fruitDAO
    // 即：将类与类之间的耦合 --> bean与bean之间的依赖
    //3. 在 ClassPathXmlApplicationContext 正式完成依赖


    @Override
    public List<Fruit> getFruitList(String keyword, Integer pageNo) {
        return fruitDAO.getFruitList(keyword, pageNo);
    }

    @Override
    public void addFruit(Fruit fruit) {
        fruitDAO.addFruit(fruit);
        Fruit fruit2 = fruitDAO.getFruitByFid(2);
        fruit2.setFcount(99);
        fruitDAO.updateFruit(fruit2);
    }

    @Override
    public void delFruit(Integer fid) {
        fruitDAO.delFruit(fid);
    }

    @Override
    public void updateFruit(Fruit fruit) {
        fruitDAO.updateFruit(fruit);
    }

    @Override
    public Fruit getFruitByFid(Integer fid) {
        return fruitDAO.getFruitByFid(fid);
    }

    @Override
    public Integer getPageCount(String keyword) {
        int fruitCount = fruitDAO.getFruitCount(keyword);
        int pageCount = (fruitCount + 5 - 1) / 5;
        return pageCount;
    }
}
