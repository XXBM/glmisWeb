package com.glmis.service.attendance;

import com.glmis.domain.attendance.UniversityAbsence;
import com.glmis.domain.message.Result;
import com.glmis.domain.personnel.Employee;
import com.glmis.utils.Utils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author: LihuaHuang
 * @Description:
 * @Date: Created in 2017-05-30
 * @Modified by:
 */

@Service
public class AttendanceExportExcelService {
    public Result exportExcel(List<Employee> presenceEmployeeList,
                              List<Employee> universityAbsenceEmployeeList,
                              String attendanceStartTime,
                              Map<Long,List<UniversityAbsence>> universityMap,
                              HttpServletResponse response,
                              HttpServletRequest request) throws ParseException, IOException {
        //创建一个workbook， 相当于一个excel
        HSSFWorkbook workbook = new HSSFWorkbook();
        //在workbook中创建一个sheet，相当于excel的sheet
        HSSFSheet sheet = workbook.createSheet("人事处报表");
        //在sheet中添加表头第0行
        HSSFRow row = sheet.createRow(0);
        row.setHeightInPoints(30);//设置行高
        //设置表头单元格居中
        HSSFCellStyle style = workbook.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);

        HSSFFont font = workbook.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 12);
        style.setFont(font);

        HSSFCell cell = null;
        cell = row.createCell(0);
        cell.setCellValue("山东建筑大学教职工考勤表");

        //为每一列设置自动宽度
        for (int i = 0; i < 21; i++) {
            sheet.autoSizeColumn(i);
        }

        HSSFCellStyle styleCenter = workbook.createCellStyle();
        //水平居中
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        //垂直居中
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

        //样式
        //第0行样式
        //HSSFCell cell = row.createCell(0);
        cell.setCellValue("山东建筑大学教职工考勤表");
        cell.setCellStyle(style);
        CellRangeAddress region0 = new CellRangeAddress(0, 0, 0, 8);
        sheet.addMergedRegion(region0);

        HSSFRow row2 = sheet.createRow(2);
        cell = row2.createCell(0);
        cell.setCellValue("部门：（盖章） 管理工程学院 ");
        cell = row2.createCell(7);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse(attendanceStartTime);
        cell.setCellValue("填报月份：" + (date.getMonth()+1) + "月");
        CellRangeAddress region1 = new CellRangeAddress(2, 2, 0, 3);
        sheet.addMergedRegion(region1);
        CellRangeAddress region2 = new CellRangeAddress(2, 2, 7, 8);
        sheet.addMergedRegion(region2);
        HSSFRow row4 = sheet.createRow(4);
        cell = row4.createCell(0);
        cell.setCellValue("项目");
        cell.setCellStyle(style);
        cell = row4.createCell(1);
        cell.setCellValue("详细情况");
        cell.setCellStyle(style);
        for (int i = 2; i < 9; i++) {
            cell = row4.createCell(i);
            cell.setCellStyle(style);
        }
        CellRangeAddress region3 = new CellRangeAddress(4, 4, 1, 8);
        sheet.addMergedRegion(region3);
        //HSSFRow row5 = sheet.createRow(5);
        //cell = row5.createCell(0);
        CellRangeAddress region4 = new CellRangeAddress(5, 21, 0, 0);
        sheet.addMergedRegion(region4);
        //cell.setCellValue("全勤人员名单");
        //cell.setCellStyle(style);
        //全勤人员名单
        int employeeId=0;
        for(int i=5;i<22;i++){
            HSSFRow rowP = sheet.createRow(i);
            if(i==5){
                cell = rowP.createCell(0);
                cell.setCellValue("全勤人员名单");
                cell.setCellStyle(style);

            }
            for(int x=1;x<9;x++){
                cell = rowP.createCell(x);
                if(employeeId>=presenceEmployeeList.size()){
                    break;
                }
                cell.setCellValue(presenceEmployeeList.get(employeeId).getName());
                employeeId++;
            }

        }
        //
        HSSFRow row22 = sheet.createRow(22);
        cell = row22.createCell(0);
        cell.setCellValue("缺勤人员名单");
        cell.setCellStyle(style);
        CellRangeAddress region5 = new CellRangeAddress(22, 38, 0, 0);
        sheet.addMergedRegion(region5);

        //具体缺勤信息
        //表头数组
        String[] title = {"","姓名","缺勤天数", "缺勤原因", "缺勤时间"};
        for (int i = 1; i < 5; i++) {
            cell = row22.createCell(i);
            cell.setCellValue(title[i]);
            cell.setCellStyle(style);
        }

        int y = 0;
        HSSFRow rowU;
        if(universityAbsenceEmployeeList.size()!=0){
            for (int i = 23; i < 38; i++) {
                rowU = sheet.createRow(i);
                if(y>=universityAbsenceEmployeeList.size()){
                    break;
                }
                cell = rowU.createCell(1);
                cell.setCellValue(universityAbsenceEmployeeList.get(y).getName());
                cell = rowU.createCell(2);
                List<UniversityAbsence> universityAbsences = universityMap.get(universityAbsenceEmployeeList.get(y).getId());
                System.out.println(universityAbsences.size()==0);
                cell.setCellValue(universityAbsences.size()+"");
                cell = rowU.createCell(3);
                //遍历集合，将description存起来去重复
                String reason = "";
                List<String> reasonS = new ArrayList<>();
                for(int a=0;a<universityAbsences.size();a++){
                    reasonS.add(universityAbsences.get(a).getUniversityAbsenceDescription().getDescription());
                }
                List newList = new ArrayList(new HashSet(reasonS));
                for(int b=0;b<newList.size();b++){
                    reason+=newList.get(b);
                }
                //输出集合
                cell.setCellValue(reason);
                //遍历集合，将集合元素的纸的时间存起来输出
                cell = rowU.createCell(4);
                String time = "";
                List<Calendar> times = new ArrayList<>();
                for(int a=0;a<universityAbsences.size();a++){
                    times.add(universityAbsences.get(a).getAttendanceSummary().getAttendanceTime());
                }
                for(int b=0;b<times.size();b++){
                    time+=times.get(b).get(Calendar.DATE)+"号";
                }
                cell.setCellValue(time);
                y++;
            }

        }

        HSSFRow row40 = sheet.createRow(40);
        row.setHeightInPoints(30);//设置行高
        cell = row40.createCell(0);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        String nowDate = df.format(new Date());// new Date()为获取当前系统时间
        cell.setCellValue("部门负责人：          填报人：   赵春全     填报时间：" + nowDate);
        cell.setCellStyle(style);
        CellRangeAddress region6 = new CellRangeAddress(40, 40, 0, 8);
        sheet.addMergedRegion(region6);
        HSSFRow row41 = sheet.createRow(41);
        cell = row41.createCell(0);
        cell.setCellValue("注：1.每月1-5日报送上月考勤表，考勤周期为上一个自然月。");
        HSSFRow row42 = sheet.createRow(42);
        cell = row42.createCell(0);
        cell.setCellValue(
                " 2.因在职攻读学位缺勤的职工，在缺勤原因中注明“攻读学位”，" +
                        "在备注中写明承担教学任务情况（不承担的写否，");
        HSSFRow row43 = sheet.createRow(43);
        cell = row43.createCell(0);
        cell.setCellValue(
                "承担的写明课程名称和授课对象)。");
        CellRangeAddress region7 = new CellRangeAddress(41, 41, 0, 8);
        sheet.addMergedRegion(region7);
        CellRangeAddress region8 = new CellRangeAddress(42, 42, 0, 8);
        sheet.addMergedRegion(region8);
        CellRangeAddress region9 = new CellRangeAddress(43, 43, 0, 8);
        sheet.addMergedRegion(region9);
        //输出文件
        FileOutputStream fileOutputStream = null;

        Result result = new Result();
        //获取桌面路径
//        FileSystemView fsv = FileSystemView.getFileSystemView();
//
//        String url = fsv.getHomeDirectory().getPath();

        //判断系统类型，系统不同，对应路径不同
//        String systemName = System.getProperty("os.name");
//        System.out.println(systemName);
//        if (systemName.contains("Linux")) {
//            url += "/Desktop/attendance.xls";
//        } else if (systemName.contains("Windows")) {
//            url += "/attendance.xls";
//        }
//        String url1 = System.getProperty("user.dir");
//        int indexOf = url1.indexOf("bin");
//        String url2= url1.substring(0, indexOf);
//        String url = url2 + "webapps/glmis" + "/WEB-INF/classes/static";
//
//        //String url = "/usr/local/tomcat9/webapps/glmis" + "/WEB-INF/classes/static";
//        url += "/attendance.xls";
//        System.out.println("url = " + url);
//        System.out.println(url);
//        try {
//            fileOutputStream = new FileOutputStream(url);
//            //给文件上锁
//            FileChannel fileChannel = fileOutputStream.getChannel();
//            FileLock fl = fileChannel.tryLock();
//            //fl为null时代表上锁失败，文件已被打开
//            if (fl == null) {
//                result.setMsg("导出失败，请关闭文件后再次操作。");
//                return result;
//            }
//            //上锁成功，继续执行写入
//            workbook.write(fileOutputStream);
//            fileOutputStream.close();
//            result.setMsg("导出成功，文件已在桌面。");
//            result.setSuccess(true);
//            return result;
//        } catch (FileNotFoundException e) {
//            result.setMsg("导出失败，请关闭文件后再次操作。");
//            System.out.println("111" + e.getMessage());
//            return result;
//        } catch (IOException e) {
//            result.setMsg("导出失败，请再次操作。");
//            System.out.println(e.getMessage());
//            return result;
//        }

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        workbook.write(byteArrayOutputStream);
        byteArrayOutputStream.flush();
        Utils.download(byteArrayOutputStream,response,request,"employee.xlsx");
        System.out.println("****************");
        return result;
    }
}
