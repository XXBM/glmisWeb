/**
 * Created by ibs on 17/1/20.
 */
var date1;
var editIndex = undefined;
//格式化日期
function formatterDate(date) {
    var year = date.getFullYear().toString();
    var month = (date.getMonth() + 1);
    var day = date.getDate().toString();
    var da =  year + "-" + month + "-" + day;
    return da;
};
//设置路径
function basePath(){
    var curWwwPath = window.document.location.href;
    var pathName =  window.document.location.pathname;
    var pos = curWwwPath.indexOf(pathName);
    var localhostPaht = curWwwPath.substring(0,pos);
    var projectName = pathName.substring(0,pathName.substr(1).indexOf('/')+1);
    return (localhostPaht + projectName);
}
//表单序列化
function serializeObject(form){
    var o = {};
    var a = $(form).serializeArray();
    $.each(a, function(){
        if (o[this.name]){
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || "");
        } else {
            o[this.name] = this.value || "";
        }
    });
    return o;
};
//更新post
$.postJSON1 = function(url, data) {
    return jQuery.ajax({
        'type' : 'PUT',
        'url' : url,
        'contentType' : 'application/json',
        'data' : JSON.stringify(data),
        'dataType' : 'json',
        success: function (msg) {
            alert("更新成功")
            $('#attendanceSummaryTb').datagrid('reload');
        },
        error:function (msg) {
            alert("更新失败" + msg);
        }
    });
};
//添加update
$.postJSON2 = function(url, data) {
    return jQuery.ajax({
        'type' : 'POST',
        'url' : url,
        'contentType' : 'application/json',
        'data' : JSON.stringify(data),
        'dataType' : 'json',
        success: function (msg) {
            alert("添加成功"+"111")
            $('#attendancetb').datagrid('reload');
            $('#attendancett').datagrid('reload');
        },
        error:function (msg) {
            alert("添加失败" + msg);
        }
    });
};
//删除请求为delete
$.postJSON3 = function(url) {
    return jQuery.ajax({
        'type' : 'DELETE',
        'url' : url,
        success: function (msg) {
            alert("删除成功");
            $('#attendanceSummaryTb').datagrid('reload');
        },
        error:function (msg) {
            alert("删除失败" + msg);
        }
    });
};


//显示界面相应的js代码
//显示表格
$(function(){
    $('#attendanceSummaryTb').datagrid({
        title:'考勤管理',
        iconCls:'icon-edit',
        method:'GET',
        width:660,
        height:350,
        singleSelect:true,
        url:basePath()+'/findAllAttendanceSummary',
        columns:[[
            {field:'id',title:'ID',width:60,hidden:true,id:"id"},
            {field:'date',title:'日期',width:220,align:'center',id:"departmentName"
            },
            {field:'attendanceSummary',title:'出勤情况',width:220,align:'center',id:"attendanceType"
            },
            {field:'action',title:'操作',width:220,align:'center',
                formatter:function(value,row,index){
                    var e = '<a href="#" onclick="editRow(this)">编辑</a> ';
                    var d = '<a href="#" onclick="deleteRow(this)">删除</a>';
                    return e+d;
                }
            },
        ]],
        pagination: "true",//分页
        onBeforeEdit:function(index,row){
            row.editing = true;
            updateActions(index);
        },
        onAfterEdit:function(index,row,value){
            var id = row.id
            var employeeid = row.employee.id;
            var date = row.date;
            var attendanceType = row.attendanceType;
            alert(id+"  "+employeeid+"  "+date+"  "+attendanceType);
            $("#editattendancefm").form("load", row);
            $("#attendancerecordid").val(row.id);
            $('#employeeId').val(row.employee.id);
            $('#attendanceId').val(attendanceType)
            $('#attendancedate').val(row.date);
            var data= serializeObject("#editattendancefm");
            $.postJSON1(basePath()+'/updateAttendanceRecord', data);
            row.editing = false;
            updateActions(index);
        },
        onCancelEdit:function(index,row){
            row.editing = false;
            updateActions(index);
        },
        onLoadSuccess:function(data){
            if(data.total>0)return;
            $('#attendanceSummaryTb').datagrid('appendRow',{
                departmentName: '没有相关记录'
            });
        }

    });
});
//更新行数据
function updateActions(index){
    $('#attendanceSummaryTb').datagrid('updateRow',{
        index: index,
        row:{},
    });
}
//获取行索引
function getRowIndex(target){
    var tr = $(target).closest('tr.datagrid-row');
    return parseInt(tr.attr('datagrid-row-index'));
}
//修改操作
function editRow(target){
    $('#editDlg').dialog('open').dialog('setTitle', '修改考勤');//给表格命名为增加
}
//删除操作
function deleteRow(target){
    $.messager.confirm('Confirm','确定要删除这条信息吗?',function(r){
        if (r){
            //得到当前选中行的记录，
            var row = $('#attendanceSummaryTb').datagrid('getSelected');
            //必须当选中一行时，才可以删除
            $.postJSON3(basePath()+'/deleteAttendanceRecord?id='+row.id);            $('#attendanceSummaryTb').datagrid('reload');
        }else{
            return false;
        }
    });
}
//点击添加按钮弹出,这个添加界面弹出
function addAttendanceSummary() {
    $('#addAttendanceSummaryTb').datagrid('reload');
    $('#addAttendanceSummaryDlg').dialog('open').dialog('setTitle', '增加考勤');//给表格命名为增加
};
//通过日期查询,在显示界面
$(document).ready(function(){
    $("#findCalendar").datebox({
        required:true,
        onSelect: function(date){
            $("#findCalendar").val(date);
            var da =  formatterDate(date);
            var url = basePath()+'/findAttendanceRecordByDate?date='+da;
            $('#attendanceSummaryTb').datagrid('reload',url);
        }
    });
});



//添加考勤记录界面相应的js代码
//结束编辑状态
function endEditing(){
    if(editIndex == undefined){return true}
    if($('#addAttendanceSummaryTb').datagrid('validateRow', editIndex)){
        //使当前行结束编辑状态
        $('#addAttendanceSummaryTb').datagrid('endEdit', editIndex);
        editIndex = undefined;
        return true;
    } else {
        return false;
    }
};
//点击事件,传入参数index表示点击的行的下标
function onClickCell(index, field){
    if (editIndex != index){
        //判断当前是否为编辑状态
        if (endEditing()){
            //将当前点击的行开启编辑状态
            $('#addAttendanceSummaryTb').datagrid('selectRow', index)
                .datagrid('beginEdit', index);
            //获取编辑器, index:本行的下标  field编辑器所在的列
            var ed = $('#addAttendanceSummaryTb').datagrid('getEditor', {index:index,field:'attendanceType'});
            if (ed){
                //判断当前的编辑器是否是textbox类型的,如果是开启文本框的编辑;focus事件就是获得鼠标光标焦点事件
                ($(ed.target).data('textbox') ? $(ed.target).textbox('textbox') : $(ed.target)).focus();
            }
            //记录当前编辑行的下标
            editIndex = index;
        } else {
            //这是用来设定一个时间, 时间到了, 就会执行一个指定的 method,这里好像没有用到
            setTimeout(function(){
                // $('#addAttendanceSummaryTb').datagrid('selectRow', editIndex);
            },0);
        }
    }
};
//结束编辑时调用的方法
function onEndEdit(index, row){
    //获取当前的编辑器
    var ed = $(this).datagrid('getEditor', {index: index, field: 'attendanceType'});
    //是当前正在编辑的编辑器获得鼠标光标
    (ed.target).focus();
    //获取当前选择的下拉框中选项对应的value
    row.attendanceType = $(ed.target).combobox('getValue');
};
function accept(){
    var i=0;
    if (endEditing()){
        var updated = $('#addAttendanceSummaryTb').datagrid('getChanges', "updated");
        var effectRow =new Object();
        while(updated.length){
            effectRow = updated[i];
            if(date1==null){var da1 = new Date();
                date1=formatterDate(da1);
            }
            var attendanceType  = effectRow.attendanceType;
            var id = effectRow.id;
            $("#addattendancefm").form("load", effectRow);
            $('#addattendanceemployeeId').val(id);
            $('#addattendanceId').val(attendanceType)
            $('#addattendancedate').val(date1);
            var data = serializeObject("#addattendancefm");
            date1 = null;
            i++;
            $.postJSON2(basePath()+'/addAttendanceSummary', data);
        }
        alert("after");

        $('#addAttendanceSummaryTb').datagrid('acceptChanges');
    }

};
function reject(){
    $('#addAttendanceSummaryTb').datagrid('rejectChanges');
    editIndex = undefined;
};
function getChanges(){
    var rows = $('#addAttendanceSummaryTb').datagrid('getChanges');
    alert(rows.length+' rows are changed!');
};
