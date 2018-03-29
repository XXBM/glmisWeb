package com.glmis.service.export;

import com.glmis.domain.message.Result;
import com.glmis.domain.personnel.Employee;
import com.glmis.utils.Utils;
import org.apache.poi.hssf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by kene213 on 2017/4/25.
 */
@Service
public class ExportService {
    final Logger logger = LoggerFactory.getLogger(ExportService.class);
    public Result exportExcel(List<Employee> employeeList,
                              HttpServletResponse response,
                              HttpServletRequest request) throws IOException {
        //创建一个workbook， 相当于一个excel
        HSSFWorkbook workbook = new HSSFWorkbook();
        //在workbook中创建一个sheet，相当于excel的sheet
        HSSFSheet sheet = workbook.createSheet("员工信息");
        //在sheet中添加表头第0行
        HSSFRow row = sheet.createRow(0);
        //设置表头单元格居中
        HSSFCellStyle style = workbook.createCellStyle();
        //水平居中
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        //垂直居中
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);


        HSSFFont font = workbook.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 12);
        style.setFont(font);

        //表头数组
        String[] title = {"职工号", "姓名", "性别", "年龄", "籍贯", "民族", "部门", "职工类型", "是否华侨", "出生日期",
                "来校时间", "参加工作", "退休时间", "工资编号", "身份证号", "联系电话", "紧急联系人电话", "家庭住址", "邮箱", "QQ",
                "微信"};


        HSSFCell cell = null;
        //设置表头列值
        for (int i = 0; i < 21; i++) {
            cell = row.createCell(i);
            cell.setCellValue(title[i]);
            cell.setCellStyle(style);
        }



        //添加员工信息，每一行代表一个员工
        for (int i = 0; i < employeeList.size(); i++) {
            row = sheet.createRow(i + 1);
            Employee employee = employeeList.get(i);
            //设置每个单元格的值和样式
            HSSFCell cell0 = row.createCell(0);
            if(employee.getNo() != null){
                cell0.setCellValue(employee.getNo());
            }
            cell0.setCellStyle(style);
            HSSFCell cell1 = row.createCell(1);
            if(employee.getName() != null){
                cell1.setCellValue(employee.getName());
            }
            cell1.setCellStyle(style);
            HSSFCell cell2 = row.createCell(2);
            if(employee.getSex() != null){
                cell2.setCellValue(employee.getSex().getDescription());
            }
            cell2.setCellStyle(style);
            HSSFCell cell3 = row.createCell(3);
            if(employee.getAge() != null){
                cell3.setCellValue(employee.getAge());
            }
            cell3.setCellStyle(style);
            HSSFCell cell4 = row.createCell(4);
            if(employee.getGrandpaBirthPlace() != null){
                cell4.setCellValue(employee.getGrandpaBirthPlace());
            }
            cell4.setCellStyle(style);
            HSSFCell cell5 = row.createCell(5);
            if(employee.getNation() != null){
                cell5.setCellValue(employee.getNation().getDescription());
            }
            cell5.setCellStyle(style);
            HSSFCell cell6 = row.createCell(6);
            if(employee.getDepartment() != null){
                cell6.setCellValue(employee.getDepartment().getDepartmentName());
            }
            cell6.setCellStyle(style);
            HSSFCell cell7 = row.createCell(7);
            if(employee.getEmploymentCategory() != null){
                cell7.setCellValue(employee.getEmploymentCategory().getDescription());
            }
            cell7.setCellStyle(style);
            HSSFCell cell8 = row.createCell(8);
            if(employee.getOverseasChineseOrNot() != null){
                cell8.setCellValue(employee.getOverseasChineseOrNot().getDescription());
            }
            cell8.setCellStyle(style);
            HSSFCell cell9 = row.createCell(9);
            if(employee.getBirth() != null){
                cell9.setCellValue(new SimpleDateFormat("yyyy-MM-dd").format(employee.getBirth().getTime()));
            }
            cell9.setCellStyle(style);
            HSSFCell cell10 = row.createCell(10);
            if(employee.getDateToSchool() != null) {
                cell10.setCellValue(new SimpleDateFormat("yyyy-MM-dd").format(employee.getDateToSchool().getTime()));
            }
            cell10.setCellStyle(style);
            HSSFCell cell11 = row.createCell(11);
            if(employee.getDateToWork() != null) {
                cell11.setCellValue(new SimpleDateFormat("yyyy-MM-dd").format(employee.getDateToWork().getTime()));
            }
            cell11.setCellStyle(style);
            HSSFCell cell12 = row.createCell(12);
            if(employee.getDateToRetire() != null) {
                cell12.setCellValue(new SimpleDateFormat("yyyy-MM-dd").format(employee.getDateToRetire().getTime()));
            }
            cell12.setCellStyle(style);
            HSSFCell cell13 = row.createCell(13);
            if(employee.getSalaryNo() != null){
                cell13.setCellValue(employee.getSalaryNo());
            }
            cell13.setCellStyle(style);
            HSSFCell cell14 = row.createCell(14);
            if(employee.getIdNo() != null){
                cell14.setCellValue(employee.getIdNo());
            }
            cell14.setCellStyle(style);
            HSSFCell cell15 = row.createCell(15);
            if(employee.getMobile() != null){
                cell15.setCellValue(employee.getMobile());
            }
            cell15.setCellStyle(style);
            HSSFCell cell16 = row.createCell(16);
            if(employee.getEmergencyMobile() != null){
                cell16.setCellValue(employee.getEmergencyMobile());
            }
            cell16.setCellStyle(style);
            HSSFCell cell17 = row.createCell(17);
            if(employee.getAddress() != null){
                cell17.setCellValue(employee.getAddress());
            }
            cell17.setCellStyle(style);
            HSSFCell cell18 = row.createCell(18);
            if(employee.getEmail() != null){
                cell18.setCellValue(employee.getEmail());
            }
            cell18.setCellStyle(style);
            HSSFCell cell19 = row.createCell(19);
            if(employee.getQq() != null){
                cell19.setCellValue(employee.getQq());
            }
            cell19.setCellStyle(style);
            HSSFCell cell20 = row.createCell(20);
            if(employee.getWeChat() != null){
                cell20.setCellValue(employee.getWeChat());
            }
            cell20.setCellStyle(style);
        }

        //为每一列设置自动宽度
        for (int i = 0; i < 21; i++) {
            sheet.autoSizeColumn(i);
        }

//        //获取桌面路径
//        FileSystemView fsv = FileSystemView.getFileSystemView();
//
//        String url = fsv.getHomeDirectory().getPath();
//            String url = "";

        //输出文件
//        FileOutputStream fileOutputStream = null;
//
        Result result = new Result();


        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        workbook.write(byteArrayOutputStream);
        byteArrayOutputStream.flush();

        Utils.download(byteArrayOutputStream,response,request,"employee.xlsx");

//
//        String fileName = "", filePath = "";

        //判断系统类型，系统不同，对应路径不同
//        String url = System.getProperty("user.dir") + "/src/main/resources/static";
//        String url1 = System.getProperty("user.dir");
//        int indexOf = url1.indexOf("bin");
//        String url2= url1.substring(0, indexOf);
//        String url = url2 + "webapps/glmis" + "/WEB-INF/classes/static";
//
//        url += "/employees.xls";
//
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
//            result.setMsg("导出成功。");
//            result.setSuccess(true);
//            return result;
//        } catch (FileNotFoundException e) {
//            if(e.getMessage().contains("另一个程序正在使用此文件，进程无法访问。")){
//                result.setMsg("导出失败，请关闭文件后再次操作。");
//            }else{
//                result.setMsg("导出失败，请稍后再次操作!");
//            }
//            logger.debug(e.getMessage());
//            return result;
//        } catch (IOException e) {
//            result.setMsg("导出失败，请再次操作。");
//            logger.debug(e.getMessage());
//            return result;
//        }
        return result;

    }

}
