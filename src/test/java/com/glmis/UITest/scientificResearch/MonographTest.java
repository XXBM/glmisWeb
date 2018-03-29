package com.glmis.UITest.scientificResearch;

import com.glmis.UITest.TestConstants;
import com.glmis.UITest.Util;
import com.glmis.excel.DataOutToExcel;
import com.glmis.excel.ImportExcel;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by kene on 17-5-14.
 */
public class MonographTest {
    private WebDriver webDriver;

    //在被@Test注解标注的方法之前运行
    @BeforeClass
    public void setUp(){
        webDriver = Util.setUpChrome();
    }


    //测试的方法，invocationCount表示此方法运行的次数,默认为1
    @Test(invocationCount = 1)
    public void TestAddMonograph() throws IOException {
        //获取Excel的测试数据，并保存到map集合中
        ImportExcel importExcel = new ImportExcel();
        String url = System.getProperty("user.dir") + "/src/test/resources/static/testData/scientificResearch/MonographTest.xls";
        Map<Integer, Map<String, String>> monographes = importExcel.importExcel(url);

        webDriver.findElement(By.xpath("//span[text()='我的科研']")).click();
        webDriver.findElement(By.xpath("//div[@id='myky']//div[@class='tabs-wrap']//ul[@class='tabs']//li[3]//a//span[2]")).click();
        /*String url2 =  FileSystemView.getFileSystemView().getHomeDirectory().getPath() + "/Desktop/Monograph_TestResult.txt";
        File file = new File(url2);
        FileOutputStream testFile = new FileOutputStream(file);
        PrintStream p = new PrintStream(testFile);*/
        //储存结果信息
        String[][] monographTestResult = new String[2][monographes.size()];
        //外层for循环代表excel中的测试数据数目，数目即excel中除标题栏还有几行数据
        for (int i = 0; i < monographes.size(); i++) {
            Map<String, String> monograph = monographes.get(i + 1);
            webDriver.findElement(By.xpath("//a[@id='Monograph_add_btn']")).click();
            //名称
            if (monograph.get("名称")!=null&&monograph.get("名称")!="") {
                webDriver.findElement(By.xpath("//td[@field='name']//input")).sendKeys(monograph.get("名称"));
            }
            //负责人
            if (monograph.get("负责人")!=null&&monograph.get("负责人")!="") {
                webDriver.findElement(By.xpath("//td[@field='author']//input")).sendKeys(monograph.get("负责人"));
            }
            //出版社
            if (monograph.get("出版社")!=null&&monograph.get("出版社")!="") {
                webDriver.findElement(By.xpath("//td[@field='press']//input")).sendKeys(monograph.get("出版社"));
            }
            //出版时间
            if (monograph.get("出版时间")!=null&&monograph.get("出版时间")!="") {
                webDriver.findElement(By.xpath("//td[@field='publicationTime']//span[@class='textbox-addon textbox-addon-right']//a")).click();
                Util.chooseCalendar(monograph.get("出版时间"), webDriver);
            }
            //千字数
            if (monograph.get("千字数")!=null&&monograph.get("千字数")!="") {
                webDriver.findElement(By.xpath("//td[@field='words']//input")).sendKeys(monograph.get("千字数"));
            }
            if (monograph.get("作者人数")!=null&&monograph.get("作者人数")!="") {
                webDriver.findElement(By.xpath("//td[@field='numOfParticipants']//input[@class='textbox-text validatebox-text validatebox-invalid textbox-prompt']")).sendKeys(monograph.get("作者人数"));
            }//ISBN
            if (monograph.get("ISBN")!=null&&monograph.get("ISBN")!="") {
                webDriver.findElement(By.xpath("//td[@field='isbn']//input")).sendKeys(monograph.get("ISBN"));
            }
            //级别
            if (monograph.get("级别")!=null&&monograph.get("级别")!="") {
                webDriver.findElement(By.xpath("//td[@field='monographRank']//span[@class='textbox-addon textbox-addon-right']")).click();
                Util.sleep(100);
                webDriver.findElement(By.xpath("//div[@class='panel combo-p' and contains(@style,'z-index: 1100')]//div[text()='" + monograph.get("级别") + "']")).click();
            }

            if (monograph.get("本人位次")!=null&&monograph.get("本人位次")!="") {
                webDriver.findElement(By.xpath("//td[@field='seating']//input[@class='textbox-text validatebox-text validatebox-invalid textbox-prompt']")).sendKeys(monograph.get("本人位次"));
            }
            webDriver.findElement(By.xpath("//a[@id='Monograph_save_btn']")).click();
            String qiWang =monograph.get("期望");
            String testResult = webDriver.findElement(By.xpath("//div[@class='panel window messager-window']//div[@class='messager-body panel-body panel-body-noborder window-body']")).getText();
            Assert.assertEquals(testResult, qiWang);
            monographTestResult[0][i] = qiWang;
            monographTestResult[1][i] = testResult;
            /*if (testResult.equals(qiWang)){
                System.out.println("测试成功");
                p.println("第"+(i+1)+"条测试成功");

            }*/
            webDriver.findElement(By.xpath("//*[text()='确定']")).click();
            if (qiWang.equals("请检查输入信息，查看红色字段")) {
                webDriver.findElement(By.xpath("//a[@id='Monograph_cancel_btn']")).click();
            }
        }
        //excel表格中输入结果数据
        new DataOutToExcel().dataOut(url, monographTestResult);
        //p.close();
    }
    @Test(invocationCount = 1)
    public void testDeleteMonograph(){

        webDriver.findElement(By.xpath("//span[text()='我的科研']")).click();

        webDriver.findElement(By.xpath("//div[@id='myky']//div[@class='tabs-wrap']//ul[@class='tabs']//li[3]//a//span[2]")).click();

        webDriver.findElement(By.xpath("//div[@class='datagrid-view']//div[@class='datagrid-body']//tr[@id='datagrid-row-r11-2-0']")).click();
        webDriver.findElement(By.xpath("//a[@id='Monograph_delete_btn']")).click();

        webDriver.findElement(By.xpath("//*[text()='确定']")).click();

        Util.sleep(100);

        webDriver.findElement(By.xpath("//*[text()='确定']")).click();
    }
    @Test(invocationCount = 1)
    public void TestEditMonograph() throws IOException {
        webDriver.findElement(By.xpath("//span[text()='我的科研']")).click();

        webDriver.findElement(By.xpath("//div[@id='myky']//div[@class='tabs-wrap']//ul[@class='tabs']//li[3]//a//span[2]")).click();
        webDriver.findElement(By.xpath("//div[@class='datagrid-view']//div[@class='datagrid-body']//tr[@id='datagrid-row-r11-2-0']")).click();

        webDriver.findElement(By.xpath("//a[@id='Monograph_update_btn']")).click();
        //名称
        webDriver.findElement(By.xpath("//td[@field='name']//input")).sendKeys("d");
        //负责人
        webDriver.findElement(By.xpath("//td[@field='author']//input")).sendKeys("d");
        //出版社
        webDriver.findElement(By.xpath("//td[@field='press']//input")).sendKeys("d");
        //出版时间
        webDriver.findElement(By.xpath("//td[@field='publicationTime']//span[@class='textbox-addon textbox-addon-right']")).click();
        webDriver.findElement(By.xpath("//div[@class='panel combo-p' and contains(@style,'z-index: 1100')]//td[@abbr='2017,5,6']")).click();
        //千字数
        webDriver.findElement(By.xpath("//td[@field='words']//input")).sendKeys("3");
        //本人位次
        webDriver.findElement(By.xpath("//td[@field='seating']//span[@class='textbox-addon textbox-addon-right']")).click();
        Util.sleep(100);
        //级别
        webDriver.findElement(By.xpath("//td[@field='monographRank']//span[@class='textbox-addon textbox-addon-right']")).click();
        Util.sleep(100);
        webDriver.findElement(By.xpath("//div[@class='panel combo-p' and contains(@style,'z-index: 1100')]//div[text()='省级出版社']")).click();
        //ISBN
        webDriver.findElement(By.xpath("//td[@field='isbn']//input")).sendKeys("a");

        //作者人数
        webDriver.findElement(By.xpath("//td[@field='numOfParticipants']//span[@class='textbox-addon textbox-addon-right']")).click();
        Util.sleep(100);

        webDriver.findElement(By.xpath("//a[@id='Monograph_save_btn']")).click();

        webDriver.findElement(By.xpath("//*[text()='确定']")).click();
        

    }

}
