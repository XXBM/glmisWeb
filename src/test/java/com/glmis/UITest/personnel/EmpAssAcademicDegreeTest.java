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
public class EmpAssAcademicDegreeTest {

    private WebDriver webDriver;

    @BeforeClass
    public void setUp(){
        webDriver = Util.setUpChrome();
    }

    @Test(invocationCount = 1)
    public void testAddTitle()throws IOException {
        //获取Excel的测试数据，并保存到map集合中
        ImportExcel importExcel = new ImportExcel();
        String url = System.getProperty("user.dir") + "/src/test/resources/static/testData/personnel/EmpAssAcademicTest.xls";
        Map<Integer, Map<String, String>> empassacdemics = importExcel.importExcel(url);

        webDriver.findElement(By.xpath("//span[text()='人事信息']")).click();
        Util.sleep(10000);
        webDriver.findElement(By.xpath("//div[@id='staffs']//div[@class='datagrid-body']//tr[@datagrid-row-index='0']")).click();
        Util.sleep(10000);
        webDriver.findElement(By.xpath("//span[text()='学位']")).click();
        Util.sleep(5000);
        String[][] ResultOfTest = new String[2][empassacdemics.size()];
        for(int i=0;i<empassacdemics.size();i++){
            Map<String, String> empassacdemic = empassacdemics.get(i + 1);

            webDriver.findElement(By.xpath("//a[@id='EmployeeAssAcademicDegree_add_btn']")).click();

            //职称下拉框
            if (empassacdemic.get("学科")!=null&&empassacdemic.get("学科")!="") {
                webDriver.findElement(By.xpath("//td[@field='academicDegreeType']//span[@class='textbox-addon textbox-addon-right']")).click();
                Util.sleep(100);
                webDriver.findElement(By.xpath("//div[@class='panel combo-p' and contains(@style,'z-index: 1100')]//div[text()='" + empassacdemic.get("学科") + "']")).click();
            }

//            //学科下拉框
//            webDriver.findElement(By.xpath("//td[@field='academicDegreeType']//span[@class='textbox-addon textbox-addon-right']")).click();
//            Util.sleep(100);
//            webDriver.findElement(By.xpath("//div[@class='panel combo-p' and contains(@style,'z-index: 1100')]//div[text()='工学']")).click();
            if (empassacdemic.get("学位")!=null&&empassacdemic.get("学位")!="") {
                webDriver.findElement(By.xpath("//td[@field='academicDegree']//span[@class='textbox-addon textbox-addon-right']")).click();
                Util.sleep(100);
                webDriver.findElement(By.xpath("//div[@class='panel combo-p' and contains(@style,'z-index: 1100')]//div[text()='" + empassacdemic.get("学位") + "']")).click();
            }
//            webDriver.findElement(By.xpath("//td[@field='academicDegree']//span[@class='textbox-addon textbox-addon-right']")).click();
//            Util.sleep(100);
//            webDriver.findElement(By.xpath("//div[@class='panel combo-p' and contains(@style,'z-index: 1100')]//div[text()='硕士']")).click();
            //datebox
            if (empassacdemic.get("获得时间")!=null&&empassacdemic.get("获得时间")!="") {
                webDriver.findElement(By.xpath("//td[@field='grantedDate']//span[@class='textbox-addon textbox-addon-right']")).click();
                Util.chooseCalendar(empassacdemic.get("获得时间"), webDriver);
            }

//            webDriver.findElement(By.xpath("//td[@field='grantedDate']//span[@class='textbox-addon textbox-addon-right']")).click();
//            webDriver.findElement(By.xpath("//div[@class='datebox-button']//a[@class='datebox-button-a']")).click();

             if(empassacdemic.get("专业")!=null&&empassacdemic.get("专业")!=""){
                 webDriver.findElement(By.xpath("//td[@field='major']//input")).sendKeys(empassacdemic.get("专业"));

             }
             if(empassacdemic.get("证书编号")!=null&&empassacdemic.get("证书编号")!=""){
                 webDriver.findElement(By.xpath("//td[@field='certificateNo']//input")).sendKeys(empassacdemic.get("证书编号"));

             }
             if(empassacdemic.get("毕业学校")!=null&&empassacdemic.get("毕业学校")!=""){
                 webDriver.findElement(By.xpath("//td[@field='university']//input")).sendKeys(empassacdemic.get("毕业学校"));

             }

            webDriver.findElement(By.xpath("//a[@id='EmployeeAssAcademicDegree_save_btn']")).click();
            String message = webDriver.findElement(By.xpath("//div[@class='panel window messager-window']//div[@class='messager-body panel-body panel-body-noborder window-body']//div[2]")).getText();
            Assert.assertEquals(message, empassacdemic.get("期望"));
            ResultOfTest[0][i] = empassacdemic.get("期望");
            ResultOfTest[1][i] = message;
            webDriver.findElement(By.xpath("//*[text()='确定']")).click();
            if (message.equals("请检查输入信息，查看红色字段")) {
                webDriver.findElement(By.xpath("//a[@id='EmployeeAssAcademicDegree_cancel_btn']")).click();
            }
//        webDriver.close();
        }
        new DataOutToExcel().dataOut(url, ResultOfTest);
    }


    @Test(invocationCount = 1)
    public void testDeleteTitle(){

        webDriver.findElement(By.xpath("//span[text()='人事信息']")).click();

        webDriver.findElement(By.xpath("//div[@id='staffs']//div[@class='datagrid-body']//tr[@datagrid-row-index='0']")).click();

        webDriver.findElement(By.xpath("//span[text()='学位']")).click();

        webDriver.findElement(By.xpath("//div[@id='information']//div[@class='datagrid-body']//tr[@datagrid-row-index='1']")).click();

        webDriver.findElement(By.xpath("//a[@id='EmployeeAssAcademicDegree_delete_btn']")).click();

        webDriver.findElement(By.xpath("//*[text()='确定']")).click();

        Util.sleep(100);

        webDriver.findElement(By.xpath("//*[text()='确定']")).click();
    }

    @Test(invocationCount = 1)
    public void testEditTitle(){
        webDriver.findElement(By.xpath("//span[text()='人事信息']")).click();

        webDriver.findElement(By.xpath("//div[@id='staffs']//div[@class='datagrid-body']//tr[@datagrid-row-index='0']")).click();

        webDriver.findElement(By.xpath("//span[text()='学位']")).click();

        webDriver.findElement(By.xpath("//div[@id='information']//div[@class='datagrid-body']//tr[@datagrid-row-index='2']")).click();

        webDriver.findElement(By.xpath("//a[@id='EmployeeAssAcademicDegree_update_btn']")).click();

        webDriver.findElement(By.xpath("//td[@field='academicDegreeType']//span[@class='textbox-addon textbox-addon-right']")).click();
        webDriver.findElement(By.xpath("//div[@class='panel combo-p' and contains(@style,'z-index: 1100')]//div[text()='文学']")).click();

        webDriver.findElement(By.xpath("//td[@field='academicDegree']//span[@class='textbox-addon textbox-addon-right']")).click();
        webDriver.findElement(By.xpath("//div[@class='panel combo-p' and contains(@style,'z-index: 1100')]//div[text()='博士']")).click();

        webDriver.findElement(By.xpath("//td[@field='grantedDate']//span[@class='textbox-addon textbox-addon-right']")).click();
        Util.sleep(200);
        webDriver.findElement(By.xpath("//div[@class='panel combo-p' and contains(@style,'z-index: 1100')]//td[@abbr='2017,5,16']")).click();

        webDriver.findElement(By.xpath("//td[@field='major']//input")).sendKeys("3");
        webDriver.findElement(By.xpath("//td[@field='certificateNo']//input")).sendKeys("3");
        webDriver.findElement(By.xpath("//td[@field='university']//input")).sendKeys("3");

        webDriver.findElement(By.xpath("//a[@id='EmployeeAssAcademicDegree_save_btn']")).click();

    }



}
