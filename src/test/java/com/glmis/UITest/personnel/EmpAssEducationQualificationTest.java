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
 * Created by inkskyu428 on 17-5-17.
 */
public class EmpAssEducationQualificationTest {
    private WebDriver webDriver;

    @BeforeClass
    public void setUp(){
        webDriver = Util.setUpChrome();
    }

    @Test(invocationCount = 1)
    public void testAddEducationQualification()throws IOException{

        //获取Excel的测试数据，并保存到map集合中
        ImportExcel importExcel = new ImportExcel();
        //String url =  FileSystemView.getFileSystemView().getHomeDirectory().getPath() + "/Desktop/EducationQualificationTest.xls";
        String url = System.getProperty("user.dir") + "/src/test/resources/static/testData/personnel/EducationQualificationTest.xls";
        Map<Integer, Map<String, String>> educationQualifications = importExcel.importExcel(url);
        webDriver.findElement(By.xpath("//span[text()='人事信息']")).click();
        Util.sleep(10000);
        webDriver.findElement(By.xpath("//div[@id='staffs']//div[@class='datagrid-body']//tr[@datagrid-row-index='0']")).click();
        Util.sleep(10000);
        webDriver.findElement(By.xpath("//span[text()='学历']")).click();
        Util.sleep(5000);
//        String resultUrl =  FileSystemView.getFileSystemView().getHomeDirectory().getPath() + "/Desktop/educationQualification_testResult.txt";
//        File file =new File(resultUrl);
//        FileOutputStream testFile =new FileOutputStream(file);
//        PrintStream printStream =new PrintStream(testFile);
        //储存结果信息
        String[][] educationQualificationTestResult = new String[2][educationQualifications.size()];
        //外层for循环代表excel中的测试数据数目，数目即excel中除标题栏还有几行数据
        for (int i = 0; i < educationQualifications.size(); i++) {
            //从employees集合后中获取第n个测试数据，即第n个要添加的员工信息, i+1是因为向employees集合中添加第一条员工数据的key为1
            Map<String, String> educationQualification = educationQualifications.get(i + 1);
            webDriver.findElement(By.xpath("//a[@id='EmployeeAssEducationQualification_add_btn']")).click();
            if (educationQualification.get("学历名称")!=null&&educationQualification.get("学历名称")!="") {
                //学历名称下拉框
                webDriver.findElement(By.xpath("//td[@field='educationQualification']//span[@class='textbox-addon textbox-addon-right']")).click();
                Util.sleep(100);
                webDriver.findElement(By.xpath("//div[@class='panel combo-p' and contains(@style,'z-index: 1100')]//div[text()='" + educationQualification.get("学历名称") + "']")).click();
            }//datebox输入框版毕业时间
            //webDriver.findElement(By.xpath("//td[@field='graduateDate']//input[@class='textbox-text validatebox-text validatebox-invalid textbox-prompt']")).sendKeys(educationQualification.get("毕业时间"));
            //datebox输入框版入学时间
            //webDriver.findElement(By.xpath("//td[@field='enrollDate']//input[@class='textbox-text validatebox-text validatebox-invalid textbox-prompt']")).sendKeys(educationQualification.get("入学时间"));
            //时间选择器版
            if (educationQualification.get("毕业时间")!=null&&educationQualification.get("毕业时间")!="") {
                webDriver.findElement(By.xpath("//td[@field='graduateDate']//span[@class='textbox-addon textbox-addon-right']//a")).click();
                Util.chooseCalendar(educationQualification.get("毕业时间"), webDriver);
            }
            if (educationQualification.get("入学时间")!=null&&educationQualification.get("入学时间")!=""){
                webDriver.findElement(By.xpath("//td[@field='enrollDate']//span[@class='textbox-addon textbox-addon-right']//a")).click();
                Util.chooseCalendar(educationQualification.get("入学时间"), webDriver);
            }
            if(educationQualification.get("专业")!=null&&educationQualification.get("专业")!=""){
                webDriver.findElement(By.xpath("//td[@field='major']//input")).sendKeys(educationQualification.get("专业"));

            }
            if(educationQualification.get("学制")!=null&&educationQualification.get("学制")!=""){
                webDriver.findElement(By.xpath("//td[@field='duration']//input")).sendKeys(educationQualification.get("学制"));

            }
            if(educationQualification.get("导师姓名")!=null&&educationQualification.get("导师姓名")!=""){
                webDriver.findElement(By.xpath("//td[@field='supervisorName']//input")).sendKeys(educationQualification.get("导师姓名"));

            }
            if(educationQualification.get("毕业证编号")!=null&&educationQualification.get("毕业证编号")!=""){
                webDriver.findElement(By.xpath("//td[@field='certificateNo']//input")).sendKeys(educationQualification.get("毕业证编号"));

            }
            if(educationQualification.get("学校")!=null&&educationQualification.get("学校")!=""){
                webDriver.findElement(By.xpath("//td[@field='university']//input")).sendKeys(educationQualification.get("学校"));

            }

            webDriver.findElement(By.xpath("//a[@id='EmployeeAssEducationQualification_save_btn']")).click();

            String message = webDriver.findElement(By.xpath("//div[@class='panel window messager-window']//div[@class='messager-body panel-body panel-body-noborder window-body']//div[2]")).getText();
//            if (educationQualification.get("期望").equals(input))
//                printStream.println("第"+(i+1)+"条"+"测试成功");
//            else
//                printStream.println("第"+(i+1)+"条"+"测试失败");
            Assert.assertEquals(message,educationQualification.get("期望"));
            educationQualificationTestResult[0][i] = educationQualification.get("期望");
            educationQualificationTestResult[1][i] = message;
            webDriver.findElement(By.xpath("//*[text()='确定']")).click();
            if (message.equals("请检查输入信息，查看红色字段")) {
                webDriver.findElement(By.xpath("//a[@id='EmployeeAssEducationQualification_cancel_btn']")).click();
            }
        }
        //excel表格中输入结果数据
        new DataOutToExcel().dataOut(url, educationQualificationTestResult);
    }


    @Test(invocationCount = 1)
    public void testDeleteEducationQualification(){

        webDriver.findElement(By.xpath("//span[text()='人事信息']")).click();

        webDriver.findElement(By.xpath("//div[@id='staffs']//div[@class='datagrid-body']//tr[@datagrid-row-index='0']")).click();

        webDriver.findElement(By.xpath("//span[text()='学历']")).click();

        webDriver.findElement(By.xpath("//div[@id='information']//div[@class='datagrid-body']//tr[@datagrid-row-index='1']")).click();

        webDriver.findElement(By.xpath("//a[@id='EmployeeAssEducationQualification_delete_btn']")).click();

        webDriver.findElement(By.xpath("//*[text()='确定']")).click();

        Util.sleep(100);

        webDriver.findElement(By.xpath("//*[text()='确定']")).click();
    }

    @Test(invocationCount = 1)
    public void testEditEducationQualification(){
        webDriver.findElement(By.xpath("//span[text()='人事信息']")).click();

        webDriver.findElement(By.xpath("//div[@id='staffs']//div[@class='datagrid-body']//tr[@datagrid-row-index='0']")).click();

        webDriver.findElement(By.xpath("//span[text()='学历']")).click();

        webDriver.findElement(By.xpath("//div[@id='information']//div[@class='datagrid-body-inner']//tr[@datagrid-row-index='0']")).click();

        webDriver.findElement(By.xpath("//a[@id='EmployeeAssEducationQualification_update_btn']")).click();

        webDriver.findElement(By.xpath("//td[@field='educationQualification']//span[@class='textbox-addon textbox-addon-right']")).click();
        webDriver.findElement(By.xpath("//div[@class='panel combo-p' and contains(@style,'z-index: 1100')]//div[text()='大学本科']")).click();

        webDriver.findElement(By.xpath("//td[@field='graduateDate']//span[@class='textbox-addon textbox-addon-right']")).click();
        Util.sleep(200);
        webDriver.findElement(By.xpath("//div[@class='panel combo-p' and contains(@style,'z-index: 1100')]//td[@abbr='2017,5,17']")).click();

        webDriver.findElement(By.xpath("//td[@field='enrollDate']//span[@class='textbox-addon textbox-addon-right']")).click();
        Util.sleep(200);
        webDriver.findElement(By.xpath("//div[@class='panel combo-p' and contains(@style,'z-index: 1100')]//td[@abbr='2017,5,16']")).click();
        webDriver.findElement(By.xpath("//td[@field='major']//input")).sendKeys("3");
        webDriver.findElement(By.xpath("//td[@field='duration']//input")).sendKeys("3");
        webDriver.findElement(By.xpath("//td[@field='supervisorName']//input")).sendKeys("3");
        webDriver.findElement(By.xpath("//td[@field='certificateNo']//input")).sendKeys("3");
        webDriver.findElement(By.xpath("//td[@field='university']//input")).sendKeys("3");

        webDriver.findElement(By.xpath("//a[@id='EmployeeAssEducationQualification_save_btn']")).click();
        
    }
}
