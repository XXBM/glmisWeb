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
 * Created by kene on 17-5-14.
 */
public class SchoolworkExperienceTest {
    private WebDriver webDriver;

    @BeforeClass
    public void setUp(){
        webDriver = Util.setUpChrome();
    }

    //测试的方法，invocationCount表示此方法运行的次数,默认为1
    @Test(invocationCount = 1)
    public void TestAddSchoolworkExperience() throws IOException {
        //获取Excel的测试数据，并保存到map集合中
        ImportExcel importExcel = new ImportExcel();
        String url = System.getProperty("user.dir") + "/src/test/resources/static/testData/personnel/SchoolworkExperienceTest.xls";
        //String url =  FileSystemView.getFileSystemView().getHomeDirectory().getPath() + "/Desktop/SchoolworkExperienceTest.xls";
        Map<Integer, Map<String, String>> schoolworkExperiences = importExcel.importExcel(url);
        webDriver.findElement(By.xpath("//span[text()='人事信息']")).click();
        Util.sleep(10000);
        webDriver.findElement(By.xpath("//div[@id='staffs']//div[@class='datagrid-body']//tr[@datagrid-row-index='0']")).click();
        Util.sleep(10000);
        webDriver.findElement(By.xpath("//span[text()='校内工作经历']")).click();
        Util.sleep(5000);
        /*String url2 =  FileSystemView.getFileSystemView().getHomeDirectory().getPath() + "/Desktop/SchoolworkExperience_TestResult.txt";
        File file = new File(url2);
        FileOutputStream testFile = new FileOutputStream(file);
        PrintStream p = new PrintStream(testFile);*/
        //储存结果信息
        String[][] schoolworkExperienceTestResult = new String[2][schoolworkExperiences.size()];
        //外层for循环代表excel中的测试数据数目，数目即excel中除标题栏还有几行数据
        for (int i = 0; i <  schoolworkExperiences.size(); i++) {
            //从集合后中获取第n个测试数据，即第n个要添加的信息, i+1是因为向集合中添加第一条员工数据的key为1
            Map<String, String> schoolworkExperience = schoolworkExperiences.get(i + 1);
            webDriver.findElement(By.xpath("//a[@id='SchoolWorkExperience_add_btn']")).click();
            //结束时间
            if (schoolworkExperience.get("结束时间")!=null&&schoolworkExperience.get("结束时间")!="") {
                webDriver.findElement(By.xpath("//td[@field='endTime']//span[@class='textbox-addon textbox-addon-right']")).click();
                Util.chooseCalendar(schoolworkExperience.get("结束时间"), webDriver);
            }
            //开始时间
            if (schoolworkExperience.get("开始时间")!=null&&schoolworkExperience.get("开始时间")!="") {
                webDriver.findElement(By.xpath("//td[@field='startTime']//span[@class='textbox-addon textbox-addon-right']")).click();
                Util.chooseCalendar(schoolworkExperience.get("开始时间"), webDriver);
            }
            //学院
            if (schoolworkExperience.get("学院")!=null&&schoolworkExperience.get("学院")!="") {
                webDriver.findElement(By.xpath("//td[@field='school']//span[@class='textbox-addon textbox-addon-right']")).click();
                Util.sleep(100);
                webDriver.findElement(By.xpath("//div[@class='panel combo-p' and contains(@style,'z-index: 1100')]//div[text()='" + schoolworkExperience.get("学院") + "']")).click();
            }
            //担任职务
            if (schoolworkExperience.get("担任职务")!=null&&schoolworkExperience.get("担任职务")!="") {
                webDriver.findElement(By.xpath("//td[@field='post']//span[@class='textbox-addon textbox-addon-right']")).click();
                Util.sleep(100);
                webDriver.findElement(By.xpath("//div[@class='panel combo-p' and contains(@style,'z-index: 1100')]//div[text()='" + schoolworkExperience.get("担任职务") + "']")).click();
            }
            //级别
            if (schoolworkExperience.get("级别")!=null&&schoolworkExperience.get("级别")!="") {
                webDriver.findElement(By.xpath("//td[@field='positionRank']//span[@class='textbox-addon textbox-addon-right']")).click();
                Util.sleep(100);
                webDriver.findElement(By.xpath("//div[@class='panel combo-p' and contains(@style,'z-index: 1100')]//div[text()='" + schoolworkExperience.get("级别") + "']")).click();
            }
            webDriver.findElement(By.xpath("//a[@id='SchoolWorkExperience_save_btn']")).click();
            String qiWang =schoolworkExperience.get("期望");
            String testResult = webDriver.findElement(By.xpath("//div[@class='panel window messager-window']//div[@class='messager-body panel-body panel-body-noborder window-body']")).getText();
            Assert.assertEquals(testResult, qiWang);
            schoolworkExperienceTestResult[0][i] = qiWang;
            schoolworkExperienceTestResult[1][i] = testResult;
            /*if (testResult.equals(qiWang)){
                System.out.println("测试成功");
                p.println("第"+(i+1)+"条测试成功");

            }*/
            webDriver.findElement(By.xpath("//*[text()='确定']")).click();
            if (qiWang.equals("请检查输入信息，查看红色字段")) {
                webDriver.findElement(By.xpath("//a[@id='SchoolWorkExperience_cancel_btn']")).click();
            }
        }
        //excel表格中输入结果数据
        new DataOutToExcel().dataOut(url, schoolworkExperienceTestResult);
        //p.close();
    }
    @Test(invocationCount = 1)
    public void testDeleteSchoolworkExperience(){

        webDriver.findElement(By.xpath("//span[text()='人事信息']")).click();

        webDriver.findElement(By.xpath("//div[@id='staffs']//div[@class='datagrid-body']//tr[@datagrid-row-index='0']")).click();

        webDriver.findElement(By.xpath("//span[text()='校内工作经历']")).click();

        webDriver.findElement(By.xpath("//div[@id='information']//div[@class='datagrid-body']//tr[@id='datagrid-row-r19-2-0']")).click();

        webDriver.findElement(By.xpath("//a[@id='SchoolWorkExperience_delete_btn']")).click();

        webDriver.findElement(By.xpath("//*[text()='确定']")).click();

        Util.sleep(100);

        webDriver.findElement(By.xpath("//*[text()='确定']")).click();
    }
    @Test(invocationCount = 1)
    public void TestEditSchoolworkExperience() throws IOException {
        webDriver.findElement(By.xpath("//span[text()='人事信息']")).click();
        webDriver.findElement(By.xpath("//div[@id='staffs']//div[@class='datagrid-body']//tr[@datagrid-row-index='0']")).click();

        webDriver.findElement(By.xpath("//span[text()='校内工作经历']")).click();
        webDriver.findElement(By.xpath("//div[@id='information']//div[@class='datagrid-body']//tr[@id='datagrid-row-r19-2-0']")).click();

        webDriver.findElement(By.xpath("//a[@id='SchoolWorkExperience_update_btn']")).click();
        //结束时间
        webDriver.findElement(By.xpath("//td[@field='endTime']//span[@class='textbox-addon textbox-addon-right']")).click();
        webDriver.findElement(By.xpath("//div[@class='panel combo-p' and contains(@style,'z-index: 1100')]//td[@abbr='2017,5,10']")).click();
        //开始时间
        webDriver.findElement(By.xpath("//td[@field='startTime']//span[@class='textbox-addon textbox-addon-right']")).click();
        webDriver.findElement(By.xpath("//div[@class='panel combo-p' and contains(@style,'z-index: 1100')]//td[@abbr='2017,5,6']")).click();
        //学院
        webDriver.findElement(By.xpath("//td[@field='school']//span[@class='textbox-addon textbox-addon-right']")).click();
        Util.sleep(100);
        webDriver.findElement(By.xpath("//div[@class='panel combo-p' and contains(@style,'z-index: 1100')]//div[text()='管理工程学院']")).click();
        //担任职务
        webDriver.findElement(By.xpath("//td[@field='post']//span[@class='textbox-addon textbox-addon-right']")).click();
        Util.sleep(100);
        webDriver.findElement(By.xpath("//div[@class='panel combo-p' and contains(@style,'z-index: 1100')]//div[text()='副院长']")).click();
        //级别
        webDriver.findElement(By.xpath("//td[@field='positionRank']//span[@class='textbox-addon textbox-addon-right']")).click();
        Util.sleep(100);
        webDriver.findElement(By.xpath("//div[@class='panel combo-p' and contains(@style,'z-index: 1100')]//div[text()='副处']")).click();

        webDriver.findElement(By.xpath("//a[@id='SchoolWorkExperience_save_btn']")).click();

        webDriver.findElement(By.xpath("//*[text()='确定']")).click();

    }

}
