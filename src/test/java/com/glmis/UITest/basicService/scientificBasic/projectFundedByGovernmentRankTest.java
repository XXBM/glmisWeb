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
public class projectFundedByGovernmentRankTest {
    private WebDriver webDriver;

    @BeforeClass
    public void setUp(){
        webDriver = Util.setUpChrome();
    }

    @Test(invocationCount = 1)
    public void testAddprojectFundedByGovernmentRank() throws IOException {

        //获取Excel的测试数据，并保存到map集合中
        ImportExcel importExcel = new ImportExcel();
        String url = System.getProperty("user.dir") + "/src/test/resources/static/testData/basicService/scientificBasic/projectFundedByGovernmentRankTest.xls";
        Map<Integer, Map<String, String>> projectFundedByGovernmentRanks = importExcel.importExcel(url);
        webDriver.findElement(By.xpath("//ul[@id='nav']//li[1]//ul//li[5]//span[@class='tree-hit tree-collapsed']")).click();
        Util.sleep(500);
        webDriver.findElement(By.xpath("//div[@id='_easyui_tree_32']")).click();
        //储存结果信息
        String[][] projectFundedByGovernmentRankTestResult = new String[2][projectFundedByGovernmentRanks.size()];
        //外层for循环代表excel中的测试数据数目，数目即excel中除标题栏还有几行数据
        for (int i = 0; i < projectFundedByGovernmentRanks.size(); i++) {
            //从employees集合后中获取第n个测试数据，即第n个要添加的员工信息, i+1是因为向employees集合中添加第一条员工数据的key为1
            Map<String, String> projectFundedByGovernmentRank = projectFundedByGovernmentRanks.get(i + 1);
            Util.sleep(100);
            webDriver.findElement(By.xpath("//a[@id='ProjectFundedByGovernmentRank_add_btn']")).click();

            webDriver.findElement(By.xpath("//td[@field='no']//input")).sendKeys(projectFundedByGovernmentRank.get("编号"));
            webDriver.findElement(By.xpath("//td[@field='description']//input")).sendKeys(projectFundedByGovernmentRank.get("纵向项目级别"));

            webDriver.findElement(By.xpath("//a[@id='ProjectFundedByGovernmentRank_save_btn']")).click();

            String message = webDriver.findElement(By.xpath("//div[@class='panel window messager-window']//div[@class='messager-body panel-body panel-body-noborder window-body']//div[2]")).getText();
            Assert.assertEquals(message, projectFundedByGovernmentRank.get("期望"));
            projectFundedByGovernmentRankTestResult[0][i] = projectFundedByGovernmentRank.get("期望");
            projectFundedByGovernmentRankTestResult[1][i] = message;
            webDriver.findElement(By.xpath("//*[text()='确定']")).click();
            if (message.equals("请检查输入信息，查看红色字段")) {
                webDriver.findElement(By.xpath("//a[@id='ProjectFundedByGovernmentRank_redo_btn']")).click();
            }

            //}
        }
        //excel表格中输入结果数据
        new DataOutToExcel().dataOut(url, projectFundedByGovernmentRankTestResult);
    }


    @Test(invocationCount = 1)
    public void testDeleteprojectFundedByGovernmentRank() {

        webDriver.findElement(By.xpath("//ul[@id='nav']//li[1]//ul//li[5]//span[@class='tree-hit tree-collapsed']")).click();
        Util.sleep(500);
        webDriver.findElement(By.xpath("//div[@id='_easyui_tree_32']")).click();

        webDriver.findElement(By.xpath("//div[@id='projectFundedByGovernmentRank_div']//div[@class='datagrid-view2']//div[@class='datagrid-body']//tr[@datagrid-row-index='4']")).click();

        webDriver.findElement(By.xpath("//a[@id='ProjectFundedByGovernmentRank_remove_btn']")).click();

        webDriver.findElement(By.xpath("//*[text()='确定']")).click();

        Util.sleep(100);

        webDriver.findElement(By.xpath("//*[text()='确定']")).click();
    }

    @Test(invocationCount = 1)
    public void testEditprojectFundedByGovernmentRank() {

        webDriver.findElement(By.xpath("//ul[@id='nav']//li[1]//ul//li[5]//span[@class='tree-hit tree-collapsed']")).click();
        Util.sleep(500);
        webDriver.findElement(By.xpath("//div[@id='_easyui_tree_32']")).click();

        webDriver.findElement(By.xpath("//div[@id='projectFundedByGovernmentRank_div']//div[@class='datagrid-view2']//div[@class='datagrid-body']//tr[@datagrid-row-index='4']")).click();

        webDriver.findElement(By.xpath("//a[@id='ProjectFundedByGovernmentRank_edit_btn']")).click();

        webDriver.findElement(By.xpath("//td[@field='no']//input")).sendKeys("1");
        webDriver.findElement(By.xpath("//td[@field='description']//input")).sendKeys("硕士");

        webDriver.findElement(By.xpath("//a[@id='ProjectFundedByGovernmentRank_save_btn']")).click();
    }
}
