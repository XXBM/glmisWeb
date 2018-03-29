/**
 * Created by miaojie on 2017/3/24.
 */


//定义全局变量（编辑行/插入行/行数据）
var editRow = undefined;
var beingInsertedRow = undefined;
var rowData = undefined;
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
//jquery.validatebox.js  扩展  正则表达式
$.extend($.fn.validatebox.defaults.rules, {
    //数字
    Number: {
        validator: function (value) {
            var reg = /^[0-9]*$/;
            return reg.test(value);
        },
        message: '此处只能输入数字'
    },
})
//增加操作
function add(datagridId) {
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
function deletePost(datagridId, url) {
    //删除时，先获取选中的所有行 （此处实现的是多行删除）
    var rows = $(datagridId).datagrid('getSelections');
    if (rows.length > 0) {
        $.messager.confirm("提示", "你确定要删除吗？",function (r) {
            if (r) {//一定要有这个(这是判断true还是false的)
                $.deletePostAjax(url + rows[0].id, datagridId);
            }
        });
    } else {
        $.messager.alert("提示", "请选择要删除的行", "error");
    }
}
//修改操作
function update(datagridId) {
    //修改时获取选中的所有行
    var rows = $(datagridId).datagrid('getSelections');
    //如果只选择了一行则可以进行修改，否则不操作
    if (rows.length == 1) {
        var rowData = $(datagridId).datagrid('getSelected');
        // //获取到当前选择行的下标（此时rows只有一行，所以是0）
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
function save(datagridId) {
    //保存时结束当前编辑的行，自动触发onAfterEdit事件如果要与后台交互可将数据通过Ajax提交后台
    $(datagridId).datagrid('endEdit', editRow);
    if (editRow != undefined) {//如果验证不通过就不会触发onAfterEdit，直接进入执行if语句
        $.messager.alert('消息提示', '请检查输入信息，查看红色字段', 'warn');
    }
}
//撤销操作
function cancel(datagridId) {
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
function endEdit(rowData, addUrl, updateUrl, datagridId) {
    // alert("2222");
    console.info(rowData);//控制台输出数据
    //添加后触发的endEdit事件
    if (beingInsertedRow != undefined) {
        $.savePostAjax(addUrl, rowData, datagridId);
        beingInsertedRow = undefined;//保存后，插入行重新变为未定义
        editRow = undefined;//保存后，编辑行重新变为未定义
    }
    //当编辑后触发的endEdit事件
    if (editRow != undefined) {
        $.updatePostAjax(updateUrl, rowData, datagridId);
        editRow = undefined;//保存后，编辑行重新变为未定义
    }
}
//双击触发的操作
function dblClick(rowData, rowIndex, datagridId) {
    //开启编辑
    $(datagridId).datagrid('beginEdit', rowIndex);
    //把当前开启编辑行赋给变量editRow
    editRow = rowIndex;
}
//更新Ajax
$.updatePostAjax = function (url, data, datagrid) {
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
                $.messager.alert("操作提示", "操作失败！" + msg.data + "," + msg.message, "error");
                $(datagrid).datagrid('reload');
            }
        },
        error: function (msg) {
            $.messager.alert('消息提示', '更新失败', 'error');
        }
    });
};
//保存添加Ajax
$.savePostAjax = function (url, data, datagrid) {
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
                $.messager.alert("操作提示", "操作失败！" + msg.data + "," + msg.message, "error");
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
$.deletePostAjax = function (url, datagrid) {
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
/*数据网格*/

$(function () {
    $("#postTable").datagrid({
        method: 'get',
        url: basePath() + '/findAllPost',//请求的数据源
        striped: true,//行背景交换
        fit:true,
        fitColumns: true,//自适应表格大小
        width:'600',
        height:'500',
        singleSelect: true,//单选
        rowNumbers: "false",//显示行号
        remoteSort: false,//对本地数据进行排序
        frozenColumns: [[{field: "chk", "checkbox": true}]],//frozenColumns数据表格的固定列，在这里设置成了单选框
        columns: [[
            {field: 'id', width: 60, hidden: true},
            {
                field: 'no', title: '编号', width: 150, fixed: true, align: 'center',
                editor: {
                    type: 'validatebox',
                    options: {
                        required: true,
                        validType: 'Number',
                        missingMessage: '该字段不能为空'
                    }
                }
            },
            {
                field: 'description', title: '职务名称', width: 100, fixed: true, align: 'center',
                editor: {
                    type: 'validatebox',
                    options: {
                        required: true,
                        missingMessage: '该字段不能为空'
                    }
                }
            }
        ]],

        toolbar: [{
            id:'Post_add_btn',
            text: '添加', iconCls: 'icon-add',
            handler: function () {
                add("#postTable");
            }
        }, '-', {
            id:'Post_remove_btn',
            text: '删除', iconCls: 'icon-remove',
            handler: function () {
                deletePost('#postTable', basePath() + '/deletePost?id=');
            }
        }, '-', {
            id:'Post_edit_btn',
            text: '修改', iconCls: 'icon-edit',
            handler: function () {
                update('#postTable');
            }
        }, '-', {
            id:'Post_save_btn',
            text: '保存', iconCls: 'icon-save',
            handler: function () {
                save('#postTable');
            }
        }, '-', {
            id:'Post_redo_btn',
            text: '撤销', iconCls: 'icon-redo',
            handler: function () {
                cancel('#postTable');
            }
        }],
        //endEdit触发后
        onAfterEdit: function (rowIndex, rowData, changes) {//rowIndex：编辑行的索引，从 0 开始;rowData：编辑行对应的记录;changes：更改的字段/值对
            endEdit(rowData, basePath() + '/addPost', basePath() + '/updatePost', '#postTable');
        },
        onCancelEdit: function (rowIndex, rowData) {
            editRow = undefined;
        },
        //双击一行
        onDblClickRow: function (rowIndex, rowData) {
            if (editRow != undefined) {
                $("#postTable").datagrid('endEdit', editRow);
            } else {
                dblClick(rowData, rowIndex, "#postTable");
            }
        },
        pagination: "true",//分页
    });
    //分页用的获取datagrid中的详细内容，后台只需要获取easyui自动提供pages和rows（我好像需要一个rows）即可。
    var p = $('#postTable').datagrid('getPager');
    $(p).pagination({
        showPageList: false,
        beforePageText: '第',//页数文本框前显示的汉字
        afterPageText: '页    共 {pages} 页',
        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录',
        onBeforefresh: function () {
            $(this).pagination('loading');
            $(this).pagination('loaded');

        }
    });
})

