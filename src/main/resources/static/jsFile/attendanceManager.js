/**
 * Created by dell on 2017-05-25 .
 */
var summaryId = 1;
var candidateId = undefined;
//定义全局变量（编辑行/插入行/行数据）
var editRow = undefined;
var beingInsertedRow = undefined;
var rowData = undefined;
//获取行
function getRow(dat) {
    var rows = $(dat).datagrid('getRows'); // get current page rows
    rowData = rows[editRow]; // your row data
}
//AttendanceSummary表格
$(function () {
    $('#AttendanceSummary_table').datagrid({
        method: 'get',
        title: '考勤列表',
        url: basePath() + '/displayAllAttendanceSummary',
        width: 300,
        height: 480,
        singleSelect: true,
        rowNumbers: "true",//显示行号
        remoteSort: false,//对本地数据进行排序
        fitColumns: true,//自适应表格大小
        toolbar: '#AttendanceSummaryToolbar',
        columns: [[
            {field: 'id', title: 'id', hidden: true},
            {field: 'attendanceName', title: '考勤名称', width: 80, align: 'center'},
            {
                field: 'attendanceManager', title: '考勤人', width: 80, align: 'center',
                formatter: function (value, rec) {
                    if (rec.attendanceManager == null) {
                        return "未选择";
                    } else {
                        return rec.attendanceManager.name;
                    }
                }
            },
        ]],


        pagination: "true",//分页
        onClickRow: function (rowIndex, rowData) {
            summaryId = rowData.id;
            var candidateUrl =basePath()+'/displayEmpBySum?id='+rowData.id;
            $('#candidate_table').datagrid('reload',candidateUrl);
            var presenceUrl = basePath() + '/displayPresenceBySummary?id=' + rowData.id;
            $('#presence_table').datagrid('reload', presenceUrl);
            var businessLeaveUrl = basePath() + '/displayBusinessLeaveBySummary?id=' + rowData.id;
            $('#businessLeave_table').datagrid('reload', businessLeaveUrl);
            var neglectWorkUrl = basePath() + '/displayNeglectWorkBySummary?id=' + rowData.id;
            $('#neglectWork_table').datagrid('reload', neglectWorkUrl);
            var privateLeaveUrl = basePath() + '/displayPrivateLeaveBySummary?id=' + rowData.id;
            $('#privateLeave_table').datagrid('reload', privateLeaveUrl);
            var universityAbsenceUrl = basePath() + '/displayUniversityAbsenceBySummary?id=' + rowData.id;
            $('#universityAbsence_table').datagrid('reload', universityAbsenceUrl);
        },
        onLoadSuccess: function (data) {
        },
    });
    //分页用的获取datagrid中的详细内容，后台只需要获取easyui自动提供pages和rows（我好像需要一个rows）即可。
    var p = $('#AttendanceSummary_table').datagrid('getPager');
    $(p).pagination({
        onBeforefresh: function () {
            $(this).pagination('loading');
            alert('before refresh');
            $(this).pagination('loaded');
        }
    });
});
//查询summary
function searchAttendanceSummary() {
    var startTime = $('#AttendanceSummary_startTime_datebox').datebox('getValue');
    var endTime = $('#AttendanceSummary_endTime_datebox').datebox('getValue');
    var url = basePath() + '/findSummary?startTime=' + startTime
        + '&;endTime=' + endTime;
    $('#AttendanceSummary_table').datagrid('reload', url);
};
//候选人
$(function () {
    $('#candidate_table').datagrid({
        method: 'get',
        width: 700,
        height: 480,
        singleSelect: true,
        rowNumbers: true,//显示行号
        remoteSort: false,//对本地数据进行排序
        //fitColumns: true,//自适应表格大小
        columns: [[
            {field: 'id', title: 'id', hidden: true},
            {
                field: 'name', title: '姓名', width: 150, align: 'center',},
            {
                field: 'no', title: '工号', width: 150, align: 'center',},
        ]],
        pagination: "true",//分页
        onClickRow: function (rowIndex, rowData) {
            candidateId = rowData.id;
            var descriptionUrl =basePath()+'/displayAttByCan?candidateId='+candidateId+'&;summaryId='+summaryId;
            $('#description_table').datagrid('reload',descriptionUrl);
        },
        onLoadSuccess: function (data) {
        },
    });
        //分页用的获取datagrid中的详细内容，后台只需要获取easyui自动提供pages和rows（我好像需要一个rows）即可。
    var p = $('#candidate_table').datagrid('getPager');
    $(p).pagination({
        showPageList: false,
        beforePageText: '第',//页数文本框前显示的汉字
        afterPageText: '页    共 {pages} 页',
        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录',
        onBeforefresh: function () {
            $(this).pagination('loading');
            alert('before refresh');
            $(this).pagination('loaded');
        }
    });
});
//考勤状态
$(function () {
    $('#description_table').datagrid({
        method: 'get',
        width: 150,
        height: 500,
        singleSelect: true,
        rowNumbers: true,//显示行号
        remoteSort: false,//对本地数据进行排序
        //fitColumns: true,//自适应表格大小
        columns: [[
            {
                field: 'description', title: '考勤状态', width: 150, align: 'center',
                formatter: function (value, rec) {
                    if (rec.presenceDescription)
                        return rec.presenceDescription.description;
                    if (rec.universityAbsenceDescription)
                        return rec.universityAbsenceDescription.description;
                    if (rec.privateLeaveDescription)
                        return rec.privateLeaveDescription.description;
                    if (rec.businessLeaveDescription)
                        return rec.businessLeaveDescription.description;
                    if (rec.neglectWorkDescription)
                        return rec.neglectWorkDescription.description;
                }
            }
        ]],
        pagination: "true",//分页
        onClickRow: function (rowIndex, rowData) {
        },
        onLoadSuccess: function (data) {
        },
    });
    //分页用的获取datagrid中的详细内容，后台只需要获取easyui自动提供pages和rows（我好像需要一个rows）即可。
    var p = $('#description_table').datagrid('getPager');
    $(p).pagination({
        onBeforefresh: function () {
            $(this).pagination('loading');
            alert('before refresh');
            $(this).pagination('loaded');
        }
    });
});
//出勤
$(function () {
    $('#presence_table').datagrid({
        method: 'get',
        width: 700,
        height: 480,
        singleSelect: true,
        rowNumbers: true,//显示行号
        remoteSort: false,//对本地数据进行排序
        fitColumns: true,//自适应表格大小
        columns: [[
            {field: 'id', title: 'id', hidden: true},
            {
                field: 'employee', title: '姓名', width: 120, align: 'center',
                formatter: function (value, rec) {
                    if (rec.employee == null) {
                        return "";
                    }else{
                        return rec.employee.name;
                    }
                },
                editor: {
                    type: 'combobox',
                    options: {
                        method: 'get',
                        url: basePath() + '/displaySpecificEmployee?',
                        valueField: 'id',
                        textField: 'name',
                        //data:suit_id,
                        panelHeight: 'auto',
                        required: true,
                        onBeforeLoad:function(param){
                            var summary =$('#AttendanceSummary_table').datagrid('getSelected');
                            param.id=summary.id;
                        },
                        onLoadSuccess: function (data) {
                            getRow('#presence_table');
                            console.log(rowData);
                            if (data){
                                if (rowData.employee == null) {//如果没有值,默认选中第一个(相当于添加状态)
                                    if(data[0]==null){
                                        $.messager.alert('消息提示', '候选人已经有考勤状态', 'info');
                                        $("#presence_table").datagrid('reload');
                                        editRow = undefined;
                                        beingInsertedRow = undefined;
                                    }else{
                                        $(this).combobox('setValue', data[0].id);
                                    }
                                } else {//相当于编辑状态
                                    $(this).combobox('setValue', rowData.employee.name);
                                }
                            }
                        },
                    }
                },
            },
            {
                field: 'employeeNo', title: '工号', width: 120, align: 'center',
                formatter: function (value, rec) {
                    if (rec.employee == null) {
                        return "";
                    }
                    else {
                        return rec.employee.no;
                    }
                }
            },
            {
                field: 'presenceDescription', title: '考勤状态', width: 120, align: 'center',
                formatter: function (value, rec) {
                    if (rec.presenceDescription == null) {
                        return "";
                    }
                    else {
                        return rec.presenceDescription.description;
                    }
                },
                editor: {
                    type: 'combobox',
                    options: {
                        method: 'get',
                        url: basePath() + '/findAllPresenceDescriptions',
                        valueField: 'id',
                        textField: 'description',
                        panelHeight: 'auto',
                        required: true,
                        onLoadSuccess: function (data) {
                            getRow('#presence_table');
                            if (data) {
                                if (rowData.presenceDescription == null) {//如果没有值,默认选中第一个(相当于添加状态)
                                    $(this).combobox('setValue', data[0].id);
                                } else {//相当于编辑状态
                                    $(this).combobox('setValue', rowData.presenceDescription.id);
                                }
                            }
                        },
                    }
                },
            },
        ]],
        toolbar: [{
            id: 'Presence_add_btn',
            text: '添加', iconCls: 'icon-add',
            handler: function () {
                addOpe("#presence_table");
            }
        }, '-', {
            id: 'Presence_delete_btn',
            text: '删除', iconCls: 'icon-remove',
            handler: function () {
                deleteOpe('#presence_table', basePath() + '/deletePresence?ids=');
            }
        }, '-', {
            id: 'Presence_update_btn',
            text: '修改', iconCls: 'icon-edit',
            handler: function () {
                updateOpe("#presence_table");
            }
        }, '-', {
            id: 'Presence_save_btn',
            text: '保存', iconCls: 'icon-save',
            handler: function () {
                saveOpe("#presence_table");
            }
        }, '-', {
            id: 'Presence_cancel_btn',
            text: '撤销', iconCls: 'icon-redo',
            handler: function () {
                cancelOpe('#presence_table');
            }
        }],
        //endEdit触发后
        onAfterEdit: function (rowIndex, rowData, changes) {//rowIndex：编辑行的索引，从 0 开始;rowData：编辑行对应的记录;changes：更改的字段/值对
            endEditOpe(rowData, basePath() + '/addPresence', basePath() + '/updatePresence', '#presence_table');
        },
        onCancelEdit: function (rowIndex, rowData) {
            editRow = undefined;
        },
        //双击一行
        onDblClickRow: function (rowIndex, rowData) {
            if (editRow != undefined) {
                $("#presence_table").datagrid('endEdit', editRow);
            } else {
                dblClickOpe(rowData, rowIndex, "#presence_table");
            }
        },
        pagination: "true",//分页
        onClickRow: function (rowIndex, rowData) {
        },
        onLoadSuccess: function (data) {
        },
    });
    //分页用的获取datagrid中的详细内容，后台只需要获取easyui自动提供pages和rows（我好像需要一个rows）即可。
    var p = $('#presence_table').datagrid('getPager');
    $(p).pagination({
        onBeforefresh: function () {
            $(this).pagination('loading');
            alert('before refresh');
            $(this).pagination('loaded');
        }
    });
});
// window.suit_id = synchroAjaxByUrl(basePath()+'/displaySpecificEmployee?id='+summaryId);
// function synchroAjaxByUrl(url){
//     var temp;
//     $.ajax({
//         url:url,
//         type:"get",
//         async:false,
//         dataType:"json",
//         success:function(data){
//             temp = data;
//         }
//     });
//     return temp;
// }
//公假
$(function () {
    $('#businessLeave_table').datagrid({
        method: 'get',
        width: 700,
        height: 480,
        singleSelect: true,
        rowNumbers: true,//显示行号
        remoteSort: false,//对本地数据进行排序
        fitColumns: true,//自适应表格大小
        columns: [[
            {field: 'id', title: 'id', hidden: true},
            {
                field: 'employee', title: '姓名', width: 120, align: 'center',
                formatter: function (value, rec) {
                    if (rec.employee == null) {
                        return "";
                    }
                    else {
                        return rec.employee.name;
                    }
                },
                editor: {
                    type: 'combobox',
                    options: {
                        method: 'get',
                        url: basePath() + '/displaySpecificEmployee?',
                        valueField: 'id',
                        textField: 'name',
                        panelHeight: 'auto',
                        required: true,
                        onBeforeLoad:function(param){
                            var summary =$('#AttendanceSummary_table').datagrid('getSelected');
                            param.id=summary.id;
                        },
                        onLoadSuccess: function (data) {
                            getRow('#businessLeave_table');
                            if (data) {
                                if (rowData.employee == null) {//如果没有值,默认选中第一个(相当于添加状态)
                                    if(data[0]==null){
                                        $.messager.alert('消息提示', '候选人已经有考勤状态', 'info');
                                        $("#businessLeave_table").datagrid('reload');
                                        editRow = undefined;
                                        beingInsertedRow = undefined;
                                    }else{
                                        $(this).combobox('setValue', data[0].id);
                                    }
                                } else {//相当于编辑状态
                                    $(this).combobox('setValue', rowData.employee.id);
                                }
                            }
                        },
                    }
                },
            },
            {
                field: 'employeeNo', title: '工号', width: 120, align: 'center',
                formatter: function (value, rec) {
                    if (rec.employee == null) {
                        return "";
                    }
                    else {
                        return rec.employee.no;
                    }
                }
            },
            {
                field: 'businessLeaveDescription', title: '考勤状态', width: 120, align: 'center',
                formatter: function (value, rec) {
                    if (rec.businessLeaveDescription == null) {
                        return "";
                    }
                    else {
                        return rec.businessLeaveDescription.description;
                    }
                },
                editor: {
                    type: 'combobox',
                    options: {
                        method: 'get',
                        url: basePath() + '/findAllBusinessLeaveDescriptions',
                        valueField: 'id',
                        textField: 'description',
                        panelHeight: 'auto',
                        required: true,
                        onLoadSuccess: function (data) {
                            getRow('#businessLeave_table');
                            if (data) {
                                if (rowData.businessLeaveDescription == null) {//如果没有值,默认选中第一个(相当于添加状态)
                                    $(this).combobox('setValue', data[0].id);
                                } else {//相当于编辑状态
                                    $(this).combobox('setValue', rowData.businessLeaveDescription.id);
                                }
                            }
                        },
                    }
                },
            },
            {field: 'remarks', title: '备注', width: 80, align: 'center',
                editor:
                    {type: 'validatebox',
                        options:
                            {
                                required: false
                            }
                    }
            }
        ]],
        toolbar: [{
            id: 'BusinessLeave_add_btn',
            text: '添加', iconCls: 'icon-add',
            handler: function () {
                addOpe("#businessLeave_table");
            }
        }, '-', {
            id: 'BusinessLeave_delete_btn',
            text: '删除', iconCls: 'icon-remove',
            handler: function () {
                deleteOpe('#businessLeave_table', basePath() + '/deleteBusinessLeave?ids=');
            }
        }, '-', {
            id: 'BusinessLeave_update_btn',
            text: '修改', iconCls: 'icon-edit',
            handler: function () {
                updateOpe("#businessLeave_table");
            }
        }, '-', {
            id: 'BusinessLeave_save_btn',
            text: '保存', iconCls: 'icon-save',
            handler: function () {
                saveOpe("#businessLeave_table");
            }
        }, '-', {
            id: 'BusinessLeave_cancel_btn',
            text: '撤销', iconCls: 'icon-redo',
            handler: function () {
                cancelOpe('#businessLeave_table');
            }
        }],
        //endEdit触发后
        onAfterEdit: function (rowIndex, rowData, changes) {//rowIndex：编辑行的索引，从 0 开始;rowData：编辑行对应的记录;changes：更改的字段/值对
            endEditOpe(rowData, basePath() + '/addBusinessLeave', basePath() + '/updateBusinessLeave', '#businessLeave_table');
        },
        onCancelEdit: function (rowIndex, rowData) {
            editRow = undefined;
        },
        //双击一行
        onDblClickRow: function (rowIndex, rowData) {
            if (editRow != undefined) {
                $("#businessLeave_table").datagrid('endEdit', editRow);
            } else {
                dblClickOpe(rowData, rowIndex, "#businessLeave_table");
            }
        },
        pagination: "true",//分页
        onClickRow: function (rowIndex, rowData) {
        },
        onLoadSuccess: function (data) {
        },
    });
    //分页用的获取datagrid中的详细内容，后台只需要获取easyui自动提供pages和rows（我好像需要一个rows）即可。
    var p = $('#businessLeave_table').datagrid('getPager');
    $(p).pagination({
        onBeforefresh: function () {
            $(this).pagination('loading');
            alert('before refresh');
            $(this).pagination('loaded');
        }
    });
});
//旷工
$(function () {
    $('#neglectWork_table').datagrid({
        method: 'get',
        width: 700,
        height: 480,
        singleSelect: true,
        rowNumbers: true,//显示行号
        remoteSort: false,//对本地数据进行排序
        fitColumns: true,//自适应表格大小
        columns: [[
            {field: 'id', title: 'id', hidden: true},
            {
                field: 'employee', title: '姓名', width: 120, align: 'center',
                formatter: function (value, rec) {
                    if (rec.employee == null) {
                        return "";
                    }
                    else {
                        return rec.employee.name;
                    }
                },
                editor: {
                    type: 'combobox',
                    options: {
                        method: 'get',
                        url: basePath() + '/displaySpecificEmployee?',
                        valueField: 'id',
                        textField: 'name',
                        panelHeight: 'auto',
                        required: true,
                        onBeforeLoad:function(param){
                            var summary =$('#AttendanceSummary_table').datagrid('getSelected');
                            param.id=summary.id;
                        },
                        onLoadSuccess: function (data) {
                            getRow('#neglectWork_table');
                            if (data) {
                                if (rowData.employee == null) {//如果没有值,默认选中第一个(相当于添加状态)
                                    if(data[0]==null){
                                        $.messager.alert('消息提示', '候选人已经有考勤状态', 'info');
                                        $("#neglectWork_table").datagrid('reload');
                                        editRow = undefined;
                                        beingInsertedRow = undefined;
                                    }else{
                                        $(this).combobox('setValue', data[0].id);
                                    }
                                } else {//相当于编辑状态
                                    $(this).combobox('setValue', rowData.employee.id);
                                }
                            }
                        },
                    }
                },
            },
            {
                field: 'employeeNo', title: '工号', width: 120, align: 'center',
                formatter: function (value, rec) {
                    if (rec.employee == null) {
                        return "";
                    }
                    else {
                        return rec.employee.no;
                    }
                }
            },
            {
                field: 'neglectWorkDescription', title: '考勤状态', width: 120, align: 'center',
                formatter: function (value, rec) {
                    if (rec.neglectWorkDescription == null){
                        return "";
                    }else {
                        return rec.neglectWorkDescription.description;
                    }
                },
                editor: {
                    type: 'combobox',
                    options: {
                        method: 'get',
                        url: basePath() + '/findAllNeglectWorkDescriptions',
                        valueField: 'id',
                        textField: 'description',
                        panelHeight: 'auto',
                        required: true,
                        onLoadSuccess: function (data) {
                            getRow('#neglectWork_table');
                            if (data) {
                                if (rowData.neglectWorkDescription == null) {//如果没有值,默认选中第一个(相当于添加状态)
                                    $(this).combobox('setValue', data[0].id);
                                } else {//相当于编辑状态
                                    $(this).combobox('setValue', rowData.neglectWorkDescription.id);
                                }
                            }
                        },
                    }
                },
            },
            {field: 'remarks', title: '备注', width: 80, align: 'center',
                editor:
                    {type: 'validatebox',
                    options:
                        {
                            required: false
                        }
                    }
            }
        ]],
        toolbar: [{
            id: 'NeglectWork_add_btn',
            text: '添加', iconCls: 'icon-add',
            handler: function () {
                addOpe("#neglectWork_table");
            }
        }, '-', {
            id: 'NeglectWork_delete_btn',
            text: '删除', iconCls: 'icon-remove',
            handler: function () {
                deleteOpe('#neglectWork_table', basePath() + '/deleteNeglectWork?ids=');
            }
        }, '-', {
            id: 'NeglectWork_update_btn',
            text: '修改', iconCls: 'icon-edit',
            handler: function () {
                updateOpe("#neglectWork_table");
            }
        }, '-', {
            id: 'NeglectWork_save_btn',
            text: '保存', iconCls: 'icon-save',
            handler: function () {
                saveOpe("#neglectWork_table");
            }
        }, '-', {
            id: 'NeglectWork_cancel_btn',
            text: '撤销', iconCls: 'icon-redo',
            handler: function () {
                cancelOpe('#neglectWork_table');
            }
        }],
        //endEdit触发后
        onAfterEdit: function (rowIndex, rowData, changes) {//rowIndex：编辑行的索引，从 0 开始;rowData：编辑行对应的记录;changes：更改的字段/值对
            endEditOpe(rowData, basePath() + '/addNeglectWork', basePath() + '/updateNeglectWork', '#neglectWork_table');
        },
        onCancelEdit: function (rowIndex, rowData) {
            editRow = undefined;
        },
        //双击一行
        onDblClickRow: function (rowIndex, rowData) {
            if (editRow != undefined) {
                $("#neglectWork_table").datagrid('endEdit', editRow);
            } else {
                dblClickOpe(rowData, rowIndex, "#neglectWork_table");
            }
        },
        pagination: "true",//分页
        onClickRow: function (rowIndex, rowData) {
        },
        onLoadSuccess: function (data) {
        },
    });
    //分页用的获取datagrid中的详细内容，后台只需要获取easyui自动提供pages和rows（我好像需要一个rows）即可。
    var p = $('#neglectWork_table').datagrid('getPager');
    $(p).pagination({
        onBeforefresh: function () {
            $(this).pagination('loading');
            alert('before refresh');
            $(this).pagination('loaded');
        }
    });
});
//事假
$(function () {
    $('#privateLeave_table').datagrid({
        method: 'get',
        width: 700,
        height: 480,
        singleSelect: true,
        rowNumbers: true,//显示行号
        remoteSort: false,//对本地数据进行排序
        fitColumns: true,//自适应表格大小
        columns: [[
            {field: 'id', title: 'id', hidden: true},
            {
                field: 'employee', title: '姓名', width: 120, align: 'center',
                formatter: function (value, rec) {
                    if (rec.employee == null) {
                        return "";
                    }else{
                        return rec.employee.name;
                    }
                },


                editor: {
                    type: 'combobox',
                    options: {
                        method: 'get',
                        url: basePath() + '/displaySpecificEmployee?',
                        valueField: 'id',
                        textField: 'name',
                        panelHeight: 'auto',
                        required: true,
                        onBeforeLoad:function(param){
                            var summary =$('#AttendanceSummary_table').datagrid('getSelected');
                            param.id=summary.id;
                        },
                        onLoadSuccess: function (data) {
                            getRow('#privateLeave_table');
                            if (data) {
                                if (rowData.employee == null) {//如果没有值,默认选中第一个(相当于添加状态)
                                    if(data[0]==null){
                                        $.messager.alert('消息提示', '候选人已经有考勤状态', 'info');
                                        $("#privateLeave_table").datagrid('reload');
                                        editRow = undefined;
                                        beingInsertedRow = undefined;
                                    }else{
                                        $(this).combobox('setValue', data[0].id);
                                    }
                                } else {//相当于编辑状态
                                    $(this).combobox('setValue', rowData.employee.id);
                                }
                            }
                        },
                    }
                },
            },
            {
                field: 'employeeNo', title: '工号', width: 120, align: 'center',
                formatter: function (value, rec) {
                    if (rec.employee == null) {
                        return "";
                    }
                    else {
                        return rec.employee.no;
                    }
                }
            },
            {
                field: 'privateLeaveDescription', title: '考勤状态', width: 120, align: 'center',
                formatter: function (value, rec) {
                    if (rec.privateLeaveDescription == null) {
                        return "";
                    }
                    else {
                        return rec.privateLeaveDescription.description;
                    }
                },
                editor: {
                    type: 'combobox',
                    options: {
                        method: 'get',
                        url: basePath() + '/findAllPrivateLeaveDescriptions',
                        valueField: 'id',
                        textField: 'description',
                        panelHeight: 'auto',
                        required: true,
                        onLoadSuccess: function (data) {
                            getRow('#privateLeave_table');
                            if (data) {
                                if (rowData.privateLeaveDescription == null) {//如果没有值,默认选中第一个(相当于添加状态)
                                    $(this).combobox('setValue', data[0].id);
                                } else {//相当于编辑状态
                                    $(this).combobox('setValue', rowData.privateLeaveDescription.id);
                                }
                            }
                        },
                    }
                },
            },
            {field: 'remarks', title: '备注', width: 80, align: 'center',
                editor: {
                    type: 'validatebox',
                    options: {
                        required: false
                    }
                }
            }
        ]],
        toolbar: [{
            id: 'PrivateLeave_add_btn',
            text: '添加', iconCls: 'icon-add',
            handler: function () {
                addOpe("#privateLeave_table");
            }
        }, '-', {
            id: 'PrivateLeave_delete_btn',
            text: '删除', iconCls: 'icon-remove',
            handler: function () {
                deleteOpe('#privateLeave_table', basePath() + '/deletePrivateLeave?ids=');
            }
        }, '-', {
            id: 'PrivateLeave_update_btn',
            text: '修改', iconCls: 'icon-edit',
            handler: function () {
                updateOpe("#privateLeave_table");
            }
        }, '-', {
            id: 'PrivateLeave_save_btn',
            text: '保存', iconCls: 'icon-save',
            handler: function () {
                saveOpe("#privateLeave_table");
            }
        }, '-', {
            id: 'PrivateLeave_cancel_btn',
            text: '撤销', iconCls: 'icon-redo',
            handler: function () {
                cancelOpe('#privateLeave_table');
            }
        }],
        //endEdit触发后
        onAfterEdit: function (rowIndex, rowData, changes) {//rowIndex：编辑行的索引，从 0 开始;rowData：编辑行对应的记录;changes：更改的字段/值对
            endEditOpe(rowData, basePath() + '/addPrivateLeave', basePath() + '/updatePrivateLeave', '#privateLeave_table');
        },
        onCancelEdit: function (rowIndex, rowData) {
            editRow = undefined;
        },
        //双击一行
        onDblClickRow: function (rowIndex, rowData) {
            if (editRow != undefined) {
                $("#privateLeave_table").datagrid('endEdit', editRow);
            } else {
                dblClickOpe(rowData, rowIndex, "#privateLeave_table");
            }
        },
        pagination: "true",//分页
        onClickRow: function (rowIndex, rowData) {
        },
        onLoadSuccess: function (data) {
        },
    });
    //分页用的获取datagrid中的详细内容，后台只需要获取easyui自动提供pages和rows（我好像需要一个rows）即可。
    var p = $('#privateLeave_table').datagrid('getPager');
    $(p).pagination({
        onBeforefresh: function () {
            $(this).pagination('loading');
            alert('before refresh');
            $(this).pagination('loaded');
        }
    });
});
//学校缺勤
$(function () {
    $('#universityAbsence_table').datagrid({
        method: 'get',
        width: 700,
        height: 480,
        singleSelect: true,
        rowNumbers: true,//显示行号
        remoteSort: false,//对本地数据进行排序
        fitColumns: true,//自适应表格大小
        columns: [[
            {field: 'id', title: 'id', hidden: true},
            {
                field: 'employee', title: '姓名', width: 120, align: 'center',
                formatter: function (value, rec) {
                    if (rec.employee == null) {
                        return "无";
                    }else{
                        return rec.employee.name;
                    }
                },

                editor: {
                    type: 'combobox',
                    options: {
                        method: 'get',
                        url: basePath() + '/displaySpecificEmployee?',
                        valueField: 'id',
                        textField: 'name',
                        panelHeight: 'auto',
                        required: true,
                        onBeforeLoad:function(param){
                            var summary =$('#AttendanceSummary_table').datagrid('getSelected');
                            param.id=summary.id;
                        },
                        onLoadSuccess: function (data) {
                            alert("------");
                            getRow('#universityAbsence_table');
                            if (data) {
                                if (rowData.employee == null) {//如果没有值,默认选中第一个(相当于添加状态)
                                    if(data[0]==null){
                                        $.messager.alert('消息提示', '候选人已经有考勤状态', 'info');
                                        $("#universityAbsence_table").datagrid('reload');
                                        editRow = undefined;
                                        beingInsertedRow = undefined;
                                    }else{
                                        $(this).combobox('setValue', data[0].id);
                                    }
                                } else {//相当于编辑状态
                                    $(this).combobox('setValue', rowData.employee.id);
                                }
                            }
                        },
                    }
                },
            },
            {
                field: 'employeeNo', title: '工号', width: 120, align: 'center',
                formatter: function (value, rec) {
                    if (rec.employee == null) {
                        return "";
                    }
                    else {
                        return rec.employee.no;
                    }
                }
            },
            {
                field: 'universityAbsenceDescription', title: '考勤状态', width: 120, align: 'center',
                formatter: function (value, rec) {
                    if (rec.universityAbsenceDescription) {
                        return rec.universityAbsenceDescription.description;
                    }
                },
                editor: {
                    type: 'combobox',
                    options: {
                        method: 'get',
                        url: basePath() + '/findAllUniversityAbsenceDescriptions',
                        valueField: 'id',
                        textField: 'description',
                        panelHeight: 'auto',
                        required: true,
                        onLoadSuccess: function (data) {
                            getRow('#universityAbsence_table');
                            if (data) {
                                console.log("------------------");
                                console.log(data);
                                console.log(rowData);
                                console.log("-----------------------")
                                if (rowData.universityAbsenceDescription == null) {//如果没有值,默认选中第一个(相当于添加状态)
                                    $(this).combobox('setValue', data[0].id);
                                } else {//相当于编辑状态
                                    $(this).combobox('setValue', rowData.universityAbsenceDescription.id);
                                }
                            }
                        },
                    }
                },
            },
            {field: 'remarks', title: '备注', width: 80, align: 'center',
                editor:
                    {type: 'validatebox',
                        options:
                            {
                                required: false
                            }
                    }
            }
        ]],
        toolbar: [{
            id: 'UniversityAbsence_add_btn',
            text: '添加', iconCls: 'icon-add',
            handler: function () {
                addOpe("#universityAbsence_table");
            }
        }, '-', {
            id: 'UniversityAbsence_delete_btn',
            text: '删除', iconCls: 'icon-remove',
            handler: function () {
                deleteOpe('#universityAbsence_table', basePath() + '/deleteUniversityAbsence?ids=');
            }
        }, '-', {
            id: 'UniversityAbsence_update_btn',
            text: '修改', iconCls: 'icon-edit',
            handler: function () {
                updateOpe("#universityAbsence_table");
            }
        }, '-', {
            id: 'UniversityAbsence_save_btn',
            text: '保存', iconCls: 'icon-save',
            handler: function () {
                saveOpe("#universityAbsence_table");
            }
        }, '-', {
            id: 'UniversityAbsence_cancel_btn',
            text: '撤销', iconCls: 'icon-redo',
            handler: function () {
                cancelOpe('#universityAbsence_table');
            }
        }],
        //endEdit触发后
        onAfterEdit: function (rowIndex, rowData, changes) {//rowIndex：编辑行的索引，从 0 开始;rowData：编辑行对应的记录;changes：更改的字段/值对
            console.log(rowData);
            endEditOpe(rowData, basePath() + '/addUniversityAbsence', basePath() + '/updateUniversityAbsence', '#universityAbsence_table');
        },
        onCancelEdit: function (rowIndex, rowData) {
            editRow = undefined;
        },
        //双击一行
        onDblClickRow: function (rowIndex, rowData) {
            if (editRow != undefined) {
                $("#universityAbsence_table").datagrid('endEdit', editRow);
                //$('#ThesisTable').datagrid('unselectRow',editRow);
            } else {
                dblClickOpe(rowData, rowIndex, "#universityAbsence_table");
            }
        },
        pagination: "true",//分页
        onClickRow: function (rowIndex, rowData) {
        },
        onLoadSuccess: function (data) {
        },
    });
    //分页用的获取datagrid中的详细内容，后台只需要获取easyui自动提供pages和rows（我好像需要一个rows）即可。
    var p = $('#universityAbsence_table').datagrid('getPager');
    $(p).pagination({
        onBeforefresh: function () {
            $(this).pagination('loading');
            alert('before refresh');
            $(this).pagination('loaded');
        }
    });
});
//下拉框
// $(function () {
//     initCombobox('#Attendance_department_combobox', 'departmentName', basePath() + '/findAllDepartment');
//     initCombobox('#Attendance_description_combobox', 'description', basePath() + '/findAllDescriptions');
// });

//初始化多选下拉框
function initCombobox(id, context, url) {
    //加载下拉框信息
    $(id).combobox({
        required: true,
        method: 'get',
        url: url,
        valueField: 'id',
        textField: context,
        multiple: true,
        panelHeight: 'auto'
    });
}
//纸的增删改
//增加
function AttendanceSummaryAddRecord() {
    isOperated = 1;
    $('#AttendanceSummary_Dialg').dialog('open').dialog('setTitle', '增加');//给表格命名为增加
    $('#AttendanceSummary_form').form('clear');

};
//修改
function AttendanceSummaryEditRecord() {
    isOperated = 0;
    var row = $('#AttendanceSummary_table').datagrid("getSelected");
    if (row == null) {
        $.messager.alert("提示", "请选择要修改的行！", "info");
    }
    if (row) {
        $('#AttendanceSummary_Dialg').dialog("open").dialog('setTitle', '修改信息');
        //绑定数据源
        $("#AttendanceSummary_form").form("load", row);
        $('#Attendance_attendanceTime_dbox').datebox('setValue', row.attendanceTime);//显示下拉框的值，必须要传id给后台
    }
};
//保存
function AttendanceSummarySaveRecord() {
    var data = serializeObject("#AttendanceSummary_form");
    if (isOperated == 1) {
        $.postJSON2(basePath() + '/addAttendanceSummary', data, '#AttendanceSummary_Dialg', '#AttendanceSummary_table');
    } else if (isOperated == 0) {
        $.postJSON1(basePath() + '/updateAttendanceSummary', data, '#AttendanceSummary_Dialg', '#AttendanceSummary_table');
    }
};
//删除
function AttendanceSummaryMoveRecord() {
    //得到当前选中行的记录，
    var row = $('#AttendanceSummary_table').datagrid('getSelected');
    //必须当选中一行时，才可以删除
    if (row != null) {
        $.messager.confirm('提示', '你确定要删除这条信息吗？', function (r) {
            if (r) {
                $.postJSON3(basePath() + '/deleteAttendanceSummary?id=' + row.id, '#AttendanceSummary_Dialg', '#AttendanceSummary_table');
            }
        });
    } else {
        $.messager.alert("提示", "请选择要删除的信息！", "info");
        return false;
    }
};
//序列化反序列化表格数据
function serializeObject(form) {
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
};
//更新的json处理
$.postJSON1 = function (url, data, dlg, grid) {//Json转化更新
    if ($(dlg).form('validate') == false) {
        $.messager.alert('消息提示', '更新失败,请检查红色字段', 'error');
        return;
    }
    else {
        return jQuery.ajax({
            'type': 'PUT',
            'url': url,
            'contentType': 'application/json',
            'data': JSON.stringify(data),
            'dataType': 'json',
            success: function (data) {
                $.messager.alert('消息提示', '更新成功', 'info');
                $(dlg).dialog('close');
                $(grid).datagrid('reload');
            },
            error: function () {
                $.messager.alert('消息提示', '更新失败', 'error');
            }
        });
    }
};
//添加的json处理
$.postJSON2 = function (url, data, dlg, grid) {//Json转化添加
    if ($(dlg).form('validate') == false) {
        $.messager.alert('消息提示', '添加失败,请检查红色字段', 'error');
        return;
    }
    else {
        return jQuery.ajax({//用Ajax的方法来传递
            'type': 'POST',//种类是POST
            'url': url,
            'contentType': 'application/json',
            'data': JSON.stringify(data),
            'dataType': 'json',//数据的种类是JSON
            success: function (msg) {
                if (msg.data == undefined) {
                    $.messager.alert('消息提示', '添加成功', 'info');
                    $(dlg).dialog('close');
                    $(grid).datagrid('reload');//添加成功之后调用
                    displayInfor();//添加成功之后调用
                } else {
                    $.messager.alert("操作提示", "操作失败！", "error");
                    $(grid).datagrid('reload');
                }

            },
            error: function (msg) {
                $.messager.alert('消息提示', '添加失败', 'error');
            }//添加失败之后调用
        });
    }
};
//删除的json处理
$.postJSON3 = function (url, dlg, grid) {
    return jQuery.ajax({
        'type': 'DELETE',
        'url': url,
        success: function (msg) {
            $.messager.alert('消息提示', '删除成功', 'info');
            $(dlg).dialog('close');
            $(grid).datagrid('reload');
            $("#candidate_table").datagrid('reload');
            $("#description_table").datagrid('reload');
            $("#presence_table").datagrid('reload');
            $("#neglectWork_table").datagrid('reload');
            $("#privateLeave_table").datagrid('reload');
            $("#businessLeave_table").datagrid('reload');
            $("#universityAbsence_table").datagrid('reload');

        },
        error: function (msg) {
            $.messager.alert('消息提示', '删除失败', 'error');

        }
    });
};
//基础路径
function basePath() {
    var curWwwPath = window.document.location.href;
    var pathName = window.document.location.pathname;
    var pos = curWwwPath.indexOf(pathName);
    var localhostPaht = curWwwPath.substring(0, pos);
    var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
    return (localhostPaht + projectName);
}
//增加操作
function addOpe(datagridId) {
    //添加前先判断是否有开始编辑的行，如果有就把那行结束编辑
    if (editRow != undefined) {
        $.messager.alert('消息提示', '请先保存修改', 'info');
    } else {//如果没有正在编辑的行，则在datagrid第一行插入一行
        $(datagridId).datagrid('insertRow', {
            index: 0,//插入第0行
            row: {}//空行
        });
        //将插入的那行开启编辑状态
        $(datagridId).datagrid('beginEdit', 0);
        editRow = 0;//定义当前编辑的行 为0
        beingInsertedRow = 0;//定义当前正在插入的行 为0
    }
}
//删除操作
function deleteOpe(datagridId, url) {
    //删除时，先获取选中的所有行 （此处实现的是多行删除）
    var rows = $(datagridId).datagrid('getSelections');
    if (rows.length > 0) {
        $.messager.confirm("提示", "你确定要删除吗？",
            function (r) {
                if (r) {//一订要有这个(这是判断true还是false的)
                    var ids = [];
                    for (var i = rows.length - 1; i >= 0; i--) {
                        ids.push(rows[i].id);//将选择的行存入数组并用，分隔转化为字符串
                    }
                    $.deleteAjax(url + ids.join(','), datagridId);
                }
            });

    } else {
        $.messager.alert("提示", "请选择要删除的行", "error");
    }
}
//修改操作
function updateOpe(datagridId) {
    //修改时获取选中的所有行
    var rows = $(datagridId).datagrid('getSelections');
    //如果只选择了一行则可以进行修改，否则不操作
    if (rows.length == 1) {
        var rowData = $(datagridId).datagrid('getSelected');
        //获取到当前选择行的下标（此时rows只有一行，所以是0）
        var index = $(datagridId).datagrid('getRowIndex', rows[0]);
        //开启编辑
        $(datagridId).datagrid('beginEdit', index);
        //把当前开启编辑行赋给变量editRow
        editRow = index;

    } else {
        $.messager.alert("提示", "必须选择一行进行编辑", "error");
    }
}
//保存操作
function saveOpe(datagridId) {
    //保存时结束当前编辑的行，自动触发onAfterEdit事件如果要与后台交互可将数据通过Ajax提交后台
    $(datagridId).datagrid('endEdit', editRow);
    if (editRow != undefined) {//如果验证不通过就不会触发onAfterEdit，直接进入执行if语句
        $.messager.alert('消息提示', '请检查输入信息，查看红色字段', 'warn');
    }
}
//撤销操作
function cancelOpe(datagridId) {
    //取消添加新行，并将新插入的该行删去
    if (beingInsertedRow == 0) {
        $(datagridId).datagrid('deleteRow', beingInsertedRow);
        beingInsertedRow = undefined;//插入行重新变为未定义
        editRow = undefined;
    }
    //取消当前编辑行把当前编辑行undefined回滚改变的数据,取消选择的行
    if (editRow != undefined) {
        $(datagridId).datagrid('cancelEdit', editRow);
    }
}
//endEdit触发的操作
function endEditOpe(rowData, addUrl, updateUrl, datagridId) {
    console.info(rowData);//控制台输出数据
    //添加后触发的endEdit事件
    if (beingInsertedRow != undefined) {
        $.saveAjax(addUrl, rowData, datagridId);
        beingInsertedRow = undefined;//保存后，插入行重新变为未定义
        editRow = undefined;//保存后，编辑行重新变为未定义
    }
    //当编辑后触发的endEdit事件
    if (editRow != undefined) {
        $.updateAjax(updateUrl, rowData, datagridId);
        editRow = undefined;//保存后，编辑行重新变为未定义
    }
}
//双击触发的操作
function dblClickOpe(rowData, rowIndex, datagridId) {
    //开启编辑
    $(datagridId).datagrid('beginEdit', rowIndex);
    //把当前开启编辑行赋给变量editRow
    editRow = rowIndex;

}
//更新Ajax
$.updateAjax = function (url, data, datagrid) {
    return jQuery.ajax({
        'type': 'PUT',
        'url': url,
        'contentType': 'application/json',
        'data': JSON.stringify(data),
        'dataType': 'json',
        success: function (msg) {
            if (msg.data == undefined) {
                $.messager.alert('消息提示', '更新成功', 'info');
                $(datagrid).datagrid('reload');//更新成功之后调用
            } else {
                $.messager.alert("操作提示", "操作失败！", "error");
                $(datagrid).datagrid('reload');
            }
        },
        error: function (msg) {
            $.messager.alert('消息提示', '更新失败', 'error');
        }
    });
};
//添加Ajax
$.saveAjax = function (url, data, datagrid) {
    return jQuery.ajax({
        'type': 'POST',
        'url': url,
        'contentType': 'application/json',
        'data': JSON.stringify(data),
        'dataType': 'json',
        success: function (msg) {
            if (msg.data == undefined) {
                $.messager.alert('消息提示', '添加成功', 'info');
                $(datagrid).datagrid('reload');//添加成功之后调用
            } else {
                $.messager.alert("操作提示", "操作失败！", "error");
                $(datagrid).datagrid('reload');
            }
        },
        error: function (msg) {
            $.messager.alert('消息提示', '添加失败', 'error');
            $(datagrid).datagrid('reload');//添加成功之后调用
        }
    });
};
//删除Ajax
$.deleteAjax = function (url, datagrid) {
    return jQuery.ajax({
        'type': 'DELETE',
        'url': url,
        success: function (msg) {
            $.messager.alert('消息提示', '删除成功', 'info');
            $(datagrid).datagrid('reload');
        },
        error: function (msg) {
            $.messager.alert('消息提示', '删除失败', 'error');
        }
    });
};
