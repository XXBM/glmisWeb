package com.glmis.UITest.scientificResearch;

import com.glmis.UITest.TestConstants;
import com.glmis.UITest.Util;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

/**
 * Created by inkskyu428 on 17-5-20.
 */
public class AwardGLTest {
    private WebDriver webDriver;

    @BeforeClass
    public void setUp(){
        webDriver = Util.setUpChrome();
    }

    @Test(invocationCount = 1)
    public void testEditAward(){
        webDriver.findElement(By.xpath("//span[text()='科研信息']")).click();
        Util.sleep(5000);
        webDriver.findElement(By.xpath("//div[@id='employee_scientificResearch_div']//div[@class='datagrid-view']//div[@class='datagrid-body']//tr[@datagrid-row-index='0']")).click();
        Util.sleep(5000);
        webDriver.findElement(By.xpath("//div[@id='scientificResearch_tabs']//div[@class='tabs-wrap']//ul[@class='tabs']//li[4]//a//span[2]")).click();
        Util.sleep(5000);
        webDriver.findElement(By.xpath("//div[@id='Awards_tab']//div[@class='datagrid-body']//tr[@datagrid-row-index='0']")).click();

        webDriver.findElement(By.xpath("//a[@id='Awards_edit_btn']")).click();

        webDriver.findElement(By.xpath("//td[@field='checkingStatus']//span[@class='textbox-addon textbox-addon-right']")).click();
        webDriver.findElement(By.xpath("//div[@class='panel combo-p' and contains(@style,'z-index: 1100')]//div[text()='通过审核']")).click();

        webDriver.findElement(By.xpath("//a[@id='Awards_save_btn']")).click();

    }
}
