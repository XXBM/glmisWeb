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
 * Created by inkskyu428 on 17-6-19.
 */
public class PositionRankTest {
    private WebDriver webDriver;

    @BeforeClass
    public void setUp(){
        webDriver = Util.setUpChrome();
    }

    @Test(invocationCount = 1)
    public void testAddPositionRank() throws IOException {

        //获取Excel的测试数据，并保存到map集合中
        ImportExcel importExcel = new ImportExcel();
        String url = System.getProperty("user.dir") + "/src/test/resources/static/testData/basicService/personnelBasic/positionRankTest.xls";
        Map<Integer, Map<String, String>> positionRanks = importExcel.importExcel(url);
        webDriver.findElement(By.xpath("//span[@class='tree-hit tree-collapsed']")).click();
        Util.sleep(5000);
        webDriver.findElement(By.xpath("//span[text()='行政级别维护']")).click();
        //储存结果信息
        String[][] positionRankTestResult = new String[2][positionRanks.size()];
        //外层for循环代表excel中的测试数据数目，数目即excel中除标题栏还有几行数据
        for (int i = 0; i < positionRanks.size(); i++) {
            //从employees集合后中获取第n个测试数据，即第n个要添加的员工信息, i+1是因为向employees集合中添加第一条员工数据的key为1
            Map<String, String> positionRank = positionRanks.get(i + 1);
            Util.sleep(1000);
            webDriver.findElement(By.xpath("//a[@id='positionRank_add_btn']")).click();

            if (positionRank.get("编号")!=null&&positionRank.get("编号")!="") {
                webDriver.findElement(By.xpath("//td[@field='no']//input")).sendKeys(positionRank.get("编号"));
            }
            if (positionRank.get("行政级别")!=null&&positionRank.get("行政级别")!="") {
                webDriver.findElement(By.xpath("//td[@field='description']//input")).sendKeys(positionRank.get("行政级别"));
            }
            Util.sleep(5000);
            webDriver.findElement(By.xpath("//a[@id='positionRank_save_btn']")).click();
            Util.sleep(5000);
            String message = webDriver.findElement(By.xpath("//div[@class='panel window messager-window']//div[@class='messager-body panel-body panel-body-noborder window-body']//div[2]")).getText();
            Assert.assertEquals(message, positionRank.get("期望"));
            positionRankTestResult[0][i] = positionRank.get("期望");
            positionRankTestResult[1][i] = message;
            webDriver.findElement(By.xpath("//*[text()='确定']")).click();
            if (message.equals("请检查输入信息，查看红色字段")) {
                webDriver.findElement(By.xpath("//a[@id='positionRank_redo_btn']")).click();
            }

            //}
        }
        //excel表格中输入结果数据
        new DataOutToExcel().dataOut(url, positionRankTestResult);
    }
}
