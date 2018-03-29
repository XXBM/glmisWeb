//package com.glmis;
//
//import com.glmis.importExcel.ImportExcel;
//import org.apache.poi.hssf.usermodel.HSSFPicture;
//import org.apache.poi.hssf.usermodel.HSSFPictureData;
//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//import org.apache.poi.poifs.filesystem.POIFSFileSystem;
//
//import javax.swing.filechooser.FileSystemView;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//
///**
// * Created by kene on 17-5-14.
// */
//public class Test {
//    public static void main(String[] args) throws IOException {
////        String url =  FileSystemView.getFileSystemView().getHomeDirectory().getPath() + "/Desktop/import2.xls";
////        //创建一个输入刘来读取excel文件
////        InputStream inputStream = new FileInputStream(url);
////        //得到文件后，使用POIFSFileSystem类来解析数据
////        POIFSFileSystem poifsFileSystem = new POIFSFileSystem(inputStream);
////        //把解析的数据生成一个excel
////        HSSFWorkbook workbook = new HSSFWorkbook(poifsFileSystem);
////
////        List<HSSFPictureData> pictures = workbook.getAllPictures();
////
////        System.out.println("data:  " + pictures.get(0).getData());
////
////        String picture = new String(pictures.get(0).getData(), "UTF-8");
////
////        System.out.println("string:  " + picture );
////
////        byte[] bytes = picture.getBytes("UTF-8");
////
////        System.out.println("bytes:  " + bytes);
//
//
////        Map<Integer, Map<String, String>> employees = new ImportExcel().importExcel();
////
////        Map<String, String> employee = employees.get(1);
//
////        FileOutputStream fileOutputStream = new FileOutputStream("/home/kene/Desktop/11.png");
////        fileOutputStream.write(employee.get("图片").getBytes("UTF-8"));
////        System.out.println(employee.get("图片").getBytes("UTF-8"));
////        fileOutputStream.close();
//
//        Map<Integer, byte[]> empPicture = new ImportExcel().importExcelPicture();
//
//        FileOutputStream fileOutputStream = new FileOutputStream("/home/kene/Desktop/11.png");
//        fileOutputStream.write(empPicture.get(1));
//        fileOutputStream.close();
//    }
//}
