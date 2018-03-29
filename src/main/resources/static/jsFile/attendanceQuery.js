/**
 * Created by inkskyu428 on 17-5-31.
 */
$(function () {
    $('#AttendanceQuery_table').datagrid({
        method: 'get',
        url: basePath() + '/displayAllAttendance',
        height: 480,
        singleSelect: true,
        rowNumbers: true,//显示行号
        remoteSort: false,//对本地数据进行排序
        fitColumns: true,//自适应表格大小
        toolbar: '#AttendanceQueryToolbar',
        columns: [[
            {field: 'id', title: 'id', hidden: true},
            {
                field: 'employeeName', title: '姓名', width: 120, align: 'center',
                formatter: function (value, rec) {
                    if (rec.employee == null) {
                        return "未选择";
                    }
                    else {
                        return rec.employee.name;
                    }
                }
            },
            {
                field: 'employeeNo', title: '工号', width: 120, align: 'center',
                formatter: function (value, rec) {
                    if (rec.employee == null) {
                        return "未选择";
                    }
                    else {
                        return rec.employee.no;
                    }
                }
            },
            {
                field: 'employeeDepartment', title: '教研室', width: 120, align: 'center',
                formatter: function (value, rec) {
                    if (rec.employee == null) {
                        return "未选择";
                    }
                    else {
                        return rec.employee.department.departmentName;
                    }
                }
            },
            {field: 'remarks', title: '备注', width: 120, align: 'center'},
        ]],
        pagination: "true",//分页
        onClickRow: function (rowIndex, rowData) {
        },
        onLoadSuccess: function (data) {
        },
    });
    //分页用的获取datagrid中的详细内容，后台只需要获取easyui自动提供pages和rows（我好像需要一个rows）即可。
    var p = $('#AttendanceQuery_table').datagrid('getPager');
    $(p).pagination({
        onBeforefresh: function () {
            $(this).pagination('loading');
            alert('before refresh');
            $(this).pagination('loaded');
        }
    });
});
//查询AttendanceQuery
function searchAttendanceQuery() {
    var startTime = $('#AttendanceQuery_startTime_datebox').datebox('getValue');
    var endTime = $('#AttendanceQuery_endTime_datebox').datebox('getValue');
    var department =$('#Attendance_department_combobox').combobox('getValues');
    var url = basePath() + '/findQuery?startTime=' + startTime
        + '&;endTime=' + endTime+'&;department'+department;
    $('#AttendanceQuery_table').datagrid('reload', url);
};
//下拉框
$(function () {
    initCombobox('#AttendanceQuery_department_combobox', 'departmentName', basePath() + '/findAllDepartment');
});
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

    //查询后台以及导出前后台未完成
    //出勤次数 缺勤次数 总次数（只包括日常考勤，无学术考勤） 备注及缺勤人员备注里详情超链接（显示所有缺勤时间）
    //排列顺序按照教研室工号顺序

}
