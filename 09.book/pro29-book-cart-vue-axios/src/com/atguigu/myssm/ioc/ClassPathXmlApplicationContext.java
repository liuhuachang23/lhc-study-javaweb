package com.atguigu.myssm.ioc;

import com.atguigu.myssm.util.StringUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: 刘华昌
 * @DATE: 2022/6/17 19:15 星期五
 * @Operating:
 * @Description: 解析 bean标签 实现bean标签的作用
 */
public class ClassPathXmlApplicationContext implements BeanFactory {

    //创建一个Map容器，用于存储所有的bean标签中的id属性值和class属性值（k-v）
    private Map<String, Object> beanMap = new HashMap<>();
    private String path = "applicationContext.xml";

    public ClassPathXmlApplicationContext() {
        this("applicationContext.xml");
    }


    public ClassPathXmlApplicationContext(String path) {
        if (StringUtil.isEmpty(path)){
            throw new RuntimeException("IOC容器的配置文件没有指定...");
        }
        //========================= 获取所有bean对象 ===================================
        try {
            //通过类加载器，获取一个和 applicationContext.xml文件 相关联的通道
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(path);
            //创建DocumentBuilderFactory对象
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            //创建DocumentBuilder对象
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            //3. 创建一个Document对象（）
            Document document = documentBuilder.parse(inputStream);

            //4. 获取所有的bean结点 （通过 指定标签名 获取对应的结点集合）
            NodeList beanNodeList = document.getElementsByTagName("bean");
            //遍历结点集合
            for (int i = 0; i < beanNodeList.getLength(); i++) {
                Node beanNode = beanNodeList.item(i);
                //判断该结点是否为 元素结点
                if (beanNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element beanElement = (Element) beanNode;
                    //按属性名称 获取 属性值
                    String beanId = beanElement.getAttribute("id");
                    String className = beanElement.getAttribute("class");
                    //通过 全类名className 获取类对象
                    Class<?> beanClass = Class.forName(className);
                    //通过类对象 创建bean实例
                    Object beanObj = beanClass.newInstance();
                    //将bean对象保存到Map容器中
                    //bean标签中 id属性值 - class属性值(bean对象) ==》（k-v）
                    beanMap.put(beanId, beanObj);

                    //到目前为止，只是获取了所有bean对象，并存储到了Map集合中，
                    //还没有建立 bean与bean之间的依赖关系

                }
            }
            //========================= 建立bean与bean之间的依赖关系 （依赖注入） ===================================

            //5. 组装bean之间的依赖关系
            //1) 遍历所有bean结点
            for (int i = 0; i < beanNodeList.getLength(); i++) {
                Node beanNode = beanNodeList.item(i);
                //判断该结点是否为 元素结点
                if (beanNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element beanElement = (Element) beanNode;
                    //获取bean标签的id属性值
                    String beanId = beanElement.getAttribute("id");

                    //2) 获取bean结点中的 property子节点
                    // 获取bean结点中的 所有子节点
                    NodeList beanChildNodeList = beanElement.getChildNodes();
                    //遍历这些子节点
                    for (int j = 0; j < beanChildNodeList.getLength(); j++) {
                        Node beanChildNode = beanChildNodeList.item(j);
                        //如果结点为 元素结点，标签结点名为property
                        if (beanChildNode.getNodeType() == Node.ELEMENT_NODE && "property".equals(beanChildNode.getNodeName())) {
                            Element propertyElement = (Element)beanChildNode;

                            //3）解析 property子节点
                            //获取name属性值 （用于连接 父节点(bean标签)中class属性值对象）
                            String propertyName = propertyElement.getAttribute("name");
                            //获取ref属性值 （用于引用 其他bean标签（通过引用其id属性值））
                            String propertyRef = propertyElement.getAttribute("ref");

                            //4）获取ref连接 的其他bean标签 中class值对象
                            //找到 ref 对应(引用)的其他bean结点的 class属性值对象
                            Object refObj = beanMap.get(propertyRef);

                            //5) 获取本bean结点的 class属性值对象 中的属性
                            //获取本bean结点的 class属性值对象
                            Object beanObj = beanMap.get(beanId);
                            //通过反射 获取该对象 中指定的属性
                            Class<?> beanClazz = beanObj.getClass();
                            Field propertyField = beanClazz.getDeclaredField(propertyName);

                            propertyField.setAccessible(true); //爆破
                            //6） 给属性 赋值（ref对应(引用)的其他bean结点的 class属性值对象）
                            propertyField.set(beanObj, refObj);

                        }
                    }

                }
            }

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object getBean(String id) {
        //通过 key 获取Map中的 value
        return beanMap.get(id);
    }
}
