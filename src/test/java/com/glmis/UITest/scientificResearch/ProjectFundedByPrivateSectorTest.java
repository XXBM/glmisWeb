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
 * Created by inkskyu428 on 17-5-23.
 */
public class ProjectFundedByPrivateSectorTest {
    private WebDriver webDriver;

    @BeforeClass
    public void setUp(){
        webDriver = Util.setUpChrome();
    }

    @Test(invocationCount = 1)
    public void testAddProjectFundedByPrivateSector()throws IOException {
        //获取Excel的测试数据，并保存到map集合中
        ImportExcel importExcel = new ImportExcel();
        String url = System.getProperty("user.dir") + "/src/test/resources/static/testData/scientificResearch/ProjectFundedByPrivateSectorTest.xls";
        //String url =  FileSystemView.getFileSystemView().getHomeDirectory().getPath() + "/Desktop/ProjectFundedByPrivateSectorTest.xls";
        Map<Integer, Map<String, String>> projectFundedByPrivateSectors = importExcel.importExcel(url);
        webDriver.findElement(By.xpath("//span[text()='我的科研']")).click();

        webDriver.findElement(By.xpath("//div[@id='myky']//div[@class='tabs-wrap']//ul[@class='tabs']//li[5]//a//span[2]")).click();
        //储存结果信息
        String[][] projectFundedByPrivateSectorTestResult = new String[2][projectFundedByPrivateSectors.size()];
//        String resultUrl =  FileSystemView.getFileSystemView().getHomeDirectory().getPath() + "/Desktop/projectFundedByPrivateSector_testResult.txt";
//        File file =new File(resultUrl);
//        FileOutputStream testFile =new FileOutputStream(file);
//        PrintStream printStream =new PrintStream(testFile);
        //外层for循环代表excel中的测试数据数目，数目即excel中除标题栏还有几行数据
        for (int i = 0; i < projectFundedByPrivateSectors.size(); i++) {
            //从employees集合后中获取第n个测试数据，即第n个要添加的员工信息, i+1是因为向employees集合中添加第一条员工数据的key为1
            Map<String, String> projectFundedByPrivateSector = projectFundedByPrivateSectors.get(i + 1);

            webDriver.findElement(By.xpath("//a[@id='ProjectFundedByPrivateSector_add_btn']")).click();
            //级别下拉框
            if (projectFundedByPrivateSector.get("级别")!=null&&projectFundedByPrivateSector.get("级别")!="") {
                webDriver.findElement(By.xpath("//td[@field='projectFundedByPrivateSectorRank']//span[@class='textbox-addon textbox-addon-right']//a")).click();
                Util.sleep(100);
                webDriver.findElement(By.xpath("//div[@class='panel combo-p' and contains(@style,'z-index: 1100')]//div[text()='" + projectFundedByPrivateSector.get("级别") + "']")).click();
            }
            if (!"".equals(projectFundedByPrivateSector.get("名称")) && projectFundedByPrivateSector.get("名称") != null) {
                webDriver.findElement(By.xpath("//td[@field='name']//input")).sendKeys(projectFundedByPrivateSector.get("名称"));
            }
            if (!"".equals(projectFundedByPrivateSector.get("编号")) && projectFundedByPrivateSector.get("编号") != null) {
                webDriver.findElement(By.xpath("//td[@field='no']//input")).sendKeys(projectFundedByPrivateSector.get("编号"));
            }
            if (!"".equals(projectFundedByPrivateSector.get("来源")) && projectFundedByPrivateSector.get("来源") != null) {
                webDriver.findElement(By.xpath("//td[@field='resource']//input")).sendKeys(projectFundedByPrivateSector.get("来源"));
            }
            if (!"".equals(projectFundedByPrivateSector.get("项目负责人")) && projectFundedByPrivateSector.get("项目负责人") != null) {
                webDriver.findElement(By.xpath("//td[@field='leader']//input")).sendKeys(projectFundedByPrivateSector.get("项目负责人"));
            }
            if (!"".equals(projectFundedByPrivateSector.get("批准部门")) && projectFundedByPrivateSector.get("批准部门") != null) {
                webDriver.findElement(By.xpath("//td[@field='sponsor']//input")).sendKeys(projectFundedByPrivateSector.get("批准部门"));
            }
            //输入框版
            //webDriver.findElement(By.xpath("//td[@field='publicationTime']//input[@class='textbox-text validatebox-text validatebox-invalid textbox-prompt']")).sendKeys(textbook.get("出版时间"));
            //时间选择器版
            if (projectFundedByPrivateSector.get("结束时间")!=null&&projectFundedByPrivateSector.get("结束时间")!="") {
                webDriver.findElement(By.xpath("//td[@field='endTime']//span[@class='textbox-addon textbox-addon-right']//a")).click();
                Util.chooseCalendar(projectFundedByPrivateSector.get("结束时间"), webDriver);
            }
            if (!"".equals(projectFundedByPrivateSector.get("经费（万）")) && projectFundedByPrivateSector.get("经费（万）") != null) {
                webDriver.findElement(By.xpath("//td[@field='expenditure']//input")).sendKeys(projectFundedByPrivateSector.get("经费（万）"));
            }
            if (!"".equals(projectFundedByPrivateSector.get("本人位次")) && projectFundedByPrivateSector.get("本人位次") != null) {
                webDriver.findElement(By.xpath("//td[@field='seating']//input[@class='textbox-text validatebox-text validatebox-invalid textbox-prompt']")).sendKeys(projectFundedByPrivateSector.get("本人位次"));
            }
            if (!"".equals(projectFundedByPrivateSector.get("参加人数")) && projectFundedByPrivateSector.get("参加人数") != null) {
                webDriver.findElement(By.xpath("//td[@field='numOfParticipants']//input[@class='textbox-text validatebox-text validatebox-invalid textbox-prompt']")).sendKeys(projectFundedByPrivateSector.get("参加人数"));
            }
            if (projectFundedByPrivateSector.get("开始时间")!=null&&projectFundedByPrivateSector.get("开始时间")!="") {
                webDriver.findElement(By.xpath("//td[@field='startTime']//span[@class='textbox-addon textbox-addon-right']//a")).click();
                Util.chooseCalendar(projectFundedByPrivateSector.get("开始时间"), webDriver);
            }
            webDriver.findElement(By.xpath("//a[@id='ProjectFundedByPrivateSector_save_btn']")).click();
            Util.sleep(1000);
            String message = webDriver.findElement(By.xpath("//div[@class='panel window messager-window']//div[@class='messager-body panel-body panel-body-noborder window-body']//div[2]")).getText();
//            if (projectFundedByPrivateSector.get("期望").equals(input))
//                printStream.println("第"+(i+1)+"条"+"测试成功");
//            else
//                printStream.println("第"+(i+1)+"条"+"测试失败");
            Assert.assertEquals(message, projectFundedByPrivateSector.get("期望"));
            projectFundedByPrivateSectorTestResult[0][i] = projectFundedByPrivateSector.get("期望");
            projectFundedByPrivateSectorTestResult[1][i] = message;
            webDriver.findElement(By.xpath("//*[text()='确定']")).click();
            if (message.equals("请检查输入信息，查看红色字段")) {
                webDriver.findElement(By.xpath("//a[@id='ProjectFundedByPrivateSector_redo_btn']")).click();
            }
//          webDriver.close();
        }
        //excel表格中输入结果数据
        new DataOutToExcel().dataOut(url, projectFundedByPrivateSectorTestResult);
    }


    @Test(invocationCount = 1)
    public void testDeleteProjectFundedByPrivateSector(){

        webDriver.findElement(By.xpath("//span[text()='我的科研']")).click();

        webDriver.findElement(By.xpath("//div[@id='myky']//div[@class='tabs-wrap']//ul[@class='tabs']//li[5]//a//span[2]")).click();

        webDriver.findElement(By.xpath("//div[@id='myky']//div[@id='projectFundedByPrivateSector_div']//div[@class='datagrid-view2']//div[@class='datagrid-body']//tr[@datagrid-row-index='0']")).click();

        webDriver.findElement(By.xpath("//a[@id='ProjectFundedByPrivateSector_remove_btn']")).click();

        webDriver.findElement(By.xpath("//*[text()='确定']")).click();

        Util.sleep(200);

        webDriver.findElement(By.xpath("//*[text()='确定']")).click();
    }

    @Test(invocationCount = 1)
    public void testEditProjectFundedByPrivateSector(){
        webDriver.findElement(By.xpath("//span[text()='我的科研']")).click();

        webDriver.findElement(By.xpath("//div[@id='myky']//div[@class='tabs-wrap']//ul[@class='tabs']//li[5]//a//span[2]")).click();

        webDriver.findElement(By.xpath("//div[@id='myky']//div[@id='projectFundedByPrivateSector_div']//div[@class='datagrid-view2']//div[@class='datagrid-body']//tr[@datagrid-row-index='0']")).click();

        webDriver.findElement(By.xpath("//a[@id='ProjectFundedByPrivateSector_edit_btn']")).click();

        //级别下拉框
        webDriver.findElement(By.xpath("//td[@field='projectFundedByPrivateSectorRank']//span[@class='textbox-addon textbox-addon-right']")).click();
        Util.sleep(100);
        webDriver.findElement(By.xpath("//div[@class='panel combo-p' and contains(@style,'z-index: 1100')]//div[text()='校级']")).click();

        webDriver.findElement(By.xpath("//td[@field='name']//input")).sendKeys("名称1");
        webDriver.findElement(By.xpath("//td[@field='no']//input")).sendKeys("428");

        webDriver.findElement(By.xpath("//td[@field='resource']//input")).sendKeys("网络");
        webDriver.findElement(By.xpath("//td[@field='leader']//input")).sendKeys("李四");
        webDriver.findElement(By.xpath("//td[@field='sponsor']//input")).sendKeys("宣传部");
        webDriver.findElement(By.xpath("//td[@field='endTime']//span[@class='textbox-addon textbox-addon-right']")).click();
        Util.sleep(200);
        webDriver.findElement(By.xpath("//div[@class='panel combo-p' and contains(@style,'z-index: 1100')]//td[@abbr='2017,2,18']")).click();

        webDriver.findElement(By.xpath("//td[@field='startTime']//span[@class='textbox-addon textbox-addon-right']")).click();
        Util.sleep(200);
        webDriver.findElement(By.xpath("//div[@class='panel combo-p' and contains(@style,'z-index: 1100')]//td[@abbr='2015,2,16']")).click();


        webDriver.findElement(By.xpath("//td[@field='expenditure']//input")).sendKeys("200");

        webDriver.findElement(By.xpath("//td[@field='seating']//span[@class='textbox-addon textbox-addon-right']//a[@class='textbox-icon spinner-arrow']//a[@class='spinner-arrow-up']")).click();
        webDriver.findElement(By.xpath("//td[@field='numOfParticipants']//span[@class='textbox-addon textbox-addon-right']//a[@class='textbox-icon spinner-arrow']//a[@class='spinner-arrow-up']")).click();

        webDriver.findElement(By.xpath("//a[@id='ProjectFundedByPrivateSector_save_btn']")).click();

    }
}
