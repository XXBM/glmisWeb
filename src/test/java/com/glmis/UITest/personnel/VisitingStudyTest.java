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
public class VisitingStudyTest {
    private WebDriver webDriver;

    //在被@Test注解标注的方法之前运行
    @BeforeClass
    public void setUp(){
        webDriver = Util.setUpChrome();
    }


    //测试的方法，invocationCount表示此方法运行的次数,默认为1
    @Test(invocationCount = 1)
    public void TestAddVisitingStudy() throws IOException {
        //获取Excel的测试数据，并保存到map集合中
        ImportExcel importExcel = new ImportExcel();
        String url = System.getProperty("user.dir") + "/src/test/resources/static/testData/personnel/VisitingStudyTest.xls";
        Map<Integer, Map<String, String>> visitingStudies = importExcel.importExcel(url);

        webDriver.findElement(By.xpath("//span[text()='人事信息']")).click();
        Util.sleep(10000);
        webDriver.findElement(By.xpath("//div[@id='staffs']//div[@class='datagrid-body']//tr[@datagrid-row-index='0']")).click();
        Util.sleep(10000);
        webDriver.findElement(By.xpath("//span[text()='国内外访学记录']")).click();
        Util.sleep(5000);
        /*String url2 =  FileSystemView.getFileSystemView().getHomeDirectory().getPath() + "/Desktop/VisitingStudy_TestResult.txt";
        File file = new File(url2);
        FileOutputStream testFile = new FileOutputStream(file);
        PrintStream p = new PrintStream(testFile);*/
        //储存结果信息
        String[][] visitingStudyTestResult = new String[2][visitingStudies.size()];
        //外层for循环代表excel中的测试数据数目，数目即excel中除标题栏还有几行数据
        for (int i = 0; i < visitingStudies.size(); i++) {
            //从集合后中获取第n个测试数据，即第n个要添加的信息, i+1是因为向集合中添加第一条员工数据的key为1
            Map<String, String> visitingStudy = visitingStudies.get(i + 1);
            webDriver.findElement(By.xpath("//a[@id='VisitingAcademic_add_btn']")).click();
            //是否国内访学
            if (visitingStudy.get("是否国内访学")!=null&&visitingStudy.get("是否国内访学")!="") {
                webDriver.findElement(By.xpath("//td[@field='domesticOrNot']//span[@class='textbox-addon textbox-addon-right']")).click();
                Util.sleep(100);
                webDriver.findElement(By.xpath("//div[@class='panel combo-p' and contains(@style,'z-index: 1100')]//div[text()='" + visitingStudy.get("是否国内访学") + "']")).click();
            }
            //进修名称
            if(visitingStudy.get("进修名称")!=null&&visitingStudy.get("进修名称")!=""){
                webDriver.findElement(By.xpath("//td[@field='program']//input")).sendKeys(visitingStudy.get("进修名称"));

            }
            //汇报时间
            if (visitingStudy.get("汇报时间")!=null&&visitingStudy.get("汇报时间")!="") {
                webDriver.findElement(By.xpath("//td[@field='reportTime']//span[@class='textbox-addon textbox-addon-right']")).click();
                Util.chooseCalendar(visitingStudy.get("汇报时间"), webDriver);
            }
            //结束时间
            if (visitingStudy.get("结束时间")!=null&&visitingStudy.get("结束时间")!="") {
                webDriver.findElement(By.xpath("//td[@field='endTime']//span[@class='textbox-addon textbox-addon-right']")).click();
                Util.chooseCalendar(visitingStudy.get("结束时间"), webDriver);
            }
            //开始时间
            if (visitingStudy.get("开始时间")!=null&&visitingStudy.get("开始时间")!="") {
                webDriver.findElement(By.xpath("//td[@field='startTime']//span[@class='textbox-addon textbox-addon-right']")).click();
                Util.chooseCalendar(visitingStudy.get("开始时间"), webDriver);
            }//资助机构
            if (visitingStudy.get("资助机构")!=null&&visitingStudy.get("资助机构")!="") {
                webDriver.findElement(By.xpath("//td[@field='sponsor']//span[@class='textbox-addon textbox-addon-right']")).click();
                Util.sleep(100);
                webDriver.findElement(By.xpath("//div[@class='panel combo-p' and contains(@style,'z-index: 1100')]//div[text()='" + visitingStudy.get("资助机构") + "']")).click();
            }
            //受访机构
            if(visitingStudy.get("受访机构")!=null&&visitingStudy.get("受访机构")!=""){
                webDriver.findElement(By.xpath("//td[@field='visitedOrganization']//input")).sendKeys(visitingStudy.get("受访机构"));

            }
            //导师姓名
            if(visitingStudy.get("导师姓名")!=null&&visitingStudy.get("导师姓名")!=""){
                webDriver.findElement(By.xpath("//td[@field='supervisorName']//input")).sendKeys(visitingStudy.get("导师姓名"));

            }
            //审核结果
            if (visitingStudy.get("审核结果")!=null&&visitingStudy.get("审核结果")!="") {
                webDriver.findElement(By.xpath("//td[@field='reviewResult']//span[@class='textbox-addon textbox-addon-right']")).click();
                Util.sleep(100);
                webDriver.findElement(By.xpath("//div[@class='panel combo-p' and contains(@style,'z-index: 1100')]//div[text()='" + visitingStudy.get("审核结果") + "']")).click();
            }
            webDriver.findElement(By.xpath("//a[@id='VisitingAcademic_save_btn']")).click();
            String qiWang =visitingStudy.get("期望");
            String testResult = webDriver.findElement(By.xpath("//div[@class='panel window messager-window']//div[@class='messager-body panel-body panel-body-noborder window-body']")).getText();

            Assert.assertEquals(testResult, qiWang);
            visitingStudyTestResult[0][i] = qiWang;
            visitingStudyTestResult[1][i] = testResult;

            /*if (testResult.equals(qiWang)){
                System.out.println("测试成功");
                p.println("第"+(i+1)+"条测试成功");

            }*/
            webDriver.findElement(By.xpath("//*[text()='确定']")).click();
            if (qiWang.equals("请检查输入信息，查看红色字段")) {
                webDriver.findElement(By.xpath("//a[@id='VisitingAcademic_cancel_btn']")).click();
            }
        }
        //excel表格中输入结果数据
        new DataOutToExcel().dataOut(url, visitingStudyTestResult);
        //p.close();

    }
    @Test(invocationCount = 1)
    public void testDeleteVisitingStudy(){

        webDriver.findElement(By.xpath("//span[text()='人事信息']")).click();

        webDriver.findElement(By.xpath("//div[@id='staffs']//div[@class='datagrid-body']//tr[@datagrid-row-index='0']")).click();

        webDriver.findElement(By.xpath("//span[text()='国内外访学记录']")).click();

        webDriver.findElement(By.xpath("//div[@id='information']//div[@class='datagrid-body']//tr[@id='datagrid-row-r23-2-0']")).click();

        webDriver.findElement(By.xpath("//a[@id='VisitingAcademic_delete_btn']")).click();

        webDriver.findElement(By.xpath("//*[text()='确定']")).click();

        Util.sleep(100);

        webDriver.findElement(By.xpath("//*[text()='确定']")).click();
    }
    @Test(invocationCount = 1)
    public void TestEditVisitingStudy() throws IOException {
        webDriver.findElement(By.xpath("//span[text()='人事信息']")).click();
        webDriver.findElement(By.xpath("//div[@id='staffs']//div[@class='datagrid-body']//tr[@datagrid-row-index='0']")).click();

        webDriver.findElement(By.xpath("//span[text()='国内外访学记录']")).click();

        webDriver.findElement(By.xpath("//div[@id='information']//div[@class='datagrid-body']//tr[@id='datagrid-row-r23-2-0']")).click();

        webDriver.findElement(By.xpath("//a[@id='VisitingAcademic_update_btn']")).click();
        //是否国内访学
        webDriver.findElement(By.xpath("//td[@field='domesticOrNot']//span[@class='textbox-addon textbox-addon-right']")).click();
        Util.sleep(100);
        webDriver.findElement(By.xpath("//div[@class='panel combo-p' and contains(@style,'z-index: 1100')]//div[text()='否']")).click();
        //进修名称
        webDriver.findElement(By.xpath("//td[@field='program']//input")).sendKeys("b");
        //汇报时间
        webDriver.findElement(By.xpath("//td[@field='reportTime']//span[@class='textbox-addon textbox-addon-right']")).click();
        webDriver.findElement(By.xpath("//div[@class='panel combo-p' and contains(@style,'z-index: 1100')]//td[@abbr='2017,5,10']")).click();
        //结束时间
        webDriver.findElement(By.xpath("//td[@field='endTime']//span[@class='textbox-addon textbox-addon-right']")).click();
        webDriver.findElement(By.xpath("//div[@class='panel combo-p' and contains(@style,'z-index: 1100')]//td[@abbr='2017,5,8']")).click();
        //开始时间
        webDriver.findElement(By.xpath("//td[@field='startTime']//span[@class='textbox-addon textbox-addon-right']")).click();
        webDriver.findElement(By.xpath("//div[@class='panel combo-p' and contains(@style,'z-index: 1100')]//td[@abbr='2017,5,6']")).click();
        //资助机构
        webDriver.findElement(By.xpath("//td[@field='sponsor']//span[@class='textbox-addon textbox-addon-right']")).click();
        Util.sleep(100);
        webDriver.findElement(By.xpath("//div[@class='panel combo-p' and contains(@style,'z-index: 1100')]//div[text()='资助机构2']")).click();
        //受访机构
        webDriver.findElement(By.xpath("//td[@field='visitedOrganization']//input")).sendKeys("b");
        //导师姓名
        webDriver.findElement(By.xpath("//td[@field='supervisorName']//input")).sendKeys("b");
        //审核结果
        webDriver.findElement(By.xpath("//td[@field='reviewResult']//span[@class='textbox-addon textbox-addon-right']")).click();
        Util.sleep(100);
        webDriver.findElement(By.xpath("//div[@class='panel combo-p' and contains(@style,'z-index: 1100')]//div[text()='不通过']")).click();

        webDriver.findElement(By.xpath("//a[@id='VisitingAcademic_save_btn']")).click();

        webDriver.findElement(By.xpath("//*[text()='确定']")).click();
        

    }

}
