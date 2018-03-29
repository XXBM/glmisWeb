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
 * Created by kene on 17-5-13.
 */
public class AcademicConferenceTest {

    private WebDriver webDriver;

    @BeforeClass
    public void setUp(){
        webDriver = Util.setUpChrome();
    }


    @Test(invocationCount = 1)
    public void testAddAcademicConference() throws IOException {
        //获取Excel的测试数据，并保存到map集合中
        ImportExcel importExcel = new ImportExcel();
        String url = System.getProperty("user.dir") + "/src/test/resources/static/testData/personnel/AcademicConferenceTest.xls";
        //String url =  "F:\\glmis\\testingData\\AcademicConferenceTest.xls";
        Map<Integer, Map<String, String>> academicConferenceList = importExcel.importExcel(url);

        /*String url2 =  FileSystemView.getFileSystemView().getHomeDirectory().getPath() + "/Desktop/AcademicConference_TestResult.txt";
        File file = new File(url2);
        FileOutputStream testFile = new FileOutputStream(file);
        PrintStream p = new PrintStream(testFile);*/
        //储存结果信息
        String[][] academicConferenceTestResult = new String[2][academicConferenceList.size()];
        webDriver.findElement(By.xpath("//span[text()='人事信息']")).click();
        Util.sleep(10000);
        webDriver.findElement(By.xpath("//div[@id='staffs']//div[@class='datagrid-body']//tr[@datagrid-row-index='0']")).click();
        Util.sleep(10000);
        webDriver.findElement(By.xpath("//span[text()='学术会议']")).click();
        Util.sleep(5000);
        for (int i = 0; i < academicConferenceList.size(); i++) {
            Map<String, String> academicConference = academicConferenceList.get(i + 1);

            webDriver.findElement(By.xpath("//a[@id='AcademicConference_add_btn']")).click();
            if (academicConference.get("汇报时间")!=null&&academicConference.get("汇报时间")!="") {
                webDriver.findElement(By.xpath("//td[@field='reportTime']//span[@class='textbox-addon textbox-addon-right']//a")).click();
                Util.chooseCalendar(academicConference.get("汇报时间"), webDriver);
            }
            if (academicConference.get("开始时间")!=null&&academicConference.get("开始时间")!="") {
                webDriver.findElement(By.xpath("//table[@class='datagrid-btable']//td[@field='startTime']//span[@class='textbox-addon textbox-addon-right']//a")).click();
                Util.chooseCalendar(academicConference.get("开始时间"), webDriver);
            }
            if (academicConference.get("结束时间")!=null&&academicConference.get("结束时间")!="") {
                webDriver.findElement(By.xpath("//table[@class='datagrid-btable']//td[@field='endTime']//span[@class='textbox-addon textbox-addon-right']//a")).click();
                Util.chooseCalendar(academicConference.get("结束时间"), webDriver);
            }
            //webDriver.findElement(By.xpath("//td[@field='startTime']//input[@class='textbox-text validatebox-text validatebox-readonly validatebox-invalid textbox-prompt']")).sendKeys(academicConference.get("开始时间"));
            //webDriver.findElement(By.xpath("//td[@field='startTime']//a")).click();
            //webDriver.findElement(By.xpath("//td[@field='endTime']//input[@class='textbox-text validatebox-text validatebox-readonly validatebox-invalid textbox-prompt']")).sendKeys(academicConference.get("结束时间"));
            //webDriver.findElement(By.xpath("//td[@field='endTime']//a")).click();

            //会议名称
            if(academicConference.get("会议名称")!=null&&academicConference.get("会议名称")!=""){
                webDriver.findElement(By.xpath("//td[@field='name']//input")).sendKeys(academicConference.get("会议名称"));

            }
            //主办机构审核结果下拉框
            if (academicConference.get("审核结果")!=null&&academicConference.get("审核结果")!="") {
                webDriver.findElement(By.xpath("//td[@field='reviewResult']//span[@class='textbox-addon textbox-addon-right']")).click();
                Util.sleep(100);
                webDriver.findElement(By.xpath("//div[@class='panel combo-p' and contains(@style,'z-index: 1100')]//div[text()='" + academicConference.get("审核结果") + "']")).click();
            }
            if (academicConference.get("主办机构")!=null&&academicConference.get("主办机构")!="") {
                webDriver.findElement(By.xpath("//td[@field='host']//span[@class='textbox-addon textbox-addon-right']")).click();
                Util.sleep(100);
                webDriver.findElement(By.xpath("//div[@class='panel combo-p' and contains(@style,'z-index: 1100')]//div[text()='" + academicConference.get("主办机构") + "']")).click();
            }
            webDriver.findElement(By.xpath("//a[@id='AcademicConference_save_btn']")).click();
            Util.sleep(2000);

            String qiWang =academicConference.get("期望");
            String testResult = webDriver.findElement(By.xpath("//div[@class='panel window messager-window']//div[@class='messager-body panel-body panel-body-noborder window-body']")).getText();
            Assert.assertEquals(testResult, qiWang);
            academicConferenceTestResult[0][i] = qiWang;
            academicConferenceTestResult[1][i] = testResult;
            /*if (testResult.equals(qiWang)){
                System.out.println("测试成功");
                p.println("第"+(i+1)+"条测试成功");

            }*/
            webDriver.findElement(By.xpath("//*[text()='确定']")).click();
            if (qiWang.equals("请检查输入信息，查看红色字段")) {
                webDriver.findElement(By.xpath("//a[@id='AcademicConference_cancel_btn']")).click();
            }
        }
        //excel表格中输入结果数据
        new DataOutToExcel().dataOut(url, academicConferenceTestResult);
        //p.close();
    }


    @Test(invocationCount = 1)
    public void testDeleteAcademicDegree(){

        webDriver.findElement(By.xpath("//span[text()='人事信息']")).click();

        webDriver.findElement(By.xpath("//div[@id='staffs']//div[@class='datagrid-body']//tr[@datagrid-row-index='0']")).click();

        webDriver.findElement(By.xpath("//span[text()='学术会议']")).click();

        webDriver.findElement(By.xpath("html/body/div[4]/div/div/div[2]/div[2]/div/div/div[4]/div/div[2]/div[9]/div/div/div/div[2]/div[1]/div[2]/div/table/tbody/tr/td/div/input")).click();

        webDriver.findElement(By.xpath("//a[@id='AcademicConference_delete_btn']")).click();

        webDriver.findElement(By.xpath("//*[text()='确定']")).click();

        Util.sleep(100);

        webDriver.findElement(By.xpath("//*[text()='确定']")).click();
    }

    @Test(invocationCount = 1)
    public void testEditConference(){
        webDriver.findElement(By.xpath("//span[text()='人事信息']")).click();

        webDriver.findElement(By.xpath("//div[@id='staffs']//div[@class='datagrid-body']//tr[@datagrid-row-index='0']")).click();

        webDriver.findElement(By.xpath("//span[text()='学术会议']")).click();

        webDriver.findElement(By.xpath("html/body/div[4]/div/div/div[2]/div[2]/div/div/div[4]/div/div[2]/div[9]/div/div/div/div[2]/div[1]/div[2]/div/table/tbody/tr/td/div/input")).click();

        webDriver.findElement(By.xpath("//a[@id='AcademicConference_update_btn']")).click();


        webDriver.findElement(By.xpath("//td[@field='reportTime']//span[@class='textbox-addon textbox-addon-right']")).click();
        Util.sleep(200);
        webDriver.findElement(By.xpath("//div[@class='panel combo-p' and contains(@style,'z-index: 1100')]//td[@abbr='2017,5,16']")).click();

        webDriver.findElement(By.xpath("html/body/div[4]/div/div/div[2]/div[2]/div/div/div[4]/div/div[2]/div[9]/div/div/div/div[2]/div[2]/div[2]/table/tbody/tr[1]/td[4]/div/table/tbody/tr/td/input")).sendKeys("3");
        webDriver.findElement(By.xpath("//td[@field='endTime']//span[@class='textbox-addon textbox-addon-right']")).click();
        Util.sleep(200);
        webDriver.findElement(By.xpath("//div[@class='panel combo-p' and contains(@style,'z-index: 1100')]//td[@abbr='2017,5,16']")).click();

        webDriver.findElement(By.xpath("//td[@field='startTime']//span[@class='textbox-addon textbox-addon-right']")).click();
        Util.sleep(200);
        webDriver.findElement(By.xpath("//div[@class='panel combo-p' and contains(@style,'z-index: 1100')]//td[@abbr='2017,5,16']")).click();

        webDriver.findElement(By.xpath("//td[@field='host']//span[@class='textbox-addon textbox-addon-right']")).click();
        Util.sleep(100);
        webDriver.findElement(By.xpath("//div[@class='panel combo-p' and contains(@style,'z-index: 1100')]//div[text()='主办机构1']")).click();

        webDriver.findElement(By.xpath("//td[@field='reviewResult']//span[@class='textbox-addon textbox-addon-right']")).click();
        Util.sleep(100);
        webDriver.findElement(By.xpath("//div[@class='panel combo-p' and contains(@style,'z-index: 1100')]//div[text()='不通过']")).click();


        webDriver.findElement(By.xpath("//a[@id='AcademicConference_save_btn']")).click();

    }



}
