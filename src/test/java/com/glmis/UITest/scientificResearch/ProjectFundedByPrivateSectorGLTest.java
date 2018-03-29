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
 * Created by inkskyu428 on 17-5-23.
 */
public class ProjectFundedByPrivateSectorGLTest {
    private WebDriver webDriver;

    @BeforeClass
    public void setUp(){
        webDriver = Util.setUpChrome();
    }
    @Test(invocationCount = 1)
    public void testEditProjectFundedByPrivateSector(){
        webDriver.findElement(By.xpath("//span[text()='科研信息']")).click();

        webDriver.findElement(By.xpath("//div[@id='employee_scientificResearch_div']//div[@class='datagrid-body']//tr[@datagrid-row-index='0']")).click();

        webDriver.findElement(By.xpath("//div[@id='scientificResearch_tabs']//div[@class='tabs-wrap']//ul[@class='tabs']//li[1]//a//span[2]")).click();

        webDriver.findElement(By.xpath("//div[@id='ProjectFundedByPrivateSector_tab']//div[@class='datagrid-body']//tr[@datagrid-row-index='0']")).click();

        webDriver.findElement(By.xpath("//a[@id='ProjectFundedByPrivateSector_edit_btn']")).click();



        webDriver.findElement(By.xpath("//td[@field='checkingStatus']//span[@class='textbox-addon textbox-addon-right']")).click();
        webDriver.findElement(By.xpath("//div[@class='panel combo-p' and contains(@style,'z-index: 1100')]//div[text()='通过审核']")).click();

        webDriver.findElement(By.xpath("//a[@id='ProjectFundedByPrivateSector_save_btn']")).click();

    }
}
