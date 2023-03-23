//1.当cart.html页面加载时执行方法
window.onload = function () {
    //2.创建vue
    var vue = new Vue({
        //将该vue绑定 id为 cart_div 的标签
        el: "#cart_div",
        data: {
            cart: {}
        },
        methods: {
            //这个是下面 mounted数据渲染时 自动调用的方法(加载购物车信息)
            getCart: function () {
                //4.向 cartController 发送POST异步请求 执行 cartInfo()
                axios({
                    method: "POST",
                    url: "cart.do",
                    params: {
                        operate: 'cartInfo'
                    }
                })
                    .then(function (value) {    //服务器端成功响应时 接收 服务器端响应回来的数据 ( value就是接收到的整个响应体 )
                        var cart = value.data;      //1)我们通过 响应体中的data属性获取 响应体数据
                        vue.cart = cart;            //2)在将 获取的数据 赋值给 vue对象的 cart属性
                                                    //  这样在cart.html页面 就可以直接使用这些数据了(以前是通过给服务器端发请求,从session中获取这些数据)

                    })
                    .catch(function (reason) {      //服务器端响应报错时

                    })
            },
            //这个是购物车 + - 按钮点击事件调用的方法
            editCart: function (cartItemId, buyCount) {
                axios({
                    method: "POST",
                    url: "cart.do",
                    params: {
                        operate: 'editCart',
                        cartItemId: cartItemId,
                        buyCount: buyCount
                    }
                })
                    .then(function (value) {     //服务器端成功响应
                        vue.getCart();           //调用一下getCart(),重新异步请求 一次更新购物车

                    })
                    .catch(function (reason) {
                    })
            }
        },
        //3.当数据渲染的时候,执行这个匿名函数
        mounted: function () {
            //调用getCart()
            this.getCart();
        }

    })
};