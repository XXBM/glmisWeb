package com.glmis.UITest.personnel;


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
 * Created by liucl on 17-5-17.
 */
public class PreviousWorkExperienceTest {
    private WebDriver webDriver;
    @BeforeClass
    public void setUp(){
        webDriver = Util.setUpChrome();
    }
    @Test
    public void PreviousWorkExperienceAddTest() throws IOException {
        //获取Excel的测试数据，并保存到map集合中
        ImportExcel importExcel = new ImportExcel();
        String url = System.getProperty("user.dir") + "/src/test/resources/static/testData/personnel/PreviousWorkExperienceTest.xls";
        Map<Integer, Map<String, String>> previousWorkExperiences = importExcel.importExcel(url);

        webDriver.findElement(By.xpath("//span[text()='人事信息']")).click();
        Util.sleep(10000);
        webDriver.findElement(By.xpath("//div[@id='empInfoTabs']//div[@class='tabs-scroller-right']")).click();
        Util.sleep(10000);
        webDriver.findElement(By.xpath("//div[@id='empInfoTabs']//span[text()='来校前工作经历']")).click();
        Util.sleep(5000);

        String[][] ResultOfTest = new String[2][previousWorkExperiences.size()];
        for(int i=0;i<previousWorkExperiences.size();i++){
            Map<String, String> previousWorkExperience = previousWorkExperiences.get(i + 1);

            webDriver.findElement(By.xpath("//div[@id='PreviousWorkExperience']//span[text()='添加']")).click();
            //datebox
            if (previousWorkExperience.get("开始时间")!=null&&previousWorkExperience.get("开始时间")!="") {
                webDriver.findElement(By.xpath("//td[@field='startTime']//span[@class='textbox-addon textbox-addon-right']")).click();
                Util.chooseCalendar(previousWorkExperience.get("开始时间"), webDriver);
            }
            if (previousWorkExperience.get("结束时间")!=null&&previousWorkExperience.get("结束时间")!="") {
                webDriver.findElement(By.xpath("//td[@field='endTime']//span[@class='textbox-addon textbox-addon-right']")).click();
                Util.chooseCalendar(previousWorkExperience.get("结束时间"), webDriver);
            }

//            //datebox
//            webDriver.findElement(By.xpath("//td[@field='startTime']//span[@class='textbox-addon textbox-addon-right']")).click();
//            Util.chooseCalendar("2016-06-05", webDriver);
//            webDriver.findElement(By.xpath("//td[@field='endTime']//span[@class='textbox-addon textbox-addon-right']")).click();
//            Util.chooseCalendar("2016-06-07", webDriver);

            //input
            if(previousWorkExperience.get("单位名称")!=null&&previousWorkExperience.get("单位名称")!=""){
                webDriver.findElement(By.xpath("//div[@id='PreviousWorkExperience']//td[@field='hiredByCompany']//td[1]//input")).sendKeys(previousWorkExperience.get("单位名称"));

            }
            if(previousWorkExperience.get("部门名称")!=null&&previousWorkExperience.get("部门名称")!=""){
                webDriver.findElement(By.xpath("//div[@id='PreviousWorkExperience']//td[@field='departmentName']//td[1]//input")).sendKeys(previousWorkExperience.get("部门名称"));

            }
            if(previousWorkExperience.get("级别")!=null&&previousWorkExperience.get("级别")!=""){
                webDriver.findElement(By.xpath("//div[@id='PreviousWorkExperience']//td[@field='rank']//td[1]//input")).sendKeys(previousWorkExperience.get("级别"));

            }
            if(previousWorkExperience.get("担任职务")!=null&&previousWorkExperience.get("担任职务")!=""){
                webDriver.findElement(By.xpath("//div[@id='PreviousWorkExperience']//td[@field='post']//td[1]//input")).sendKeys(previousWorkExperience.get("担任职务"));

            }

            //保存
            webDriver.findElement(By.xpath("//div[@id='PreviousWorkExperience']//a[@id='PreviousExperiences_save_btn']")).click();

            String message = webDriver.findElement(By.xpath("//div[@class='panel window messager-window']//div[@class='messager-body panel-body panel-body-noborder window-body']//div[2]")).getText();
            Assert.assertEquals(message, previousWorkExperience.get("期望"));
            ResultOfTest[0][i] = previousWorkExperience.get("期望");
            ResultOfTest[1][i] = message;
            webDriver.findElement(By.xpath("//*[text()='确定']")).click();
            if (message.equals("请检查输入信息，查看红色字段")) {
                webDriver.findElement(By.xpath("//a[@id='PreviousExperiences_cancel_btn']")).click();
            }

        }
        new DataOutToExcel().dataOut(url, ResultOfTest);
    }
    @Test
    public void PreviousWorkExperienceDeleteTest() throws IOException {
        webDriver.findElement(By.xpath("//span[text()='人事信息']")).click();
        webDriver.findElement(By.xpath("//div[@id='empInfoTabs']//div[@class='tabs-scroller-right']")).click();
        webDriver.findElement(By.xpath("//div[@id='empInfoTabs']//span[text()='来校前工作经历']")).click();
        //管理员
        webDriver.findElement(By.xpath("//div[@id='staffs']//div[@class='datagrid-body']//td[@field='name']")).click();
        //2行
        webDriver.findElement(By.xpath("//div[@id='PreviousWorkExperience']//div[@class='datagrid-body']//tr[2]")).click();
        //删除
        webDriver.findElement(By.xpath("//div[@id='PreviousWorkExperience']//a[@id='PreviousExperiences_delele_btn']")).click();
        webDriver.findElement(By.xpath("//div[@class='panel window messager-window']//div[@class='dialog-button messager-button']//a")).click();
        Util.sleep(100);
        webDriver.findElement(By.xpath("//*[text()='确定']")).click();
    }
    @Test
    public void PreviousWorkExperienceUpdateTest() throws IOException {
        webDriver.findElement(By.xpath("//span[text()='人事信息']")).click();
        webDriver.findElement(By.xpath("//div[@id='empInfoTabs']//div[@class='tabs-scroller-right']")).click();
        webDriver.findElement(By.xpath("//div[@id='empInfoTabs']//span[text()='来校前工作经历']")).click();
        webDriver.findElement(By.xpath("//div[@id='staffs']//div[@class='datagrid-body']//td[@field='name']")).click();
        //修改第二行
        webDriver.findElement(By.xpath("//div[@id='PreviousWorkExperience']//div[@class='datagrid-body']//tr[2]")).click();
        webDriver.findElement(By.xpath("//div[@id='PreviousWorkExperience']//a[@id='PreviousExperiences_update_btn']")).click();

        //datebox
        webDriver.findElement(By.xpath("//td[@field='startTime']//span[@class='textbox-addon textbox-addon-right']")).click();
        Util.chooseCalendar("2016-06-09", webDriver);

        webDriver.findElement(By.xpath("//td[@field='endTime']//span[@class='textbox-addon textbox-addon-right']")).click();
        Util.chooseCalendar("2016-06-11", webDriver);

        //input
        webDriver.findElement(By.xpath("//div[@id='PreviousWorkExperience']//td[@field='hiredByCompany']//td[1]//input")).clear();
        webDriver.findElement(By.xpath("//div[@id='PreviousWorkExperience']//td[@field='hiredByCompany']//td[1]//input")).sendKeys("445666");
        webDriver.findElement(By.xpath("//div[@id='PreviousWorkExperience']//td[@field='departmentName']//td[1]//input")).clear();
        webDriver.findElement(By.xpath("//div[@id='PreviousWorkExperience']//td[@field='departmentName']//td[1]//input")).sendKeys("445666");
        webDriver.findElement(By.xpath("//div[@id='PreviousWorkExperience']//td[@field='rank']//td[1]//input")).clear();
        webDriver.findElement(By.xpath("//div[@id='PreviousWorkExperience']//td[@field='rank']//td[1]//input")).sendKeys("445666");
        webDriver.findElement(By.xpath("//div[@id='PreviousWorkExperience']//td[@field='post']//td[1]//input")).clear();
        webDriver.findElement(By.xpath("//div[@id='PreviousWorkExperience']//td[@field='post']//td[1]//input")).sendKeys("445666");

        //保存
        webDriver.findElement(By.xpath("//div[@id='PreviousWorkExperience']//a[@id='PreviousExperiences_save_btn']")).click();
        webDriver.findElement(By.xpath("//*[text()='确定']")).click();

    }
}


