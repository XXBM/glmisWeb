/**
 * Created by dell on 2017-05-25 .
 */
//这个table不起作用，只是为了让其他datagrid正常显示
$(function () {
    $('#noUse').datagrid({});
});
//定义全局变量（编辑行/插入行/行数据）
var editRow = undefined;
var beingInsertedRow = undefined;
var rowData = undefined;
var emplId = undefined;
//基础路径
function basePath() {
    var curWwwPath = window.document.location.href;
    var pathName = window.document.location.pathname;
    var pos = curWwwPath.indexOf(pathName);
    var localhostPaht = curWwwPath.substring(0, pos);
    var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
    return (localhostPaht + projectName);
}
//获取行
function getRow(dat) {
    var rows = $(dat).datagrid('getRows'); // get current page rows
    rowData = rows[editRow]; // your row data
}

//显示职员列表
$(function () {
    $('#staffTable').datagrid({
        method: 'get',
        url: basePath() + '/displayAllEmployeeName',
        width: 270,
        height: 420,
        singleSelect: true,
        rowNumbers: "true",//显示行号
        remoteSort: false,//对本地数据进行排序
        fitColumns: true,//自适应表格大小
        columns: [[
            {field: 'id', title: 'id', hidden: true},
            {field: 'no', title: '职工号', width: 80, align: 'center'},
            {field: 'name', title: '姓名', width: 80, align: 'center'},
        ]],
        pagination: "true",//分页
        onClickRow: function (rowIndex, rowData) {
            $('#p').panel('collapse', true);
            emplId = rowData.id;
            var leaveUrl = basePath() + '/displayLeaveByEmp?id=' + rowData.id;
            $('#Leave').datagrid('reload', leaveUrl);
        },
    });
    //分页用的获取datagrid中的详细内容，后台只需要获取easyui自动提供pages和rows（我好像需要一个rows）即可。
    var p = $('#staffTable').datagrid('getPager');
    $(p).pagination({
        showPageList: false,
        beforePageText: '第',//页数文本框前显示的汉字
        afterPageText: '页   共 {pages} 页',
        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录',
        onBeforefresh: function () {
            $(this).pagination('loading');
            alert('before refresh');
            $(this).pagination('loaded');
        }
    });
});
//显示职员请假信息Leave表格
$(function () {
    $('#Leave').datagrid({
        method: 'get',
        width: 860,
        height: 480,
        singleSelect: true,
        rowNumbers: true,//显示行号
        remoteSort: false,//对本地数据进行排序
        fitColumns: true,//自适应表格大小
        //toolbar: '#LeaveToolbar',
        columns: [[
            {field: 'id', title: 'id', hidden: true},
            {
                field: 'startTime', title: '计划开始时间', width: 85, align: 'center',sortable: true,
                editor: {
                    type: 'datebox',
                    options: {
                        required: true,
                        editable:false,
                        missingMessage: '该字段不能为空'
                    }
                }
            },
            {
                field: 'endTime', title: '计划结束时间', width: 85, align: 'center',sortable: true,
                editor: {
                    type: 'datebox',
                    options: {
                        required: true,
                        editable:false,
                        missingMessage: '该字段不能为空'
                    }
                }
            },
            {
                field: 'realTime', title: '实际销假时间', width: 85, align: 'center',sortable: true,
                editor: {
                    type: 'datebox',
                    options: {
                        required: true,
                        editable:false,
                        missingMessage: '该字段不能为空'
                    }
                }
            },
            {
                field: 'reason', title: '请假原因', width: 150, align: 'center',
                editor: {
                    type: 'validatebox',
                    options: {
                        required: true,
                        missingMessage: '该字段不能为空'
                    }
                }
            },
        ]],
        toolbar: [{
            id: 'Leave_add_btn', text: '添加', iconCls: 'icon-add',
            handler: function () {
                addOpe("#Leave");
            }
        }, '-', {
            id: 'Leave_delete_btn', text: '删除', iconCls: 'icon-cut',
            handler: function () {
                deleteOpe('#Leave', basePath() + '/deleteLeave?id=');
            }
        }, '-', {
            id: 'Leave_edit_btn', text: '修改', iconCls: 'icon-edit',
            handler: function () {
                updateOpe("#Leave");
            }
        }, '-', {
            id: 'Leave_save_btn', text: '保存', iconCls: 'icon-save',
            handler: function () {
                saveOpe("#Leave");
            }
        }, '-', {
            id: 'Leave_cancel_btn', text: '撤销', iconCls: 'icon-redo',
            handler: function () {
                cancelOpe('#Leave');
            }
        }],
        //endEdit触发后
        onAfterEdit: function (rowIndex, rowData, changes) {//rowIndex：编辑行的索引，从 0 开始;rowData：编辑行对应的记录;changes：更改的字段/值对
            endEditOpe(rowData, basePath() + '/addLeave', basePath() + '/updateLeave', '#Leave');
        },
        onCancelEdit: function (rowIndex, rowData) {
            editRow = undefined;
        },
        //双击一行
        onDblClickRow: function (rowIndex, rowData) {
            if (editRow != undefined) {
                $("#visitingStudy").datagrid('endEdit', editRow);
            } else {
                dblClickOpe(rowData, rowIndex, "#Leave");
            }
        },
        pagination: "true",//分页
        onClickRow: function (rowIndex, rowData) {
        },
        onLoadSuccess: function (data) {
        },
    });
    //分页用的获取datagrid中的详细内容，后台只需要获取easyui自动提供pages和rows（我好像需要一个rows）即可。
    var p = $('#Leave_table').datagrid('getPager');
    $(p).pagination({
        onBeforefresh: function () {
            $(this).pagination('loading');
            alert('before refresh');
            $(this).pagination('loaded');
        }
    });
});
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
    if (editRow != undefined) {
        $.messager.alert('消息提示', '请先保存修改', 'info');
    } else {
        //删除时，先获取选中的所有行 （此处实现的是多行删除）
        var rows = $(datagridId).datagrid('getSelections');
        var checkFlag = false;
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
}
//修改操作
function updateOpe(datagridId) {
    if (editRow != undefined) {
        $.messager.alert('消息提示', '请先保存修改', 'info');
    } else {
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
            success: function () {
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
        },
        error: function (msg) {
            $.messager.alert('消息提示', '删除失败', 'error');

        }
    });
};
