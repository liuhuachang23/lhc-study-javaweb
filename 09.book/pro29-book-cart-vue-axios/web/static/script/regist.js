//返回指定id的标签
function $(id) {
    return document.getElementById(id)
}

//注册格式校验函数
function preRegist() {
    //两种方式获取指定id的标签
    //1）BOM方式：document.forms[0].uname
    //2）DOM方式：var unameTxt = document.getElementById("unameTxt");

    //校验 用户名 是否符合格式
    //1）我们使用方式2：调用$(id)方法 获取用户名标签（id为unameTxt 的input标签）
    var unameTxt = $("unameTxt").value;
    //2）获取标签value
    var uname = unameTxt.value;
    //3）根据要求编写正则表达式：用户名应为6~16位数组和字母组成
    var unameReg = /[/w]{8,}/;
    //4）获取用于 错误提示的span标签
    var unameSpan = $("unameSpan");
    //4）比较 value和正则表达式
    if (!unameReg.test(uname)) {
        //校验不成功就将 修改错误提示的span标签 让它显示出来
        unameSpan.style.visibility = "visible";
        //并且 让表单 onsubmit="return false" 即阻止提交
        return false;
    } else {
        //验证成功 就隐藏span标签
        unameSpan.style.visibility = "hidden";
    }

    //2. 校验密码是否符号相应格式
    var pwd = $("pwdTxt").value;
    var pwdReg = /[0-9]{8,}$/;
    var pwdSpan = $("pwdSpan");
    if (!pwdReg.test(pwd)) {
        pwdSpan.style.visibility = "visible";
        return false;
    } else {
        pwdSpan.style.visibility = "hidden";
    }

    //3. 验证再次输入的密码和第一次 一致
    var pwd2 = $("pwdTxt2").value;
    var pwdSpan2 = $("pwdSpan2");
    if (pwd2 != pwd) {
        pwdSpan2.style.visibility = "visible";
        return false;
    } else {
        pwdSpan2.style.visibility = "hidden";
    }

    //4. 校验邮箱
    var email = $("emailTxt").value;
    var emailReg = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
    var emailSpan = $("emailSpan");
    if (!emailReg.test(email)) {
        emailSpan.style.visibility = "visible";
        return false;
    } else {
        emailSpan.style.visibility = "hidden";
    }
    //验证全部通过就返回true 即将 onsubmit="return preRegist()" 改为 onsubmit="return true"
    return true;
}

//=======================异步请求======================
function ckUname(uname) {
    //1.如果需要发送异步请求， 首先创建xmlHttpRequest对象（在createXMLHttpRequest函数内操作）
    createXMLHttpRequest();
    //2. open (发送给 服务器端 被UserController拦截，执行ckUname(uname) )
    var url = "user.do?operate=ckUname&uname=" + uname;
    xmlHttpRequest.open("GET", url, true);
    //3. 设置回调 (在ckUnameCB函数内操作)
    xmlHttpRequest.onreadystatechange = ckUnameCB;
    //发送请求
    xmlHttpRequest.send();
}

//创建XMLHttpRequest对象 函数
var xmlHttpRequest;

function createXMLHttpRequest() {
    if (window.XMLHttpRequest) { //1）符合DOM2标准的浏览器，xmlHttpRequest的创建方式
        xmlHttpRequest = new XMLHttpRequest();
    } else if (window.ActiveXObject) { //2）IE浏览器 创建方式
        try {
            xmlHttpRequest = new ActiveXObject("Microsoft.XMLHTTP");
        } catch (e) {
            xmlHttpRequest = new ActiveXObject("Msxml2.XMLHTTP");
        }
    }
}

//回调 函数 （就是接收服务器端，在DispatcherServlet 视图处理后 在PrintWriter流中写入的内容）
function ckUnameCB() {
    //判断xmlHttpRequest对象的状态
    if (xmlHttpRequest.readyState == 4 && xmlHttpRequest.status == 200) {
        //xmlHttpRequest.responseText 表示服务器端响应给我的文本
        //如果客户端输入的用户名不存在就是 {'uname':'0'} 已存在就是 {'uname':'1'}
        var responseText = xmlHttpRequest.responseText;
        //解析 responseText
        if (responseText=="{'uname':'1'}"){
            alert("用户名已被注册！")
        } else {
            alert("用户名可以被注册！")

        }
    }
}