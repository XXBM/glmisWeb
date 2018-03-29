package com.glmis.UITest.personnel;

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
 * Created by inkskyu428 on 17-5-17.
 */
public class RewardTest {
    private WebDriver webDriver;

    @BeforeClass
    public void setUp(){
        webDriver = Util.setUpChrome();
    }
    @Test(invocationCount = 1)
    public void testAddReward()throws IOException {

        //获取Excel的测试数据，并保存到map集合中
        ImportExcel importExcel = new ImportExcel();
        //String url =  FileSystemView.getFileSystemView().getHomeDirectory().getPath() + "/Desktop/RewardTest.xls";
        String url = System.getProperty("user.dir") + "/src/test/resources/static/testData/personnel/RewardTest.xls";
        Map<Integer, Map<String, String>> rewards = importExcel.importExcel(url);

        webDriver.findElement(By.xpath("//span[text()='人事信息']")).click();
        Util.sleep(10000);
        webDriver.findElement(By.xpath("//div[@id='staffs']//div[@class='datagrid-body']//tr[@datagrid-row-index='0']")).click();
        Util.sleep(10000);
        webDriver.findElement(By.xpath("//span[text()='奖励记录']")).click();
        Util.sleep(5000);
//        String resultUrl =  FileSystemView.getFileSystemView().getHomeDirectory().getPath() + "/Desktop/reward_testResult.txt";
//        File file =new File(resultUrl);
//        FileOutputStream testFile =new FileOutputStream(file);
//        PrintStream printStream =new PrintStream(testFile);
        //储存结果信息
        String[][] rewardTestResult = new String[2][rewards.size()];
        //外层for循环代表excel中的测试数据数目，数目即excel中除标题栏还有几行数据
        for (int i = 0; i < rewards.size(); i++) {
            //从employees集合后中获取第n个测试数据，即第n个要添加的员工信息, i+1是因为向employees集合中添加第一条员工数据的key为1
            Map<String, String> reward = rewards.get(i + 1);

            webDriver.findElement(By.xpath("//a[@id='Reward_add_btn']")).click();

            //datebox输入框版
            //webDriver.findElement(By.xpath("//td[@field='grantedDate']//input[@class='textbox-text validatebox-text validatebox-invalid textbox-prompt']")).sendKeys(reward.get("授予时间"));
            //时间选择器版
            if (reward.get("授予时间")!=null&&reward.get("授予时间")!="") {
                webDriver.findElement(By.xpath("//td[@field='grantedDate']//span[@class='textbox-addon textbox-addon-right']//a")).click();
                Util.chooseCalendar(reward.get("授予时间"), webDriver);
            }
            if(reward.get("授予时间")!=null&&reward.get("授予时间")!=""){
                webDriver.findElement(By.xpath("//td[@field='name']//input")).sendKeys(reward.get("名称"));

            }
            if(reward.get("授予时间")!=null&&reward.get("授予时间")!=""){
                webDriver.findElement(By.xpath("//td[@field='rank']//input")).sendKeys(reward.get("级别"));

            }

            if(reward.get("位次")!=null&&reward.get("位次")!=""){
                webDriver.findElement(By.xpath("//td[@field='precedence']//input[@class='textbox-text validatebox-text validatebox-invalid textbox-prompt']")).sendKeys(reward.get("位次"));

            }
            if(reward.get("参加人数")!=null&&reward.get("参加人数")!=""){
                webDriver.findElement(By.xpath("//td[@field='numOfParticipants']//input[@class='textbox-text validatebox-text validatebox-invalid textbox-prompt']")).sendKeys(reward.get("参加人数"));

            }

            if(reward.get("授予机关")!=null&&reward.get("授予机关")!=""){
                webDriver.findElement(By.xpath("//td[@field='grantedBy']//input")).sendKeys(reward.get("授予机关"));

            }

            webDriver.findElement(By.xpath("//a[@id='Reward_save_btn']")).click();
            //if(doesWebElementExist(webDriver,By.xpath("//div[@class='panel window messager-window']"))) {
            String message = webDriver.findElement(By.xpath("//div[@class='panel window messager-window']//div[@class='messager-body panel-body panel-body-noborder window-body']//div[2]")).getText();
            Assert.assertEquals(message, reward.get("期望"));
            rewardTestResult[0][i] = reward.get("期望");
            rewardTestResult[1][i] = message;
//            if (reward.get("期望").equals(input))
//                printStream.println("第"+(i+1)+"条"+"测试成功");
//            else
//                printStream.println("第"+(i+1)+"条"+"测试失败");
            webDriver.findElement(By.xpath("//*[text()='确定']")).click();
            if (message.equals("请检查输入信息，查看红色字段")) {
                webDriver.findElement(By.xpath("//a[@id='Reward_cancel_btn']")).click();
            }

            //}
        }
        //excel表格中输入结果数据
        new DataOutToExcel().dataOut(url, rewardTestResult);
    }


    @Test(invocationCount = 1)
    public void testDeleteEducationQualification(){

        webDriver.findElement(By.xpath("//span[text()='人事信息']")).click();

        webDriver.findElement(By.xpath("//div[@id='staffs']//div[@class='datagrid-body']//tr[@datagrid-row-index='0']")).click();

        webDriver.findElement(By.xpath("//span[text()='奖励记录']")).click();

        webDriver.findElement(By.xpath("//div[@id='information']//div[@class='datagrid-body']//tr[@datagrid-row-index='1']")).click();

        webDriver.findElement(By.xpath("//a[@id='Reward_delete_btn']")).click();

        webDriver.findElement(By.xpath("//*[text()='确定']")).click();

        Util.sleep(100);

        webDriver.findElement(By.xpath("//*[text()='确定']")).click();
    }

    @Test(invocationCount = 1)
    public void testEditEducationQualification(){
        webDriver.findElement(By.xpath("//span[text()='人事信息']")).click();

        webDriver.findElement(By.xpath("//div[@id='staffs']//div[@class='datagrid-body']//tr[@datagrid-row-index='0']")).click();

        webDriver.findElement(By.xpath("//span[text()='奖励记录']")).click();

        webDriver.findElement(By.xpath("//div[@id='information']//div[@class='datagrid-body-inner']//tr[@id='datagrid-row-r22-1-0']")).click();

        webDriver.findElement(By.xpath("//a[@id='Reward_update_btn']")).click();

        webDriver.findElement(By.xpath("//td[@field='grantedDate']//span[@class='textbox-addon textbox-addon-right']")).click();
        Util.sleep(200);
        webDriver.findElement(By.xpath("//div[@class='panel combo-p' and contains(@style,'z-index: 1100')]//td[@abbr='2017,5,18']")).click();

        webDriver.findElement(By.xpath("//td[@field='name']//input")).sendKeys("3");
        webDriver.findElement(By.xpath("//td[@field='rank']//input")).sendKeys("3");

        webDriver.findElement(By.xpath("//td[@field='precedence']//span[@class='textbox-addon textbox-addon-right']//a[@class='textbox-icon spinner-arrow']//a[@class='spinner-arrow-up']")).click();
        webDriver.findElement(By.xpath("//td[@field='numOfParticipants']//span[@class='textbox-addon textbox-addon-right']//a[@class='textbox-icon spinner-arrow']//a[@class='spinner-arrow-up']")).click();

        webDriver.findElement(By.xpath("//td[@field='grantedBy']//input")).sendKeys("3");

        webDriver.findElement(By.xpath("//a[@id='Reward_save_btn']")).click();
    }
}
