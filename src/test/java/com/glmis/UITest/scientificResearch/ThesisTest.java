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
 * Created by kene on 17-5-13.
 */
public class ThesisTest {

    private WebDriver webDriver;

    @BeforeClass
    public void setUp(){
        webDriver = Util.setUpChrome();
    }



    @Test(invocationCount = 1)
    public void testAddAcademicDegree() throws IOException {
        //获取Excel的测试数据，并保存到map集合中
        ImportExcel importExcel = new ImportExcel();
        //String url =  "F:\\glmis\\testingData\\ThesisTest.xls";
        String url = System.getProperty("user.dir") + "/src/test/resources/static/testData/scientificResearch/ThesisTest.xls";
        Map<Integer, Map<String, String>> thesisList = importExcel.importExcel(url);

        webDriver.findElement(By.xpath("//span[text()='我的科研']")).click();
        webDriver.findElement(By.xpath("//div[@id='myky']//div[@class='tabs-wrap']//ul[@class='tabs']//li[1]//a//span[2]")).click();
        Util.sleep(5000);
        String[][] ResultOfTest = new String[2][thesisList.size()];
        for (int i = 0; i < thesisList.size(); i++) {
            Map<String, String> thesis = thesisList.get(i+1);
            webDriver.findElement(By.xpath("//a[@id='Thesis_add_btn']")).click();
            //职称下拉框


            if (thesis.get("期刊级别")!=null&&thesis.get("期刊级别")!="") {
                webDriver.findElement(By.xpath("//td[@field='journalRank']//span[@class='textbox-addon textbox-addon-right']")).click();
                Util.sleep(100);
                webDriver.findElement(By.xpath("//div[@class='panel combo-p' and contains(@style,'z-index: 1100')]//div[text()='" + thesis.get("期刊级别") + "']")).click();
            }
//
            if (thesis.get("收录情况")!=null&&thesis.get("收录情况")!="") {
                webDriver.findElement(By.xpath("//td[@field='citation']//span[@class='textbox-addon textbox-addon-right']")).click();
                Util.sleep(100);
                webDriver.findElement(By.xpath("//div[@class='panel combo-p' and contains(@style,'z-index: 1100')]//div[text()='"+thesis.get("收录情况")+"']")).click();
            }
            if (!"".equals(thesis.get("题目")) && thesis.get("题目") != null) {
                //评定文号+聘任文号
                webDriver.findElement(By.xpath("//td[@field='title']//input")).sendKeys(thesis.get("题目"));
            }
            if (!"".equals(thesis.get("期刊名称")) && thesis.get("期刊名称") != null) {
                webDriver.findElement(By.xpath("//td[@field='name']//input")).sendKeys(thesis.get("期刊名称"));
            }
            if (!"".equals(thesis.get("发表一年")) && thesis.get("发表一年") != null) {
                webDriver.findElement(By.xpath("//td[@field='year']//input")).sendKeys(thesis.get("发表一年"));
            }
            if (!"".equals(thesis.get("发表一期")) && thesis.get("发表一期") != null) {
                webDriver.findElement(By.xpath("//td[@field='issue']//input")).sendKeys(thesis.get("发表一期"));
            }
            if (!"".equals(thesis.get("发表一卷")) && thesis.get("发表一卷") != null) {
            webDriver.findElement(By.xpath("//td[@field='volume']//input")).sendKeys(thesis.get("发表一卷"));
            }
            if (!"".equals(thesis.get("起始页码")) && thesis.get("起始页码") != null) {
                webDriver.findElement(By.xpath("//td[@field='startingPageNo']//input")).sendKeys(thesis.get("起始页码"));
            }
            if (!"".equals(thesis.get("结束页码")) && thesis.get("结束页码") != null) {
                webDriver.findElement(By.xpath("//td[@field='endingPageNo']//input")).sendKeys(thesis.get("结束页码"));
            }
            if (!"".equals(thesis.get("本人位次")) && thesis.get("本人位次") != null) {
                webDriver.findElement(By.xpath("//td[@field='seating']//input[@class='textbox-text validatebox-text validatebox-invalid textbox-prompt']")).sendKeys(thesis.get("本人位次"));
                //webDriver.findElement(By.xpath("//td[@field='seating']//span[@class='textbox-addon textbox-addon-right']//a[@class='textbox-icon spinner-arrow']//a[@class='spinner-arrow-up']")).click();
            }
            if (!"".equals(thesis.get("参加人数")) && thesis.get("参加人数") != null) {
                webDriver.findElement(By.xpath("//td[@field='numOfParticipants']//input[@class='textbox-text validatebox-text validatebox-invalid textbox-prompt']")).sendKeys(thesis.get("参加人数"));
                //webDriver.findElement(By.xpath("//td[@field='numOfParticipants']//span[@class='textbox-addon textbox-addon-right']//a[@class='textbox-icon spinner-arrow']//a[@class='spinner-arrow-up']")).click();
            }

            webDriver.findElement(By.xpath("//a[@id='Thesis_save_btn']")).click();
            String qiWang =thesis.get("期望");
            String testResult = webDriver.findElement(By.xpath("//div[@class='panel window messager-window']//div[@class='messager-body panel-body panel-body-noborder window-body']")).getText();
            Assert.assertEquals(testResult, qiWang);
            ResultOfTest[0][i] = qiWang;
            ResultOfTest[1][i] = testResult;

            webDriver.findElement(By.xpath("//*[text()='确定']")).click();
            if (qiWang.equals("请检查输入信息，查看红色字段")) {
                webDriver.findElement(By.xpath("//a[@id='Thesis_cancel_btn']")).click();
            }
        }
//        webDriver.close();
        //excel表格中输入结果数据
        new DataOutToExcel().dataOut(url, ResultOfTest);
    }


    @Test(invocationCount = 1)
    public void testDeleteAcademicDegree(){

        webDriver.findElement(By.xpath("//span[text()='我的科研']")).click();

        webDriver.findElement(By.xpath("//div[@id='myky']//div[@class='tabs-wrap']//ul[@class='tabs']//li[1]//a//span[2]")).click();

        webDriver.findElement(By.xpath("html/body/div[4]/div/div/div[2]/div[2]/div/div/div/div/div[2]/div[1]/div/div/div/div[2]/div[1]/div[2]/div/table/tbody/tr[1]/td/div")).click();

        webDriver.findElement(By.xpath("//a[@id='Thesis_delete_btn']")).click();

        webDriver.findElement(By.xpath("//*[text()='确定']")).click();

        Util.sleep(200);

        webDriver.findElement(By.xpath("//*[text()='确定']")).click();
    }

    @Test(invocationCount = 1)
    public void testEditAcademicDegree(){
        webDriver.findElement(By.xpath("//span[text()='我的科研']")).click();

        webDriver.findElement(By.xpath("//div[@id='myky']//div[@class='tabs-wrap']//ul[@class='tabs']//li[1]//a//span[2]")).click();

        webDriver.findElement(By.xpath("html/body/div[4]/div/div/div[2]/div[2]/div/div/div/div/div[2]/div[1]/div/div/div/div[2]/div[1]/div[2]/div/table/tbody/tr[1]/td/div")).click();

        webDriver.findElement(By.xpath("//a[@id='Thesis_update_btn']")).click();



        webDriver.findElement(By.xpath("//td[@field='journalRank']//span[@class='textbox-addon textbox-addon-right']")).click();
        webDriver.findElement(By.xpath("//div[@class='panel combo-p' and contains(@style,'z-index: 1100')]//div[text()='中文核心']")).click();
        webDriver.findElement(By.xpath("//td[@field='citation']//span[@class='textbox-addon textbox-addon-right']")).click();
        webDriver.findElement(By.xpath("//div[@class='panel combo-p' and contains(@style,'z-index: 1100')]//div[text()='CSSCI']")).click();


        webDriver.findElement(By.xpath("//td[@field='title']//input")).sendKeys("网络安全1");
        webDriver.findElement(By.xpath("//td[@field='name']//input")).sendKeys("网络安全1");
        webDriver.findElement(By.xpath("//td[@field='year']//input")).clear();
        webDriver.findElement(By.xpath("//td[@field='year']//input")).sendKeys("2016");
        webDriver.findElement(By.xpath("//td[@field='issue']//input")).sendKeys("26");
        webDriver.findElement(By.xpath("//td[@field='volume']//input")).sendKeys("264");
        webDriver.findElement(By.xpath("//td[@field='startingPageNo']//input")).sendKeys("2");
        webDriver.findElement(By.xpath("//td[@field='endingPageNo']//input")).sendKeys("5");

        webDriver.findElement(By.xpath("//td[@field='seating']//span[@class='textbox-addon textbox-addon-right']//a[@class='textbox-icon spinner-arrow']//a[@class='spinner-arrow-up']")).click();
        webDriver.findElement(By.xpath("//td[@field='numOfParticipants']//span[@class='textbox-addon textbox-addon-right']//a[@class='textbox-icon spinner-arrow']//a[@class='spinner-arrow-up']")).click();

        webDriver.findElement(By.xpath("//a[@id='Thesis_save_btn']")).click();

    }



}
