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
public class awardsRankTest {
    private WebDriver webDriver;

    @BeforeClass
    public void setUp(){
        webDriver = Util.setUpChrome();
    }

    @Test(invocationCount = 1)
    public void testAdd() throws IOException {
        //获取Excel的测试数据，并保存到map集合中
        ImportExcel importExcel = new ImportExcel();
        String url = System.getProperty("user.dir") + "/src/test/resources/static/testData/basicService/scientificBasic/awardRankTest.xls";
        Map<Integer, Map<String, String>> awardRanks = importExcel.importExcel(url);
        String[][] awardsRankTestResult = new String[2][awardRanks.size()];
        webDriver.findElement(By.xpath("//div[@id='_easyui_tree_24']//span[2]")).click();
        Util.sleep(500);
        webDriver.findElement(By.xpath("//div[@id='_easyui_tree_28']//span[5]")).click();

        for (int i = 0; i < awardRanks.size(); i++) {
            Map<String, String> awardRank = awardRanks.get(i + 1);

            webDriver.findElement(By.xpath("//a[@id='AwardsRank_add_btn']")).click();
            if (awardRank.get("编号")!=null&&awardRank.get("编号")!="") {
                webDriver.findElement(By.xpath("//td[@field='no']//input")).sendKeys(awardRank.get("编号"));
            }
            if (awardRank.get("获奖等级")!=null&&awardRank.get("获奖等级")!="") {
                webDriver.findElement(By.xpath("//td[@field='description']//input")).sendKeys(awardRank.get("获奖等级"));
            }
            webDriver.findElement(By.xpath("//a[@id='AwardsRank_save_btn']")).click();
            Util.sleep(2000);

            String qiWang =awardRank.get("期望");
            String testResult = webDriver.findElement(By.xpath("//div[@class='panel window messager-window']//div[@class='messager-body panel-body panel-body-noborder window-body']")).getText();
            Assert.assertEquals(testResult, qiWang);
            awardsRankTestResult[0][i] = qiWang;
            awardsRankTestResult[1][i] = testResult;
            /*if (testResult.equals(qiWang)){
                System.out.println("测试成功");
                p.println("第"+(i+1)+"条测试成功");

            }*/
            webDriver.findElement(By.xpath("//*[text()='确定']")).click();
            if (qiWang.equals("请检查输入信息，查看红色字段")) {
                webDriver.findElement(By.xpath("//a[@id='AwardsRank_redo_btn']")).click();
            }
        }
        //excel表格中输入结果数据
        new DataOutToExcel().dataOut(url, awardsRankTestResult);

    }
    @Test(invocationCount = 1)
    public void testDeleteProjectFundedByGovernment(){

        webDriver.findElement(By.xpath("//div[@id='_easyui_tree_24']//span[2]")).click();
        Util.sleep(500);
        webDriver.findElement(By.xpath("//div[@id='_easyui_tree_28']//span[5]")).click();

        webDriver.findElement(By.xpath("//div[@id='tabs']//div[@class='datagrid-view2']//div[@class='datagrid-body']//tr[@datagrid-row-index='0']")).click();

        webDriver.findElement(By.xpath("//a[@id='AwardsRank_remove_btn']")).click();

        webDriver.findElement(By.xpath("//*[text()='确定']")).click();

        Util.sleep(200);

        webDriver.findElement(By.xpath("//*[text()='确定']")).click();
    }

    @Test(invocationCount = 1)
    public void testEditProjectFundedByGovernment(){
        webDriver.findElement(By.xpath("//div[@id='_easyui_tree_24']//span[2]")).click();
        Util.sleep(500);
        webDriver.findElement(By.xpath("//div[@id='_easyui_tree_28']//span[5]")).click();

        webDriver.findElement(By.xpath("//div[@id='tabs']//div[@class='datagrid-view2']//div[@class='datagrid-body']//tr[@datagrid-row-index='0']")).click();

        webDriver.findElement(By.xpath("//a[@id='AwardsRank_edit_btn']")).click();

        webDriver.findElement(By.xpath("//td[@field='no']//input")).sendKeys("4");
        webDriver.findElement(By.xpath("//td[@field='description']//input")).sendKeys("1");


        webDriver.findElement(By.xpath("//a[@id='AwardsRank_save_btn']")).click();

    }
}
