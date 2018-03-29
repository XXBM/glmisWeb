package com.glmis.excel;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;

import java.io.*;

/**
 * Created by kene on 17-5-23.
 */
public class DataOutToExcel {

    public void dataOut(String url, String[][] empTestResult) throws IOException {
        //创建一个输入刘来读取excel文件
        InputStream inputStream = new FileInputStream(url);
        //得到文件后，使用POIFSFileSystem类来解析数据
        POIFSFileSystem poifsFileSystem = new POIFSFileSystem(inputStream);
        //把解析的数据生成一个excel
        HSSFWorkbook workbook = new HSSFWorkbook(poifsFileSystem);
        //获取此excel的第一个sheet，下标为0
        HSSFSheet sheet = workbook.getSheetAt(0);

        //字体样式
        HSSFFont font = workbook.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 12);

        //设置cell样式
        HSSFCellStyle styleColorRed = workbook.createCellStyle();
        styleColorRed.setFillForegroundColor(IndexedColors.RED.getIndex());
        styleColorRed.setFillPattern(CellStyle.SOLID_FOREGROUND);
        //水平居中
        styleColorRed.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        //垂直居中
        styleColorRed.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        //把字体样式添加到cell样式中
        styleColorRed.setFont(font);

        HSSFCellStyle styleColorGreen = workbook.createCellStyle();
        styleColorGreen.setFillForegroundColor(IndexedColors.GREEN.getIndex());
        styleColorGreen.setFillPattern(CellStyle.SOLID_FOREGROUND);
        styleColorGreen.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        styleColorGreen.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        styleColorGreen.setFont(font);


        for (int i = 1; i < sheet.getLastRowNum()+1; i++) {
            //获取行，
            HSSFRow row = sheet.getRow(i);
            //因为结果列单元格为空，所以需要创建
            HSSFCell cell = row.createCell(1);
            //判断结果和期望是否一样，一样背景设为绿色
            if (empTestResult[0][i-1].equals(empTestResult[1][i-1])) {
                cell.setCellValue(empTestResult[1][i-1]);
                cell.setCellStyle(styleColorGreen);
            //否则为红色
            } else {
                cell.setCellValue(empTestResult[1][i-1]);
                cell.setCellStyle(styleColorRed);
            }

        }
        //设置结果列自动适应行宽
        sheet.autoSizeColumn(1);

        //把excel数据输出
        FileOutputStream fileOutputStream = new FileOutputStream(url);
        workbook.write(fileOutputStream);
        fileOutputStream.close();
    }
}
