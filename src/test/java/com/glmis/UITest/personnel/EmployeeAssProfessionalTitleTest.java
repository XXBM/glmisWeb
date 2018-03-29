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
public class EmployeeAssProfessionalTitleTest {

    private WebDriver webDriver;

    @BeforeClass
    public void setUp(){
        webDriver = Util.setUpChrome();
    }


    @Test(invocationCount = 1)
    public void testAddProfessionalTitle() throws IOException {

        //获取Excel的测试数据，并保存到map集合中
        ImportExcel importExcel = new ImportExcel();
        //String url =  "F:\\glmis\\testingData\\EmployeeAssProfessionalTitleTest.xls";
        //String url =  FileSystemView.getFileSystemView().getHomeDirectory().getPath() + "/Desktop/ProfessionalTitleTest.xls";
        String url = System.getProperty("user.dir") + "/src/test/resources/static/testData/personnel/ProfessionalTitleTest.xls";
        Map<Integer, Map<String, String>> professionalTitleList = importExcel.importExcel(url);

        webDriver.findElement(By.xpath("//span[text()='人事信息']")).click();

        Util.sleep(10000);

        webDriver.findElement(By.xpath("//div[@id='staffs']//div[@class='datagrid-body']//tr[@datagrid-row-index='0']")).click();
        Util.sleep(10000);
        webDriver.findElement(By.xpath("//span[text()='职称记录']")).click();
        //储存结果信息
        Util.sleep(5000);
        String[][] professionalTitleTestResult = new String[2][professionalTitleList.size()];
//        String resultUrl =  FileSystemView.getFileSystemView().getHomeDirectory().getPath() + "/Desktop/professionalTitle_testResult.txt";
//        File file =new File(resultUrl);
//        FileOutputStream testFile =new FileOutputStream(file);
//        PrintStream printStream =new PrintStream(testFile);
//        Util.sleep(5000);
        for (int i = 0; i < professionalTitleList.size(); i++) {
            Map<String, String> professionalTitle = professionalTitleList.get(i+1);

            webDriver.findElement(By.xpath("//a[@id='EmployeeAssProfessionalTitle_add_btn']")).click();

            //职称下拉框
            if (professionalTitle.get("职称")!=null&&professionalTitle.get("职称")!="") {
                webDriver.findElement(By.xpath("//td[@field='professionalTitle']//span[@class='textbox-addon textbox-addon-right']")).click();
                Util.sleep(100);
                webDriver.findElement(By.xpath("//div[@class='panel combo-p' and contains(@style,'z-index: 1100')]//div[text()='" + professionalTitle.get("职称") + "']")).click();
            }
            //评定文号+聘任文号
            webDriver.findElement(By.xpath("//td[@field='nominatedNo']//input")).sendKeys(professionalTitle.get("评定文号"));
            webDriver.findElement(By.xpath("//td[@field='appointedNo']//input")).sendKeys(professionalTitle.get("聘任文号"));

            //时间
//            webDriver.findElement(By.xpath("//td[@field='nominatedDate']//input[@class='textbox-text validatebox-text validatebox-invalid textbox-prompt']")).sendKeys(professionalTitle.get("评定时间"));
//            webDriver.findElement(By.xpath("//td[@field='nominatedDate']//a")).click();
//            webDriver.findElement(By.xpath("//td[@field='appointedDate']//input[@class='textbox-text validatebox-text validatebox-invalid textbox-prompt']")).sendKeys(professionalTitle.get("聘任时间"));
//            webDriver.findElement(By.xpath("//td[@field='appointedDate']//a")).click();

            if (professionalTitle.get("评定时间")!=null&&professionalTitle.get("评定时间")!="") {
                webDriver.findElement(By.xpath("//td[@field='nominatedDate']//span[@class='textbox-addon textbox-addon-right']//a")).click();
                Util.chooseCalendar(professionalTitle.get("评定时间"), webDriver);
            }
            if (professionalTitle.get("聘任时间")!=null&&professionalTitle.get("聘任时间")!=""){
                webDriver.findElement(By.xpath("//td[@field='appointedDate']//span[@class='textbox-addon textbox-addon-right']//a")).click();
                Util.chooseCalendar(professionalTitle.get("聘任时间"),webDriver);
            }
            webDriver.findElement(By.xpath("//a[@id='EmployeeAssProfessionalTitle_save_btn']")).click();

            String message = webDriver.findElement(By.xpath("//div[@class='panel window messager-window']//div[@class='messager-body panel-body panel-body-noborder window-body']//div[2]")).getText();
//            if (professionalTitle.get("期望").equals(input))
//                printStream.println("第"+(i+1)+"条"+"测试成功");
//            else
//                printStream.println("第"+(i+1)+"条"+"测试失败");
            Assert.assertEquals(message, professionalTitle.get("期望"));
            professionalTitleTestResult[0][i] = professionalTitle.get("期望");
            professionalTitleTestResult[1][i] = message;
            webDriver.findElement(By.xpath("//*[text()='确定']")).click();
            if (message.equals("请检查输入信息，查看红色字段")) {
                webDriver.findElement(By.xpath("//a[@id='EmployeeAssProfessionalTitle_cancel_btn']")).click();
            }
        }
//        webDriver.close();
        //excel表格中输入结果数据
        new DataOutToExcel().dataOut(url, professionalTitleTestResult);
    }


    @Test(invocationCount = 1)
    public void testDeleteAcademicDegree(){

        webDriver.findElement(By.xpath("//span[text()='人事信息']")).click();

        webDriver.findElement(By.xpath("//div[@id='staffs']//div[@class='datagrid-body']//tr[@datagrid-row-index='0']")).click();

        webDriver.findElement(By.xpath("//span[text()='职称记录']")).click();

        webDriver.findElement(By.xpath("//div[@id='information']//div[@class='datagrid-body']//tr[@datagrid-row-index='1']")).click();

        webDriver.findElement(By.xpath("//a[@id='EmployeeAssProfessionalTitle_delete_btn']")).click();

        webDriver.findElement(By.xpath("//*[text()='确定']")).click();

        Util.sleep(100);

        webDriver.findElement(By.xpath("//*[text()='确定']")).click();
    }

    @Test(invocationCount = 1)
    public void testEditAcademicDegree(){
        webDriver.findElement(By.xpath("//span[text()='人事信息']")).click();

        webDriver.findElement(By.xpath("//div[@id='staffs']//div[@class='datagrid-body']//tr[@datagrid-row-index='0']")).click();

        webDriver.findElement(By.xpath("//span[text()='职称记录']")).click();

        webDriver.findElement(By.xpath("//div[@id='information']//div[@class='datagrid-body']//tr[@datagrid-row-index='0']")).click();

        webDriver.findElement(By.xpath("//a[@id='EmployeeAssProfessionalTitle_update_btn']")).click();



        webDriver.findElement(By.xpath("//td[@field='professionalTitle']//span[@class='textbox-addon textbox-addon-right']")).click();
        webDriver.findElement(By.xpath("//div[@class='panel combo-p' and contains(@style,'z-index: 1100')]//div[text()='副教授']")).click();

        webDriver.findElement(By.xpath("//td[@field='appointedDate']//span[@class='textbox-addon textbox-addon-right']")).click();
        Util.sleep(200);
        webDriver.findElement(By.xpath("//div[@class='panel combo-p' and contains(@style,'z-index: 1100')]//td[@abbr='2017,5,16']")).click();

        webDriver.findElement(By.xpath("//td[@field='nominatedDate']//span[@class='textbox-addon textbox-addon-right']")).click();
        Util.sleep(200);
        webDriver.findElement(By.xpath("//div[@class='panel combo-p' and contains(@style,'z-index: 1100')]//td[@abbr='2017,5,16']")).click();


        webDriver.findElement(By.xpath("//td[@field='nominatedNo']//input")).sendKeys("3");
        webDriver.findElement(By.xpath("//td[@field='appointedNo']//input")).sendKeys("3");

        webDriver.findElement(By.xpath("//a[@id='EmployeeAssProfessionalTitle_save_btn']")).click();

    }



}
