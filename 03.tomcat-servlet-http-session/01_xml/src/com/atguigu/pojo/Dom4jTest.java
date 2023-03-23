package com.atguigu.pojo;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

public class Dom4jTest {
    @Test
    public void test1() throws Exception {
        //1. xml配置文件 生成Document对象
        //1）创建一个SaxReader输入流
        SAXReader saxReader = new SAXReader();
        //2）通过SaxReader输入流 读取xml配置文件 生成Document对象
        Document document = saxReader.read("src/books.xml");

        //2. 通过Document对象 获取根元素对象 （books标签）
        Element rootElement = document.getRootElement();

        //3. 通过根元素对象 获取指定标签名的子元素对象 返回一个元素集合
        //1) 标签对象.elements("子标签名") 获取标签里 所有的子标签对象
        //2) 当子标签只有一个时使用 标签对象.element("子标签名")
        List<Element> books = rootElement.elements("book");

        //4. 遍历集合，进行相应的操作
        for(Element book : books){
            //5. 标签的一些常用方法

            //1) 标签对象.asXML() 把标签对象转换为标签字符串
            String bookStr = book.asXML();
            System.out.println(bookStr);
            System.out.println("=========================");

            //2) 标签对象.element("子标签名")  获取标签内的 指定标签名的 子标签对象
            Element elementName = book.element("name");
            System.out.println(elementName);
            System.out.println("=========================");

            //3) 标签对象.getText() 获取标签中的文本内容
            String nameText = elementName.getText();

            //3) 标签对象.elementText("子标签名") 获取标签内 指定子标签名的 文本内容
            String priceText = book.elementText("price");
            String authorText = book.elementText("author");

            //4) 标签对象.attributeValue("属性名") 获取标签内 指定属性名的 属性值
            String snValue = book.attributeValue("sn");

            //将需要的数据，转成book对象
            System.out.println(new Book(snValue,nameText,new BigDecimal(priceText),authorText));
            System.out.println("**************************************");

        }
    }
}
