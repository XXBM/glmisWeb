/**
 * Created by dell on 2017/2/12.
 */

//这个table不起作用，只是为了让其他datagrid正常显示
$(function () {
    $('#noUse').datagrid({});
});
//定义全局变量（编辑行/插入行/行数据）
var editRow = undefined;
var beingInsertedRow = undefined;
var rowData = undefined;
//获取行
function getRow(dat) {
    var rows = $(dat).datagrid('getRows'); // get current page rows
    rowData = rows[editRow]; // your row data
}
//论文
$(function () {
    $('#ThesisTable').datagrid({
        method: 'get',
        url: basePath() + '/displayOwnThesis',//请求的数据源
        width: 840,
        fit: true,   //自适应大小
        striped: true,//行背景交换
        //nowrap:false,//列内容多时自动折至下一行,对于英文字母、数字以及英文字符无效只识别汉字
        rowNumbers: true,//显示行号
        remoteSort: false,//对本地数据进行排序
        fitColumns: true,//自适应表格大小
        columns: [[
            {field: 'id', title: 'ID', width: 60, hidden: true, id: "id"},
            {
                field: 'title', title: '题目', width: 80, align: 'center',
                editor: {
                    type: 'validatebox',
                    options: {
                        required: true,
                        missingMessage: '该字段不能为空'
                    }
                }
            },
            {
                field: 'name', title: '期刊名称', width: 80, align: 'center',
                editor: {
                    type: 'validatebox',
                    options: {
                        required: true,
                        missingMessage: '该字段不能为空'
                    }
                }
            },
            {
                field: 'journalRank', title: '期刊级别', width: 80, align: 'center',
                formatter: function (value, rec) {//格式化行数据
                    if (rec.journalRank == null) {
                        return '无';
                    }
                    else {
                        return rec.journalRank.description;
                    }
                },
                editor: {
                    type: 'combobox',
                    options: {
                        method: 'get',
                        url: basePath() + '/findAllJournalRanks',
                        valueField: 'id',
                        textField: 'description',
                        panelHeight: 'auto',
                        required: true,
                        onLoadSuccess: function (data) {
                            getRow('#ThesisTable');
                            if (data) {
                                if (rowData.journalRank == null) {//如果没有值,默认选中第一个(相当于添加状态)
                                    $(this).combobox('setValue', data[0].id);
                                } else {//相当于编辑状态
                                    $(this).combobox('setValue', rowData.journalRank.id);
                                }
                            }
                        },
                    }
                },
            },
            {
                field: 'seating', title: '本人位次', width: 80, align: 'center', sortable: true,
                editor: {
                    type: 'numberspinner',//数值微调器（扩展的spinner和numberbox）
                    options: {
                        min: 1,
                        max: 20,
                        required: true,
                        validType: 'Number',
                    }
                },
            },
            {
                field: 'numOfParticipants', title: '参加人数', width: 70, align: 'center', sortable: true,
                editor: {
                    type: 'numberspinner',
                    options: {
                        min: 1,
                        max: 20,
                        required: true,
                        validType: 'Number',
                    }
                },
            },
            {
                field: 'year', title: '发表-年', width: 80, align: 'center', sortable: true,
                editor: {
                    type: 'validatebox',
                    options: {
                        required: true,
                        validType: 'Year',
                        missingMessage: '该字段不能为空'
                    }
                }
            },
            {
                field: 'issue', title: '发表-期', width: 80, align: 'center', sortable: true,
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
                field: 'volume', title: '发表-卷', width: 80, align: 'center', sortable: true,
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
                field: 'startingPageNo', title: '起始页码', width: 80, align: 'center',
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
                field: 'endingPageNo', title: '结束页码', width: 80, align: 'center',
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
                field: 'citation', title: '收录情况', width: 80, align: 'center',
                formatter: function (value, rec) {//格式化行数据
                    if (rec.citation == null) {
                        return '无';
                    }
                    else {
                        return rec.citation.description;
                    }
                },
                editor: {
                    type: 'combobox',
                    options: {
                        method: 'get',
                        url: basePath() + '/findAllCitation',
                        valueField: 'id',
                        textField: 'description',
                        panelHeight: 'auto',
                        required: true,
                        onLoadSuccess: function (data) {
                            getRow('#ThesisTable');
                            if (data) {
                                if (rowData.citation == null) {//如果没有值,默认选中第一个(相当于添加状态)
                                    $(this).combobox('setValue', data[0].id);
                                } else {//相当于编辑状态
                                    $(this).combobox('setValue', rowData.citation.id);
                                }
                            }
                        },
                    }
                },
            },
            {
                field: 'checkingStatus', title: '审核状态', width: 80, align: 'center',
                formatter: function (value, rec) {
                    if (rec.checkingStatus == null) {
                        return '<span style="color:red;">' + '未审核' + '</span>';//格式化显示情况：字体为红色（未审核和修改重申都显示为红色）
                    } else {
                        if (rec.checkingStatus.state == '通过审核') {
                            return rec.checkingStatus.state;
                        } else {
                            return '<span style="color:red;">' + rec.checkingStatus.state + '</span>';
                        }
                    }
                },
            }
        ]],
        toolbar: [{
            id: 'Thesis_add_btn',
            text: '添加', iconCls: 'icon-add',
            handler: function () {
                addOpe("#ThesisTable");
            }
        }, '-', {
            id: 'Thesis_delete_btn',
            text: '删除', iconCls: 'icon-remove',
            handler: function () {
                deleteOpe('#ThesisTable', basePath() + '/deleteThesis?ids=');
            }
        }, '-', {
            id: 'Thesis_update_btn',
            text: '修改', iconCls: 'icon-edit',
            handler: function () {
                updateOpe("#ThesisTable");
            }
        }, '-', {
            id: 'Thesis_save_btn',
            text: '保存', iconCls: 'icon-save',
            handler: function () {
                saveOpe("#ThesisTable");
            }
        }, '-', {
            id: 'Thesis_cancel_btn',
            text: '撤销', iconCls: 'icon-redo',
            handler: function () {
                cancelOpe('#ThesisTable');
            }
        }],
        //endEdit触发后
        onAfterEdit: function (rowIndex, rowData, changes) {//rowIndex：编辑行的索引，从 0 开始;rowData：编辑行对应的记录;changes：更改的字段/值对
            endEditOpe(rowData, basePath() + '/addThesis', basePath() + '/updateThesis', '#ThesisTable');
        },
        onCancelEdit: function (rowIndex, rowData) {
            editRow = undefined;
        },
        //双击一行
        onDblClickRow: function (rowIndex, rowData) {
            if (editRow != undefined) {
                $("#ThesisTable").datagrid('endEdit', editRow);
                //$('#ThesisTable').datagrid('unselectRow',editRow);
            } else {
                dblClickOpe(rowData, rowIndex, "#ThesisTable");
            }
        },
        pagination: "true",//分页
    });
    //分页用的获取datagrid中的详细内容，后台只需要获取easyui自动提供pages和rows（我好像需要一个rows）即可。
    var p = $('#ThesisTable').datagrid('getPager');
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
});
//获奖
$(function () {
    $('#AwardTable').datagrid({
        method: 'get',
        url: basePath() + '/displayOwnAwards',//请求的数据源
        fit: true,   //自适应大小
        striped: true,//行背景交换
        //            nowrap:false,//列内容多时自动折至下一行,对于英文字母、数字以及英文字符无效只识别汉字
        rowNumbers: true,//显示行号
        remoteSort: false,//对本地数据进行排序
        fitColumns: true,//自适应表格大小
        columns: [[
            {field: 'id', title: 'ID', width: 60, hidden: true, id: "id"},
            {
                field: 'awardsRank', title: '级别', width: 70, fixed: true, align: 'center',
                formatter: function (value, rec) {//格式化行数据
                    if (rec.awardsRank == null) {
                        return '无';
                    }
                    else {
                        return rec.awardsRank.description;
                    }
                },
                editor: {
                    type: 'combobox',
                    options: {
                        url: basePath() + '/findAllAwardsRanks',
                        method: 'get',
                        valueField: 'id',
                        textField: 'description',
                        panelHeight: 'auto',
                        required: true,
                        onLoadSuccess: function (data) {
                            getRow('#AwardTable');
                            if (data) {
                                if (rowData.awardsRank == null) {//如果没有值,默认选中第一个
                                    $(this).combobox('setValue', data[0].id);
                                } else {
                                    $(this).combobox('setValue', rowData.awardsRank.id);//要用id!
                                }
                            }
                        },
                    }
                },
            },
            {
                field: 'title', title: '成果名称', width: 100, align: 'center',
                editor: {
                    type: 'validatebox',
                    options: {
                        required: true,
                        missingMessage: '该字段不能为空'
                    }
                }
            },
            {
                field: 'author', title: '代表作者', width: 80, align: 'center',
                editor: {
                    type: 'validatebox',
                    options: {
                        required: true,
                        missingMessage: '该字段不能为空'
                    }
                }
            },
            {
                field: 'name', title: '奖项名称', width: 100, align: 'center',
                editor: {
                    type: 'validatebox',
                    options: {
                        required: true,
                        missingMessage: '该字段不能为空'
                    }
                }
            },
            {
                field: 'awardLevel', title: '等级', width: 80, align: 'center',
                formatter: function (value, rec) {//格式化行数据
                    if (rec.awardLevel == null) {
                        return '无';
                    }
                    else {
                        return rec.awardLevel.description;
                    }
                },
                editor: {
                    type: 'combobox',
                    options: {
                        method: 'get',
                        url: basePath() + '/findAllAwardLevel',
                        valueField: 'id',
                        textField: 'description',
                        panelHeight: 'auto',
                        required: true,
                        onLoadSuccess: function (data) {
                            getRow('#AwardTable');
                            if (data) {
                                if (rowData.awardLevel == null) {//如果没有值,默认选中第一个(相当于添加状态)
                                    $(this).combobox('setValue', data[0].id);
                                } else {//相当于编辑状态
                                    $(this).combobox('setValue', rowData.awardLevel.id);
                                }
                            }
                        },
                    }
                },
            },
            {
                field: 'sponsor', title: '批准部门', width: 70, align: 'center',
                editor: {
                    type: 'validatebox',
                    options: {
                        required: true,
                        missingMessage: '该字段不能为空'
                    }
                }
            },
            {
                field: 'dateOfAward', title: '获奖时间', width: 80, align: 'center', sortable: true,
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
                field: 'seating', title: '本人位次', width: 80, align: 'center', sortable: true,
                editor: {
                    type: 'numberspinner',//数值微调器（扩展的spinner和numberbox）
                    options: {
                        min: 1,
                        max: 20,
                        required: true,
                        validType: 'Number',
                    }
                },
            },
            {
                field: 'numOfParticipants', title: '参加人数', width: 70, align: 'center', sortable: true,
                editor: {
                    type: 'numberspinner',//数值微调器（扩展的spinner和numberbox）
                    options: {
                        min: 1,
                        max: 20,
                        required: true,
                        validType: 'Number',
                    }
                },
            },
            {
                field: 'checkingStatus', title: '状态', width: 80, align: 'center',
                formatter: function (value, rec) {
                    if (rec.checkingStatus == null) {
                        return '<span style="color:red;">' + '未审核' + '</span>';//格式化显示情况：字体为红色（未审核和修改重申都显示为红色）
                    } else {
                        if (rec.checkingStatus.state == '通过审核') {
                            return rec.checkingStatus.state;
                        } else {
                            return '<span style="color:red;">' + rec.checkingStatus.state + '</span>';
                        }
                    }
                }
            }
        ]],
        toolbar: [{
            id: 'Award_add_btn',
            text: '添加', iconCls: 'icon-add',
            handler: function () {
                addOpe('#AwardTable');
            }
        }, '-', {
            id: 'Award_delete_btn',
            text: '删除', iconCls: 'icon-remove',
            handler: function () {
                deleteOpe('#AwardTable', basePath() + '/deleteAwards?ids=');
            }
        }, '-', {
            id: 'Award_update_btn',
            text: '修改', iconCls: 'icon-edit',
            handler: function () {
                updateOpe('#AwardTable');
            }
        }, '-', {
            id: 'Award_save_btn',
            text: '保存', iconCls: 'icon-save',
            handler: function () {
                saveOpe('#AwardTable');
            }
        }, '-', {
            id: 'Award_cancel_btn',
            text: '撤销', iconCls: 'icon-redo',
            handler: function () {
                cancelOpe('#AwardTable');
            }
        }],
        //endEdit触发后
        onAfterEdit: function (rowIndex, rowData, changes) {//rowIndex：编辑行的索引，从 0 开始;rowData：编辑行对应的记录;changes：更改的字段/值对
            endEditOpe(rowData, basePath() + '/addAwards', basePath() + '/updateAwards', '#AwardTable');
        },
        onCancelEdit: function (rowIndex, rowData) {
            editRow = undefined;
        },
        //双击一行
        onDblClickRow: function (rowIndex, rowData) {
            if (editRow != undefined) {
                $('#AwardTable').datagrid('endEdit', editRow);
            } else {
                dblClickOpe(rowData, rowIndex, '#AwardTable');
            }
        },
        pagination: "true",//分页
    });
    //分页用的获取datagrid中的详细内容，后台只需要获取easyui自动提供pages和rows（我好像需要一个rows）即可。
    var p = $('#AwardTable').datagrid('getPager');
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
});
//专著
$(function () {
    $('#MonographTable').datagrid({
        method: 'get',
        url: basePath() + '/displayMonograph',//请求的数据源
        fit: true,   //自适应大小
        striped: true,//行背景交换
        fitColumns: true,//自适应表格大小
        columns: [[
            {field: 'id', width: 60, hidden: true},
            {field: 'employee', hidden: true},
            {
                field: 'name', title: '名称', width: 100, align: 'center',
                editor: {
                    type: 'validatebox',
                    options: {
                        required: true,
                        missingMessage: '该字段不能为空'
                    }
                }
            },
            {
                field: 'author', title: '负责人', width: 80, align: 'center',
                editor: {
                    type: 'validatebox',
                    options: {
                        required: true,
                        missingMessage: '该字段不能为空'
                    }
                }
            },
            {
                field: 'press', title: '出版社', width: 70, align: 'center',
                editor: {
                    type: 'validatebox',
                    options: {
                        required: true,
                        missingMessage: '该字段不能为空'
                    }
                }
            },
            {
                field: 'publicationTime', title: '出版时间', width: 90, align: 'center', sortable: true,
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
                field: 'words', title: '千字数', width: 70, align: 'center', sortable: true,
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
                field: 'monographRank', title: '级别', width: 70, fixed: true, align: 'center',
                formatter: function (value, rec) {//格式化行数据
                    if (rec.monographRank == null) {
                        return '无';
                    }
                    else {
                        return rec.monographRank.description;
                    }
                },
                editor: {
                    type: 'combobox',
                    options: {
                        url: basePath() + '/findAllMonographRanks',
                        method: 'get',
                        valueField: 'id',
                        textField: 'description',
                        panelHeight: 'auto',
                        required: true,
                        onLoadSuccess: function (data) {
                            getRow('#MonographTable');
                            if (data) {
                                if (rowData.monographRank == null) {//如果没有值,默认选中第一个
                                    $(this).combobox('setValue', data[0].id);
                                } else {
                                    $(this).combobox('setValue', rowData.monographRank.id);//要用id!
                                }
                            }
                        },
                    }
                },
            },
            {
                field: 'isbn', title: 'ISBN', width: 70, align: 'center',
                editor: {
                    type: 'validatebox',
                    options: {
                        required: true,
                        missingMessage: '该字段不能为空'
                    }
                }
            },
            {
                field: 'seating', title: '本人位次', width: 80, align: 'center', sortable: true,
                editor: {
                    type: 'numberspinner',//数值微调器（扩展的spinner和numberbox）
                    options: {
                        min: 1,
                        max: 20,
                        validType: 'Number',
                        required: true,
                    }
                },
            },

            {
                field: 'numOfParticipants', title: '作者人数', width: 70, align: 'center', sortable: true,
                editor: {
                    type: 'numberspinner',//数值微调器（扩展的spinner和numberbox）
                    options: {
                        min: 1,
                        max: 20,
                        validType: 'Number',
                        required: true,
                    }
                },
            },
            {
                field: 'checkingStatus', title: '审核状态', width: 80, align: 'center',
                formatter: function (value, rec) {
                    if (rec.checkingStatus == null) {
                        return '<span style="color:red;">' + '未审核' + '</span>';//格式化显示情况：字体为红色（未审核和修改重申都显示为红色）
                    } else {
                        if (rec.checkingStatus.state == '通过审核') {
                            return rec.checkingStatus.state;
                        } else {
                            return '<span style="color:red;">' + rec.checkingStatus.state + '</span>';
                        }
                    }
                },
            }
        ]],
        toolbar: [{
            id: 'Monograph_add_btn',
            text: '添加', iconCls: 'icon-add',
            handler: function () {
                addOpe('#MonographTable');
            }
        }, '-', {
            id: 'Monograph_delete_btn',
            text: '删除', iconCls: 'icon-remove',
            handler: function () {
                deleteOpe('#MonographTable', basePath() + '/deleteMonograph?ids=');
            }
        }, '-', {
            id: 'Monograph_update_btn',
            text: '修改', iconCls: 'icon-edit',
            handler: function () {
                updateOpe('#MonographTable');
            }
        }, '-', {
            id: 'Monograph_save_btn',
            text: '保存', iconCls: 'icon-save',
            handler: function () {
                saveOpe('#MonographTable');
            }
        }, '-', {
            id: 'Monograph_cancel_btn',
            text: '撤销', iconCls: 'icon-redo',
            handler: function () {
                cancelOpe('#MonographTable');
            }
        }],
        //endEdit触发后
        //rowIndex：编辑行的索引，从 0 开始;rowData：编辑行对应的记录;changes：更改的字段/值对
        onAfterEdit: function (rowIndex, rowData, changes) {
            endEditOpe(rowData, basePath() + '/addMonograph', basePath() + '/updateMonograph', '#MonographTable');
        },
        onCancelEdit: function (rowIndex, rowData) {
            editRow = undefined;
        },
        //双击一行
        onDblClickRow: function (rowIndex, rowData) {
            if (editRow != undefined) {
                $("#MonographTable").datagrid('endEdit', editRow);
            } else {
                dblClickOpe(rowData, rowIndex, '#MonographTable');
            }
        },
        pagination: "true",//分页
    });
    //分页用的获取datagrid中的详细内容，后台只需要获取easyui自动提供pages和rows（我好像需要一个rows）即可。
    var p = $('#MonographTable').datagrid('getPager');
    $(p).pagination({
        showPageList: false,
        beforePageText: '第',//页数文本框前显示的汉字
        afterPageText: '页    共 {pages} 页',
        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录',//此处无效
        onBeforefresh: function () {
            $(this).pagination('loading');
            $(this).pagination('loaded');
        }
    });
});
//教材
$(function () {
    $('#TextBookTable').datagrid({
        method: 'get',
        url: basePath() + '/displayTextbook',//请求的数据源
        fit: true,   //自适应大小
        striped: true,//行背景交换
        fitColumns: true,//自适应表格大小
        columns: [[
            {field: 'id', width: 60, hidden: true},
            {field: 'employee', hidden: true},
            {
                field: 'name', title: '名称', width: 100, align: 'center',
                editor: {
                    type: 'validatebox',
                    options: {
                        required: true,
                        missingMessage: '该字段不能为空'
                    }
                }
            },
            {
                field: 'editor', title: '负责人', width: 80, align: 'center',
                editor: {
                    type: 'validatebox',
                    options: {
                        required: true,
                        missingMessage: '该字段不能为空'
                    }
                }
            },
            {
                field: 'press', title: '出版社', width: 70, align: 'center',
                editor: {
                    type: 'validatebox',
                    options: {
                        required: true,
                        missingMessage: '该字段不能为空'
                    }
                }
            },
            {
                field: 'publicationTime', title: '出版时间', width: 90, align: 'center', sortable: true,
                editor: {
                    type: 'datebox',
                    options: {
                        required: true,
                        editable: false,
                        missingMessage: '该字段不能为空'
                    }
                }
            },
            {
                field: 'words', title: '千字数', width: 70, align: 'center', sortable: true,
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
                field: 'textbookRank', title: '级别', width: 70, fixed: true, align: 'center',
                formatter: function (value, rec) {//格式化行数据
                    if (rec.textbookRank == null) {
                        return '无';
                    }
                    else {
                        return rec.textbookRank.description;
                    }
                },
                editor: {
                    type: 'combobox',
                    options: {
                        url: basePath() + '/findAllTextbookRanks',
                        method: 'get',
                        valueField: 'id',
                        textField: 'description',
                        panelHeight: 'auto',
                        required: true,
                        onLoadSuccess: function (data) {
                            getRow('#TextBookTable');
                            if (data) {
                                if (rowData.textbookRank == null) {//如果没有值,默认选中第一个
                                    $(this).combobox('setValue', data[0].id);
                                } else {
                                    $(this).combobox('setValue', rowData.textbookRank.id);//要用id!
                                }
                            }
                        },
                    }
                },
            },
            {
                field: 'isbn', title: 'ISBN', width: 70, align: 'center',
                editor: {
                    type: 'validatebox',
                    options: {
                        required: true,
                        missingMessage: '该字段不能为空'
                    }
                }
            },
            {
                field: 'seating', title: '本人位次', width: 80, align: 'center', sortable: true,
                editor: {
                    type: 'numberspinner',//数值微调器（扩展的spinner和numberbox）
                    options: {
                        min: 1,
                        max: 20,
                        validType: 'Number',
                        required: true,
                    }
                },
            },

            {
                field: 'numOfParticipants', title: '作者人数', width: 70, align: 'center', sortable: true,
                editor: {
                    type: 'numberspinner',//数值微调器（扩展的spinner和numberbox）
                    options: {
                        min: 1,
                        max: 20,
                        validType: 'Number',
                        required: true,
                    }
                },
            },
            {
                field: 'checkingStatus', title: '审核状态', width: 80, align: 'center',
                formatter: function (value, rec) {
                    if (rec.checkingStatus == null) {
                        return '<span style="color:red;">' + '未审核' + '</span>';//格式化显示情况：字体为红色（未审核和修改重申都显示为红色）
                    } else {
                        if (rec.checkingStatus.state == '通过审核') {
                            return rec.checkingStatus.state;
                        } else {
                            return '<span style="color:red;">' + rec.checkingStatus.state + '</span>';
                        }
                    }
                },
            }
        ]],
        toolbar: [{
            id: 'TextBook_add_btn',
            text: '添加', iconCls: 'icon-add',
            handler: function () {
                addOpe('#TextBookTable');
            }
        }, '-', {
            id: 'TextBook_remove_btn',
            text: '删除', iconCls: 'icon-remove',
            handler: function () {
                deleteOpe('#TextBookTable', basePath() + '/deleteTextbook?ids=');
            }
        }, '-', {
            id: 'TextBook_edit_btn',
            text: '修改', iconCls: 'icon-edit',
            handler: function () {
                updateOpe('#TextBookTable');
            }
        }, '-', {
            id: 'TextBook_save_btn',
            text: '保存', iconCls: 'icon-save',
            handler: function () {
                saveOpe('#TextBookTable');
            }
        }, '-', {
            id: 'TextBook_redo_btn',
            text: '撤销', iconCls: 'icon-redo',
            handler: function () {
                cancelOpe('#TextBookTable');
            }
        }],
        //endEdit触发后
        //rowIndex：编辑行的索引，从 0 开始;rowData：编辑行对应的记录;changes：更改的字段/值对
        onAfterEdit: function (rowIndex, rowData, changes) {
            endEditOpe(rowData, basePath() + '/addTextbook', basePath() + '/updateTextbook', '#TextBookTable');
        },
        onCancelEdit: function (rowIndex, rowData) {
            editRow = undefined;
        },
        //双击一行
        onDblClickRow: function (rowIndex, rowData) {
            if (editRow != undefined) {
                $("#TextBookTable").datagrid('endEdit', editRow);
            } else {
                dblClickOpe(rowData, rowIndex, '#TextBookTable');
            }
        },
        pagination: "true",//分页
    });
    //分页用的获取datagrid中的详细内容，后台只需要获取easyui自动提供pages和rows（我好像需要一个rows）即可。
    var p = $('#TextBookTable').datagrid('getPager');
    $(p).pagination({
        showPageList: false,
        beforePageText: '第',//页数文本框前显示的汉字
        afterPageText: '页    共 {pages} 页',
        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录',//此处无效
        onBeforefresh: function () {
            $(this).pagination('loading');
            $(this).pagination('loaded');
        }
    });
});
//横向项目
$(function () {
    $('#PrivateSectorProjectTable').datagrid({
        method: 'get',
        url: basePath() + '/displayOwnProjectFundedByPrivateSector',//请求的数据源
        fit: true,   //自适应大小
        striped: true,//行背景交换
        fitColumns: true,//自适应表格大小
        columns: [[
            {field: 'id', width: 60, hidden: true},
            {field: 'employee', hidden: true},
            {
                field: 'projectFundedByPrivateSectorRank', title: '级别', width: 70, fixed: true, align: 'center',
                formatter: function (value, rec) {//格式化行数据，value是字段的值，rec是行的记录数据  fixed:true??
                    if (rec.projectFundedByPrivateSectorRank == null) {
                        return '无';
                    }
                    else {
                        return rec.projectFundedByPrivateSectorRank.description;
                    }
                },
                editor: {
                    type: 'combobox',
                    options: {
                        url: basePath() + '/findAllProjectFundedByPrivateSectorRanks',
                        method: 'get',
                        valueField: 'id',
                        textField: 'description',
                        panelHeight: 'auto',
                        required: true,
                        onLoadSuccess: function (data) {
                            getRow('#PrivateSectorProjectTable');
                            if (data) {
                                if (rowData.projectFundedByPrivateSectorRank == null) {//如果没有值,默认选中第一个
                                    $(this).combobox('setValue', data[0].id);
                                } else {
                                    $(this).combobox('setValue', rowData.projectFundedByPrivateSectorRank.id);//要用id!
                                }
                            }
                        }
                    }
                }
            },
            {
                field: 'name', title: '名称', width: 100, align: 'center',
                editor: {
                    type: 'validatebox',
                    options: {
                        required: true,
                        missingMessage: '该字段不能为空'
                    }
                }
            },
            {
                field: 'no', title: '编号', width: 80, align: 'center',
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
                field: 'resource', title: '来源', width: 100, align: 'center',
                editor: {
                    type: 'validatebox',
                    options: {
                        required: true,
                        missingMessage: '该字段不能为空'
                    }
                }
            },
            {
                field: 'leader', title: '项目负责人', width: 80, align: 'center',
                editor: {
                    type: 'validatebox',
                    options: {
                        required: true,
                        missingMessage: '该字段不能为空'

                    }
                }
            },
            {
                field: 'sponsor', title: '批准部门', width: 70, align: 'center',
                editor: {
                    type: 'validatebox',
                    options: {
                        required: true,
                        missingMessage: '该字段不能为空'
                    }
                }
            },
            {
                id: 'startTime',
                field: 'startTime', title: '开始时间', width: 90, align: 'center', sortable: true,
                editor: {
                    type: 'datebox',
                    options: {
                        required: true,
                        editable:false,
                        missingMessage: '该字段不能为空',
                        // onSelect:function(date){
                        //     alert(date);
                        // }
                    }

                }
            },
            {
                id: 'endTime',
                field: 'endTime', title: '结束时间', width: 90, align: 'center', sortable: true,
                editor: {
                    type: 'datebox',
                    options: {
                        required: true,
                        editable:false,
                        missingMessage: '该字段不能为空',
                    },
                },
            },
            {
                field: 'expenditure', title: '经费（万）', width: 70, align: 'center', sortable: true,
                editor: {
                    type: 'validatebox',
                    options: {
                        required: true,
                        validType: 'Number',
                        missingMessage: '该字段不能为空'
                    },

                }
            },
            {
                field: 'seating', title: '本人位次', width: 80, align: 'center', sortable: true,
                editor: {
                    type: 'numberspinner',//数值微调器（扩展的spinner和numberbox）
                    options: {
                        min: 1,
                        max: 20,
                        //editable: false,
                        validType: 'Number',
                        required: true
                    }
                }
            },
            {
                field: 'numOfParticipants', title: '参加人数', width: 70, align: 'center', sortable: true,
                editor: {
                    type: 'numberspinner',//数值微调器（扩展的spinner和numberbox）
                    options: {
                        min: 1,
                        max: 20,
                        //editable: false,
                        validType: 'Number',
                        required: true
                    }
                }
            },
            {
                field: 'checkingStatus', title: '状态', width: 80, align: 'center',
                formatter: function (value, rec) {
                    if (rec.checkingStatus == null) {
                        return '<span style="color:red;">' + '未审核' + '</span>';//格式化显示情况：字体为红色（未审核和修改重申都显示为红色）
                    } else {
                        if (rec.checkingStatus.state == '通过审核') {
                            return rec.checkingStatus.state;
                        } else {
                            return '<span style="color:red;">' + rec.checkingStatus.state + '</span>';
                        }
                    }
                }
            }
        ]],
        toolbar: [{
            id:'ProjectFundedByPrivateSector_add_btn',
            text: '添加', iconCls: 'icon-add',
            handler: function () {
                addOpe('#PrivateSectorProjectTable');
            }
        }, '-', {
            id:'ProjectFundedByPrivateSector_remove_btn',
            text: '删除', iconCls: 'icon-remove',
            handler: function () {
                deleteOpe('#PrivateSectorProjectTable', basePath() + '/deleteProjectFundedByPrivateSector?ids=');
            }
        }, '-', {
            id:'ProjectFundedByPrivateSector_edit_btn',
            text: '修改', iconCls: 'icon-edit',
            handler: function () {
                updateOpe('#PrivateSectorProjectTable');
            }
        }, '-', {
            id:'ProjectFundedByPrivateSector_save_btn',
            text: '保存', iconCls: 'icon-save',
            handler: function () {
                saveOpe('#PrivateSectorProjectTable');
            }
        }, '-', {
            id:'ProjectFundedByPrivateSector_redo_btn',
            text: '撤销', iconCls: 'icon-redo',
            handler: function () {
                cancelOpe('#PrivateSectorProjectTable');
            }
        }],
        //endEdit触发后
        onAfterEdit: function (rowIndex, rowData, changes) {//rowIndex：编辑行的索引，从 0 开始;rowData：编辑行对应的记录;changes：更改的字段/值对
            endEditOpe(rowData, basePath() + '/addProjectFundedByPrivateSector', basePath() + '/updateProjectFundedByPrivateSector', '#PrivateSectorProjectTable');
        },
        onCancelEdit: function (rowIndex, rowData) {
            editRow = undefined;
        },
        //双击一行
        onDblClickRow: function (rowIndex, rowData) {
            if (editRow != undefined) {
                $("#PrivateSectorProjectTable").datagrid('endEdit', editRow);
            } else {
                dblClickOpe(rowData, rowIndex, '#PrivateSectorProjectTable');
            }
        },
        pagination: "true",//分页
    });
    //分页用的获取datagrid中的详细内容，后台只需要获取easyui自动提供pages和rows（我好像需要一个rows）即可。
    var p = $('#PrivateSectorProjectTable').datagrid('getPager');
    $(p).pagination({
        showPageList: false,
        beforePageText: '第',//页数文本框前显示的汉字
        afterPageText: '页    共 {pages} 页',
        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录',//此处无效
        onBeforefresh: function () {
            $(this).pagination('loading');
            $(this).pagination('loaded');
        }
    });
});
//纵向项目
$(function () {
    $('#GovernmentProjectTable').datagrid({
        method: 'get',
        url: basePath() + '/displayOwnProjectFundedByGovernment',//请求的数据源
        fit: true,   //自适应大小
        striped: true,//行背景交换
        fitColumns: true,//自适应表格大小
        columns: [[
            {field: 'id', width: 60, hidden: true},
            {field: 'employee', hidden: true},
            {
                field: 'projectFundedByGovernmentRank', title: '级别', width: 70, fixed: true, align: 'center',
                formatter: function (value, rec) {//格式化行数据，value是字段的值，rec是行的记录数据  fixed:true??
                    if (rec.projectFundedByGovernmentRank == null) {
                        return '无';
                    }
                    else {
                        return rec.projectFundedByGovernmentRank.description;
                    }
                },
                editor: {
                    type: 'combobox',
                    options: {
                        url: basePath() + '/findAllProjectFundedByGovernmentRanks',
                        method: 'get',
                        valueField: 'id',
                        textField: 'description',
                        panelHeight: 'auto',
                        required: true,
                        onLoadSuccess: function (data) {
                            getRow('#GovernmentProjectTable');
                            if (data) {
                                if (rowData.projectFundedByGovernmentRank == null) {//如果没有值,默认选中第一个
                                    $(this).combobox('setValue', data[0].id);
                                } else {
                                    $(this).combobox('setValue', rowData.projectFundedByGovernmentRank.id);//要用id!
                                }
                            }
                        }
                    }
                }
            },
            {
                field: 'name', title: '名称', width: 100, align: 'center',
                editor: {
                    type: 'validatebox',
                    options: {
                        required: true,
                        missingMessage: '该字段不能为空'
                    }
                }
            },
            {
                field: 'no', title: '编号', width: 80, align: 'center',
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
                field: 'resource', title: '来源', width: 100, align: 'center',
                editor: {
                    type: 'validatebox',
                    options: {
                        required: true,
                        missingMessage: '该字段不能为空'
                    }
                }
            },
            {
                field: 'leader', title: '项目负责人', width: 80, align: 'center',
                editor: {
                    type: 'validatebox',
                    options: {
                        required: true,
                        missingMessage: '该字段不能为空'

                    }
                }
            },
            {
                field: 'sponsor', title: '批准部门', width: 70, align: 'center',
                editor: {
                    type: 'validatebox',
                    options: {
                        required: true,
                        missingMessage: '该字段不能为空'
                    }
                }
            },
            {
                id: 'startTime',
                field: 'startTime', title: '开始时间', width: 90, align: 'center', sortable: true,
                editor: {
                    type: 'datebox',
                    options: {
                        required: true,
                        editable: false,
                        missingMessage: '该字段不能为空',
                        // onSelect:function(date){
                        //     alert(date);
                        // }
                    }

                }
            },
            {
                id: 'endTime',
                field: 'endTime', title: '结束时间', width: 90, align: 'center', sortable: true,
                editor: {
                    type: 'datebox',
                    options: {
                        required: true,
                        editable: false,
                        missingMessage: '该字段不能为空',
                    },
                },
            },
            {
                field: 'expenditure', title: '经费（万）', width: 70, align: 'center', sortable: true,
                editor: {
                    type: 'validatebox',
                    options: {
                        required: true,
                        validType: 'Number',
                        missingMessage: '该字段不能为空'
                    },

                }
            },
            {
                field: 'seating', title: '本人位次', width: 80, align: 'center', sortable: true,
                editor: {
                    type: 'numberspinner',//数值微调器（扩展的spinner和numberbox）
                    options: {
                        min: 1,
                        max: 20,
                        //editable: false,
                        validType: 'Number',
                        required: true
                    }
                }
            },
            {
                field: 'numOfParticipants', title: '参加人数', width: 70, align: 'center', sortable: true,
                editor: {
                    type: 'numberspinner',//数值微调器（扩展的spinner和numberbox）
                    options: {
                        min: 1,
                        max: 20,
                        //editable: false,
                        validType: 'Number',
                        required: true
                    }
                }
            },
            {
                field: 'checkingStatus', title: '状态', width: 80, align: 'center',
                formatter: function (value, rec) {
                    if (rec.checkingStatus == null) {
                        return '<span style="color:red;">' + '未审核' + '</span>';//格式化显示情况：字体为红色（未审核和修改重申都显示为红色）
                    } else {
                        if (rec.checkingStatus.state == '通过审核') {
                            return rec.checkingStatus.state;
                        } else {
                            return '<span style="color:red;">' + rec.checkingStatus.state + '</span>';
                        }
                    }
                }
            }
        ]],
        toolbar: [{
            id:'ProjectFundedByGovernment_add_btn',
            text: '添加', iconCls: 'icon-add',
            handler: function () {
                addOpe('#GovernmentProjectTable');
            }
        }, '-', {
            id:'ProjectFundedByGovernment_remove_btn',
            text: '删除', iconCls: 'icon-remove',
            handler: function () {
                deleteOpe('#GovernmentProjectTable', basePath() + '/deleteProjectFundedByGovernment?ids=');
            }
        }, '-', {
            id:'ProjectFundedByGovernment_edit_btn',
            text: '修改', iconCls: 'icon-edit',
            handler: function () {
                updateOpe('#GovernmentProjectTable');
            }
        }, '-', {
            id:'ProjectFundedByGovernment_save_btn',
            text: '保存', iconCls: 'icon-save',
            handler: function () {
                saveOpe('#GovernmentProjectTable');
            }
        }, '-', {
            id:'ProjectFundedByGovernment_redo_btn',
            text: '撤销', iconCls: 'icon-redo',
            handler: function () {
                cancelOpe('#GovernmentProjectTable');
            }
        }],
        //endEdit触发后
        onAfterEdit: function (rowIndex, rowData, changes) {//rowIndex：编辑行的索引，从 0 开始;rowData：编辑行对应的记录;changes：更改的字段/值对
            endEditOpe(rowData, basePath() + '/addProjectFundedByGovernment', basePath() + '/updateProjectFundedByGovernment', '#GovernmentProjectTable');
        },
        onCancelEdit: function (rowIndex, rowData) {
            editRow = undefined;
        },
        //双击一行
        onDblClickRow: function (rowIndex, rowData) {
            if (editRow != undefined) {
                $("#GovernmentProjectTable").datagrid('endEdit', editRow);
            } else {
                dblClickOpe(rowData, rowIndex, '#GovernmentProjectTable');
            }
        },
        pagination: "true",//分页
    });
    //分页用的获取datagrid中的详细内容，后台只需要获取easyui自动提供pages和rows（我好像需要一个rows）即可。
    var p = $('#GovernmentProjectTable').datagrid('getPager');
    $(p).pagination({
        showPageList: false,
        beforePageText: '第',//页数文本框前显示的汉字
        afterPageText: '页    共 {pages} 页',
        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录',//此处无效
        onBeforefresh: function () {
            $(this).pagination('loading');
            $(this).pagination('loaded');
        }
    });
});