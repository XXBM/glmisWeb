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
public class AwardTest {
    private WebDriver webDriver;

    //在被@Test注解标注的方法之前运行
    @BeforeClass
    public void setUp(){
        webDriver = Util.setUpChrome();
    }


    //测试的方法，invocationCount表示此方法运行的次数,默认为1
    @Test(invocationCount = 1)
    public void TestAddAward() throws IOException {
        //获取Excel的测试数据，并保存到map集合中
        ImportExcel importExcel = new ImportExcel();
        String url = System.getProperty("user.dir") + "/src/test/resources/static/testData/scientificResearch/AwardTest.xls";
        //String url =  FileSystemView.getFileSystemView().getHomeDirectory().getPath() + "/Desktop/AwardTest.xls";
        Map<Integer, Map<String, String>> awards = importExcel.importExcel(url);

        webDriver.findElement(By.xpath("//span[text()='我的科研']")).click();
        webDriver.findElement(By.xpath("//div[@id='myky']//div[@class='tabs-wrap']//ul[@class='tabs']//li[2]//a//span[2]")).click();
        /*String url2 =  FileSystemView.getFileSystemView().getHomeDirectory().getPath() + "/Desktop/Award_TestResult.txt";
        File file = new File(url2);
        FileOutputStream testFile = new FileOutputStream(file);
        PrintStream p = new PrintStream(testFile);*/
        //储存结果信息
        String[][] awardTestResult = new String[2][awards.size()];
        //外层for循环代表excel中的测试数据数目，数目即excel中除标题栏还有几行数据
        for (int i = 0; i < awards.size(); i++) {
            Map<String, String> award = awards.get(i + 1);
            webDriver.findElement(By.xpath("//a[@id='Award_add_btn']")).click();
            //级别
            if (award.get("级别")!=null&&award.get("级别")!="") {
                webDriver.findElement(By.xpath("//td[@field='awardsRank']//span[@class='textbox-addon textbox-addon-right']")).click();
                Util.sleep(100);
                webDriver.findElement(By.xpath("//div[@class='panel combo-p' and contains(@style,'z-index: 1100')]//div[text()='" + award.get("级别") + "']")).click();
            }
            //等级
            if (award.get("等级")!=null&&award.get("等级")!="") {
                webDriver.findElement(By.xpath("//td[@field='awardLevel']//span[@class='textbox-addon textbox-addon-right']")).click();
                Util.sleep(100);
                webDriver.findElement(By.xpath("//div[@class='panel combo-p' and contains(@style,'z-index: 1100')]//div[text()='" + award.get("等级") + "']")).click();
            }
            //成果名称
            if (award.get("成果名称")!=null&&award.get("成果名称")!="") {
                webDriver.findElement(By.xpath("//td[@field='title']//input")).sendKeys(award.get("成果名称"));
            }
            //代表作者
            if (award.get("代表作者")!=null&&award.get("代表作者")!="") {
                webDriver.findElement(By.xpath("//td[@field='author']//input")).sendKeys(award.get("代表作者"));
            }
            //奖项名称
            if (award.get("奖项名称")!=null&&award.get("奖项名称")!="") {
                webDriver.findElement(By.xpath("//td[@field='name']//input")).sendKeys(award.get("奖项名称"));
            }
            //批准部门
            if (award.get("批准部门")!=null&&award.get("批准部门")!="") {
                webDriver.findElement(By.xpath("//td[@field='sponsor']//input")).sendKeys(award.get("批准部门"));
            }
            if (award.get("参加人数")!=null&&award.get("参加人数")!="") {
                webDriver.findElement(By.xpath("//td[@field='numOfParticipants']//input[@class='textbox-text validatebox-text validatebox-invalid textbox-prompt']")).sendKeys(award.get("参加人数"));
            }
            if (award.get("本人位次")!=null&&award.get("本人位次")!="") {
                webDriver.findElement(By.xpath("//td[@field='seating']//input[@class='textbox-text validatebox-text validatebox-invalid textbox-prompt']")).sendKeys(award.get("本人位次"));
            }//获得时间
            if (award.get("获奖时间")!=null&&award.get("获奖时间")!="") {
                webDriver.findElement(By.xpath("//td[@field='dateOfAward']//span[@class='textbox-addon textbox-addon-right']//a")).click();
                Util.chooseCalendar(award.get("获奖时间"), webDriver);
            }
            //

            webDriver.findElement(By.xpath("//a[@id='Award_save_btn']")).click();
            String qiWang =award.get("期望");
            String testResult = webDriver.findElement(By.xpath("//div[@class='panel window messager-window']//div[@class='messager-body panel-body panel-body-noborder window-body']")).getText();
            Assert.assertEquals(testResult, qiWang);
            awardTestResult[0][i] = qiWang;
            awardTestResult[1][i] = testResult;
            /*if (testResult.equals(qiWang)){
                System.out.println("测试成功");
                p.println("第"+(i+1)+"条测试成功");

            }*/
            webDriver.findElement(By.xpath("//*[text()='确定']")).click();
            if (qiWang.equals("请检查输入信息，查看红色字段")) {
                webDriver.findElement(By.xpath("//a[@id='Award_cancel_btn']")).click();
            }
        }
        //excel表格中输入结果数据
        new DataOutToExcel().dataOut(url, awardTestResult);
        //p.close();
    }
    @Test(invocationCount = 1)
    public void testDeleteAward(){

        webDriver.findElement(By.xpath("//span[text()='我的科研']")).click();

        webDriver.findElement(By.xpath("//div[@id='myky']//div[@class='tabs-wrap']//ul[@class='tabs']//li[2]//a//span[2]")).click();

        webDriver.findElement(By.xpath("//div[@class='datagrid-view']//div[@class='datagrid-body']//tr[@id='datagrid-row-r10-1-0']")).click();
        webDriver.findElement(By.xpath("//a[@id='Award_delete_btn']")).click();

        webDriver.findElement(By.xpath("//*[text()='确定']")).click();

        Util.sleep(100);

        webDriver.findElement(By.xpath("//*[text()='确定']")).click();
    }
    @Test(invocationCount = 1)
    public void TestEditAward() throws IOException {
        webDriver.findElement(By.xpath("//span[text()='我的科研']")).click();

        webDriver.findElement(By.xpath("//div[@id='myky']//div[@class='tabs-wrap']//ul[@class='tabs']//li[2]//a//span[2]")).click();
        webDriver.findElement(By.xpath("//div[@class='datagrid-view']//div[@class='datagrid-body']//tr[@id='datagrid-row-r10-1-0']")).click();

        webDriver.findElement(By.xpath("//a[@id='Award_update_btn']")).click();

        //级别
        webDriver.findElement(By.xpath("//td[@field='awardsRank']//span[@class='textbox-addon textbox-addon-right']")).click();
        Util.sleep(100);
        webDriver.findElement(By.xpath("//div[@class='panel combo-p' and contains(@style,'z-index: 1100')]//div[text()='校级']")).click();
        //成果名称
        webDriver.findElement(By.xpath("//td[@field='title']//input")).sendKeys("d");
        //代表作者
        webDriver.findElement(By.xpath("//td[@field='author']//input")).sendKeys("d");
        //奖项名称
        webDriver.findElement(By.xpath("//td[@field='name']//input")).sendKeys("d");
        //等级
        webDriver.findElement(By.xpath("//td[@field='awardLevel']//span[@class='textbox-addon textbox-addon-right']")).click();
        Util.sleep(100);
        webDriver.findElement(By.xpath("//div[@class='panel combo-p' and contains(@style,'z-index: 1100')]//div[text()='三等奖']")).click();
        //批准部门
        webDriver.findElement(By.xpath("//td[@field='sponsor']//input")).sendKeys("d");
        //获奖时间
        webDriver.findElement(By.xpath("//td[@field='dateOfAward']//span[@class='textbox-addon textbox-addon-right']")).click();
        webDriver.findElement(By.xpath("//div[@class='panel combo-p' and contains(@style,'z-index: 1100')]//td[@abbr='2017,3,6']")).click();
        //本人位次
        webDriver.findElement(By.xpath("//td[@field='seating']//span[@class='textbox-addon textbox-addon-right']")).click();
        Util.sleep(100);
        //作者人数
        webDriver.findElement(By.xpath("//td[@field='numOfParticipants']//span[@class='textbox-addon textbox-addon-right']")).click();
        Util.sleep(100);

        webDriver.findElement(By.xpath("//a[@id='Award_save_btn']")).click();

        webDriver.findElement(By.xpath("//*[text()='确定']")).click();
        

    }

}
