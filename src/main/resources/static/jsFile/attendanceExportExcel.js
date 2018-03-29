/**
 * Created by dell on 2017-05-30 .
 */
// //下拉框
// $(function () {
//     initCombobox('#Attendance_month_combobox', 'text', basePath() + '/json/attendanceMonth.json');
// });
//初始化多选下拉框
// function initCombobox(id, context, url) {
//     //加载下拉框信息
//     $(id).combobox({
//         required: true,
//         method: 'get',
//         url: url,
//         valueField: 'id',
//         textField: context,
//         multiple: false,
//         panelHeight: 'auto'
//     });
// }


function attendanceExportExcel() {
    var attendanceStartTime = $('#Attendance_startTime_dbox').datebox('getValues');
    var attendanceEndTime = $('#Attendance_endTime_dbox').datebox('getValues');
    var url = basePath() + "/attendanceExportExcel?attendanceStartTime="+attendanceStartTime
        +'&;attendanceEndTime='+attendanceEndTime;


    //提交
    $.ajax({
        'type': 'POST',
        'url': url,
        success: function (result) {
            if (result.success) {
                $.messager.alert("提示", result.msg, "info");
                var path = window.location.href;
                // var glmisIndex = path.indexOf(":");
                var protocol = window.location.protocol;
                var host = window.location.host;
                var url = protocol +"//" + host + "/glmis/attendance.xls";
                window.location.href = url;
                //window.location.href="http://10.0.3.253:8080/glmis/attendance.xls";
            } else {
                $.messager.alert("提示", result.msg, "error");
            }
        },
        error: function () {
            $.messager.alert("提示", "导出失败，请再次尝试", "error");
        }
    });
}