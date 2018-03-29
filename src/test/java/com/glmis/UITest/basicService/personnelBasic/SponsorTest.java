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
public class SponsorTest {
    private WebDriver webDriver;

    @BeforeClass
    public void setUp(){
        webDriver = Util.setUpChrome();
    }

    @Test(invocationCount = 1)
    public void testAddSponsor() throws IOException {

        //获取Excel的测试数据，并保存到map集合中
        ImportExcel importExcel = new ImportExcel();
        //String url =  FileSystemView.getFileSystemView().getHomeDirectory().getPath() + "/Desktop/SponsorTest.xls";
        String url = System.getProperty("user.dir") + "/src/test/resources/static/testData/basicService/personnelBasic/SponsorTest.xls";
        Map<Integer, Map<String, String>> Sponsors = importExcel.importExcel(url);
        webDriver.findElement(By.xpath("//span[@class='tree-hit tree-collapsed']")).click();
        Util.sleep(1000);
        webDriver.findElement(By.xpath("//span[text()='机构维护']")).click();
        Util.sleep(1000);
        webDriver.findElement(By.xpath("//span[text()='资助机构']")).click();
        Util.sleep(1000);
        //储存结果信息
        String[][] SponsorTestResult = new String[2][Sponsors.size()];
        //外层for循环代表excel中的测试数据数目，数目即excel中除标题栏还有几行数据
        for (int i = 0; i < Sponsors.size(); i++) {
            //从employees集合后中获取第n个测试数据，即第n个要添加的员工信息, i+1是因为向employees集合中添加第一条员工数据的key为1
            Map<String, String> sponsor = Sponsors.get(i + 1);
            Util.sleep(1000);
            webDriver.findElement(By.xpath("//a[@id='Sponsor_add_btn']")).click();

            if (sponsor.get("编号")!=null&&sponsor.get("编号")!="") {
                webDriver.findElement(By.xpath("//td[@field='no']//input")).sendKeys(sponsor.get("编号"));
            }
            if (sponsor.get("资助机构")!=null&&sponsor.get("资助机构")!="") {
                webDriver.findElement(By.xpath("//td[@field='description']//input")).sendKeys(sponsor.get("资助机构"));
            }
            Util.sleep(5000);
            webDriver.findElement(By.xpath("//a[@id='Sponsor_save_btn']")).click();

            String message = webDriver.findElement(By.xpath("//div[@class='panel window messager-window']//div[@class='messager-body panel-body panel-body-noborder window-body']//div[2]")).getText();
            Assert.assertEquals(message, sponsor.get("期望"));
            SponsorTestResult[0][i] = sponsor.get("期望");
            SponsorTestResult[1][i] = message;
            webDriver.findElement(By.xpath("//*[text()='确定']")).click();
            if (message.equals("请检查输入信息，查看红色字段")) {
                webDriver.findElement(By.xpath("//a[@id='Sponsor_redo_btn']")).click();
            }

            //}
        }
        //excel表格中输入结果数据
        new DataOutToExcel().dataOut(url, SponsorTestResult);
    }


    @Test(invocationCount = 1)
    public void testDeleteSponsor() {

        webDriver.findElement(By.xpath("//span[@class='tree-hit tree-collapsed']")).click();

        webDriver.findElement(By.xpath("//span[text()='机构维护']")).click();
        webDriver.findElement(By.xpath("//span[text()='资助机构']")).click();

        webDriver.findElement(By.xpath("//div[@id='Sponsor_div']//div[@class='datagrid-view2']//div[@class='datagrid-body']//tr[@datagrid-row-index='2']")).click();

        webDriver.findElement(By.xpath("//a[@id='Sponsor_remove_btn']")).click();

        webDriver.findElement(By.xpath("//*[text()='确定']")).click();

        Util.sleep(100);

        webDriver.findElement(By.xpath("//*[text()='确定']")).click();
    }

    @Test(invocationCount = 1)
    public void testEditSponsor() {

        webDriver.findElement(By.xpath("//span[@class='tree-hit tree-collapsed']")).click();

        webDriver.findElement(By.xpath("//span[text()='机构维护']")).click();
        webDriver.findElement(By.xpath("//span[text()='资助机构']")).click();

        webDriver.findElement(By.xpath("//div[@id='Sponsor_div']//div[@class='datagrid-view2']//div[@class='datagrid-body']//tr[@datagrid-row-index='0']")).click();

        webDriver.findElement(By.xpath("//a[@id='Sponsor_edit_btn']")).click();

        webDriver.findElement(By.xpath("//td[@field='no']//input")).sendKeys("1");
        webDriver.findElement(By.xpath("//td[@field='description']//input")).sendKeys("硕士");

        webDriver.findElement(By.xpath("//a[@id='Sponsor_save_btn']")).click();
    }
}
