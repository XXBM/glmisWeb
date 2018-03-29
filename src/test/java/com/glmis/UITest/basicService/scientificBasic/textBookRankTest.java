package com.glmis.UITest.basicService.scientificBasic;

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
 * Created by inkskyu428 on 17-6-19.
 */
public class textBookRankTest {
    private WebDriver webDriver;

    @BeforeClass
    public void setUp(){
        webDriver = Util.setUpChrome();
    }

    @Test(invocationCount = 1)
    public void testAddtextBookRank() throws IOException {

        //获取Excel的测试数据，并保存到map集合中
        ImportExcel importExcel = new ImportExcel();
        String url = System.getProperty("user.dir") + "/src/test/resources/static/testData/basicService/scientificBasic/textBookRankTest.xls";
        Map<Integer, Map<String, String>> textBookRanks = importExcel.importExcel(url);
        webDriver.findElement(By.xpath("//ul[@id='nav']//li[1]//ul//li[5]//span[@class='tree-hit tree-collapsed']")).click();
        Util.sleep(500);
        webDriver.findElement(By.xpath("//div[@id='_easyui_tree_30']")).click();
        //储存结果信息
        String[][] textBookRankTestResult = new String[2][textBookRanks.size()];
        //外层for循环代表excel中的测试数据数目，数目即excel中除标题栏还有几行数据
        for (int i = 0; i < textBookRanks.size(); i++) {
            //从employees集合后中获取第n个测试数据，即第n个要添加的员工信息, i+1是因为向employees集合中添加第一条员工数据的key为1
            Map<String, String> textBookRank = textBookRanks.get(i + 1);
            Util.sleep(100);
            webDriver.findElement(By.xpath("//a[@id='TextbookRank_add_btn']")).click();
            
            webDriver.findElement(By.xpath("//td[@field='no']//input")).sendKeys(textBookRank.get("编号"));
            webDriver.findElement(By.xpath("//td[@field='description']//input")).sendKeys(textBookRank.get("教材级别"));

            webDriver.findElement(By.xpath("//a[@id='TextbookRank_save_btn']")).click();

            String message = webDriver.findElement(By.xpath("//div[@class='panel window messager-window']//div[@class='messager-body panel-body panel-body-noborder window-body']//div[2]")).getText();
            Assert.assertEquals(message, textBookRank.get("期望"));
            textBookRankTestResult[0][i] = textBookRank.get("期望");
            textBookRankTestResult[1][i] = message;
            webDriver.findElement(By.xpath("//*[text()='确定']")).click();
            if (message.equals("请检查输入信息，查看红色字段")) {
                webDriver.findElement(By.xpath("//a[@id='TextbookRank_redo_btn']")).click();
            }

            //}
        }
        //excel表格中输入结果数据
        new DataOutToExcel().dataOut(url, textBookRankTestResult);
    }


    @Test(invocationCount = 1)
    public void testDeletetextBookRank() {

        webDriver.findElement(By.xpath("//ul[@id='nav']//li[1]//ul//li[5]//span[@class='tree-hit tree-collapsed']")).click();
        Util.sleep(500);
        webDriver.findElement(By.xpath("//div[@id='_easyui_tree_30']")).click();

        webDriver.findElement(By.xpath("//div[@id='textBookRank_div']//div[@class='datagrid-view2']//div[@class='datagrid-body']//tr[@datagrid-row-index='3']")).click();

        webDriver.findElement(By.xpath("//a[@id='TextbookRank_remove_btn']")).click();

        webDriver.findElement(By.xpath("//*[text()='确定']")).click();

        Util.sleep(100);

        webDriver.findElement(By.xpath("//*[text()='确定']")).click();
    }

    @Test(invocationCount = 1)
    public void testEdittextBookRank() {

        webDriver.findElement(By.xpath("//ul[@id='nav']//li[1]//ul//li[5]//span[@class='tree-hit tree-collapsed']")).click();
        Util.sleep(500);
        webDriver.findElement(By.xpath("//div[@id='_easyui_tree_30']")).click();

        webDriver.findElement(By.xpath("//div[@id='textBookRank_div']//div[@class='datagrid-view2']//div[@class='datagrid-body']//tr[@datagrid-row-index='3']")).click();

        webDriver.findElement(By.xpath("//a[@id='TextbookRank_edit_btn']")).click();

        webDriver.findElement(By.xpath("//td[@field='no']//input")).sendKeys("1");
        webDriver.findElement(By.xpath("//td[@field='description']//input")).sendKeys("硕士");

        webDriver.findElement(By.xpath("//a[@id='TextbookRank_save_btn']")).click();
    }
}
