function basePath(){
    var curWwwPath = window.document.location.href;
    var pathName =  window.document.location.pathname;
    var pos = curWwwPath.indexOf(pathName);
    var localhostPaht = curWwwPath.substring(0,pos);
    var projectName = pathName.substring(0,pathName.substr(1).indexOf('/')+1);
    return (localhostPaht + projectName);
}
function show_cur_times(){
    //获取当前日期
    var date_time = new Date();
    //定义星期
    var week;
    //switch判断
    switch (date_time.getDay()){
        case 1: week="星期一"; break;
        case 2: week="星期二"; break;
        case 3: week="星期三"; break;
        case 4: week="星期四"; break;
        case 5: week="星期五"; break;
        case 6: week="星期六"; break;
        default:week="星期天"; break;
    }

    //年
    var year = date_time.getFullYear();
    //判断小于10，前面补0
    if(!year>=10){
        year="0"+year;
    }

    //月
    var month = date_time.getMonth()+1;
    //判断小于10，前面补0
    if(!month>=10){
        month="0"+month;
    }

    //日
    var day = date_time.getDate();
    //判断小于10，前面补0
    if(!day>=10){
        day="0"+day;
    }

    //时
    var hours =date_time.getHours();
    //判断小于10，前面补0
    var haha = "";
    switch (hours){
        case 1: haha="早上好"; break;
        case 2: haha="早上好"; break;
        case 3: haha="早上好"; break;
        case 4: haha="早上好"; break;
        case 5: haha="早上好"; break;
        case 6: haha="早上好"; break;
        case 7: haha="早上好"; break;
        case 8: haha="上午好"; break;
        case 9: haha="上午好"; break;
        case 10: haha="上午好"; break;
        case 11: haha="上午好"; break;
        case 12: haha="上午好"; break;
        case 13: haha="上午好"; break;
        case 14: haha="下午好"; break;
        case 15: haha="下午好"; break;
        case 16: haha="下午好"; break;
        case 17: haha="下午好"; break;
        case 18: haha="下午好"; break;
        case 19: haha="晚上好"; break;
        case 20: haha="晚上好"; break;
        case 21: haha="晚上好"; break;
        case 22: haha="晚上好"; break;
        case 23: haha="晚上好"; break;
        case 24: haha="晚上好"; break;
    }
    if(!hours>=10){
        hours="0"+hours;
    }

    //分
    var minutes =date_time.getMinutes();
    //判断小于10，前面补0
    if(!minutes>=10){
        minutes="0"+minutes;
    }
    //秒
    var seconds=date_time.getSeconds();
    //判断小于10，前面补0
    if(!seconds>=10){
        seconds="0"+seconds;
    }
    //拼接年月日时分秒
    var date_str = year+"年"+month+"月"+day+"日 "+hours+":"+minutes+":"+seconds+" "+week;
    //显示在id为showtimes的容器里

    document.getElementById("showtimes").innerHTML= date_str;
    if(document.getElementById("time")){
        document.getElementById("time").innerHTML= ""+haha;
    }
}
//设置1秒调用一次show_cur_times函数
setInterval("show_cur_times()",100);

$(function(){
    $('#tabs').tabs({
        fit : true,
        border : false,
    });
    $("#nav").tree({
        url:basePath()+'/menu',
        method:'get',
        animate:true,//动画效果Y
        lines:true,//显示一个虚线Y
        //渲染节点
        formatter:function(node){
            //node返回的是每个节点对象
            return node.text;
        },
       // 在数据加载成功以后触发
        onLoadSuccess : function (node, data) {
             if (data) {
                 $(data).each(function (index, value,node) {
                    if (node.state == 'closed') {
                        console.log(node.state);
                        $("#nav").tree("collapseAll");
                    }
                 });
             }
        },
        //点击某一个的时候去执行
        //异步加载，如果想从数据库里获取导航内容，就必须实现一张父类子类的无限极分类表
        //主要有编号id---名称text---状态state---类别tid
        //单击一个节点的时候触发
        onClick : function (node) {
            console.log(node.text);
            if (node.url) {
                if ($('#tabs').tabs('exists', node.text)) {
                     $('#tabs').tabs('select', node.text);
                    var current_tab = $('#tabs').tabs('getSelected');
                    $('#tabs').tabs('update', {
                        tab: current_tab,
                        options: {
                            href:basePath()+node.url,
                        }
                    });
                } else {
                    $('#tabs').tabs('add', {
                        title : node.text,
                         //图标
                        iconCls : node.iconCls,
                        closable : true,//关闭按钮
                        href:basePath()+node.url,
                        toolPosition:'left'
                    });
                }
            }
        },
    });
});
