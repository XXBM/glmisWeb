//这个table不起作用，只是为了让其他datagrid正常显示
$(function () {
    $('#noUse').datagrid({});
});
//定义全局变量（编辑行/插入行/行数据）
var editRow = undefined;
var beingInsertedRow = undefined;
var rowData = undefined;
var emplId = undefined;
//获取行
function getRow(dat) {
    var rows = $(dat).datagrid('getRows'); // get current page rows
    rowData = rows[editRow]; // your row data
}
//显示职员列表
$(function () {
    $('#staff').datagrid({
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
            isSaveStaff = true;//点击保存后，添加状态true，保存状态为false，不进行保存，直接选中一行，设置保存状态为true
            emplId = rowData.id;
            $('#partyInfo').datagrid('reload', basePath() + '/displayAllParty?id=' + emplId);
            displayInfor(rowData);//获取数据显示到个人信息的表单
        },
        onLoadSuccess: function (data) {
            //当没有职员时
            if (data.rows.length == 0) {
                if (isAddStaff == false) {
                    $.messager.alert('消息提示', '首次进入页面，务必先点击添加按钮才可进行职员的添加！', 'info');
                    $('#empInfoTabs').hide();//隐藏tabs
                    //jquery效果（显示和隐藏）
                    $('#saveStaff').hide();
                }
            }
            //当有datagrid列表有数据时
            if (data) {
                if (isAddStaff == false) {
//                                        alert('添加状态' + isAddStaff + isSaveStaff);
                    $('#staff').datagrid("selectRow", 0);
                    displayInfor();//显示职员信息
                    if (isSaveStaff) {
                        if (editRowIndex == -1) {
//                                            alert('添加后保存状态' + isAddStaff + isSaveStaff + editRowIndex);
//                                            alert('下标' + data.rows.length-1);
                            $('#staff').datagrid("selectRow", data.rows.length - 1);
                            displayInfor();//显示职员信息
                        }
                        if (editRowIndex != -1) {
//                                            alert('33333----' + editRowIndex);
                            $('#staff').datagrid("selectRow", editRowIndex);
                            displayInfor();//显示职员信息
                        }
                    }
                }
                if (isAddStaff) {
//                                    alert('添加状态' + isAddStaff + isSaveStaff);
                    //添加用户状态下，图片显示为空
                    $('#Employee_Img').attr('src', '');//设置属性值
                    if (isSaveStaff) {
                        if (editRowIndex == -1) {
//                                            alert('添加后保存状态' + isAddStaff + isSaveStaff + editRowIndex);
                            $('#staff').datagrid("selectRow", data.rows.length - 1);
                            displayInfor();//显示职员信息
                        }
                        if (editRowIndex != -1) {
//                                            alert('222222');
                            $('#staff').datagrid("selectRow", editRowIndex);
                            displayInfor();//显示职员信息
                        }
                    }
                }
            }
        },
    });
    //分页用的获取datagrid中的详细内容，后台只需要获取easyui自动提供pages和rows（我好像需要一个rows）即可。
    var p = $('#staff').datagrid('getPager');
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
$(function () {
    $('#partyInfo').datagrid({
        method: 'get',
        url: basePath() + '/displayAllParty?id=' + emplId,
        width: 270,
        height: 420,
        hidden: true,
        singleSelect: true,
        rowNumbers: "true",//显示行号
        remoteSort: false,//对本地数据进行排序
        fitColumns: true,//自适应表格大小
        columns: [[
            {field: 'pid', title: 'id'},
        ]],
        onClickRow: function (rowIndex, rowData) {
        },
        onLoadSuccess: function (data) {
        },
    });
    //分页用的获取datagrid中的详细内容，后台只需要获取easyui自动提供pages和rows（我好像需要一个rows）即可。
    var p = $('#staff').datagrid('getPager');
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
//显示职员信息
function displayInfor(rowData) {
    var row = $('#staff').datagrid("getSelected");//获取当前选中行
    $("#Actor").form("load", row);
    if (row == null) {
        $('#Actor').form('clear');
        $('#party').form('clear');
        //如果没选中行，不显示图片，图片显示处为空
        $('#Employee_Img').attr('src', '');
        showMsgNoEmp();
    }
    if (row) {
        $('#saveStaff').show();
        $('#empInfoTabs').show();//显示tabs
        $('#ageDiv').show();
        $('#workAgeDiv').show();
        //该职员有部门的时候在显示
        if (row.sex) {
            $('#Employee_sex_combobox').combobox('setValue', row.sex.id);
        }
        if (row.department) {
            $('#Employee_department_combobox').combobox('setValue', row.department.id);
        }
        if (row.nation) {
            $('#Employee_nation_combobox').combobox('setValue', row.nation.id);
        }
        if (row.politicalParty) {
            $('#Employee_politicalParty_combobox').combobox('setValue', row.politicalParty.id);
        }
        if (row.employmentCategory) {
            $('#Employee_employmentCategory_combobox').combobox('setValue', row.employmentCategory.id);
        }
        if (row.overseasChineseOrNot) {
            $('#Employee_overseasChineseOrNot_combobox').combobox('setValue', row.overseasChineseOrNot.id);
        }
        if (row.photo == null) {
            $('#Employee_Img').attr('src', '');//设置属性值
        }
        if (row.photo == "") {
            $('#Employee_Img').attr('src', '');//设置属性值
        }
        if (row.photo) {
            //显示图片
            showImg(row);
        }
        if (row.user) {
            $('#Employee_user_valkidatebox').val(row.user.id);//绑定值，用id
        }
        //根据职员id显示相关信息
        showMsgByEmp(rowData);
    }
}
function displayParty(rowData) {
    $("#partyInfo").datagrid("selectAll");
    var row2 = $('#partyInfo').datagrid("getSelected");//获取当前选中行
    $("#party").form("load", row2);
    if (row2 == null) {
//                    $('#saveStaff').hide();
        $('#party').form('clear');
//              如果没选中行，不显示图片，图片显示处为空
    }
    if (row2) {
        if (row2.politicalParty) {
            $('#Employee_politicalParty_combobox').combobox('setValue', row2.politicalParty.id);
        }
    }

}
//通过职员id，显示职员其他信息
function showMsgByEmp(row) {
    // 根据职员id显示相关信息
    var degreeUrl = basePath() + '/displayDegByEmp?id=' + row.id;
    $('#employeeAssAcademicDegree').datagrid('reload', degreeUrl);
    var educationUrl = basePath() + '/displayEduByEmp?id=' + row.id;
    $('#employeeAssEducationQualification').datagrid('reload', educationUrl);
    var qualificationUrl = basePath() + '/displayQuaByEmp?id=' + row.id;
    $('#qualification').datagrid('reload', qualificationUrl);
    var rewardUrl = basePath() + '/displayRewByEmp?id=' + row.id;
    $('#reward').datagrid('reload', rewardUrl);
    var employmentRecordUrl = basePath() + '/displayRecByEmp?id=' + row.id;
    $('#schoolworkExperience').datagrid('reload', employmentRecordUrl);
    var employmentPostUrl = basePath() + '/displayPostByEmp?id=' + row.id;
    $('#employmentPost').datagrid('reload', employmentPostUrl);
    var academicConferenceUrl = basePath() + '/displayAcademicConferenceById?id=' + row.id;
    $('#academicConference').datagrid('reload', academicConferenceUrl);
    var visitingStudyUrl = basePath() + '/displayStuByEmp?id=' + row.id;
    $('#visitingStudy').datagrid('reload', visitingStudyUrl);
    var EmployeeAssProfessionalTitleUrl = basePath() + '/displayNameByEmp?id=' + row.id;
    $('#employeeAssProfessionalTitle').datagrid('reload', EmployeeAssProfessionalTitleUrl);
    var previousExperiencesUrl = basePath() + '/displayBeExByEmp?id=' + row.id;
    $('#previousExperiences').datagrid('reload', previousExperiencesUrl);
}
//没有职员id，不显示职员其他信息
function showMsgNoEmp() {
    var degreeUrl = basePath() + '/displayDegByEmp?id=' + '-1';
    $('#employeeAssAcademicDegree').datagrid('reload', degreeUrl);
    var educationUrl = basePath() + '/displayEduByEmp?id=' + '-1';
    $('#employeeAssEducationQualification').datagrid('reload', educationUrl);
    var qualificationUrl = basePath() + '/displayQuaByEmp?id=' + '-1';
    $('#qualification').datagrid('reload', qualificationUrl);
    var rewardUrl = basePath() + '/displayRewByEmp?id=' + '-1';
    $('#reward').datagrid('reload', rewardUrl);
    var employmentRecordUrl = basePath() + '/displayRecByEmp?id=' + '-1';
    $('#schoolworkExperience').datagrid('reload', employmentRecordUrl);
    var employmentPostUrl = basePath() + '/displayPostByEmp?id=' + '-1';
    $('#employmentPost').datagrid('reload', employmentPostUrl);
    var academicConferenceUrl = basePath() + '/displayAcademicConferenceById?id=' + '-1';
    $('#academicConference').datagrid('reload', academicConferenceUrl);
    var visitingStudyUrl = basePath() + '/displayStuByEmp?id=' + '-1';
    $('#visitingStudy').datagrid('reload', visitingStudyUrl);
    var EmployeeAssProfessionalTitleUrl = basePath() + '/displayNameByEmp?id=' + '-1';
    $('#employeeAssProfessionalTitleUrl').datagrid('reload', EmployeeAssProfessionalTitleUrl);
    var previousExperiencesUrl = basePath() + '/displayBeExByEmp?id=' + '-1';
    $('#previousExperiences').datagrid('reload', previousExperiencesUrl);
}
//学术会议
$(function () {
    $('#academicConference').datagrid({
        method: 'get',
        width: 730,
        height: 390,
        singleSelect: true,
        rowNumbers: true,//显示行号
        remoteSort: false,//对本地数据进行排序
        fitColumns: true,//自适应表格大小
        frozenColumns: [[{field: "chk", "checkbox": true}]],//单选框
        columns: [[

            {field: 'id', title: 'id', hidden: true},
            {
                field: 'startTime', title: '开始时间', width: 150, align: 'center',sortable: true,
                editor: {type: 'datebox', options: {required: true, editable:false, missingMessage: '该字段不能为空'}}
            },

            {
                field: 'endTime', title: '结束时间', width: 150, align: 'center',sortable: true,
                editor: {type: 'datebox', options: {required: true,editable:false, missingMessage: '该字段不能为空'}}
            },
            {
                field: 'name', title: '会议名称', width: 150, align: 'center',
                editor: {type: 'validatebox', options: {required: true, missingMessage: '该字段不能为空'}}
            },
            {
                field: 'host',
                formatter: function (value, rec) {
                    if (rec.host == null) {
                        return "未选择";
                    }
                    else {
                        return rec.host.description;
                    }
                }
                , title: '主办机构', width: 150, align: 'center',
                editor: {
                    type: 'combobox', options: {
                        required: true,
                        method: 'get',
                        url: basePath() + '/findAllHostOrganization',
                        valueField: 'id',
                        textField: 'description',
                        editable: false,//可编辑为false
                        //启用编辑时的下拉框回显
                        onLoadSuccess: function (data) {
                            getRow('#academicConference');
                            if (data) {
                                //editingRow是编辑时选中的行数据，
                                if (rowData.host == null) {
                                    //如果编辑行的host为null, 显示下拉数据的第一条
                                    $(this).combobox('setValue', data[0].id);
                                } else {
                                    //如果不为null，显示回显的数据
                                    $(this).combobox('setValue', rowData.host.id);
                                }
                            }
                        }
                    }
                }
            },
            {
                field: 'reportTime', title: '汇报时间', width: 150, align: 'center',
                editor: {type: 'datebox', options: {required: true, editable:false, missingMessage: '该字段不能为空'}}
            },
            {
                field: 'reviewResult',
                formatter: function (value, rec) {
                    if (rec.reviewResult == null) {
                        return '未选择';
                    }
                    else {
                        return rec.reviewResult.description;
                    }
                }, title: '审核结果', width: 100, align: 'center',
                editor: {
                    type: 'combobox', options: {
                        required: true,
                        method: 'get',
                        url: basePath() + '/findAllCheck',
                        valueField: 'id',
                        textField: 'description',
                        editable: false,//可编辑为false
                        //启用编辑时的下拉框回显
                        onLoadSuccess: function (data) {
                            getRow('#academicConference');
                            if (data) {
                                //editingRow是编辑时选中的行数据，
                                if (rowData.reviewResult == null) {
                                    //如果编辑行的host为null, 显示下拉数据的第一条
                                    $(this).combobox('setValue', data[0].id);
                                } else {
                                    //如果不为null，显示回显的数据
                                    $(this).combobox('setValue', rowData.reviewResult.id);
                                }
                            }
                        }
                    }
                }
            },
        ]],
        toolbar: [{
            id: 'AcademicConference_add_btn', text: '添加', iconCls: 'icon-add',
            handler: function () {
                addOpe("#academicConference");
            }
        }, '-', {
            id: 'AcademicConference_delete_btn', text: '删除', iconCls: 'icon-remove',
            handler: function () {
                deleteOpe('#academicConference', basePath() + '/deleteAcademicConference?id=');
            }
        }, '-', {
            id: 'AcademicConference_update_btn', text: '修改', iconCls: 'icon-edit',
            handler: function () {
                updateOpe("#academicConference");
            }
        }, '-', {
            id: 'AcademicConference_save_btn', text: '保存', iconCls: 'icon-save',
            handler: function () {
                saveOpe("#academicConference");
            }
        }, '-', {
            id: 'AcademicConference_cancel_btn', text: '撤销', iconCls: 'icon-redo',
            handler: function () {
                cancelOpe('#academicConference');
            }
        }],
        onAfterEdit: function (rowIndex, rowData) {
            endEditOpe(rowData, basePath() + '/addAcademicConference'
                , basePath() + '/updateAcademicConference', '#academicConference');
        },
        onCancelEdit: function (rowIndex, rowData) {
            editRow = undefined;
        },
        //双击一行
        onDblClickRow: function (rowIndex, rowData) {
            if (editRow != undefined) {
                $("#academicConference").datagrid('endEdit', editRow);
            } else {
                dblClickOpe(rowData, rowIndex, "#academicConference");
            }
        },
        pagination: "true"//分页
    });
//分页用的获取datagrid中的详细内容，后台只需要获取easyui自动提供pages和rows（我好像需要一个rows）即可。
    var p = $('#visitingAcademic').datagrid('getPager');
    $(p).pagination({
        showPageList: false,
        beforePageText: '第',//页数文本框前显示的汉字
        afterPageText: '页    共 {pages} 页',
        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录',
    });
});
//学位信息
$(function () {
    $('#employeeAssAcademicDegree').datagrid({
        method: 'get',
        width: 730,
        height: 390,
        singleSelect: true,
        rowNumbers: "true",//显示行号
        remoteSort: false,//对本地数据进行排序
        fitColumns: true,//自适应表格大小
        frozenColumns: [[{field: "chk", "checkbox": true}]],//单选框
        columns: [[
            {field: 'id', title: 'id', hidden: true},
            {field: 'employee', hidden: true},
            {
                field: 'academicDegreeType', title: '学科', width: 80, align: 'center',
                formatter: function (value, rec) {
                    if (rec.academicDegreeType == null) {
                        return '未审核';
                    }
                    else {
                        return rec.academicDegreeType.description;
                    }
                },
                editor: {
                    type: 'combobox',
                    options: {
                        method: 'get',
                        url: basePath() + '/findAllAcademicDegreeTypes',
                        valueField: 'id',
                        textField: 'description',
                        panelHeight: 'auto',
                        required: true,
                        onLoadSuccess: function (data) {
                            getRow('#employeeAssAcademicDegree');
                            if (data) {
                                if (rowData.academicDegreeType == null) {//如果没有值,默认选中第一个(相当于添加状态)
                                    $(this).combobox('setValue', data[0].id);
                                } else {//相当于编辑状态
                                    $(this).combobox('setValue', rowData.academicDegreeType.id);
                                }
                            }
                        },
                    }
                },
            },
            {
                field: 'academicDegree', title: '学位', width: 80, align: 'center',
                formatter: function (value, rec) {
                    if (rec.academicDegree == null) {
                        return '未审核';
                    }
                    else {
                        return rec.academicDegree.description;
                    }
                },
                editor: {
                    type: 'combobox',
                    options: {
                        method: 'get',
                        url: basePath() + '/findAllAcademicDegrees',
                        valueField: 'id',
                        textField: 'description',
                        panelHeight: 'auto',
                        required: true,
                        onLoadSuccess: function (data) {
                            getRow('#employeeAssAcademicDegree');
                            if (data) {
                                if (rowData.academicDegree == null) {//如果没有值,默认选中第一个(相当于添加状态)
                                    $(this).combobox('setValue', data[0].id);
                                } else {//相当于编辑状态
                                    $(this).combobox('setValue', rowData.academicDegree.id);
                                }
                            }
                        },
                    }
                },
            },
            {
                field: 'grantedDate', title: '获得时间', width: 80, align: 'center', sortable: true,
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
                field: 'major', title: '专业', width: 80, align: 'center',
                editor: {
                    type: 'validatebox',
                    options: {
                        required: true,
                        missingMessage: '该字段不能为空'
                    }
                }
            },
            {
                field: 'certificateNo', title: '证书编号', width: 80, align: 'center',
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
                field: 'university', title: '毕业学校', width: 80, align: 'center',
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
            id: 'EmployeeAssAcademicDegree_add_btn', text: '添加', iconCls: 'icon-add',
            handler: function () {
                addOpe("#employeeAssAcademicDegree");
            }
        }, '-', {
            id: 'EmployeeAssAcademicDegree_delete_btn', text: '删除', iconCls: 'icon-remove',
            handler: function () {
                deleteOpe('#employeeAssAcademicDegree', basePath() + '/deleteDegree?ids=');
            }
        }, '-', {
            id: 'EmployeeAssAcademicDegree_update_btn', text: '修改', iconCls: 'icon-edit',
            handler: function () {
                updateOpe("#employeeAssAcademicDegree");
            }
        }, '-', {
            id: 'EmployeeAssAcademicDegree_save_btn', text: '保存', iconCls: 'icon-save',
            handler: function () {
                saveOpe("#employeeAssAcademicDegree");
            }
        }, '-', {
            id: 'EmployeeAssAcademicDegree_cancel_btn', text: '撤销', iconCls: 'icon-redo',
            handler: function () {
                cancelOpe('#employeeAssAcademicDegree');
            }
        }],
        //endEdit触发后
        onAfterEdit: function (rowIndex, rowData, changes) {//rowIndex：编辑行的索引，从 0 开始;rowData：编辑行对应的记录;changes：更改的字段/值对
            endEditOpe(rowData, basePath() + '/addDegree', basePath() + '/updateDegree', '#employeeAssAcademicDegree');
        },
        onCancelEdit: function (rowIndex, rowData) {
            editRow = undefined;
        },
        //双击一行
        onDblClickRow: function (rowIndex, rowData) {
            if (editRow != undefined) {
                $("#employeeAssAcademicDegree").datagrid('endEdit', editRow);
            } else {
                dblClickOpe(rowData, rowIndex, "#employeeAssAcademicDegree");
            }
        },
        pagination: "true"//分页
    });
    //分页用的获取datagrid中的详细内容，后台只需要获取easyui自动提供pages和rows（我好像需要一个rows）即可。
    var p = $('#employeeAssAcademicDegree').datagrid('getPager');
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
//在校工作经历
$(function () {
    $('#schoolworkExperience').datagrid({
        method: 'get',
        width: 730,
        height: 390,
        singleSelect: true,
        rowNumbers: "true",//显示行号
        remoteSort: false,//对本地数据进行排序
        fitColumns: true,//自适应表格大小
        frozenColumns: [[{field: "chk", "checkbox": true}]],//单选框
        columns: [[
            {field: 'id', title: 'id', hidden: true},

            {
                field: 'startTime', title: '开始时间', width: 100, align: 'center',sortable: true,
                editor: {type: 'datebox', options: {required: true, editable:false, missingMessage: '该字段不能为空'}}
            },
            {
                field: 'endTime', title: '结束时间', width: 100, align: 'center',sortable: true,
                editor: {type: 'datebox', options: {required: true, editable:false, missingMessage: '该字段不能为空'}}
            },
            {
                field: 'school', title: '学院', width: 80, align: 'center',
                formatter: function (value, rec) {
                    if (rec.school == null) {
                        return '未选择';
                    }
                    else {
                        return rec.school.schoolName;
                    }
                },
                editor: {
                    type: 'combobox',
                    options: {
                        method: 'get',
                        url: basePath() + '/findAllSchoolName',
                        valueField: 'id',
                        textField: 'schoolName',
                        panelHeight: 'auto',
                        required: true,
                        onLoadSuccess: function (data) {
                            getRow('#schoolworkExperience');
                            if (data) {
                                if (rowData.school == null) {//如果没有值,默认选中第一个(相当于添加状态)
                                    $(this).combobox('setValue', data[0].id);
                                } else {//相当于编辑状态
                                    $(this).combobox('setValue', rowData.school.id);
                                }
                            }
                        },
                    }
                },
            },
            {
                field: 'post', title: '担任职务', width: 80, align: 'center',
                formatter: function (value, rec) {
                    if (rec.post == null) {
                        return '未选择';
                    }
                    else {
                        return rec.post.description;
                    }
                },
                editor: {
                    type: 'combobox',
                    options: {
                        method: 'get',
                        url: basePath() + '/findAllPost',
                        valueField: 'id',
                        textField: 'description',
                        panelHeight: 'auto',
                        required: true,
                        onLoadSuccess: function (data) {
                            getRow('#schoolworkExperience');
                            if (data) {
                                if (rowData.post == null) {//如果没有值,默认选中第一个(相当于添加状态)
                                    $(this).combobox('setValue', data[0].id);
                                } else {//相当于编辑状态
                                    $(this).combobox('setValue', rowData.post.id);
                                }
                            }
                        },
                    }
                },
            },
            {
                field: 'positionRank', title: '级别', width: 80, align: 'center',
                formatter: function (value, rec) {
                    if (rec.positionRank == null) {
                        return '未选择';
                    }
                    else {
                        return rec.positionRank.description;
                    }
                },
                editor: {
                    type: 'combobox',
                    options: {
                        method: 'get',
                        url: basePath() + '/findAllRank',
                        valueField: 'id',
                        textField: 'description',
                        panelHeight: 'auto',
                        required: true,
                        onLoadSuccess: function (data) {
                            getRow('#schoolworkExperience');
                            if (data) {
                                if (rowData.positionRank == null) {//如果没有值,默认选中第一个(相当于添加状态)
                                    $(this).combobox('setValue', data[0].id);
                                } else {//相当于编辑状态
                                    $(this).combobox('setValue', rowData.positionRank.id);
                                }
                            }
                        },
                    }
                },
            },
        ]],
        toolbar: [{
            id: 'SchoolWorkExperience_add_btn', text: '添加', iconCls: 'icon-add',
            handler: function () {
                addOpe("#schoolworkExperience");
            }
        }, '-', {
            id: 'SchoolWorkExperience_delete_btn', text: '删除', iconCls: 'icon-remove',
            handler: function () {
                deleteOpe('#schoolworkExperience', basePath() + '/deleteEmploymentRecord?id=');
            }
        }, '-', {
            id: 'SchoolWorkExperience_update_btn', text: '修改', iconCls: 'icon-edit',
            handler: function () {
                updateOpe("#schoolworkExperience");
            }
        }, '-', {
            id: 'SchoolWorkExperience_save_btn', text: '保存', iconCls: 'icon-save',
            handler: function () {
                saveOpe("#schoolworkExperience");
            }
        }, '-', {
            id: 'SchoolWorkExperience_cancel_btn', text: '撤销', iconCls: 'icon-redo',
            handler: function () {
                cancelOpe('#schoolworkExperience');
            }
        }],
        pagination: "true",//分页
        //endEdit触发后
        onAfterEdit: function (rowIndex, rowData, changes) {//rowIndex：编辑行的索引，从 0 开始;rowData：编辑行对应的记录;changes：更改的字段/值对
            endEditOpe(rowData, basePath() + '/addEmploymentRecord', basePath() + '/addEmploymentRecord', '#schoolworkExperience');
        },
        onCancelEdit: function (rowIndex, rowData) {
            editRow = undefined;
        },
        //双击一行
        onDblClickRow: function (rowIndex, rowData) {
            if (editRow != undefined) {
                $("#schoolworkExperience").datagrid('endEdit', editRow);
            } else {
                dblClickOpe(rowData, rowIndex, "#schoolworkExperience");
            }
        },
    });
    //分页用的获取datagrid中的详细内容，后台只需要获取easyui自动提供pages和rows。
    var p = $('#schoolworkExperience').datagrid('getPager');
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
//职称记录
$(function () {
    //显示职称信息
    $('#employeeAssProfessionalTitle').datagrid({
        //获取方法
        method: 'get',
        striped: true,//行背景交换
        width: 730,
        height: 390,
        //设置为单选
        singleSelect: true,
        //显示行号formatter
        rowNumbers: "true",
        //不对本地数据进行排序
        remoteSort: false,
        //自适应表格大小
        fitColumns: true,
        //单选框的设置
        frozenColumns: [[{field: "chk", "checkbox": true}]],
        columns: [[
            {field: 'id', title: 'id', hidden: true},
            {field: 'employee', hidden: true},
            {
                field: 'professionalTitle', title: '职称', width: 100, formatter: function (value, rec) {
                if (rec.professionalTitle == null)
                    return null;
                else
                    return rec.professionalTitle.description;
            },
                editor: {
                    type: 'combobox',
                    options: {
                        url: basePath() + '/findAllPostNames',
                        method: 'get',
                        valueField: 'id',
                        textField: 'description',
                        panelHeight: 'auto',
                        required: true,
                        onLoadSuccess: function (data) {
                            getRow('#employeeAssProfessionalTitle');
                            if (data) {
                                if (rowData.professionalTitle == null) {//如果没有值,默认选中第一个
                                    $(this).combobox('setValue', data[0].id);
                                } else {
                                    $(this).combobox('setValue', rowData.professionalTitle.id);//要用id!
                                }
                            }
                        }

                    }
                }
            },
            {
                field: 'nominatedNo', title: '评定文号', width: 100,
                editor: {
                    type: 'validatebox',
                    options: {
                        required: true,
                        missingMessage: '该字段不能为空'
                    }
                }
            },
            {
                field: 'nominatedDate', title: '评定时间', width: 100,sortable: true,
                editor: {
                    type: 'datebox',
                    options: {
                        required: true,
                        missingMessage: '该字段不能为空'
                    }
                }
            },
            {
                field: 'appointedNo', title: '聘任文号', width: 100,
                editor: {
                    type: 'validatebox',
                    options: {
                        required: true,
                        missingMessage: '该字段不能为空'
                    }
                }
            },
            {
                field: 'appointedDate', title: '聘任时间', width: 100,sortable: true,
                editor: {
                    type: 'datebox',
                    options: {
                        required: true,
                        missingMessage: '该字段不能为空'
                    }
                }
            },

        ]],
        toolbar: [{
            id: 'EmployeeAssProfessionalTitle_add_btn', text: '添加', iconCls: 'icon-add',
            handler: function () {
                addOpe("#employeeAssProfessionalTitle");
            }
        }, '-', {
            id: 'EmployeeAssProfessionalTitle_delete_btn', text: '删除', iconCls: 'icon-remove',
            handler: function () {
                deleteOpe('#employeeAssProfessionalTitle', basePath() + '/deletePostName?id=');
            }
        }, '-', {
            id: 'EmployeeAssProfessionalTitle_update_btn', text: '修改', iconCls: 'icon-edit',
            handler: function () {
                updateOpe("#employeeAssProfessionalTitle");
            }
        }, '-', {
            id: 'EmployeeAssProfessionalTitle_save_btn', text: '保存', iconCls: 'icon-save',
            handler: function () {
                saveOpe("#employeeAssProfessionalTitle");
            }
        }, '-', {
            id: 'EmployeeAssProfessionalTitle_cancel_btn', text: '撤销', iconCls: 'icon-redo',
            handler: function () {
                cancelOpe('#employeeAssProfessionalTitle');
            }
        }],

        pagination: "true",//分页
        //endEdit触发后
        onAfterEdit: function (rowIndex, rowData, changes) {//rowIndex：编辑行的索引，从 0 开始;rowData：编辑行对应的记录;changes：更改的字段/值对
            endEditOpe(rowData, basePath() + '/addPostName', basePath() + '/updatePostName', '#employeeAssProfessionalTitle');
        },
        onCancelEdit: function (rowIndex, rowData) {
            editRow = undefined;
        },
        //双击一行
        onDblClickRow: function (rowIndex, rowData) {
            if (editRow != undefined) {
                $("#employeeAssProfessionalTitle").datagrid('endEdit', editRow);
            } else {
                dblClickOpe(rowData, rowIndex, "#employeeAssProfessionalTitle");
            }
        },
    })
    var p = $('#employeeAssProfessionalTitle').datagrid('getPager');
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
//学历信息
$(function () {
    $('#employeeAssEducationQualification').datagrid({
        method: 'get',
        width: 730,
        height: 390,
        singleSelect: true,
        rowNumbers: "true",//显示行号
        remoteSort: false,//对本地数据进行排序
        fitColumns: true,//自适应表格大小
        frozenColumns: [[{field: "chk", "checkbox": true}]],//单选框
        columns: [[
            {field: 'id', title: 'id', hidden: true},
            {field: 'employee', hidden: true},
            {
                field: 'educationQualification',
                formatter: function (value, rec) {
                    if (rec.educationQualification == null) {
                        return "无";
                    } else {
                        return rec.educationQualification.description;
                    }
                }, title: '学历名称', width: 100, align: 'center',
                //可编辑
                editor: {
                    //类型为下拉框
                    type: 'combobox',
                    options: {
                        required: true,
                        url: basePath() + '/findAllEducationName',
                        method: 'get',
                        mode: 'remote',
                        valueField: 'id',
                        textField: 'description',
                        editale: false,
                        missingMessage: '该字段不能为空',
                        onLoadSuccess: function (data) {
                            getRow('#employeeAssEducationQualification');
                            if (data) {
                                if (rowData.educationQualification == null) {//如果没有值,默认选中第一个
                                    $(this).combobox('setValue', data[0].id);
                                } else {
                                    $(this).combobox('setValue', rowData.educationQualification.id);//要用id!
                                }
                            }
                        }
                    }
                }
            },
            {
                field: 'enrollDate', title: '入学时间', width: 150, align: 'center',sortable: true,
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
                field: 'graduateDate', title: '毕业时间', width: 100, align: 'center',sortable: true,
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
                field: 'major', title: '专业', width: 150, align: 'center',
                editor: {
                    type: 'validatebox',
                    options: {
                        required: true,
                        missingMessage: '该字段不能为空'
                    }
                }
            },
            {
                field: 'duration', title: '学制', width: 100, align: 'center',
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
                field: 'supervisorName', title: '导师姓名', width: 100, align: 'center',
                editor: {
                    type: 'validatebox',
                    options: {
                        required: true,
                        missingMessage: '该字段不能为空'
                    }
                }
            },
            {
                field: 'certificateNo', title: '毕业证编号', width: 100, align: 'center',
                editor: {
                    type: 'validatebox',
                    options: {
                        required: true,
                        missingMessage: '该字段不能为空'
                    }
                }
            },
            {
                field: 'university', title: '学校', width: 100, align: 'center',
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
            id: 'EmployeeAssEducationQualification_add_btn', text: '添加', iconCls: 'icon-add',
            handler: function () {
                addOpe("#employeeAssEducationQualification");
            }
        }, '-', {
            id: 'EmployeeAssEducationQualification_delete_btn', text: '删除', iconCls: 'icon-remove',
            handler: function () {
                deleteOpe('#employeeAssEducationQualification', basePath() + '/deleteEducation?ids=');
            }
        }, '-', {
            id: 'EmployeeAssEducationQualification_update_btn', text: '修改', iconCls: 'icon-edit',
            handler: function () {
                updateOpe("#employeeAssEducationQualification");
            }
        }, '-', {
            id: 'EmployeeAssEducationQualification_save_btn', text: '保存', iconCls: 'icon-save',
            handler: function () {
                saveOpe("#employeeAssEducationQualification");
            }
        }, '-', {
            id: 'EmployeeAssEducationQualification_cancel_btn', text: '撤销', iconCls: 'icon-redo',
            handler: function () {
                cancelOpe('#employeeAssEducationQualification');
            }
        }],
        //endEdit方法触发此事件后
        onAfterEdit: function (rowIndex, rowData, changes) {
            //rowIndex：编辑行的索引，从 0 开始;rowData：编辑行对应的记录;changes：更改的字段/值对
            endEditOpe(rowData, basePath() + '/addEducation', basePath() + '/updateEducation', '#employeeAssEducationQualification');
        },
        //双击一行
        onDblClickRow: function (rowIndex, rowData) {
            if (editRow != undefined) {
                $("#employeeAssEducationQualification").datagrid('endEdit', editRow);
            } else {
                dblClickOpe(rowData, rowIndex, "#employeeAssEducationQualification");
            }
        },
        onCancelEdit: function (rowIndex, rowData) {
            editRow = undefined;
        },
        pagination: "true"//分页
    });
    //分页用的获取datagrid中的详细内容，后台只需要获取easyui自动提供pages和rows（我好像需要一个rows）即可。
    var p = $('#employeeAssEducationQualification').datagrid('getPager');
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
//奖励记录
$(function () {
    $('#reward').datagrid({
        method: 'get',
        width: 730,
        height: 390,
        singleSelect: true,
        rowNumbers: "true",//显示行号
        remoteSort: false,//对本地数据进行排序
        fitColumns: true,//自适应表格大小
        frozenColumns: [[{field: "chk", "checkbox": true}]],//单选框
        columns: [[
            {field: 'id', title: 'id', hidden: true},
            {field: 'employee', hidden: true},
            {
                field: 'grantedDate', title: '授予时间', width: 70, align: 'center',sortable: true,
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
                field: 'name', title: '名称', width: 80, align: 'center',
                editor: {
                    type: 'validatebox',
                    options: {
                        required: true,
                        missingMessage: '该字段不能为空'
                    }
                }
            },
            {
                field: 'rank', title: '级别', width: 80, align: 'center',
                editor: {
                    type: 'validatebox',
                    options: {
                        required: true,
                        missingMessage: '该字段不能为空'
                    }
                }
            },
            {
                field: 'precedence', title: '位次', width: 100, align: 'center', sortable: true,
                editor: {
                    type: 'numberspinner',//数值微调器
                    options: {
                        min: 1,
                        max: 50,
                        required: true,
                        missingMessage: '该字段不能为空'
                    }
                }
            },
            {
                field: 'numOfParticipants', title: '参加人数', width: 100, align: 'center', sortable: true,
                editor: {
                    type: 'numberspinner',//数值微调器
                    options: {
                        min: 1,
                        max: 50,
                        required: true,
                        missingMessage: '该字段不能为空'
                    }
                }
            },
            {
                field: 'grantedBy', title: '授予机关', width: 150, align: 'center',
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
            id: 'Reward_add_btn', text: '添加', iconCls: 'icon-add',
            handler: function () {
                addOpe("#reward");
            }
        }, '-', {
            id: 'Reward_delete_btn', text: '删除', iconCls: 'icon-remove',
            handler: function () {
                deleteOpe('#reward', basePath() + '/deleteReward?id=');
            }
        }, '-', {
            id: 'Reward_update_btn', text: '修改', iconCls: 'icon-edit',
            handler: function () {
                updateOpe("#reward");
            }
        }, '-', {
            id: 'Reward_save_btn', text: '保存', iconCls: 'icon-save',
            handler: function () {
                saveOpe("#reward");
            }
        }, '-', {
            id: 'Reward_cancel_btn', text: '撤销', iconCls: 'icon-redo',
            handler: function () {
                cancelOpe('#reward');
            }
        }],
        onAfterEdit: function (rowIndex, rowData, changes) {//rowIndex：编辑行的索引，从 0 开始;rowData：编辑行对应的记录;changes：更改的字段/值对
            endEditOpe(rowData, basePath() + '/addReward', basePath() + '/updateReward', '#reward');
        },
        //双击一行
        onDblClickRow: function (rowIndex, rowData) {
            if (editRow != undefined) {
                $("#reward").datagrid('endEdit', editRow);
            } else {
                dblClickOpe(rowData, rowIndex, "#reward");
            }
        },
        onCancelEdit: function (rowIndex, rowData) {
            editRow = undefined;
        },
        pagination: "true",//分页
    });

    //分页用的获取datagrid中的详细内容，后台只需要获取easyui自动提供pages和rows（我好像需要一个rows）即可。
    var p = $('#reward').datagrid('getPager');
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
//国内外访学记录
$(function () {
    $('#visitingStudy').datagrid({
        method: 'get',
        width: 730,
        height: 390,
        singleSelect: true,
        rowNumbers: "true",//显示行号
        remoteSort: false,//对本地数据进行排序
        fitColumns: true,//自适应表格大小
        frozenColumns: [[{field: "chk", "checkbox": true}]],//单选框
        columns: [[
            {field: 'id', title: 'id', hidden: true},
            {
                field: 'domesticOrNot',
                formatter: function (value, rec) {
                    if (rec.domesticOrNot == null) {
                        return '未选择';
                    }
                    else {
                        return rec.domesticOrNot.description;
                    }
                },
                editor: {
                    type: 'combobox',
                    options: {
                        method: 'get',
                        url: basePath() + '/findAllYesOrNo',
                        valueField: 'id',
                        textField: 'description',
                        panelHeight: 'auto',
                        required: true,
                        onLoadSuccess: function (data) {
                            getRow('#visitingStudy');
                            if (data) {
                                if (rowData.domesticOrNot == null) {//如果没有值,默认选中第一个(相当于添加状态)
                                    $(this).combobox('setValue', data[0].id);
                                } else {//相当于编辑状态
                                    $(this).combobox('setValue', rowData.domesticOrNot.id);
                                }
                            }
                        },
                    }
                },
                title: '是否国内访学', width: 100, align: 'center'
            },
            {
                field: 'program', title: '进修名称', width: 100, align: 'center',
                editor: {
                    type: 'validatebox',
                    options: {
                        required: true,
                        missingMessage: '该字段不能为空'
                    }
                }
            },
            {
                field: 'startTime', title: '开始时间', width: 85, align: 'center',sortable: true,
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
                field: 'endTime', title: '结束时间', width: 85, align: 'center',sortable: true,
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
                field: 'sponsor',
                formatter: function (value, rec) {
                    if (rec.sponsor == null) {
                        return '未选择';
                    }
                    else {
                        return rec.sponsor.description;
                    }
                },
                editor: {
                    type: 'combobox',
                    options: {
                        method: 'get',
                        url: basePath() + '/findAllFundOrganization',
                        valueField: 'id',
                        textField: 'description',
                        panelHeight: 'auto',
                        required: true,
                        onLoadSuccess: function (data) {
                            getRow('#visitingStudy');
                            if (data) {
                                if (rowData.sponsor == null) {//如果没有值,默认选中第一个(相当于添加状态)
                                    $(this).combobox('setValue', data[0].id);
                                } else {//相当于编辑状态
                                    $(this).combobox('setValue', rowData.sponsor.id);
                                }
                            }
                        },
                    }
                },
                title: '资助机构', width: 100, align: 'center'
            },
            {
                field: 'visitedOrganization', title: '受访机构', width: 150, align: 'center',
                editor: {
                    type: 'validatebox',
                    options: {
                        required: true,
                        missingMessage: '该字段不能为空'
                    }
                }
            },
            {
                field: 'supervisorName', title: '导师姓名', width: 80, align: 'center',
                editor: {
                    type: 'validatebox',
                    options: {
                        required: true,
                        missingMessage: '该字段不能为空'
                    }
                }
            },
            {
                field: 'reportTime', title: '汇报时间', width: 150, align: 'center',sortable: true,
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
                field: 'reviewResult',
                formatter: function (value, rec) {
                    if (rec.reviewResult == null) {
                        return '未选择';
                    }
                    else {
                        return rec.reviewResult.description;
                    }
                },
                editor: {
                    type: 'combobox',
                    options: {
                        method: 'get',
                        url: basePath() + '/findAllCheck',
                        valueField: 'id',
                        textField: 'description',
                        panelHeight: 'auto',
                        required: true,
                        onLoadSuccess: function (data) {
                            getRow('#visitingStudy');
                            if (data) {
                                if (rowData.reviewResult == null) {//如果没有值,默认选中第一个(相当于添加状态)
                                    $(this).combobox('setValue', data[0].id);
                                } else {//相当于编辑状态
                                    $(this).combobox('setValue', rowData.reviewResult.id);
                                }
                            }
                        },
                    }
                },
                title: '审核结果', width: 80, align: 'center'
            }
        ]],
        toolbar: [{
            id: 'VisitingAcademic_add_btn', text: '添加', iconCls: 'icon-add',
            handler: function () {
                addOpe("#visitingStudy");
            }
        }, '-', {
            id: 'VisitingAcademic_delete_btn', text: '删除', iconCls: 'icon-remove',
            handler: function () {
                deleteOpe('#visitingStudy', basePath() + '/deleteVisitingStudy?id=');
            }
        }, '-', {
            id: 'VisitingAcademic_update_btn', text: '修改', iconCls: 'icon-edit',
            handler: function () {
                updateOpe("#visitingStudy");
            }
        }, '-', {
            id: 'VisitingAcademic_save_btn', text: '保存', iconCls: 'icon-save',
            handler: function () {
                saveOpe("#visitingStudy");
            }
        }, '-', {
            id: 'VisitingAcademic_cancel_btn', text: '撤销', iconCls: 'icon-redo',
            handler: function () {
                cancelOpe('#visitingStudy');
            }
        }],
        //endEdit触发后
        onAfterEdit: function (rowIndex, rowData, changes) {//rowIndex：编辑行的索引，从 0 开始;rowData：编辑行对应的记录;changes：更改的字段/值对
            endEditOpe(rowData, basePath() + '/addVisitingStudy', basePath() + '/updateVisitingStudy', '#visitingStudy');
        },
        onCancelEdit: function (rowIndex, rowData) {
            editRow = undefined;
        },
        //双击一行
        onDblClickRow: function (rowIndex, rowData) {
            if (editRow != undefined) {
                $("#visitingStudy").datagrid('endEdit', editRow);
            } else {
                dblClickOpe(rowData, rowIndex, "#visitingStudy");
            }
        },
        pagination: "true",//分页
    });
    //分页用的获取datagrid中的详细内容，后台只需要获取easyui自动提供pages和rows（我好像需要一个rows）即可。
    var p = $('#visitingStudy').datagrid('getPager');
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
//来校前工作经历
$(function () {
    $('#previousExperiences').datagrid({
        method: 'get',
        fit: true,
        striped: true,//行背景交换
        rowNumbers: true,//显示行号
        remoteSort: false,//对本地数据进行排序
        fitColumns: true,//自适应表格大小
        singleSelect: true,
        idField: 'ID', //主键
        columns: [[
            {field: 'id', title: '编号', width: 60, checkbox: true},
            {field: 'employee', hidden: true},
            {
                field: 'startTime', title: '开始时间', width: 100, align: 'center',sortable: true,
                editor: {type: 'datebox', options: {required: true,editable:false}}
            },

            {
                field: 'endTime', title: '结束时间', width: 90, align: 'center',sortable: true,
                editor: {type: 'datebox', options: {required: true,editable:false}}
            },
            {
                field: 'hiredByCompany', title: '单位名称', width: 80, align: 'center',
                editor: {type: 'validatebox', options: {required: true, missingMessage: '该字段不能为空',}}
            },
            {
                field: 'departmentName', title: '部门名称', width: 90, align: 'center',
                editor: {type: 'validatebox', options: {required: true, missingMessage: '该字段不能为空',}}
            },
            {
                field: 'rank', title: '级别', width: 80, align: 'center',
                editor: {type: 'validatebox', options: {required: true, missingMessage: '该字段不能为空',}}
            },
            {
                field: 'post', title: '担任职务', width: 70, fixed: true, align: 'center',
                editor: {type: 'validatebox', options: {required: true, missingMessage: '该字段不能为空',}}
            }
        ]],

        toolbar: [{
            id: 'PreviousExperiences_add_btn', text: '添加', iconCls: 'icon-add',
            handler: function () {
                addOpe('#previousExperiences');
            }
        }, '-', {
            id: 'PreviousExperiences_delele_btn', text: '删除', iconCls: 'icon-remove',
            handler: function () {
                deleteOpe('#previousExperiences', basePath() + '/deleteBeforeExperiences?ids=');
            }
        }, '-', {
            id: 'PreviousExperiences_update_btn', text: '修改', iconCls: 'icon-edit',
            handler: function () {
                updateOpe('#previousExperiences');
            }
        }, '-', {
            id: 'PreviousExperiences_save_btn', text: '保存', iconCls: 'icon-save',
            handler: function () {
                saveOpe('#previousExperiences');
            }
        }, '-', {
            id: 'PreviousExperiences_cancel_btn', text: '撤销', iconCls: 'icon-redo',
            handler: function () {
                cancelOpe('#previousExperiences');
            }
        }],
        //endEdit触发后
        onAfterEdit: function (rowIndex, rowData, changes) {
            //rowIndex：编辑行的索引，从 0 开始;rowData：编辑行对应的记录;changes：更改的字段/值对

            endEditOpe(rowData, basePath() + '/addBeforeExperiences?employeeId=' + emplId, basePath() + '/updateBeforeExperiences?employeeId=' + emplId, '#previousExperiences');
        },
        onCancelEdit: function (rowIndex, rowData) {
            editRow = undefined;
        },
        //双击一行
        onDblClickRow: function (rowIndex, rowData) {
            if (editRow != undefined) {
                $("#previousExperiences").datagrid('endEdit', editRow);
            } else {
                dblClickOpe(rowData, rowIndex, '#previousExperiences');
            }
        },
        pagination: "true",//分页
    });
    //分页用的获取datagrid中的详细内容，后台只需要获取easyui自动提供pages和rows（我好像需要一个rows）即可。
    var p = $('#previousExperiences').datagrid('getPager');
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
//职位变更信息
$(function () {
    $('#employmentPost').datagrid({
        method: 'get',
        width: 730,
        height: 390,
        singleSelect: true,
        rowNumbers: "true",//显示行号
        remoteSort: false,//对本地数据进行排序
        fitColumns: true,//自适应表格大小
        frozenColumns: [[{field: "chk", "checkbox": true}]],//单选框
        columns: [[
            {field: 'id', title: 'id', hidden: true},
            //职位名称
            {
                field: 'post',
                formatter: function (value, rec) {
                    if (rec.post == null) {
                        return "无";
                    } else {
                        return rec.post.description;
                    }
                }, title: '职位名称', width: 100, align: 'center',
                //可编辑
                editor: {
                    //类型为下拉框
                    type: 'combobox',
                    options: {
                        required: true,
                        url: basePath() + '/findAllPost',
                        method: 'get',
                        mode: 'remote',
                        valueField: 'id',
                        textField: 'description',
                        editale: false,
                        missingMessage: '该字段不能为空',
                        onLoadSuccess: function (data) {
                            getRow('#employmentPost');
                            if (data) {
                                if (rowData.post == null) {//如果没有值,默认选中第一个
                                    $(this).combobox('setValue', data[0].id);
                                } else {
                                    $(this).combobox('setValue', rowData.post.id);//要用id!
                                }
                            }
                        }
                    }
                }
            },
            //任命时间
            {
                field: 'appointedDate', title: '任命时间', width: 150, align: 'center',sortable: true,
                editor: {
                    type: 'datebox',
                    options: {
                        required: true,
                        editable:false,
                        missingMessage: '该字段不能为空'
                    }
                }
            },
            //红头文号
            {
                field: 'commissionNo', title: '红头文号', width: 150, align: 'center',
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
            id: 'EmploymentAssPost_add_btn', text: '添加', iconCls: 'icon-add',
            handler: function () {
                addOpe("#employmentPost");
            }
        }, '-', {
            id: 'EmploymentAssPost_delete_btn', text: '删除', iconCls: 'icon-remove',
            handler: function () {
                deleteOpe('#employmentPost', basePath() + '/deleteEmploymentPost?id=');
            }
        }, '-', {
            id: 'EmploymentAssPost_update_btn', text: '修改', iconCls: 'icon-edit',
            handler: function () {
                updateOpe("#employmentPost");
            }
        }, '-', {
            id: 'EmploymentAssPost_save_btn', text: '保存', iconCls: 'icon-save',
            handler: function () {
                saveOpe("#employmentPost");
            }
        }, '-', {
            id: 'EmploymentAssPost_cancel_btn', text: '撤销', iconCls: 'icon-redo',
            handler: function () {
                cancelOpe('#employmentPost');
            }
        }],
        //endEdit方法触发此事件后
        onAfterEdit: function (rowIndex, rowData, changes) {
            //rowIndex：编辑行的索引，从 0 开始;rowData：编辑行对应的记录;changes：更改的字段/值对
            endEditOpe(rowData, basePath() + '/addEmploymentPost', basePath() + '/updateEmploymentPost', '#employmentPost');
        },
        //双击一行
        onDblClickRow: function (rowIndex, rowData) {
            if (editRow != undefined) {
                $("#employmentPost").datagrid('endEdit', editRow);
            } else {
                dblClickOpe(rowData, rowIndex, "#employmentPost");
            }
        },
        onCancelEdit: function (rowIndex, rowData) {
            editRow = undefined;
        },
        pagination: "true"//分页
    });
    //分页用的获取datagrid中的详细内容，后台只需要获取easyui自动提供pages和rows（我好像需要一个rows）即可。
    var p = $('#employmentPost').datagrid('getPager');
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
                $.messager.alert('消息提示', '更新成功!', 'info');
                $(datagrid).datagrid('reload');//添加成功之后调用
            } else {
                $.messager.alert("操作提示", "更新失败！"+msg.data, "error");
                $(datagrid).datagrid('reload');
                $(datagrid).datagrid('unselectAll');
            }
        },
        error: function (msg) {
            $.messager.alert('消息提示', '更新失败', 'error');
            $(datagrid).datagrid('reload');
            $(datagrid).datagrid('unselectAll');
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
                $.messager.alert('消息提示', '添加成功！', 'info');
                $(datagrid).datagrid('reload');//添加成功之后调用
            } else {
                $.messager.alert("操作提示", "操作失败！"+msg.data, "error");
                $(datagrid).datagrid('reload');
                $(datagrid).datagrid('unselectAll');
            }
        },
        error: function (msg) {
            $.messager.alert('消息提示', '添加失败', 'error');
            $(datagrid).datagrid('reload');//添加成功之后调用
            $(datagrid).datagrid('unselectAll');
        }
    });
};
//删除Ajax
$.deleteAjax = function (url, datagrid) {
    return jQuery.ajax({
        'type': 'DELETE',
        'url': url,
        success: function (msg) {
            if (msg.data == undefined) {
                $.messager.alert('消息提示', '删除成功', 'info');
                $(datagrid).datagrid('reload');
                $(datagrid).datagrid('unselectAll');
            } else {
                $.messager.alert("操作提示", "删除失败！"+msg.data, "error");
                $(datagrid).datagrid('reload');
                $(datagrid).datagrid('unselectAll');
            }
        },
        error: function (msg) {
            $.messager.alert('消息提示', '删除失败', 'error');
            $(datagrid).datagrid('reload');
            $(datagrid).datagrid('unselectAll');
        }
    });
};




//标识是否为添加员工状态和是否保存员工和是否编辑员工的初始状态都为false
var isAddStaff = false;
var isSaveStaff = false;
var editRowIndex = -1;
//删除一个员工
function staffremoveRecord() {
    //得到当前选中行的记录，
    var row = $('#staff').datagrid('getSelected');
    isSaveStaff = false;
    isAddStaff = true;
    //必须当选中一行时，才可以删除
    if (row != null) {
        $.messager.confirm('提示', '你确定要删除这条信息吗？', function (r) {
            if (r) {
                $.postJSON33(basePath() + '/deleteEmployee?id=' + row.id);
                //删除后，图片为空
                $('#Employee_Img').attr('src', '');//设置属性值
                $('#saveStaff').hide();//显示保存按钮
            }
        });
    } else {
        $.messager.alert("提示", "请选择要删除的人员！", "info");
        return false;
    }
};
//添加新员工
function newStaffadd() {
    isAddStaff = true;
    isSaveStaff = false;
    editRowIndex = -1;
    /*combobox设置默认值*/
    $('#partyInfo').datagrid('reload', basePath() + '/displayAllParty?id=0');
    $('#Employee_nation_combobox').combobox({
        onLoadSuccess: function (data) {
            if (data) {
                $(this).combobox('setValue', data[0].id);
            }
        },
    });
    $('#Employee_overseasChineseOrNot_combobox').combobox({
        onLoadSuccess: function (data) {
            if (data) {
                $(this).combobox('setValue', data[1].id);
            }
        },
    });
    $('#Employee_Img').attr('src', '');//设置属性值
    $('#staff').datagrid('reload');
    $('#saveStaff').show();//显示保存按钮
    $('#empInfoTabs').show();//显示tabs
    $('#photoDiv').show();//显示上传框div
    $('#ageDiv').hide();//隐藏年龄
    $('#workAgeDiv').hide();//隐藏工龄
    $('#Actor').form('clear');//清空表格数据
    $('#party').form('clear');
}
//保存员工个人信息
function staffsaveRecord() {
    isSaveStaff = true;
    $('#ageDiv').show();
    $('#workAgeDiv').show();
    var empRow = $('#staff').datagrid("getSelected");//获取当前选中行
    var data = serializeObject("#Actor");//序列反序列化表格
    var data2 = serializeObject("#party");//序列反序列化表格
    if (empRow == null) {
        $.postJSON20(basePath() + '/addParty', data2);
        $.postJSON22(basePath() + '/addEmployee', data, '#staff');//应用方法传送，写上了URL和传的data
    } else if (empRow) {
        $.postJSON10(basePath() + '/updateParty?id=' + empRow.id, data2);
        $.postJSON11(basePath() + '/updateEmployee', data, '#staff');
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
//职员信息更新的json处理
$.postJSON11 = function (url, data, grid) {//Json转化更新
    if ($('#Actor').form('validate') == false) {
        $.messager.alert('消息提示', '修改信息失败,请检查红色字段', 'error');
        return;
    }
    else {
        return jQuery.ajax({
            'type': 'PUT',
            'url': url,
            'contentType': 'application/json',
            'data': JSON.stringify(data),
            'dataType': 'json',
            success: function (msg) {
                $.messager.alert('消息提示', '职员信息修改成功', 'info');
                editRowIndex = $('#staff').datagrid('getRowIndex', $('#staff').datagrid('getSelected'));//获取当前选中行
                $('#staff').datagrid('reload');//修改成功之后调用
                // isEditStaff = true;
            },
            error:function (msg) {
                $.messager.alert('消息提示', '更新失败', 'error');
            }
        });
    }
};
$.postJSON10 = function (url, data) {//Json转化更新
    return jQuery.ajax({
        'type': 'PUT',
        'url': url,
        'contentType': 'application/json',
        'data': JSON.stringify(data),
        'dataType': 'json',
        success: function (msg) {
        },
        error: function (msg) {
            $.messager.alert('消息提示', '更新失败', 'error');
        }
    });
};
//职员信息添加的json处理
$.postJSON22 = function (url, data, grid) {//Json转化添加
    if ($('#Actor').form('validate') == false) {
        $.messager.alert('消息提示', '添加失败,请检查红色字段', 'error');
        return;
    } else {
        return jQuery.ajax({//用Ajax的方法来传递
            'type': 'POST',//种类是POST
            'url': url,
            'contentType': 'application/json',
            'data': JSON.stringify(data),
            'dataType': 'json',//数据的种类是JSON
            success: function (msg) {
                $.messager.alert('消息提示', '成功添加新职员', 'info');
                $(grid).datagrid('reload');//添加成功之后调用
            },
            error: function (msg) {
                $.messager.alert('消息提示', '添加失败', 'error');
            }//添加失败之后调用
        });
    }
};
$.postJSON20 = function (url, data) {//Json转化添加
    return jQuery.ajax({//用Ajax的方法来传递
        'type': 'POST',//种类是POST
        'url': url,
        'contentType': 'application/json',
        'data': JSON.stringify(data),
        'dataType': 'json',//数据的种类是JSON
        success: function (msg) {
        },
        error: function (msg) {
            $.messager.alert('消息提示', '添加失败', 'error');
        }//添加失败之后调用
    });
};
//职员删除的json处理
$.postJSON33 = function (url) {
    return jQuery.ajax({
        'type': 'DELETE',
        'url': url,
        success: function (msg) {
            $.messager.alert('消息提示', '删除成功', 'info');
            $('#staff').datagrid('reload');
            $('#degree').datagrid('reload');
            $('#Actor').form('clear');
        },
        error: function () {
            $.messager.alert('消息提示', '删除失败', 'error');
        }
    });
};
//临时预览图片
function PreviewImage(fileObj, imgPreviewId, divPreviewId) {
    var allowExtention = ".jpg,.bmp,.gif,.png,.jpeg";//允许上传文件的后缀名
    var extention = fileObj.value.substring(fileObj.value.lastIndexOf(".") + 1).toLowerCase();//获取文件后缀名
    var browserVersion = window.navigator.userAgent.toUpperCase();//获取浏览器版本
    if (allowExtention.indexOf(extention) > -1) {
        if (fileObj.files) {//HTML5实现预览，兼容chrome、火狐7+等
            if (window.FileReader) {
                var reader = new FileReader();//用来读取本地文件
                reader.onload = function (e) {
//                  通过e.target.result生成图片的dataUrl
                    document.getElementById(imgPreviewId).setAttribute("src", e.target.result);//添加指定属性，并为其赋值
                }
                //将file文件读入，将File类型转化为dataUrl，结果在e.target.result得到
                reader.readAsDataURL(fileObj.files[0]);
            } else if (browserVersion.indexOf("SAFARI") > -1) {
                alert("不支持Safari6.0以下浏览器的图片预览!");
            }
        } else if (browserVersion.indexOf("MSIE") > -1) {
            if (browserVersion.indexOf("MSIE 6") > -1) {//ie6
                document.getElementById(imgPreviewId).setAttribute("src", fileObj.value);
            } else {//ie[7-9]
                fileObj.select();
                if (browserVersion.indexOf("MSIE 9") > -1)
                    fileObj.blur();//不加上document.selection.createRange().text在ie9会拒绝访问
                var newPreview = document.getElementById(divPreviewId + "New");
                if (newPreview == null) {
                    newPreview = document.createElement("div");
                    newPreview.setAttribute("id", divPreviewId + "New");
                    newPreview.style.width = document.getElementById(imgPreviewId).width + "px";
                    newPreview.style.height = document.getElementById(imgPreviewId).height + "px";
                    newPreview.style.border = "solid 1px #d2e2e2";
                }
                newPreview.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod='scale',src='" + document.selection.createRange().text + "')";
                var tempDivPreview = document.getElementById(divPreviewId);
                tempDivPreview.parentNode.insertBefore(newPreview, tempDivPreview);
                tempDivPreview.style.display = "none";
            }
        } else if (browserVersion.indexOf("FIREFOX") > -1) {//firefox
            var firefoxVersion = parseFloat(browserVersion.toLowerCase().match(/firefox\/([\d.]+)/)[1]);
            if (firefoxVersion > 7) {//firefox7以下版本
                document.getElementById(imgPreviewId).setAttribute("src", window.URL.createObjectURL(fileObj.files[0]));
            } else {//firefox7.0+
                document.getElementById(imgPreviewId).setAttribute("src", fileObj.files[0].getAsDataURL());
            }
        } else {
            document.getElementById(imgPreviewId).setAttribute("src", fileObj.value);
        }
    } else {
        alert("仅支持" + allowExtention + "为后缀名的文件!");
        fileObj.value = "";//清空选中文件
        if (browserVersion.indexOf("MSIE") > -1) {
            fileObj.select();
            document.selection.clear();
        }
        fileObj.outerHTML = fileObj.outerHTML;
    }
}
//职员显示图片
function showImg(row) {
    var timenow = new Date().getTime();
    var img = document.getElementById('Employee_Img');
    //每次请求需要一个不同的参数，否则返回的仍是原来的图片
    // iframe 中的加载的html或者jsp文件会被浏览器缓存，
    //导致引入的页面内容发生变化以后，iframe 中的内容不随 iframe 所在页面的刷新而刷新，仍旧显示客户端计算机中缓存的旧的 html 文件的内容
    //获取每次的当前时间，加入参数中，这样每次请求的参数不一样，浏览器就会认为请求的不是同一个页面
    img.src = basePath() + '/toLookImge?imgId=' + row.id + '&;nowT=' + timenow;
}
//提交file文件到后台
function fileSubmit(formName, url) {
    $(formName).ajaxSubmit({
        type: 'POST',
        url: url,
        //使用同步的方式
        async: false,
        //下面三个必须这样写
        contentType: false,
        cache: false,
        processData: false,
        success: function () {
            $.messager.alert('消息提示', '图片选择成功', 'info');
            if (formName == '#empFile') {
                //临时预览图片
                PreviewImage($("input[name='imge']")[0], 'Employee_Img', 'imgDiv');
            }
        },
        error: function () {
            $.messager.alert('消息提示', '图片选择失败', 'error');
        }
    });
}
//jquery.validatebox.js  扩展  正则表达式
$.extend($.fn.validatebox.defaults.rules, {
    //移动手机号码验证
    Mobile: {//value值为文本框中的值
        validator: function (value) {
            var reg = /^1[3|4|5|7|8|9]\d{9}$/;
            // var tel = /^(\(\d{3,4}\)|\d{3,4}-|\s)?\d{7,14}$/;
            var tel = /^((\d2,3)|(\d{3}\-))?(0\d2,3|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/;
            // alert('shoujihao --' + reg.test(value));
            if (reg.test(value) || tel.test(value)) {
                return true;
            }
            // return reg.test(value);
        },
        message: '请输入正确的手机号码或电话号码'
    },
    //数字
    Number: {
        validator: function (value) {
            var reg = /^[0-9]*$/;
            return reg.test(value);
        },
        message: '此处只能输入数字'
    },
    IDcard: {// 验证身份证
        validator: function (value) {
            return /^\d{15}(\d{2}[A-Za-z0-9])?$/i.test(value);
        },
        message: '身份证号码格式不正确'
    },
});

