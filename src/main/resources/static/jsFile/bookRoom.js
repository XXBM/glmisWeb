/**
 * Created by apple on 2016/12/3.
 */
function basePath(){
    var curWwwPath = window.document.location.href;
    var pathName =  window.document.location.pathname;
    var pos = curWwwPath.indexOf(pathName);
    var localhostPaht = curWwwPath.substring(0,pos);
    var projectName = pathName.substring(0,pathName.substr(1).indexOf('1')+1);
    return (localhostPaht + projectName);
}
$(function(){
    //日历
    $('#rp-calendar').calendar({
        width: 300,
        height: 200,
        onSelect:function(date) {
            var nowdate = new Date();
            var nowyear = nowdate.getFullYear().toString();
            var nowmonth = (nowdate.getMonth() + 1);
            var nowday = nowdate.getDate().toString();
            var year = date.getFullYear().toString();
            var month = (date.getMonth() + 1);
            var day = date.getDate().toString();
            if(year==nowyear){
                if(month>=nowmonth){
                    if(day>=nowday){
                        var da =  year + "-" + month + "-" + day;
                        $("#roomFm #roomCal").textbox('setValue',da);  //设置房间借用输入框的值
                        $("#pjtFm #pjtCal").textbox('setValue',da);  //设置投影仪借用输入框的值
                        returnDate(da);
                        //$.postJSON1('/addTime', da)
                    }else {
                        $.messager.alert('Warning','请选择正确的日期');
                    }
                }else {
                    $.messager.alert('Warning','请选择正确的日期');
                }
            }else {
                $.messager.alert('Warning','请选择正确的日期');
            }
        }
    })
    var roomJson=new Array;
    var pjtJson=new Array;
    //预约房间的datagrid属性
    $('#roomDg').datagrid({
        url: basePath() + '/displayRoomInf',
        method:'get',
        fit: true,
        singleSelect: true,
        rowNumbers: true,//显示行号
        pagination: true,//分页
        fitColumns: true,//自适应表格大小
        onLoadSuccess:function(data){
            //eval() 函数可计算某个字符串，并执行其中的的 JavaScript 代码。
            roomJson = eval(data.adresses);
        },
        onClickCell: function (index, field, value) {
            getRoomField(field);
        },

    });
    //预约投影仪的的datagrid属性
    $('#pjtDg').datagrid({
        url:basePath()+'/displayEquipmentInf',
        method:'get',
        fit:true,
        singleSelect: true,
        rowNumbers: true,//显示行号
        pagination: true,//分页
        fitColumns:true,//自适应表格大小
        onLoadSuccess:function(data){
            //eval() 函数可计算某个字符串，并执行其中的的 JavaScript 代码。
            pjtJson = eval(data.equipments);
        },
        onClickCell:function pa(index,field,value){
            getPjtField(field);
        }

    });
    $('#room').panel({
        closed:true,
        closable:true,
        onOpen:function(){
            var ids = new Array();
            var cols = new Array();
            cols.push($.parseJSON('{"field": "id", "title": "","hidden":true}'));
            cols.push($.parseJSON('{"field": "num", "title": "节数", "align": "center"}'));
            cols.push($.parseJSON('{"field": "begin", "title": "开始时间", "align": "center"}'));
            cols.push($.parseJSON('{"field": "end", "title": "结束时间", "align": "center"}'));
            $.each(roomJson, function(q,value) {
                var columns = {};
                columns["title"] = "" + roomJson[q].name;//我这里默认用键值做title，一般可以根据键值自定义
                columns["field"] = "" + roomJson[q].name;//数据的键值就是fieldroomJson[q].id
                columns["align"] = "center";
                columns["formatter"] = roomFormatContactUrl;
                cols.push(columns);//放入定义数组
                ids[roomJson[q].name]=roomJson[q].id
            });
            $("#roomDg").datagrid({columns: [cols]});
            getRoomIds(ids);
        }
    })
    $('#projector').panel({
        closed:true,
        closable:true,
        onOpen:function(){
            var ids = new Array();
            var pcols = new Array();
            pcols.push($.parseJSON('{"field": "id", "title": "","hidden":true}'));
            pcols.push($.parseJSON('{"field": "begin", "title": "开始时间", "align": "center"}'));
            pcols.push($.parseJSON('{"field": "end", "title": "结束时间", "align": "center"}'));
            $.each(pjtJson, function(q,value) {
                var pcolumns = {};
                pcolumns["title"] = "" + pjtJson[q].name;//我这里默认用键值做title，一般可以根据键值自定义
                pcolumns["field"] = "" + pjtJson[q].name;//数据的键值就是field
                pcolumns["align"] = "center";
                pcolumns["formatter"] = pjtFormatContactUrl;
                pcols.push(pcolumns);//放入定义数组
                ids[pjtJson[q].name]=pjtJson[q].id;
            });
            $("#pjtDg").datagrid({columns: [pcols]});
            getPjtIds(ids);
        }
    })
    //分页用的获取datagrid中的详细内容，后台只需要获取easyui自动提供pages和rows（我好像需要一个rows）即可。
    //房间表格分页
    var roomPager = $('#roomDg').datagrid('getPager');
    $(roomPager).pagination({
        showPageList:false,
        beforePageText: '第',//页数文本框前显示的汉字
        afterPageText: '页    共 {pages} 页',
        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录',
        onBeforefresh:function () {
            $(this).pagination('loading');
            alert('before refresh');
            $(this).pagination('loaded');

        }
    });
    //投影仪分页
    var pjtPager = $('#pjtDg').datagrid('getPager');
    $(pjtPager).pagination({
        showPageList:false,
        beforePageText: '第',//页数文本框前显示的汉字
        afterPageText: '页    共 {pages} 页',
        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录',
        onBeforefresh:function () {
            $(this).pagination('loading');
            alert('before refresh');
            $(this).pagination('loaded');
        }
    });
});
//控制如果用户不选择日期，无法点击借用
var chooseDate ;
function returnDate(da){
    chooseDate = da;
}
function roomCheck(){
    if(chooseDate==null){
        $.messager.alert('Warning','请选择借用日期');
    }else{
        $('#room').panel ('open');
    }
}
var roomIds;
function getRoomIds(ids) {
    roomIds=ids;
}
var pjtIds;
function getPjtIds(ids) {
    pjtIds=ids;
}
var timeId;
function roomId(id){
    timeId = id;
}
var ptimeId;
function pjtId(id){
    ptimeId = id;
}
var roomField;
function getRoomField(field){
    roomField=field;
}
var pjtField;
function getPjtField(field){
    pjtField=field;
}
//保存完之后的显示
var roomRowIndex;
function getRoomIndex(index){
    roomRowIndex = index;
}
var pjtRowIndex;
function getPjtIndex(index){
    pjtRowIndex = index;
}

function projectorCheck(){
    if(chooseDate==null){
        $.messager.alert('Warning','请选择借用日期');
    }else{
        $('#projector').panel ('open');
    }
}
//房间借用
function roomFormatContactUrl(val,rowData,rowIndex){
    if(val==null) {
        return '<a href="javascript:roomBorrow()">' + '点击借用' + '</a>';
    }
    if(val!=null) {
        return '<p>' + val + '</p>';
    }
}
function roomBorrow(){
    var row = $('#roomDg').datagrid('getSelected');
    var index = $('#roomDg').datagrid('getRowIndex',row);
    col = $('#roomDg').datagrid( "getColumnOption" , roomField);
    $("#roomClass").textbox('setValue',col.title);  //设置输入框的值
    var time = row.begin + "-"+row.end;
    roomId(row.id);
    $("#roomDate").textbox('setValue',time);
    if (row){
        $('#roomDlg').dialog('open').dialog('setTitle','预约信息');
        $('#roomFm').form('load',row);
    }
    //保存完之后显示
    var index = $('#roomDg').datagrid('getRowIndex',row);
    getRoomIndex(index);
}
/*projector弹出框和数据表格链接*/
function pjtFormatContactUrl(val,row,index){
    if(val==null) {
        return '<a href="javascript:pjtBorrow()">' + '点击借用' + '</a>';
    }
    if(val!=null) {
        return '<p>' + val + '</p>';
    }
}
function pjtBorrow(){
    var row = $('#pjtDg').datagrid('getSelected');
    col = $('#pjtDg').datagrid( "getColumnOption" , pjtField);
    $("#pjt").textbox('setValue',col.title);  //设置输入框的值
    var time = row.begin + "-"+row.end;
    pjtId(row.id);
    $("#pjtDate").textbox('setValue',time);
    if (row){
        $('#pjtDlg').dialog('open').dialog('setTitle','预约信息');
        $('#pjtFm').form('load',row);
    }
    //保存完之后显示
    var index = $('#pjtDg').datagrid('getRowIndex',row);
    getPjtIndex(index);
}
//表单提交
//获取form中的全部文本文本框内容，文本框名字要相同
function packObject(form) {
    var o = {};
    var a = $(form).serializeArray();
    $.each(a, function () {
        if (o[this.name]) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || "");
        } else {
            o[this.name] = this.value || "";
        }
    });
    return o;
}
//房间借用和投影仪借用弹出窗的表单提交
function submit() {
    var txt = packObject("#roomFm");
    delete txt['bookingEquipments'];
    txt['time']=timeId;
    txt['bookingAdress']=roomIds[roomField];
    alert(txt['day']);
    saveAppointment(txt);
}

function psubmit() {
    var txt = packObject("#pjtFm");
    delete txt['bookingAdress'];
    txt['time']=ptimeId;
    txt['bookingEquipments']=pjtIds[pjtField];
    alert(txt['day']);
    saveAppointment(txt);
}
//真正提交执行方法
function saveAppointment(txt){
    $.post('/saveAppointment', txt)
}
//url向后台传送方法
$.post = function (url, data) {
    return jQuery.ajax({//用Ajax的方法来传递
        'type' : 'POST',
        'url' : url,
        'contentType' : 'application/json',
        'data' : JSON.stringify(data),
        'dataType' : 'json',
        error: function () {
            alert("提交成功");
            $('#roomDlg').dialog('close');
            $('#pjtDlg').dialog('close');
            //保存之后显示“以借用”
            var roomRows = $('#roomDg').datagrid("getRows");
            var roomRow =  roomRows[roomRowIndex];
            var param = {index:roomRowIndex,row:{"id":roomRow.id,"num":roomRow.num,"begin":roomRow.begin,"end":roomRow.end}};
            $(param.row).attr(roomField, "已经借用");
            $('#roomDg').datagrid('updateRow',param);

            var pjtRows = $('#pjtDg').datagrid("getRows");
            var pjtRow =  pjtRows[pjtRowIndex];
            var pjtParam = {index:roomRowIndex,row:{"id":pjtRow.id,"begin":pjtRow.begin,"end":pjtRow.end}};
            $(pjtParam.row).attr(pjtField, "已经借用");
            $('#pjtDg').datagrid('updateRow',param);
        }
    });
}

