package com.glmis.UITest.personnel;

import com.glmis.UITest.Util;
import com.glmis.excel.DataOutToExcel;
import com.glmis.excel.ImportExcel;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Map;

/**
 * Created by kene on 17-5-20.
 */
public class EmpAssPractisingCertificationTest {
    private WebDriver webDriver;

    @BeforeClass
    public void setUp(){
        webDriver = Util.setUpChrome();
    }

    @Test(invocationCount = 1)
    public void testAdd() throws IOException {
        //获取Excel的测试数据，并保存到map集合中
        ImportExcel importExcel = new ImportExcel();
        String url = System.getProperty("user.dir") + "/src/test/resources/static/testData/personnel/practistingcertificationTest.xls";
        Map<Integer, Map<String, String>> empasspractisingcertifications = importExcel.importExcel(url);
        Map<Integer, byte[]> Pictures = importExcel.importExcelPicture(url);
        webDriver.findElement(By.xpath("//span[text()='人事信息']")).click();
        Util.sleep(10000);
        webDriver.findElement(By.xpath("//div[@id='staffs']//div[@class='datagrid-body']//tr[@datagrid-row-index='0']")).click();
        Util.sleep(10000);
        webDriver.findElement(By.xpath("//span[text()='执业资格']")).click();
        Util.sleep(5000);
        String[][] ResultOfTest = new String[2][empasspractisingcertifications.size()];

        for(int i=0;i<empasspractisingcertifications.size();i++){
            Map<String, String> empasspractisingcertification = empasspractisingcertifications.get(i + 1);

            webDriver.findElement(By.xpath("//a[@id='PractisingCertification_add_linkbutton']")).click();

            ((JavascriptExecutor) webDriver).executeScript("$('#qualificationdlg').scrollTop(-1000);");

            if (empasspractisingcertification.get("证书名称")!=null&&empasspractisingcertification.get("证书名称")!="") {
                webDriver.findElement(By.xpath("//div[@class='panel window']/div[@id='qualificationdlg']/form[@id='qualificationfm']" +
                        "/div[@id='PractisingCertification_name_div']/span/span/a")).click();
                Util.sleep(100);
                webDriver.findElement(By.xpath("//div[@class='panel combo-p' and contains(@style,'z-index: 1100') and not(contains(@style,'display: none'))]//div[text()='" + empasspractisingcertification.get("证书名称") + "']")).click();
            }
            //注册编号
            webDriver.findElement(By.xpath("//div[@class='panel window']/div[@id='qualificationdlg']/form[@id='qualificationfm']" +
                    "/div[@id='PractisingCertification_registerNo_div']/input[@id='PractisingCertification_registerNo_validatebox']")).clear();
            if(empasspractisingcertification.get("注册编号")!=null&&empasspractisingcertification.get("注册编号")!="") {

                webDriver.findElement(By.xpath("//div[@class='panel window']/div[@id='qualificationdlg']/form[@id='qualificationfm']" +
                        "/div[@id='PractisingCertification_registerNo_div']/input[@id='PractisingCertification_registerNo_validatebox']")).sendKeys(empasspractisingcertification.get("注册编号"));
            }
            //证书编号
            webDriver.findElement(By.xpath("//div[@class='panel window']/div[@id='qualificationdlg']/form[@id='qualificationfm']" +
                    "/div[@id='PractisingCertification_certificateNo_div']/input[@id='PractisingCertification_certificateNo_validatebox']")).clear();
            if(empasspractisingcertification.get("证书编号")!=null&&empasspractisingcertification.get("证书编号")!="") {

                webDriver.findElement(By.xpath("//div[@class='panel window']/div[@id='qualificationdlg']/form[@id='qualificationfm']" +
                        "/div[@id='PractisingCertification_certificateNo_div']/input[@id='PractisingCertification_certificateNo_validatebox']")).sendKeys(empasspractisingcertification.get("证书编号"));
            }
                //移动弹框的滚动条
            ((JavascriptExecutor) webDriver).executeScript("$('#qualificationdlg').scrollTop(1000);");

            //聘用企业
            webDriver.findElement(By.xpath("//div[@class='panel window']/div[@id='qualificationdlg']/form[@id='qualificationfm']" +
                    "/div[@id='PractisingCertification_servedOrganization_div']/input[@id='PractisingCertification_servedOrganization_validatebox']")).clear();
            if(empasspractisingcertification.get("聘用企业1")!=null&&empasspractisingcertification.get("聘用企业1")!="") {
                webDriver.findElement(By.xpath("//div[@class='panel window']/div[@id='qualificationdlg']/form[@id='qualificationfm']" +
                        "/div[@id='PractisingCertification_servedOrganization_div']/input[@id='PractisingCertification_servedOrganization_validatebox']")).sendKeys(empasspractisingcertification.get("聘用企业1"));
            }

            if (empasspractisingcertification.get("获得时间")!=null&&empasspractisingcertification.get("获得时间")!="") {
                webDriver.findElement(By.xpath("//div[@class='panel window']/div[@id='qualificationdlg']/form[@id='qualificationfm']" +
                        "/div[@id='PractisingCertification_issuedDate_div']/span/span/a")).click();
                Util.chooseCalendar(empasspractisingcertification.get("获得时间"), webDriver);
            }
            //下拉框
            if (empasspractisingcertification.get("发证机关")!=null&&empasspractisingcertification.get("发证机关")!="") {
                webDriver.findElement(By.xpath("//div[@class='panel window']/div[@id='qualificationdlg']/form[@id='qualificationfm']" +
                        "/div[@id='PractisingCertification_issuer_div']/span/span/a")).click();
                Util.sleep(100);
                webDriver.findElement(By.xpath("//div[@class='panel combo-p' and contains(@style,'z-index: 1100') and not(contains(@style,'display: none'))]//div[text()='" + empasspractisingcertification.get("发证机关") + "']")).click();
            }
            //图片
            if (i + 1 < empasspractisingcertification.size()) {
                //webDriver.findElement(By.xpath("//input[@id='Employee_add_photoId']")).sendKeys(Util.getPicturePath(Pictures.get(1)));
                webDriver.findElement(By.xpath("//div[@class='panel window']/div[@id='qualificationdlg']//form[@id='quaFile']" +
                        "//input[@name='quaImgName']")).sendKeys(Util.getPicturePath(Pictures.get(i+1),  i));
                Util.deletePicture(i);
                //照片选择成功后 点击提示对话框的确定按钮
                webDriver.findElement(By.xpath("//span[@class='l-btn-text' and text()='确定']")).click();
            }

            webDriver.findElement(By.xpath("//div[@class='panel window']//span[@class='l-btn-text' and text()='保存']")).click();
            Util.sleep(5000);
            String message = webDriver.findElement(By.xpath("//div[@class='panel window messager-window']//div[@class='messager-body panel-body panel-body-noborder window-body']//div[2]")).getText();
            Assert.assertEquals(message, empasspractisingcertification.get("期望"));
            ResultOfTest[0][i] = empasspractisingcertification.get("期望");
            ResultOfTest[1][i] = message;
            webDriver.findElement(By.xpath("//*[text()='确定']")).click();
            if (message.equals("添加失败,请检查红色字段")) {
                webDriver.findElement(By.xpath("//a[@id='PractisingCertification_cancel_linkbutton']")).click();
            }
        }
        Util.sleep(5000);
        webDriver.findElement(By.xpath("//a[@id='PractisingCertification_cancel_linkbutton']")).click();

        new DataOutToExcel().dataOut(url, ResultOfTest);
    }

    @Test
    public void testDelete(){
        webDriver.findElement(By.xpath("//span[text()='人事信息']")).click();
        webDriver.findElement(By.xpath("//div[@id='staffs']//div[@class='datagrid-body']//tr[@datagrid-row-index='0']")).click();
        webDriver.findElement(By.xpath("//span[text()='执业资格']")).click();

        webDriver.findElement(By.xpath("//div[@id='information']//div[@class='panel' and not(contains(@style,'display: none'))]//div[@class='datagrid-view1']/div[@class='datagrid-body']//tr[@datagrid-row-index='1']")).click();

        webDriver.findElement(By.xpath("//a[@id='PractisingCertification_remove_linkbutton']")).click();

        webDriver.findElement(By.xpath("//div[@class='panel window messager-window']/div[@class='dialog-button messager-button']//span[text()='确定']")).click();
        webDriver.findElement(By.xpath("//span[text()='确定']")).click();

    }

    @Test
    public void testEdit(){
        webDriver.findElement(By.xpath("//span[text()='人事信息']")).click();
        webDriver.findElement(By.xpath("//div[@id='staffs']//div[@class='datagrid-body']//tr[@datagrid-row-index='0']")).click();
        webDriver.findElement(By.xpath("//span[text()='执业资格']")).click();

        webDriver.findElement(By.xpath("//div[@id='information']//div[@class='panel' and not(contains(@style,'display: none'))]//div[@class='datagrid-view1']/div[@class='datagrid-body']//tr[@datagrid-row-index='1']")).click();

        webDriver.findElement(By.xpath("//a[@id='PractisingCertification_edit_linkbutton']")).click();

        //证书名称
        webDriver.findElement(By.xpath("//div[@class='panel window']/div[@id='qualificationdlg']/form[@id='qualificationfm']" +
                "/div[@id='PractisingCertification_name_div']/span/span/a")).click();
        webDriver.findElement(By.xpath("//div[@class='panel combo-p' and contains(@style,'z-index: 1100') and not(contains(@style,'display: none'))]//div[text()='注册造价工程师']")).click();
        //注册编号
        webDriver.findElement(By.xpath("//div[@class='panel window']/div[@id='qualificationdlg']/form[@id='qualificationfm']" +
                "/div[@id='PractisingCertification_registerNo_div']/input[@id='PractisingCertification_registerNo_validatebox']")).sendKeys("zc2");

        //证书编号
        webDriver.findElement(By.xpath("//div[@class='panel window']/div[@id='qualificationdlg']/form[@id='qualificationfm']" +
                "/div[@id='PractisingCertification_certificateNo_div']/input[@id='PractisingCertification_certificateNo_validatebox']")).sendKeys("zs2");
        //移动弹框的滚动条
        ((JavascriptExecutor) webDriver).executeScript("$('#qualificationdlg').scrollTop(1000);");

        //聘用企业
        webDriver.findElement(By.xpath("//div[@class='panel window']/div[@id='qualificationdlg']/form[@id='qualificationfm']" +
                "/div[@id='PractisingCertification_servedOrganization_div']/input[@id='PractisingCertification_servedOrganization_validatebox']")).sendKeys("py2");
        //获得时间
        webDriver.findElement(By.xpath("//div[@class='panel window']/div[@id='qualificationdlg']/form[@id='qualificationfm']" +
                "/div[@id='PractisingCertification_issuedDate_div']/span/span/a")).click();

        webDriver.findElement(By.xpath("//div[@class='panel combo-p' and contains(@style,'z-index: 1100') and not(contains(@style, 'display: none'))]" +
                "//div[@class='datebox-button']//a[@class='datebox-button-a']")).click();
        //发证机关
        webDriver.findElement(By.xpath("//div[@class='panel window']/div[@id='qualificationdlg']/form[@id='qualificationfm']" +
                "/div[@id='PractisingCertification_issuer_div']/span/span/a")).click();
        webDriver.findElement(By.xpath("//div[@class='panel combo-p' and contains(@style,'z-index: 1100') and not(contains(@style,'display: none'))]//div[text()='发证机构2']")).click();
        //文件
        webDriver.findElement(By.xpath("//div[@class='panel window']/div[@id='qualificationdlg']//form[@id='quaFile']" +
                "//input[@name='quaImgName']")).sendKeys("/home/kene/Desktop/sky2.png");

        webDriver.findElement(By.xpath("//span[@class='l-btn-text' and text()='确定']")).click();

        webDriver.findElement(By.xpath("//div[@class='panel window']//span[@class='l-btn-text' and text()='保存']")).click();

        webDriver.findElement(By.xpath("//div[@class='panel window messager-window']//span[text()='确定']")).click();


    }

}
