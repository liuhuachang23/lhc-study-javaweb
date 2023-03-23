/*
//动态注册事件
window.onload = function () {
    //需求1：设置 鼠标悬停和离开---------------------------------------------------

    //根据标签名 获取指定标签名 的对象集合
    var trs = document.getElementsByTagName("tr");

    for (var i = 0; i < trs.length; i++) {

        // 定义一个变量来保存修改这个class属性值之前的旧的样式名
        var oldClass = "";

        //通过得到的 标签对象.事件名 = function(){}
        //onmouseover 为鼠标悬停事件
        trs[i].onmouseover = function () {
            //调用不同的 class对象
            oldClass = this.className;
            this.className = "over"
        };
        // onmouseout 为当鼠标离开事件
        trs[i].onmouseout = function () {
            this.className = oldClass;
        };
    }
};
*/

//window表示这个页面
//onload 表示 加载完成事件
//window.onload=function(){} 表示当页面加载完之后执行{}内的东西，常用于动态注册事件

//动态注册事件
window.onload = function () {

    //根据id 获取指定标签名 的对象集合 （通过table标签的id 获取table对象）
    var tableObj = $("tbl_fruit");
    //获取表格中的所有行(tr)
    var rows = tableObj.rows;
    for (var i = 0; i < rows.length - 1; i++) {
        var tr = rows[i];

        //我们将tr需要绑定的事件封装到该方法
        trBindEvent(tr);
    }
    //10.绑定添加行的点击事件
    //获取添加按钮结点
    var addObj = $("addBtn");
    //绑定点击事件
    addObj.onclick = addFruit;

    //11.重置按钮点击事件
    var revObj = $("revBtn");
    //绑定点击事件
    revObj.onclick = revAddTbl;
};

//我们把tr 或者 td 绑定的事件放到这个方法
function trBindEvent(tr) {
    //1.绑定鼠标悬浮事件(表格行变背景颜色)
    tr.onmouseover = showBGColor;
    //2.绑定鼠标离开事件(表格行变背景颜色)
    tr.onmouseout = clearBGColor;

    //获取改行的所有列（td）
    var cells = tr.cells;
    //得到单价列
    var priceTD = cells[1];

    //3.绑定鼠标悬浮事件(光标变小手)
    priceTD.onmouseover = showHand;

    //4.绑定鼠标点击单价单元格事件()
    priceTD.onclick = editPrice;

    //9.绑定删除小图标的点击事件
    //cells[4] 为改行中第5列（操作列）
    //firstChild 该列中的第一个结点(img标签结点)
    var imgObj = cells[4].firstChild;
    if (imgObj && imgObj.tagName == 'IMG') {
        //绑定单击事件
        imgObj.onclick = delFruit;
    }
}


//通过id 获取标签对象
function $(id) {
    return document.getElementById(id)
}

//当鼠标悬浮 更新行的背景颜色及字体
function showBGColor() {

    //event   //当前发生的事件 [object MouseEvent]
    //event.srcElement    //事件源(事件标签对象) [object HTMLTableCellElement]
    //event.srcElement.tagName    //事件源的标签名称：TD

    //如果 事件存在 && 事件源存在 && 事件源的标签名称为TD
    if (event && event.srcElement && event.srcElement.tagName == "TD") {
        //获取td标签对象
        var td = event.srcElement;
        //获取tr标签对象（ 标签对象.parentElement 获取当前结点的父结点  (标签对象也称为结点) ）
        var tr = td.parentElement;

        //设置鼠标悬停时 tr内的样式改变：
        //方式一：标签对象.style.属性="属性值"
        tr.style.backgroundColor = "darkgrey";
        //方式二：标签对象.className="类选择器的名称"
        //tr.className="over";

        //获取tr标签中的所有单元格
        var tds = tr.cells;
        for (var i = 0; i < tds.length; i++) {
            //标签对象.style.属性="属性值"  设置标签对象的属性
            tds[i].style.color = "yellow";
        }
    }
}

//当鼠标移开时 恢复背景颜色和字体
function clearBGColor() {
    //如果 事件存在 && 事件源存在 && 事件源的标签名称为TD
    if (event && event.srcElement && event.srcElement.tagName == "TD") {
        //获取td标签对象
        var td = event.srcElement;
        //获取tr标签对象（ 标签对象.parentElement 获取当前结点的父结点  (标签对象也称为结点) ）
        var tr = td.parentElement;

        //设置鼠标悬停时 tr内的样式改变：
        //方式一：标签对象.style.属性="属性值"
        tr.style.backgroundColor = "whitesmoke";

        //获取tr标签中的所有单元格
        var tds = tr.cells;
        for (var i = 0; i < tds.length; i++) {
            //标签对象.style.属性="属性值"  设置标签对象的属性
            tds[i].style.color = "dimgrey";
        }
    }
}

//当鼠标悬浮到单价单元格时 更新光标
function showHand() {
    if (event && event.srcElement && event.srcElement.tagName == "TD") {
        var priceTD = event.srcElement;
        //设置td的cursor属性(鼠标的光标) = 手型
        priceTD.style.cursor = "hand";
    }
}

//当鼠标点击单价单元格
function editPrice() {
    if (event && event.srcElement && event.srcElement.tagName == "TD") {
        var priceTD = event.srcElement;

        //结点.nodeType 可以获取该结点类型 如果是TextNode文本结点就等于3 如果是ElementNode元素结点就为1
        //这个if 是用来判断 priceTD存在子节点 而且该第一个子节点为文本结点
        if (priceTD.firstChild && priceTD.firstChild.nodeType == 3) {
            //标签对象.innerText 可以读写 标签之间(开始标签和结束标签之间)的文本
            //这里是读取 单价文本框中原本的文本(价格)
            var oldPrice = priceTD.innerText;

            //标签对象.innerHTML 可以读写 标签之间(开始标签和结束标签之间)的内容
            //<input type='text'> 将文本框 写入标签之间
            priceTD.innerHTML = "<input type='text' size='1'>";

            //标签对象.firstChild 获取当前节点的第一个子节点(input标签对象)
            var inputObj = priceTD.firstChild;
            if (inputObj && inputObj.tagName == 'INPUT') {
                //将原本的价格填入文本框中
                inputObj.value = oldPrice;
                //选中输入框内部的文本
                inputObj.select();

                //上面就可以完成修改 对单价的修改了，下面就要实现 修改后鼠标点击别的地方 就会将修改的单价填入单元格中，并且更新小计和总计
                //5.绑定输入框失去焦点事件（将修改的单价填入单元格中）
                inputObj.onblur = updatePrice;

                //8. 在输入框上 绑定键盘摁下的事件（保证用户输入的是数字）
                inputObj.onkeydown = ckInput;
            }
        }
    }
}

//更新价格方法
function updatePrice() {
    if (event && event.srcElement && event.srcElement.tagName == "INPUT") {
        var inputObj = event.srcElement;
        var newPrice = inputObj.value;

        //得到input结点的父节点td 将上面的新价格更新到父节点内
        var priceTD = inputObj.parentElement;
        priceTD.innerText = newPrice;

        //6.调用 更新当前行的小计 方法
        updateXJ(priceTD.parentElement);
    }
}

//更新小计方法
function updateXJ(tr) {
    if (tr && tr.tagName == "TR") {
        var tds = tr.cells;

        //获取该行的单价
        var price = tds[1].innerText;
        //获取该行的数量
        var count = tds[2].innerText;
        //修改改行的小计
        // parseInt(var) 将字符串 转为int
        tds[3].innerText = parseInt(price) * parseInt(count);

        //7.调用 更新总计 方法
        updateZJ();
    }
}

//更新总计方法
function updateZJ() {
    //先获取table结点
    var tableObj = document.getElementById("tbl_fruit");
    //获取所有行
    var rows = tableObj.rows;
    var sum = 0;
    //统计所有小计
    for (var i = 1; i < rows.length - 1; i++) {

        sum += parseInt(rows[i].cells[3].innerText);
    }

    //更新总计
    rows[rows.length - 1].cells[1].innerText = sum;
}

//检验键盘摁下的值 方法
function ckInput() {

    //keyCode 可以获取 键盘上的输入 对应的 ASCII码
    //比如 0~9 --> 48~57 , backspace(空格键) --> 8 , enter(回车键) --> 13
    var kc = event.keyCode;
    //在浏览器控制台上打印kc
    //console.log(kc);

    if (!(kc >= 48 && kc <= 57 || kc == 8 || kc == 13)) {
        //不符合上面的键盘输入
        //就取消 数据源上之前发生的操作（不允许修该）
        event.returnValue = false;
    }

    //设置回车就代表失去焦点 ，即回车就可以确认修改单价了
    if (kc == 13) {
        event.srcElement.blur();
    }

}

//删除水果方法
function delFruit() {
    if (event && event.srcElement && event.srcElement.tagName == "IMG") {

        //confirm 表示弹出一个对话框，有确定和取消按钮，点击确定返回true，点击取消返回false
        //和之前使用到的 alert有点相似，但alert只有确定按钮
        //window.confirm() 表示页面会弹出
        if (window.confirm("是否确认删除当前库存记录")) {

            //获取img结点
            var imgObj = event.srcElement;
            //img的爷爷结点 tr结点
            var tr = imgObj.parentElement.parentElement;
            //获取table结点
            var tableObj = document.getElementById("tbl_fruit");
            //tableObj.deleteRow(删除行的下标) 删除table中的指定下标行
            //tr.rowIndex 获取指定行的下标
            tableObj.deleteRow(tr.rowIndex);

            //删除之后 还要更新总计
            updateZJ();

        }
    }
}

//添加水果方法
function addFruit() {

    //获取 添加的水果名称、单价、数量
    var fnameObj = $("fname");
    var fname = fnameObj.value;
    var priceObj = $("price");
    var price = parseInt(priceObj.value);
    var fcountObj = $("fcount");
    var fcount = parseInt(fcountObj.value);

    //tableObj.insertRow(index) 在表中在某一行中下插入一行
    var tblFruit = $("tbl_fruit");
    var tr = tblFruit.insertRow(tblFruit.rows.length - 1);

    //tr.insertCell([index]) 在行中插入列，索引可以不写 表示在行中依次插入列
    //名称
    var fnameTD = tr.insertCell();
    fnameTD.innerText = fname;
    //价格
    var priceTD = tr.insertCell();
    priceTD.innerText = price;
    //数量
    var FcountTD = tr.insertCell();
    FcountTD.innerText = fcount;
    //小计
    var xjTD = tr.insertCell();
    xjTD.innerText = price * fcount;
    //操作
    var imgTD = tr.insertCell();
    imgTD.innerHTML = "<img class='deImg' src='../img/del.webp'> ";

    //更新总计
    updateZJ();

    //让新增的tr 也绑定我们之前的所有事件
    trBindEvent(tr);
}

//重置 添加水果的表格 方法
function revAddTbl() {
    //获取 添加的水果名称、单价、数量
    var fnameObj = $("fname");
    fnameObj.value = "";
    var priceObj = $("price");
    priceObj.value = "";
    var fcountObj = $("fcount");
    fcountObj.value= "";
}