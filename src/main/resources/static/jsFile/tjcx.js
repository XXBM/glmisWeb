$(function () {
    $('#employeeTable').datagrid({
        method: 'get',
        // url: basePath() + '/displayAllEmployeeName',
        width: 1166,
        height: 420,
        singleSelect: true,
        rowNumbers: "true",//显示行号
        remoteSort: false,//对本地数据进行排序
        fitColumns: true,//自适应表格大小
        columns: [[
            {field: 'id', title: 'id', hidden: true},
            {field: 'no', title: '职工号', width: 200, align: 'center'},
            {field: 'name', title: '姓名', width: 200, align: 'center'},
            {field: 'birth', title: '出生日期', width: 200, align: 'center'},
            {field: 'dateToSchool', title: '来校时间', width: 200, align: 'center'},
            {field: 'dateToWork', title: '参加工作时间', width: 200, align: 'center'},
            {field: 'dateToRetire', title: '退休时间', width: 200, align: 'center'},
            {field: 'age', title: '年龄', width: 200, align: 'center'},
            {field: 'grandpaBirthPlace', title: '籍贯', width: 200, align: 'center'},
            {field: 'salaryNo', title: '工资编号', width: 200, align: 'center'},
            {field: 'weChat', title: '微信', width: 200, align: 'center'},
            {field: 'qq', title: 'QQ', width: 200, align: 'center'},
            {field: 'idNo', title: '身份证号', width: 200, align: 'center'},
            {field: 'mobile', title: '联系电话', width: 200, align: 'center'},
            {field: 'emergencyMobile', title: '紧急联系人电话', width: 200, align: 'center'},
            {field: 'address', title: '家庭住址', width: 200, align: 'center'},
            {field: 'email', title: '邮箱', width: 200, align: 'center'},
            {
                field: 'sex', title: '性别', width: 200, align: 'center',
                formatter: function (value, rec) {
                    if (rec.sex == null) {
                        return '未选择';
                    }
                    else {
                        return rec.sex.description;
                    }
                }
            },
            {
                field: 'nation', title: '民族', width: 200, align: 'center',
                formatter: function (value, rec) {
                    if (rec.nation == null) {
                        return '未选择';
                    }
                    else {
                        return rec.nation.description;
                    }
                }
            },
            {
                field: 'department', title: '部门', width: 200, align: 'center',
                formatter: function (value, rec) {
                    if (rec.department == null) {
                        return '未选择';
                    }
                    else {
                        return rec.department.departmentName;
                    }
                }
            },
            {
                field: 'employmentCategory', title: '职工类别', width: 200, align: 'center',
                formatter: function (value, rec) {
                    if (rec.employmentCategory == null) {
                        return '未选择';
                    }
                    else {
                        return rec.employmentCategory.description;
                    }
                }
            },
            {
                field: 'overseasChineseOrNot', title: '是否华侨', width: 200, align: 'center',
                formatter: function (value, rec) {
                    if (rec.overseasChineseOrNot == null) {
                        return '未选择';
                    }
                    else {
                        return rec.overseasChineseOrNot.description;
                    }
                }
            },
        ]],
        pagination: "true",//分页
        onClickRow: function (rowIndex, rowData) {
        },
        onLoadSuccess: function (data) {
        }
    });

    $(".easyui-accordion .panel-header").click();
    setTimeout(function () {
        searchEmployee();
    }, 300);

});
//下拉框
$(function () {
    initCombobox('#Employee_academicDegree_cbox', 'description', basePath() + '/findAllAcademicDegrees');
    initCombobox('#Employee_overseasChineseOrNot_cbox', 'description', basePath() + '/findAllYesOrNo');
    initCombobox('#Employee_sex_cbox', 'description', basePath() + '/findAllSex');
    initCombobox('#Employee_practisingCertification_cbox', 'description', basePath() + '/findAllQualificationName');
    initCombobox('#Employee_professionalTitle_cbox', 'description', basePath() + '/findAllPostNames');
    initCombobox('#Employee_department_cbox', 'departmentName', basePath() + '/findAllDepartment');
    initCombobox('#Employee_domesticOrNot_cbox', 'description', basePath() + '/findAllYesOrNo');
    initCombobox('#Employee_politicalParty_cbox', 'description', basePath() + '/findAllPoliticalParties');
    initCombobox('#Employee_academicConference_cbox', 'text', basePath() + '/json/academicConference.json');
    initCombobox('#Employee_nation_cbox', 'description', basePath() + '/findAllNation');
    initCombobox('#Employee_sponsor_cbox', 'description', basePath() + '/findAllFundOrganization');
});
//初始化多选下拉框
function initCombobox(id, context, url) {
    //加载下拉框信息
    $(id).combobox({
        method: 'get',
        url: url,
        valueField: 'id',
        textField: context,
        multiple: true,
        panelHeight: 'auto'
    });
}

function searchEmployee() {
    var academicDegrees = $('#Employee_academicDegree_cbox').combobox('getValues');
    var overseasChineseOrNot = $('#Employee_overseasChineseOrNot_cbox').combobox('getValues');
    var sex = $('#Employee_sex_cbox').combobox('getValues');
    var practisingCertifications = $('#Employee_practisingCertification_cbox').combobox('getValues');
    var endDate = $('#Employee_endDate_dbox').datebox('getValue')
    var startBirth = $('#Employee_startBirth_dbox').datebox('getValue');
    var endBirth = $('#Employee_endBirth_dbox').datebox('getValue');
    var professionalTitle = $('#Employee_professionalTitle_cbox').combobox('getValues');
    var departments = $('#Employee_department_cbox').combobox('getValues');
    var domesticOrNot = $('#Employee_domesticOrNot_cbox').combobox('getValues');
    var politicalParties = $('#Employee_politicalParty_cbox').combobox('getValues');
    var academicConferences = $('#Employee_academicConference_cbox').combobox('getValues');
    var nation = $('#Employee_nation_cbox').combobox('getValues');
    var sponsor = $('#Employee_sponsor_cbox').combobox('getValues');

    var searchCondition = {}
    searchCondition.academicDegrees = academicDegrees;
    searchCondition.overseasChineseOrNot = overseasChineseOrNot;
    searchCondition.sex = sex;
    searchCondition.practisingCertification = practisingCertifications;
    searchCondition.endDate = endDate;
    searchCondition.startBirth = startBirth;
    searchCondition.endBirth = endBirth;
    searchCondition.professionalTitle = professionalTitle;
    searchCondition.departments = departments;
    searchCondition.domesticOrNot = domesticOrNot;
    searchCondition.politicalParties = politicalParties;
    searchCondition.academicConferences = academicConferences;
    searchCondition.nation = nation;
    searchCondition.sponsor = sponsor;

    $.ajax({
        'type': "POST",
        'url' : basePath() + '/searchEmployeeBySpecification',
        'data': JSON.stringify(searchCondition),
        'contentType': 'application/json',
        'dataType': 'json',
        success: function (data) {
            $('#employeeTable').datagrid({loadFilter:pagerFilter}).datagrid('loadData', data);
        },
        error: function (msg) {
            console.info(msg.responseText);
        }
    });


}

function pagerFilter(data){
    if (typeof data.length == 'number' && typeof data.splice == 'function'){    // 判断数据是否是数组
        data = {
            total: data.length,
            rows: data
        }
    }
    var dg = $(this);
    var opts = dg.datagrid('options');
    var pager = dg.datagrid('getPager');
    pager.pagination({
        showPageList: false,
        beforePageText: '第',//页数文本框前显示的汉字
        afterPageText: '页    共 {pages} 页',
        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录',
        onSelectPage:function(pageNum, pageSize){
            opts.pageNumber = pageNum;
            opts.pageSize = pageSize;
            pager.pagination('refresh',{
                pageNumber:pageNum,
                pageSize:pageSize
            });
            dg.datagrid('loadData',data);
        }
    });
    if (!data.originalRows){
        data.originalRows = (data.rows);
    }
    var start = (opts.pageNumber-1)*parseInt(opts.pageSize);
    var end = start + parseInt(opts.pageSize);
    data.rows = (data.originalRows.slice(start, end));
    return data;
}

function test() {
    try {
        var message = "\u8bf7\u9009\u62e9\u6587\u4ef6\u5939"; //选择框提示信息
        var shell = new ActiveXObject("Shell.Application");
        var folder = shell.BrowseForFolder(0, message, 64, 17); //起始目录为：我的电脑
        // var folder = shell.BrowseForFolder(0, message, 0); //起始目录为：桌面
        if (folder != null) {
            folder = folder.items(); // 返回 FolderItems 对象
            folder = folder.item(); // 返回 Folderitem 对象
            folder = folder.Path; // 返回路径
            if (folder.charAt(folder.length - 1) != "\\") {
                folder = folder + "\\";
            }
            $.messager.alert("1",folder);
            return folder;
        }
    }
    catch (e) {
        $.messager.alert("提示", e.message, "info");
    }


}

function exportExcel() {

    var url = basePath() + "/exportExcel";

    // 提交
    jQuery.ajax({
        'type': 'POST',
        'url': url,
        success: function (result) {
            if (result.success) {
                $.messager.alert("提示", result.msg, "info");
                //var path = window.location.href;
                // var glmisIndex = path.indexOf(":");
                // var protocol = window.location.protocol;
                // var host = window.location.host;
                // var url = protocol +"//" + host + "/glmis/employees.xls";
                // window.location.href = url;
            } else {
                $.messager.alert("提示", result.msg, "error");
            }
        },
        error: function (msg) {
            $.messager.alert("提示", "导出失败，请再次尝试。", "error");
            console.info(msg);
        }
    });
}
