package com.atguigu.cookies_kaptcha.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @Author: 刘华昌
 * @DATE: 2022/7/17 19:54 星期日
 * @Operating:
 * @Description:
 */
@WebServlet("/kaptcha01")
public class KaptchaServletDemo01 extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //1.先让 客户端访问 http://localhost:8080/pro26/hello02.html,
        //1) hello02.html中的 <img src="kaptcha.jpg"/> 通过web.xml的配置关联 执行 KaptchaServlet
        //   （KaptchaServlet为 kaptcha-2.3.2.jar包中的：com.google.code.kaptcha.servlet.KaptchaServlet）
        //2) 在xml文件中 可以设置验证码图片的相关属性 （kaptcha验证码图片的各个属性在 处理接口Constants中）
        //3) KaptchaServlet 会生成验证码图片，并将验证码信息保存到session中， 我们可以到session中获取这个验证码的值


        //2.再让 客户端 访问http://localhost:8080/pro26/kaptcha01 执行 KaptchaServletDemo01
        //1）获取session
        HttpSession session = request.getSession();
        //2）获取验证码 ( KaptchaServlet生成并保存到session里面的验证码 )
        Object obj = session.getAttribute("KAPTCHA_SESSION_KEY");
        //3）打印验证码
        System.out.println(obj);

        //3. 测试通过，我们可以将验证码应用与 书城项目的注册用户请求时
        // (将用户文本框中输入的验证码值和session中保存的值进行比较，相等，则进行注册)

    }
}
