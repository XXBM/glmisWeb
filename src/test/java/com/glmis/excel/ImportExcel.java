package com.glmis.excel;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kene on 17-5-14.
 */
public class ImportExcel {

    public Map<Integer, Map<String, String>> importExcel(String url) throws IOException {

        //此集合用来储存测试的数据，key为第几条数据，value为数据的内容（Map类型）
        Map<Integer, Map<String, String>> employees = new HashMap<>();
        //创建一个输入刘来读取excel文件
        InputStream inputStream = new FileInputStream(url);
        //得到文件后，使用POIFSFileSystem类来解析数据
        POIFSFileSystem poifsFileSystem = new POIFSFileSystem(inputStream);
        //把解析的数据生成一个excel
        HSSFWorkbook workbook = new HSSFWorkbook(poifsFileSystem);
        //获取此excel的第一个sheet，下标为0
        HSSFSheet sheet = workbook.getSheetAt(0);
        //获取此sheet的第一行，即标题行
        HSSFRow row = sheet.getRow(0);
        //根据标题行的cell个数，创建一个数组来储存标题
        String[] title = new String[row.getPhysicalNumberOfCells()];

        //获取每个cell的数值（String类型），保存到title数组中
        for(int i = 0; i < row.getPhysicalNumberOfCells(); i++){
            title[i] = row.getCell(i).getStringCellValue();
        }

        //设置全局的cell数目。 因为当某个单元格为空时，计算数目并不会计算在内
        int rowNumberOfCells = row.getPhysicalNumberOfCells();

        //此循环表示数据的条数，因为表格第一行为标题，所以从第二行数据开始（i=1）,
        //sheet.getLastRowNum()为获取表格数据的行数，如为3行，则sheet.getLastRowNum()值为2，（0,1,2）
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            //获得表格的某行
            row = sheet.getRow(i);
            //创建一个Map集合，储存测试的数据，key为标题(从title中获取)，value为数值(读取)
            Map<String, String> employee = new HashMap<>();
            //循环此行每个单元格
            for (int j = 0; j < rowNumberOfCells; j++) {
                //第几个单元格
                HSSFCell cell = row.getCell(j);

                if(cell == null){

                    employee.put(title[j], "");
                    continue;
                }
                //因为数据并一定为什么格式(用到的只有String和number)

                switch (cell.getCellType()) {
                    //如果为String类型
                    case HSSFCell.CELL_TYPE_STRING:
                        //key为title(姓名)，value为数据(李四)
                        //使用getStringCellValue()方法来获取String类型的数据
                        employee.put(title[j],cell.getStringCellValue());
                        break;
                    //如果数据为number，此测试excel涉及到两种(date和int)
                    case HSSFCell.CELL_TYPE_NUMERIC:
                        //如果此数值为date类型，getDateCellValue()方法获取，并格式化为yyyy-MM-dd类型
                        if (HSSFDateUtil.isCellDateFormatted(cell)) {
                            employee.put(title[j] ,new SimpleDateFormat("yyyy-MM-dd").format(cell.getDateCellValue()));
                        //如果为int型，使用getNumericCellValue()方法获取
                        } else {
                            employee.put(title[j], String.valueOf(cell.getNumericCellValue()));
                        }
                        break;
                }
            }
            //当内层for循环结束，表示此行数据都保存到employee集合中，把employee集合保存到employees集合中
            //key为i(如”1”)， value为Map集合(如第二行的数据或者说要测试的第一个员工信息)
            employees.put(i, employee);
        }


        //返回employees集合(包含n个要测试的员工信息)
        return employees;
    }

    public Map<Integer, byte[]> importExcelPicture(String url) throws IOException {
        Map<Integer, byte[]> empPictures = new HashMap<>();

        //创建一个输入刘来读取excel文件
        InputStream inputStream = new FileInputStream(url);
        //得到文件后，使用POIFSFileSystem类来解析数据
        POIFSFileSystem poifsFileSystem = new POIFSFileSystem(inputStream);
        //把解析的数据生成一个excel
        HSSFWorkbook workbook = new HSSFWorkbook(poifsFileSystem);

        List<HSSFPictureData> pictures = workbook.getAllPictures();


        for (int i = 0; i < pictures.size() ; i++) {
            empPictures.put(i+1, pictures.get(i).getData());
        }

        return empPictures;
    }
}
