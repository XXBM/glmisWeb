package com.glmis.UITest.scientificResearch;

import com.glmis.UITest.Util;
import com.glmis.excel.DataOutToExcel;
import com.glmis.excel.ImportExcel;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Map;

/**
 * Created by inkskyu428 on 17-5-20.
 */
public class TextBookTest {
    private WebDriver webDriver;

    @BeforeClass
    public void setUp(){
        webDriver = Util.setUpChrome();
    }

    @Test(invocationCount = 1)
    public void testAddTextBook()throws IOException{
        //获取Excel的测试数据，并保存到map集合中
        ImportExcel importExcel = new ImportExcel();
        String url = System.getProperty("user.dir") + "/src/test/resources/static/testData/scientificResearch/TextBookTest.xls";
        //String url =  FileSystemView.getFileSystemView().getHomeDirectory().getPath() + "/Desktop/TextBookTest.xls";
        Map<Integer, Map<String, String>> textbooks = importExcel.importExcel(url);
        webDriver.findElement(By.xpath("//span[text()='我的科研']")).click();

        webDriver.findElement(By.xpath("//div[@id='myky']//div[@class='tabs-wrap']//ul[@class='tabs']//li[4]//a//span[2]")).click();
        //储存结果信息
        String[][] textbookTestResult = new String[2][textbooks.size()];
//        String resultUrl =  FileSystemView.getFileSystemView().getHomeDirectory().getPath() + "/Desktop/textbook_testResult.txt";
//        File file =new File(resultUrl);
//        FileOutputStream testFile =new FileOutputStream(file);
//        PrintStream printStream =new PrintStream(testFile);
        //外层for循环代表excel中的测试数据数目，数目即excel中除标题栏还有几行数据
        for (int i = 0; i < textbooks.size(); i++) {
            //从employees集合后中获取第n个测试数据，即第n个要添加的员工信息, i+1是因为向employees集合中添加第一条员工数据的key为1
            Map<String, String> textbook = textbooks.get(i + 1);

            webDriver.findElement(By.xpath("//a[@id='TextBook_add_btn']")).click();
            if (!"".equals(textbook.get("名称")) && textbook.get("名称") != null) {
                webDriver.findElement(By.xpath("//td[@field='name']//input")).sendKeys(textbook.get("名称"));
            }
            if (!"".equals(textbook.get("负责人")) && textbook.get("负责人") != null) {
                webDriver.findElement(By.xpath("//td[@field='editor']//input")).sendKeys(textbook.get("负责人"));
            }
            if (!"".equals(textbook.get("出版社")) && textbook.get("出版社") != null) {
                webDriver.findElement(By.xpath("//td[@field='press']//input")).sendKeys(textbook.get("出版社"));
            }
            //输入框版
            //webDriver.findElement(By.xpath("//td[@field='publicationTime']//input[@class='textbox-text validatebox-text validatebox-invalid textbox-prompt']")).sendKeys(textbook.get("出版时间"));
            //时间选择器版
            if (textbook.get("出版时间")!=null&&textbook.get("出版时间")!="") {
                webDriver.findElement(By.xpath("//td[@field='publicationTime']//span[@class='textbox-addon textbox-addon-right']//a")).click();
                Util.chooseCalendar(textbook.get("出版时间"), webDriver);
            }
            if (!"".equals(textbook.get("千字数")) && textbook.get("千字数") != null) {
                webDriver.findElement(By.xpath("//td[@field='words']//input")).sendKeys(textbook.get("千字数"));
            }
            if (!"".equals(textbook.get("isbn")) && textbook.get("isbn") != null) {
                webDriver.findElement(By.xpath("//td[@field='isbn']//input")).sendKeys(textbook.get("isbn"));
            }

            if (!"".equals(textbook.get("本人位次")) && textbook.get("本人位次") != null) {
                webDriver.findElement(By.xpath("//td[@field='seating']//input[@class='textbox-text validatebox-text validatebox-invalid textbox-prompt']")).sendKeys(textbook.get("本人位次"));
            }
            if (!"".equals(textbook.get("作者人数")) && textbook.get("作者人数") != null) {
                webDriver.findElement(By.xpath("//td[@field='numOfParticipants']//input[@class='textbox-text validatebox-text validatebox-invalid textbox-prompt']")).sendKeys(textbook.get("作者人数"));
            }
            //级别下拉框
            if (textbook.get("级别")!=null&&textbook.get("级别")!="") {
                webDriver.findElement(By.xpath("//td[@field='textbookRank']//span[@class='textbox-addon textbox-addon-right']")).click();
                Util.sleep(100);
                webDriver.findElement(By.xpath("//div[@class='panel combo-p' and contains(@style,'z-index: 1100')]//div[text()='" + textbook.get("级别") + "']")).click();
            }
            webDriver.findElement(By.xpath("//a[@id='TextBook_save_btn']")).click();
            Util.sleep(1000);
            String message = webDriver.findElement(By.xpath("//div[@class='panel window messager-window']//div[@class='messager-body panel-body panel-body-noborder window-body']//div[2]")).getText();
//            if (textbook.get("期望").equals(input))
//                printStream.println("第"+(i+1)+"条"+"测试成功");
//            else
//                printStream.println("第"+(i+1)+"条"+"测试失败");
            Assert.assertEquals(message, textbook.get("期望"));
            textbookTestResult[0][i] = textbook.get("期望");
            textbookTestResult[1][i] = message;
            webDriver.findElement(By.xpath("//*[text()='确定']")).click();
            if (message.equals("请检查输入信息，查看红色字段")) {
                webDriver.findElement(By.xpath("//a[@id='TextBook_redo_btn']")).click();
            }
//          webDriver.close();
        }
        //excel表格中输入结果数据
        new DataOutToExcel().dataOut(url, textbookTestResult);
    }


    @Test(invocationCount = 1)
    public void testDeleteTextBook(){

        webDriver.findElement(By.xpath("//span[text()='我的科研']")).click();

        webDriver.findElement(By.xpath("//div[@id='myky']//div[@class='tabs-wrap']//ul[@class='tabs']//li[4]//a//span[2]")).click();

        webDriver.findElement(By.xpath("//div[@id='myky']//div[@id='textBook_div']//div[@class='datagrid-view2']//div[@class='datagrid-body']//tr[@datagrid-row-index='0']")).click();

        webDriver.findElement(By.xpath("//a[@id='TextBook_remove_btn']")).click();

        webDriver.findElement(By.xpath("//*[text()='确定']")).click();

        Util.sleep(200);

        webDriver.findElement(By.xpath("//*[text()='确定']")).click();
    }

    @Test(invocationCount = 1)
    public void testEditTextBook(){
        webDriver.findElement(By.xpath("//span[text()='我的科研']")).click();

        webDriver.findElement(By.xpath("//div[@id='myky']//div[@class='tabs-wrap']//ul[@class='tabs']//li[4]//a//span[2]")).click();

        webDriver.findElement(By.xpath("//div[@id='myky']//div[@id='textBook_div']//div[@class='datagrid-view2']//div[@class='datagrid-body']//tr[@datagrid-row-index='0']")).click();

        webDriver.findElement(By.xpath("//a[@id='TextBook_edit_btn']")).click();

        webDriver.findElement(By.xpath("//td[@field='name']//input")).sendKeys("教材2");
        webDriver.findElement(By.xpath("//td[@field='editor']//input")).sendKeys("李四");
        webDriver.findElement(By.xpath("//td[@field='press']//input")).sendKeys("中央出版社");

        webDriver.findElement(By.xpath("//td[@field='publicationTime']//span[@class='textbox-addon textbox-addon-right']")).click();
        Util.sleep(200);
        webDriver.findElement(By.xpath("//div[@class='panel combo-p' and contains(@style,'z-index: 1100')]//td[@abbr='2013,6,17']")).click();
        webDriver.findElement(By.xpath("//td[@field='words']//input")).sendKeys("200");
        //级别下拉框
        webDriver.findElement(By.xpath("//td[@field='textbookRank']//span[@class='textbox-addon textbox-addon-right']")).click();
        Util.sleep(100);
        webDriver.findElement(By.xpath("//div[@class='panel combo-p' and contains(@style,'z-index: 1100')]//div[text()='省级规划教材']")).click();
        webDriver.findElement(By.xpath("//td[@field='isbn']//input")).sendKeys("319890428");

        webDriver.findElement(By.xpath("//td[@field='seating']//span[@class='textbox-addon textbox-addon-right']//a[@class='textbox-icon spinner-arrow']//a[@class='spinner-arrow-up']")).click();
        webDriver.findElement(By.xpath("//td[@field='numOfParticipants']//span[@class='textbox-addon textbox-addon-right']//a[@class='textbox-icon spinner-arrow']//a[@class='spinner-arrow-up']")).click();

        webDriver.findElement(By.xpath("//a[@id='TextBook_save_btn']")).click();

    }
}
