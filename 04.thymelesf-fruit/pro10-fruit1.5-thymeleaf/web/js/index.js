function delFruit(fid) {
    if (confirm('是否确认删除')){
        /*
        window表示这个html页面，location表示页面上的地址栏，href属性='del.do?fid='+fid 就表示给这个地址栏赋值
        整个点击事件就是：点击这个img标签，就会触发点击事件，确认是否删除，确认删除后就会给DelServlet发送请求，并附带该水果的fid值
        */
        window.location.href='del.do?fid='+fid;
    }
}