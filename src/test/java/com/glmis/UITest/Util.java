package com.glmis.UITest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;



/**
 * Created by kene on 17-5-14.
 */
public class Util {


    public static WebDriver setUpChrome() {
        System.setProperty("webdriver.chrome.driver", TestConstants.chromeDriverForWindows);
        WebDriver webDriver = new ChromeDriver();
        webDriver.manage().window().maximize();
        webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        webDriver.get("http://localhost:8080/glmis/login");
        //根据id检索用户名的输入框，并输入值
        webDriver.findElement(By.id("username")).sendKeys("1");
        //根据id检索密码的输入框，并输入值
        webDriver.findElement(By.id("password")).sendKeys("1");
        //当用户名和密码输入完成后，根据id检索到登录按钮，并单击，进行登录
        sleep(8000);
        webDriver.findElement(By.id("submit")).click();
        return webDriver;
    }



    public static void sleep(Integer millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void chooseCalendar(String birth, WebDriver webDriver) {
            //以“-”分割日期
            String[] addDate = birth.split("-");//2017 04 03
            //点击时间选择器的标题  如：五月 2017
            webDriver.findElement(By.xpath("//div[@class='panel combo-p' and contains(@style,'z-index: 110')" +
                    " and not(contains(@style,'display: none'))]//div[@class='calendar-title']/span[@class='calendar-text']")).click();
            //定位到年份的输入框
            WebElement webElement = webDriver.findElement(By.xpath("//div[@class='panel combo-p' and contains(@style,'z-index: 110')" +
                    " and not(contains(@style,'display: none'))]//div[@class='calendar-menu-year-inner']//input"));
            //清空内容
            webElement.clear();
            //输入要选择的年份
            webElement.sendKeys(addDate[0]);
            //点击要选择的月份
            webDriver.findElement(By.xpath("//div[@class='panel combo-p' and contains(@style,'z-index: 110')" +
                    " and not(contains(@style,'display: none'))]//div[@class='calendar-menu-month-inner']" +
                    "//td[@abbr='" + Integer.parseInt(addDate[1]) + "']")).click();
            //改成时间选择器的abbr属性格式    2017,5,1
            String chooseDate = "";
            for (int j = 0; j < addDate.length; j++) {
                chooseDate += Integer.parseInt(addDate[j]) + "";
                if (j + 1 != addDate.length) {
                    chooseDate += ",";
                }
            }
            //点击要选择的日期
            webDriver.findElement(By.xpath("//div[@class='panel combo-p' and contains(@style,'z-index: 110')" +
                    " and not(contains(@style,'display: none'))]//table[@class='calendar-dtable']//td[@abbr='" + chooseDate + "']")).click();
    }

    public static String getPicturePath(byte[] picture, Integer i) {
        String url = FileSystemView.getFileSystemView().getHomeDirectory().getPath() + "/Desktop/testAddPicture"+i+".png";
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(url);
            fileOutputStream.write(picture);
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return url;
    }


    public static void deletePicture(Integer i) {
        File file = new File(FileSystemView.getFileSystemView().getHomeDirectory().getPath() + "/Desktop/testAddPicture"+i+".png");
        file.delete();
    }

}
