package com.glmis.UITest.basicService.personnelBasic;

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
 * Created by inkskyu428 on 17-5-26.
 */
public class ProfessionalTitleTest {
    private WebDriver webDriver;
    @BeforeClass
    public void setUp(){
        webDriver = Util.setUpChrome();
    }
    @Test(invocationCount = 1)
    public void testAddProfessionalTitle()throws IOException {

        //获取Excel的测试数据，并保存到map集合中
        ImportExcel importExcel = new ImportExcel();
        //String url =  FileSystemView.getFileSystemView().getHomeDirectory().getPath() + "/Desktop/ProfessionalTitleTest.xls";
        String url = System.getProperty("user.dir") + "/src/test/resources/static/testData/basicService/personnelBasic/ProfessionalTitleTest.xls";
        Map<Integer, Map<String, String>> professionalTitles = importExcel.importExcel(url);
        webDriver.findElement(By.xpath("//span[@class='tree-hit tree-collapsed']")).click();
        Util.sleep(1000);
        webDriver.findElement(By.xpath("//span[text()='职称维护']")).click();
        Util.sleep(1000);
        //储存结果信息
        String[][] professinalTitleTestResult = new String[2][professionalTitles.size()];
        //外层for循环代表excel中的测试数据数目，数目即excel中除标题栏还有几行数据
        for (int i = 0; i < professionalTitles.size(); i++) {
            //从employees集合后中获取第n个测试数据，即第n个要添加的员工信息, i+1是因为向employees集合中添加第一条员工数据的key为1
            Map<String, String> professionalTitle =professionalTitles.get(i + 1);
            Util.sleep(1000);
            webDriver.findElement(By.xpath("//a[@id='ProfessionalTitle_add_btn']")).click();

            if (professionalTitle.get("编号")!=null&&professionalTitle.get("编号")!="") {
                webDriver.findElement(By.xpath("//td[@field='no']//input")).sendKeys(professionalTitle.get("编号"));
            }
            if (professionalTitle.get("职称")!=null&&professionalTitle.get("职称")!="") {
                webDriver.findElement(By.xpath("//td[@field='description']//input")).sendKeys(professionalTitle.get("职称"));
            }
            Util.sleep(5000);
            webDriver.findElement(By.xpath("//a[@id='ProfessionalTitle_save_btn']")).click();
            Util.sleep(5000);
            String message = webDriver.findElement(By.xpath("//div[@class='panel window messager-window']//div[@class='messager-body panel-body panel-body-noborder window-body']//div[2]")).getText();
            Assert.assertEquals(message, professionalTitle.get("期望"));
            professinalTitleTestResult[0][i] = professionalTitle.get("期望");
            professinalTitleTestResult[1][i] = message;
            webDriver.findElement(By.xpath("//*[text()='确定']")).click();
            if (message.equals("请检查输入信息，查看红色字段")) {
                webDriver.findElement(By.xpath("//a[@id='ProfessionalTitle_redo_btn']")).click();
            }

            //}
        }
        //excel表格中输入结果数据
        new DataOutToExcel().dataOut(url, professinalTitleTestResult);
    }


    @Test(invocationCount = 1)
    public void testDeleteProfessionalTitle(){

        webDriver.findElement(By.xpath("//span[@class='tree-hit tree-collapsed']")).click();

        webDriver.findElement(By.xpath("//span[text()='职称维护']")).click();

        webDriver.findElement(By.xpath("//div[@class='datagrid-view2']//div[@class='datagrid-body']//tr[@datagrid-row-index='0']")).click();

        webDriver.findElement(By.xpath("//a[@id='ProfessionalTitle_remove_btn']")).click();

        webDriver.findElement(By.xpath("//*[text()='确定']")).click();

        Util.sleep(100);

        webDriver.findElement(By.xpath("//*[text()='确定']")).click();
    }

    @Test(invocationCount = 1)
    public void testEditProfessionalTitle(){

        webDriver.findElement(By.xpath("//span[@class='tree-hit tree-collapsed']")).click();

        webDriver.findElement(By.xpath("//span[text()='职称维护']")).click();

        webDriver.findElement(By.xpath("//div[@class='datagrid-view2']//div[@class='datagrid-body']//tr[@datagrid-row-index='0']")).click();

        webDriver.findElement(By.xpath("//a[@id='ProfessionalTitle_edit_btn']")).click();

        webDriver.findElement(By.xpath("//td[@field='no']//input")).sendKeys("1");
        webDriver.findElement(By.xpath("//td[@field='description']//input")).sendKeys("教授");

        webDriver.findElement(By.xpath("//a[@id='ProfessionalTitle_save_btn']")).click();
    }
}
