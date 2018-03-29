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
public class EmployeeAssPostTest {
    private WebDriver webDriver;
    @BeforeClass(alwaysRun = false)
    public void setUp() {
        webDriver = Util.setUpChrome();
    }
    @Test(invocationCount = 1)
    public void TestEmployeeAssPostAdd() throws IOException {
        //获取Excel的测试数据，并保存到map集合中
        ImportExcel importExcel = new ImportExcel();
        String url = System.getProperty("user.dir") + "/src/test/resources/static/testData/personnel/PostTest.xls";
        Map<Integer, Map<String, String>> employeeassposts = importExcel.importExcel(url);

        webDriver.findElement(By.xpath("//span[text()='人事信息']")).click();
        Util.sleep(10000);
        webDriver.findElement(By.xpath("//div[@id='empInfoTabs']//span[text()='职位变更']")).click();
        Util.sleep(10000);
        //储存结果信息
        String[][] ResultOfTest = new String[2][employeeassposts.size()];
        Util.sleep(5000);
        //外层for循环代表excel中的测试数据数目，数目即excel中除标题栏还有几行数据
        for(int i=0;i<employeeassposts.size();i++){

            Map<String, String> employeeasspost = employeeassposts.get(i + 1);

            webDriver.findElement(By.xpath("//div[@id='EmployeeAssPost']//span[text()='添加']")).click();
            //职称下拉框
            if (employeeasspost.get("职位名称")!=null&&employeeasspost.get("职位名称")!="") {
                webDriver.findElement(By.xpath("//div[@id='EmployeeAssPost']//td[@field='post']//span[@class='textbox-addon textbox-addon-right']")).click();
                Util.sleep(100);
                webDriver.findElement(By.xpath("//div[@class='panel combo-p' and contains(@style,'z-index: 1100')]//div[text()='" + employeeasspost.get("职位名称") + "']")).click();
            }

            //datebox
            if (employeeasspost.get("任命时间")!=null&&employeeasspost.get("任命时间")!="") {
                webDriver.findElement(By.xpath("//td[@field='appointedDate']//span[@class='textbox-addon textbox-addon-right']")).click();
                Util.chooseCalendar(employeeasspost.get("任命时间"), webDriver);
            }

            //input
            if(employeeasspost.get("红头文号")!=null&&employeeasspost.get("红头文号")!=""){
                webDriver.findElement(By.xpath("//div[@id='EmployeeAssPost']//td[@field='commissionNo']//td[1]//input")).sendKeys(employeeasspost.get("红头文号"));

            }

            //保存
            webDriver.findElement(By.xpath("//div[@id='EmployeeAssPost']//a[@id='EmploymentAssPost_save_btn']")).click();

            String message = webDriver.findElement(By.xpath("//div[@class='panel window messager-window']//div[@class='messager-body panel-body panel-body-noborder window-body']//div[2]")).getText();
            Assert.assertEquals(message, employeeasspost.get("期望"));
            ResultOfTest[0][i] = employeeasspost.get("期望");
            ResultOfTest[1][i] = message;
            webDriver.findElement(By.xpath("//*[text()='确定']")).click();
            if (message.equals("请检查输入信息，查看红色字段")) {
                webDriver.findElement(By.xpath("//a[@id='EmploymentAssPost_cancel_btn']")).click();
            }
        }
        //excel表格中输入结果数据
        new DataOutToExcel().dataOut(url, ResultOfTest);
    }
    @Test(invocationCount = 1)
    public void TestEmployeeAssPostDelete() throws IOException {
        webDriver.findElement(By.xpath("//span[text()='人事信息']")).click();
        webDriver.findElement(By.xpath("//div[@id='empInfoTabs']//span[text()='职位变更']")).click();
        //管理员
        webDriver.findElement(By.xpath("//div[@id='staffs']//div[@class='datagrid-body']//td[@field='name']")).click();
        //2行
        webDriver.findElement(By.xpath("//div[@id='EmployeeAssPost']//div[@class='datagrid-body']//tr[2]")).click();
        //删除
        webDriver.findElement(By.xpath("//div[@id='EmployeeAssPost']//a[@id='EmploymentAssPost_delete_btn']")).click();
        webDriver.findElement(By.xpath("//div[@class='panel window messager-window']//div[@class='dialog-button messager-button']//a")).click();
        Util.sleep(100);
        webDriver.findElement(By.xpath("//*[text()='确定']")).click();
    }
    @Test(invocationCount = 1)
    public void TestEmployeeAssPostUpdate() throws IOException {
        webDriver.findElement(By.xpath("//span[text()='人事信息']")).click();
        webDriver.findElement(By.xpath("//div[@id='empInfoTabs']//span[text()='职位变更']")).click();
        webDriver.findElement(By.xpath("//div[@id='staffs']//div[@class='datagrid-body']//td[@field='name']")).click();
        //修改第二行1
        webDriver.findElement(By.xpath("//div[@id='EmployeeAssPost']//div[@class='datagrid-body']//tr[2]")).click();
        webDriver.findElement(By.xpath("//div[@id='EmployeeAssPost']//a[@id='EmploymentAssPost_update_btn']")).click();
        //下拉框内容改为第四
        webDriver.findElement(By.xpath("//div[@id='EmployeeAssPost']//td[@field='post']//span[@class='textbox-addon textbox-addon-right']")).click();
        Util.sleep(100);
        webDriver.findElement(By.xpath("//div[@class='panel combo-p' and contains(@style,'z-index: 110000')]//div[@id='_easyui_combobox_i10_4']")).click();

        //datebox
        webDriver.findElement(By.xpath("//td[@field='appointedDate']//span[@class='textbox-addon textbox-addon-right']")).click();
        Util.chooseCalendar("2016-06-05", webDriver);

        //input
        webDriver.findElement(By.xpath("//div[@id='EmployeeAssPost']//td[@field='commissionNo']//td[1]//input")).clear();
        webDriver.findElement(By.xpath("//div[@id='EmployeeAssPost']//td[@field='commissionNo']//td[1]//input")).sendKeys("5678");

        //save
        webDriver.findElement(By.xpath("//div[@id='EmployeeAssPost']//a[@id='EmploymentAssPost_save_btn']")).click();
        webDriver.findElement(By.xpath("//*[text()='确定']")).click();

    }
}


