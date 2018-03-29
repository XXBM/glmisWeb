/**
 * Created by dell on 2017/2/8.
 */
$.ajaxSetup({
    error: function (XMLHttpRequest, textStatus, errorThrown) {
        if (XMLHttpRequest.status == 403) {
            $.messager.alert('消息提示', '您没有权限访问此资源或进行此操作', 'info');
            return false;
        }
    },
    complete: function (XMLHttpRequest, textStatus) {
        var sessionstatus = XMLHttpRequest.getResponseHeader("sessionstatus");//通过XMLHttpRequest取得响应头，sessionstatus
        if (sessionstatus == 'timeout') {
            $.messager.alert('消息提示', '登陆超时，请重新登陆' + sessionstatus, 'info');
            window.location.replace(basePath() + "/login");
        }
    }
});
//基础路径
function basePath() {
    var curWwwPath = window.document.location.href;
    var pathName = window.document.location.pathname;
    var pos = curWwwPath.indexOf(pathName);
    var localhostPaht = curWwwPath.substring(0, pos);
    var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
    return (localhostPaht + projectName);
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
    //年份
    Year: {
        validator: function (value) {
            var reg = /^(?:19|20)[0-9][0-9]$/;
            return reg.test(value);
        },
        message: '输入正确的年份'
    },
})
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
    console.log(rows);
    var checkFlag = false;
    if (rows.length > 0) {
        for (var i = rows.length - 1; i >= 0; i--) {
            var checkingState = '未审核';//定义局部变量审核状态为 ‘未审核’,每次都要变
            if (rows[i].checkingStatus != null) {//不触动下拉框时，默认是null,所以定义一个局部变量初始值为未审核
                checkingState = rows[i].checkingStatus.state;
            }
            if (checkingState == "通过审核") {
                $.messager.alert('消息提示', '所选中行中含有通过审核的信息，不可进行删除操作', 'warn');
                checkFlag = true;
                return;
            }
        }
        if (checkFlag == false) {
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
        }
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
        var checkingState = '未审核';//定义局部变量审核状态为 ‘未审核’
        if (rowData.checkingStatus != null) {//不触动下拉框时，默认是null,所以定义一个局部变量初始值为未审核
            checkingState = rowData.checkingStatus.state;
        }
        if (checkingState == '通过审核') {
            $.messager.alert('消息提示', '您的审核已通过，不可进行修改', 'warn');
        } else {
            //获取到当前选择行的下标（此时rows只有一行，所以是0）
            var index = $(datagridId).datagrid('getRowIndex', rows[0]);
            //开启编辑
            $(datagridId).datagrid('beginEdit', index);
            //把当前开启编辑行赋给变量editRow
            editRow = index;
        }
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
    var checkingState = '未审核';//定义局部变量审核状态为 ‘未审核’
    if (rowData.checkingStatus != null) {//不触动下拉框时，默认是null,所以定义一个局部变量初始值为未审核
        checkingState = rowData.checkingStatus.state;
    }
    if (checkingState == '通过审核') {
        $.messager.alert('消息提示', '您的审核已通过，不可进行修改', 'info');
    } else {
        //开启编辑
        $(datagridId).datagrid('beginEdit', rowIndex);
        //把当前开启编辑行赋给变量editRow
        editRow = rowIndex;
    }
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
            $.messager.alert('消息提示','添加失败', 'error');
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
