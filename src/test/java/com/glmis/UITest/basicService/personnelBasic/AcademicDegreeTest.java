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
public class AcademicDegreeTest {
    private WebDriver webDriver;
    @BeforeClass
    public void setUp(){
        webDriver = Util.setUpChrome();
    }
    @Test(invocationCount = 1)
    public void testAddAcademicDegree()throws IOException {

        //获取Excel的测试数据，并保存到map集合中
        ImportExcel importExcel = new ImportExcel();
        //String url =  FileSystemView.getFileSystemView().getHomeDirectory().getPath() + "/Desktop/AcademicDegreeTest.xls";
        String url = System.getProperty("user.dir") + "/src/test/resources/static/testData/basicService/personnelBasic/AcademicDegreeTest.xls";
        Map<Integer, Map<String, String>> academicDegrees = importExcel.importExcel(url);
        webDriver.findElement(By.xpath("//span[@class='tree-hit tree-collapsed']")).click();
        Util.sleep(1000);
        webDriver.findElement(By.xpath("//span[text()='学位维护']")).click();
        Util.sleep(1000);
        webDriver.findElement(By.xpath("//span[text()='层次']")).click();

        //储存结果信息
        String[][] academicDegreeTestResult = new String[2][academicDegrees.size()];
        //外层for循环代表excel中的测试数据数目，数目即excel中除标题栏还有几行数据
        for (int i = 0; i < academicDegrees.size(); i++) {
            //从employees集合后中获取第n个测试数据，即第n个要添加的员工信息, i+1是因为向employees集合中添加第一条员工数据的key为1
            Map<String, String> academicDegree =academicDegrees.get(i + 1);
            Util.sleep(1000);
            webDriver.findElement(By.xpath("//a[@id='AcademicDegree_add_btn']")).click();

            if (academicDegree.get("编号")!=null&&academicDegree.get("编号")!="") {
                webDriver.findElement(By.xpath("//td[@field='no']//input")).sendKeys(academicDegree.get("编号"));

            }
            if (academicDegree.get("层次")!=null&&academicDegree.get("层次")!="") {
                webDriver.findElement(By.xpath("//td[@field='description']//input")).sendKeys(academicDegree.get("层次"));
            }
            Util.sleep(5000);
            webDriver.findElement(By.xpath("//a[@id='AcademicDegree_save_btn']")).click();
            Util.sleep(5000);
            String message = webDriver.findElement(By.xpath("//div[@class='panel window messager-window']//div[@class='messager-body panel-body panel-body-noborder window-body']//div[2]")).getText();
            Assert.assertEquals(message, academicDegree.get("期望"));
            academicDegreeTestResult[0][i] = academicDegree.get("期望");
            academicDegreeTestResult[1][i] = message;
            webDriver.findElement(By.xpath("//*[text()='确定']")).click();
            if (message.equals("请检查输入信息，查看红色字段")) {
                webDriver.findElement(By.xpath("//a[@id='AcademicDegree_redo_btn']")).click();
            }

            //}
        }
        //excel表格中输入结果数据
        new DataOutToExcel().dataOut(url, academicDegreeTestResult);
    }


    @Test(invocationCount = 1)
    public void testDeleteAcademicDegree(){

        webDriver.findElement(By.xpath("//span[@class='tree-hit tree-collapsed']")).click();

        webDriver.findElement(By.xpath("//span[text()='学位维护']")).click();
        webDriver.findElement(By.xpath("//span[text()='层次']")).click();

        webDriver.findElement(By.xpath("//div[@id='academicDegree_div']//div[@class='datagrid-view2']//div[@class='datagrid-body']//tr[@datagrid-row-index='4']")).click();

        webDriver.findElement(By.xpath("//a[@id='AcademicDegree_remove_btn']")).click();

        webDriver.findElement(By.xpath("//*[text()='确定']")).click();

        Util.sleep(100);

        webDriver.findElement(By.xpath("//*[text()='确定']")).click();
    }

    @Test(invocationCount = 1)
    public void testEditAcademicDegree(){

        webDriver.findElement(By.xpath("//span[@class='tree-hit tree-collapsed']")).click();

        webDriver.findElement(By.xpath("//span[text()='学位维护']")).click();
        webDriver.findElement(By.xpath("//span[text()='层次']")).click();

        webDriver.findElement(By.xpath("//div[@id='academicDegree_div']//div[@class='datagrid-view2']//div[@class='datagrid-body']//tr[@datagrid-row-index='0']")).click();

        webDriver.findElement(By.xpath("//a[@id='AcademicDegree_edit_btn']")).click();

        webDriver.findElement(By.xpath("//td[@field='no']//input")).sendKeys("1");
        webDriver.findElement(By.xpath("//td[@field='description']//input")).sendKeys("硕士");

        webDriver.findElement(By.xpath("//a[@id='AcademicDegree_save_btn']")).click();
    }
}
