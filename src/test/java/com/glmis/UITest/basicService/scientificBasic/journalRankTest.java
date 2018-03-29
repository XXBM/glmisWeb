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
 * Created by inkskyu428 on 17-5-19.
 */
public class journalRankTest {
    private WebDriver webDriver;

    @BeforeClass
    public void setUp(){
        webDriver = Util.setUpChrome();
    }

    @Test(invocationCount = 1)
    public void testAdd() throws IOException {
        //获取Excel的测试数据，并保存到map集合中
        ImportExcel importExcel = new ImportExcel();
        String url = System.getProperty("user.dir") + "/src/test/resources/static/testData/basicService/scientificBasic/journalRankTest.xls";
        Map<Integer, Map<String, String>> journalRanks = importExcel.importExcel(url);
        String[][] journalRankTestResult = new String[2][journalRanks.size()];
        webDriver.findElement(By.xpath("//div[@id='_easyui_tree_24']//span[2]")).click();
        Util.sleep(500);
        webDriver.findElement(By.xpath("//div[@id='_easyui_tree_25']//span[5]")).click();

        for (int i = 0; i < journalRanks.size(); i++) {
            Map<String, String> journalRank = journalRanks.get(i + 1);

            webDriver.findElement(By.xpath("//a[@id='JournalRank_add_btn']")).click();
            if (journalRank.get("编号")!=null&&journalRank.get("编号")!="") {
                webDriver.findElement(By.xpath("//td[@field='no']//input")).sendKeys(journalRank.get("编号"));
            }
            if (journalRank.get("获奖等级")!=null&&journalRank.get("获奖等级")!="") {
                webDriver.findElement(By.xpath("//td[@field='description']//input")).sendKeys(journalRank.get("获奖等级"));
            }
            webDriver.findElement(By.xpath("//a[@id='JournalRank_save_btn']")).click();
            Util.sleep(2000);

            String qiWang =journalRank.get("期望");
            String testResult = webDriver.findElement(By.xpath("//div[@class='panel window messager-window']//div[@class='messager-body panel-body panel-body-noborder window-body']")).getText();
            Assert.assertEquals(testResult, qiWang);
            journalRankTestResult[0][i] = qiWang;
            journalRankTestResult[1][i] = testResult;
            /*if (testResult.equals(qiWang)){
                System.out.println("测试成功");
                p.println("第"+(i+1)+"条测试成功");

            }*/
            webDriver.findElement(By.xpath("//*[text()='确定']")).click();
            if (qiWang.equals("请检查输入信息，查看红色字段")) {
                webDriver.findElement(By.xpath("//a[@id='JournalRank_redo_btn']")).click();
            }
        }
        //excel表格中输入结果数据
        new DataOutToExcel().dataOut(url, journalRankTestResult);

    }
    @Test(invocationCount = 1)
    public void testDeleteProjectFundedByGovernment(){

        webDriver.findElement(By.xpath("//div[@id='_easyui_tree_24']//span[2]")).click();
        Util.sleep(500);
        webDriver.findElement(By.xpath("//div[@id='_easyui_tree_25']//span[5]")).click();

        webDriver.findElement(By.xpath("//div[@id='tabs']//div[@class='datagrid-view2']//div[@class='datagrid-body']//tr[@datagrid-row-index='0']")).click();

        webDriver.findElement(By.xpath("//a[@id='JournalRank_remove_btn']")).click();

        webDriver.findElement(By.xpath("//*[text()='确定']")).click();

        Util.sleep(200);

        webDriver.findElement(By.xpath("//*[text()='确定']")).click();
    }

    @Test(invocationCount = 1)
    public void testEditProjectFundedByGovernment(){
        webDriver.findElement(By.xpath("//div[@id='_easyui_tree_24']//span[2]")).click();
        Util.sleep(500);
        webDriver.findElement(By.xpath("//div[@id='_easyui_tree_25']//span[5]")).click();

        webDriver.findElement(By.xpath("//div[@id='tabs']//div[@class='datagrid-view2']//div[@class='datagrid-body']//tr[@datagrid-row-index='0']")).click();

        webDriver.findElement(By.xpath("//a[@id='JournalRank_edit_btn']")).click();

        webDriver.findElement(By.xpath("//td[@field='no']//input")).sendKeys("4");
        webDriver.findElement(By.xpath("//td[@field='description']//input")).sendKeys("1");


        webDriver.findElement(By.xpath("//a[@id='JournalRank_save_btn']")).click();

    }
}
