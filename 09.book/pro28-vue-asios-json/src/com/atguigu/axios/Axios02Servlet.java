package com.atguigu.axios;

import com.atguigu.pojo.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Author: 刘华昌
 * @DATE: 2022/7/22 14:01 星期五
 * @Operating:
 * @Description: 服务器端
 */
@WebServlet("/axios02.do")
public class Axios02Servlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //客户端 传送JSON格式的参数者
        //在服务器端 不能使用 request.getParameter("username") 来获取接收值了

        //1) 由于请求体数据有可能很大，所以Servlet标准在设计API的时候要求我们通过输入流来读取
        BufferedReader br = request.getReader();

        //2) 创建StringBuilder对象来累加存储从请求体中读取到的每一行
        StringBuffer stringBuffer = new StringBuffer();

        //3) 声明临时变量
        String str = null;

        //4) 循环读取
        while((str=br.readLine())!=null){
            stringBuffer.append(str);       //将每一次读取到的数据 追加到 stringBuffer 上
        }

        //5) 关闭流
        br.close();

        //6) 累加的结果就是整个请求体
        str = stringBuffer.toString();

        //7) 创建Gson对象用于解析JSON字符串 , 两种方式创建：
        //方式1：这种方式功能更强大
        // Gson gson = new GsonBuilder().create();
        //例如 pojo类中存在日期属性 我们可以对其做格式化：
        // new GsonBuilder().setDateFormat("yyyy-MM-dd").create()

        //方式2
        Gson gson = new Gson();

        //8) Gson 有两个API
        //① fromJson(String,T)      将字符串转成 java对象
        //② toJson(java object)     将java对象 转化为 json字符串，这样才能响应给客户端

        //将字符串转换为java对象
        User user = gson.fromJson(str, User.class);
        System.out.println(user);

    }
}
