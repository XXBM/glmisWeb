package com.glmis.UITest.personnel;

import com.glmis.UITest.Util;
import com.glmis.excel.DataOutToExcel;
import com.glmis.excel.ImportExcel;
import org.openqa.selenium.*;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Map;

/**
 * Created by kene on 17-5-14.
 */
public class EmployeeTest {
    private WebDriver webDriver;

    @BeforeClass
    public void setUp(){
        webDriver = Util.setUpChrome();
    }

    //测试的方法，invocationCount表示此方法运行的次数,默认为1
    @Test(invocationCount = 1)
    public void testAddEmployee() throws IOException {
        //获取Excel的测试数据，并保存到map集合中
        ImportExcel importExcel = new ImportExcel();
        String importUrl = System.getProperty("user.dir") + "/src/test/resources/static/testData/personnel/Employee.xls";
        Map<Integer, Map<String, String>> employees = importExcel.importExcel(importUrl);
        Map<Integer, byte[]> empPictures = importExcel.importExcelPicture(importUrl);
        //使用xpath定位，“//”表示所有标签，“span”代表span标签，并且此标签的文本为“人事信息”， 定位到目录的人事信息栏，并点击
        webDriver.findElement(By.xpath("//span[text()='人事信息']")).click();
        //外层for循环代表excel中的测试数据数目，数目即excel中除标题栏还有几行数据
        Util.sleep(10000);
        //储存结果信息
        String[][] empTestResult = new String[2][employees.size()];

        for (int i = 0; i < employees.size(); i++) {
            //从employees集合后中获取第n个测试数据，即第n个要添加的员工信息, i+1是因为向employees集合中添加第一条员工数据的key为1
            Map<String, String> employee = employees.get(i + 1);
            //xpath定位，定位到Employee的添加按钮，并点击
            webDriver.findElement(By.xpath("//a[@id='Employee_add_linkbutton']")).click();

            //姓名
            //根据id定位到姓名的输入框，并从employee集合中根据key“姓名”获取对应的值输入，如“李四”。
            webDriver.findElement(By.xpath("//input[@id='Employee_name_validatebox']")).sendKeys(employee.get("姓名"));

            Util.sleep(800);

            if (!"".equals(employee.get("性别")) && employee.get("性别") != null) {
                //性别
                //首先定位到下拉框的倒三角符号（a标签），来弹出下拉框的信息
                webDriver.findElement(By.xpath("//form[@id='Actor']/div[@id='Employee_sex_div']/span/span/a")).click();
                //当下拉框弹出后，此下拉框的z-index会变成1100××，据此来定位div。    根据性别（如“男”）来点击相对应的Option
                webDriver.findElement(By.xpath("//div[@class='panel combo-p' and contains(@style,'z-index: 110')]//div[text()='" + employee.get("性别") + "']")).click();
            }

            if (!"".equals(employee.get("部门")) && employee.get("部门") != null) {
                //部门    下拉框
                webDriver.findElement(By.xpath("//form[@id='Actor']/div[@id='Employee_department_div']//a")).click();
                webDriver.findElement(By.xpath("//div[@class='panel combo-p' and contains(@style,'z-index: 110')]//div[text()='" + employee.get("部门") + "']")).click();
            }

            //选择图片   只有i+1张照片
//            if (i + 1 <= employee.size()) {
//                webDriver.findElement(By.xpath("//input[@id='Employee_add_photoId']")).sendKeys(Util.getPicturePath(empPictures.get(i + 1), i));
//                Util.deletePicture(i);
//                //照片选择成功后 点击提示对话框的确定按钮
//                webDriver.findElement(By.xpath("//span[@class='l-btn-text' and text()='确定']")).click();
//            }

            //员工号     输入框
            if(!"".equals(employee.get("职工号")) && employee.get("职工号") != null){
                webDriver.findElement(By.xpath("//input[@id='Employee_no_validatebox']")).sendKeys(employee.get("职工号"));

            }

            if (!"".equals(employee.get("出生日期")) && employee.get("出生日期") != null) {
                //出生日期   时间选择器
                webDriver.findElement(By.xpath("//form[@id='Actor']/div[@id='Employee_birth_div']//a")).click();
                Util.chooseCalendar(employee.get("出生日期"), webDriver);
            }

            if (!"".equals(employee.get("民族")) && employee.get("民族") != null) {
                //民族     下拉框
                webDriver.findElement(By.xpath("//form[@id='Actor']/div[@id='Employee_nation_div']//a")).click();
                webDriver.findElement(By.xpath("//div[@class='panel combo-p' and contains(@style,'z-index: 110')]//div[text()='" + employee.get("民族") + "']")).click();
            }

            //籍贯    文本框
            if(!"".equals(employee.get("籍贯")) && employee.get("籍贯") != null){
                webDriver.findElement(By.xpath("//input[@id='Employee_grandpaBirthPlace_validatebox']")).sendKeys(employee.get("籍贯"));
            }
            //身份证号   文本框
            webDriver.findElement(By.xpath("//input[@id='Employee_idNo_validatebox']")).sendKeys(employee.get("身份证号"));

            //联系电话   文本框
            if(!"".equals(employee.get("联系电话")) && employee.get("联系电话") != null) {
                webDriver.findElement(By.xpath("//input[@id='Employee_mobile_validatebox']")).sendKeys(employee.get("联系电话"));
            }
            if (!"".equals(employee.get("职工类型")) && employee.get("职工类型") != null) {
                //职工类型   下拉框
                webDriver.findElement(By.xpath("//form[@id='Actor']/div[@id='Employee_employmentCategory_div']//a")).click();
                webDriver.findElement(By.xpath("//div[@class='panel combo-p' and contains(@style,'z-index: 110')]//div[text()='" + employee.get("职工类型") + "']")).click();
            }

            if (!"".equals(employee.get("来校时间")) && employee.get("来校时间") != null) {
                //来校时间     时间选择器
                webDriver.findElement(By.xpath("//form[@id='Actor']/div[@id='Employee_dateToSchool_div']//a")).click();
                Util.chooseCalendar(employee.get("来校时间"), webDriver);
            }

            if (!"".equals(employee.get("参加工作")) && employee.get("参加工作") != null) {
                //参加工作时间    时间选择器
                webDriver.findElement(By.xpath("//form[@id='Actor']/div[@id='Employee_dateToWork_div']//a")).click();
                Util.chooseCalendar(employee.get("参加工作"), webDriver);
            }

            if (!"".equals(employee.get("是否华侨")) && employee.get("是否华侨") != null) {
                //是否华侨     下拉框
                webDriver.findElement(By.xpath("//form[@id='Actor']/div[@id='Employee_overseasChineseOrNot_div']//a")).click();
                webDriver.findElement(By.xpath("//div[@class='panel combo-p' and contains(@style,'z-index: 110')]//div[text()='" + employee.get("是否华侨") + "']")).click();
            }

            if (!"".equals(employee.get("退休时间")) && employee.get("退休时间") != null) {

                //退休时间   时间选择器
                webDriver.findElement(By.xpath("//form[@id='Actor']/div[@id='Employee_dateToRetire_div']/span/span/a")).click();
                Util.chooseCalendar(employee.get("退休时间"), webDriver);
            }
            //滚动条下移，相对于滚动条顶部偏移1000个像素
            ((JavascriptExecutor) webDriver).executeScript("$('#personalMessage').scrollTop(1000);");

            //紧急联系人电话    文本框
            if (!"".equals(employee.get("紧急联系人电话")) && employee.get("紧急联系人电话") != null) {
                webDriver.findElement(By.xpath("//input[@id='Employee_emergencyMobile_validatebox']")).sendKeys(employee.get("紧急联系人电话"));
            }
            //家庭住址      文本框
            if (!"".equals(employee.get("家庭住址")) && employee.get("家庭住址") != null) {
                webDriver.findElement(By.xpath("//input[@id='Employee_address_validatebox']")).sendKeys(employee.get("家庭住址"));
            }
            //邮箱     文本框
            if(!"".equals(employee.get("邮箱")) && employee.get("邮箱") != null) {
                webDriver.findElement(By.xpath("//input[@id='Employee_email_validatebox']")).sendKeys(employee.get("邮箱"));
            }
            //QQ     文本框
            if(!"".equals(employee.get("QQ")) && employee.get("QQ") != null) {
                webDriver.findElement(By.xpath("//input[@id='Employee_qq_validatebox']")).sendKeys(employee.get("QQ"));
            }
            //工资编号     文本框
            if(!"".equals(employee.get("工资编号")) && employee.get("工资编号") != null){
                webDriver.findElement(By.xpath("//input[@id='Employee_salaryNo_validatebox']")).sendKeys(employee.get("工资编号"));

            }

            //微信     文本框
            if(!"".equals(employee.get("微信")) && employee.get("微信") != null){
                webDriver.findElement(By.xpath("//input[@id='Employee_weChat_validatebox']")).sendKeys(employee.get("微信"));
            }

            //此处使用css定位，打开党派信息的面板
            webDriver.findElement(By.cssSelector("a.icon-add")).click();
            //党派信息的面板会有一个弹出的动画效果，防止没弹出完全就进行定位的情况
            Util.sleep(500);
            if (!"".equals(employee.get("政治面貌")) && employee.get("政治面貌") != null) {
                //政治面貌    下拉框
                webDriver.findElement(By.xpath("//form[@id='party']/div[@id='Employee_politicalParty_div']/span/span/a")).click();
                Util.sleep(600);
                webDriver.findElement(By.xpath("//div[@class='panel combo-p' and contains(@style,'z-index: 110') and not(contains(@style,'display: none'))]//div[text()='" + employee.get("政治面貌") + "']")).click();
            }

            if (!"".equals(employee.get("入党时间")) && employee.get("入党时间") != null) {
                //入党时间    时间选择器
                webDriver.findElement(By.xpath("//form[@id='party']/div[@id='Employee_pid_div']/span/span/a")).click();
                Util.chooseCalendar(employee.get("入党时间"), webDriver);
            }

            //点击保存按钮保存人事基本信息
            webDriver.findElement(By.xpath("//button[@id='Employee_save_linkbutton']")).click();
            Util.sleep(10000);

            String message = webDriver.findElement(By.xpath("//div[@class='panel window messager-window']/div[2]/div[2]")).getText();

            empTestResult[0][i] = employee.get("期望");
            empTestResult[1][i] = message;

            //保存后弹出对话框，点击确定按钮
            webDriver.findElement(By.xpath("//span[@class='l-btn-text' and text()='确定']")).click();

            Util.sleep(800);
            if ("成功添加新职员".equals(message)) {
                //获取职工列表的页码字段，如 "页 共 2 页"，根据空格分割，获取“2”.
                String maxPage = webDriver.findElement(By.xpath("//div[@id='staffs']//div[@class='datagrid-pager pagination']//td[6]/span")).getText().split(" ")[2];
                //清除页码输入框的内容
                System.out.println("----------------------------------"+maxPage);
                webDriver.findElement(By.xpath("//div[@id='staffs']//div[@class='datagrid-pager pagination']//td[5]/input")).clear();
                //在页码输入框输入最大页码，如“2”
                webDriver.findElement(By.xpath("//div[@id='staffs']//div[@class='datagrid-pager pagination']//td[5]/input")).sendKeys(maxPage);
                //模拟点击“回车”，跳到输入页数
                webDriver.findElement(By.xpath("//div[@id='staffs']//div[@class='datagrid-pager pagination']//td[5]/input")).sendKeys(Keys.ENTER);
                //等待跳转完成
                Util.sleep(8000);
                //根据添加的职工号点击该员工
                webDriver.findElement(By.xpath("//div[@id='staffs']//table//div[text()='" + employee.get("姓名") + "']")).click();
                Util.sleep(500);
                //姓名
                Assert.assertEquals(webDriver.findElement(By.xpath("//input[@id='Employee_name_validatebox']")).getAttribute("value"), employee.get("姓名"));
                //性别
                Assert.assertEquals(webDriver.findElement(By.xpath("//form[@id='Actor']/div[@id='Employee_sex_div']/span/input")).getAttribute("value"), employee.get("性别"));
                //部门
                Assert.assertEquals(webDriver.findElement(By.xpath("//form[@id='Actor']/div[@id='Employee_department_div']/span/input")).getAttribute("value"), employee.get("部门"));
                //员工号
                if(employee.get("职工号")==null){
                    Assert.assertEquals(webDriver.findElement(By.xpath("//input[@id='Employee_no_validatebox']")).getAttribute("value"), "");

                }else{
                    Assert.assertEquals(webDriver.findElement(By.xpath("//input[@id='Employee_no_validatebox']")).getAttribute("value"), employee.get("职工号"));

                }
                //出生年月
                Assert.assertEquals(webDriver.findElement(By.xpath("//form[@id='Actor']/div[@id='Employee_birth_div']/span/input")).getAttribute("value"), employee.get("出生日期"));
                //民族
                Assert.assertEquals(webDriver.findElement(By.xpath("//form[@id='Actor']/div[@id='Employee_nation_div']/span/input")).getAttribute("value"), employee.get("民族"));
                //年龄
                Assert.assertEquals(webDriver.findElement(By.xpath("//input[@id='Employee_age_validatebox']")).getAttribute("value"), Math.round(Double.parseDouble(employee.get("年龄"))) + "");
                //籍贯
                if(employee.get("籍贯")==null){
                    Assert.assertEquals(webDriver.findElement(By.xpath("//input[@id='Employee_grandpaBirthPlace_validatebox']")).getAttribute("value"), "");

                }else{
                    Assert.assertEquals(webDriver.findElement(By.xpath("//input[@id='Employee_grandpaBirthPlace_validatebox']")).getAttribute("value"), employee.get("籍贯"));

                }
                //身份证号
                Assert.assertEquals(webDriver.findElement(By.xpath("//input[@id='Employee_idNo_validatebox']")).getAttribute("value"), employee.get("身份证号"));
                //联系电话
                if(employee.get("联系电话")==null){
                    Assert.assertEquals(webDriver.findElement(By.xpath("//input[@id='Employee_mobile_validatebox']")).getAttribute("value"), "");

                }else{
                    Assert.assertEquals(webDriver.findElement(By.xpath("//input[@id='Employee_mobile_validatebox']")).getAttribute("value"), employee.get("联系电话"));

                }
                //职工类型
                Assert.assertEquals(webDriver.findElement(By.xpath("//form[@id='Actor']/div[@id='Employee_employmentCategory_div']/span/input")).getAttribute("value"), employee.get("职工类型"));
                //来校时间
                System.out.println(employee.get("来校时间"));
                System.out.println(webDriver.findElement(By.xpath("//form[@id='Actor']/div[@id='Employee_dateToSchool_div']/span/input")).getAttribute("value"));

                if(employee.get("来校时间")==null){
                    Assert.assertEquals(String.valueOf(webDriver.findElement(By.xpath("//form[@id='Actor']/div[@id='Employee_dateToSchool_div']/span/input")).getAttribute("value")),"");
                }else{
                    Assert.assertEquals(String.valueOf(webDriver.findElement(By.xpath("//form[@id='Actor']/div[@id='Employee_dateToSchool_div']/span/input")).getAttribute("value")),employee.get("来校时间"));
                }
                //参加工作时间
                if(employee.get("参加工作")==null){
                    Assert.assertEquals(webDriver.findElement(By.xpath("//form[@id='Actor']/div[@id='Employee_dateToWork_div']/span/input")).getAttribute("value"), "");

                }else{
                    Assert.assertEquals(webDriver.findElement(By.xpath("//form[@id='Actor']/div[@id='Employee_dateToWork_div']/span/input")).getAttribute("value"), employee.get("参加工作"));

                }
                //是否华侨
                Assert.assertEquals(webDriver.findElement(By.xpath("//form[@id='Actor']/div[@id='Employee_overseasChineseOrNot_div']/span/input")).getAttribute("value"), employee.get("是否华侨"));
                //退休时间
                if(employee.get("退休时间")==null){
                    Assert.assertEquals(webDriver.findElement(By.xpath("//form[@id='Actor']/div[@id='Employee_dateToRetire_div']/span/input")).getAttribute("value"),"");
                }else{
                    Assert.assertEquals(webDriver.findElement(By.xpath("//form[@id='Actor']/div[@id='Employee_dateToRetire_div']/span/input")).getAttribute("value"), employee.get("退休时间"));
                }
                //紧急联系人电话
                if(employee.get("紧急联系人电话")==null){
                    Assert.assertEquals(webDriver.findElement(By.xpath("//input[@id='Employee_emergencyMobile_validatebox']")).getAttribute("value"),"");
                }else{
                    Assert.assertEquals(webDriver.findElement(By.xpath("//input[@id='Employee_emergencyMobile_validatebox']")).getAttribute("value"), employee.get("紧急联系人电话"));
                }
                //家庭住址
                if(employee.get("家庭住址")==null){
                    Assert.assertEquals(webDriver.findElement(By.xpath("//input[@id='Employee_address_validatebox']")).getAttribute("value"), "");
                }else{
                    Assert.assertEquals(webDriver.findElement(By.xpath("//input[@id='Employee_address_validatebox']")).getAttribute("value"), employee.get("家庭住址"));
                }
                //邮箱
                if(employee.get("邮箱")==null){
                    Assert.assertEquals(webDriver.findElement(By.xpath("//input[@id='Employee_email_validatebox']")).getAttribute("value"), "");

                }else{
                    Assert.assertEquals(webDriver.findElement(By.xpath("//input[@id='Employee_email_validatebox']")).getAttribute("value"), employee.get("邮箱"));

                }
                //QQ
                if(employee.get("QQ")==null){
                    Assert.assertEquals(webDriver.findElement(By.xpath("//input[@id='Employee_qq_validatebox']")).getAttribute("value"), "");
                }else{
                    Assert.assertEquals(webDriver.findElement(By.xpath("//input[@id='Employee_qq_validatebox']")).getAttribute("value"), employee.get("QQ"));
                }
                //工资编号
                if(employee.get("工资编号")==null){
                    Assert.assertEquals(webDriver.findElement(By.xpath("//input[@id='Employee_salaryNo_validatebox']")).getAttribute("value"), "");

                }else{
                    Assert.assertEquals(webDriver.findElement(By.xpath("//input[@id='Employee_salaryNo_validatebox']")).getAttribute("value"), employee.get("工资编号"));

                }
                //微信
                if(employee.get("微信")==null){
                    Assert.assertEquals(webDriver.findElement(By.xpath("//input[@id='Employee_weChat_validatebox']")).getAttribute("value"),"");
                }else{
                    Assert.assertEquals(webDriver.findElement(By.xpath("//input[@id='Employee_weChat_validatebox']")).getAttribute("value"), employee.get("微信"));
                }
                //打开面板
                webDriver.findElement(By.cssSelector("a.icon-add")).click();
                //政治面貌
                if(employee.get("政治面貌")==null){
                    Assert.assertEquals(webDriver.findElement(By.xpath("//form[@id='party']/div[@id='Employee_politicalParty_div']/span/input")).getAttribute("value"), "");
                }else{
                    Assert.assertEquals(webDriver.findElement(By.xpath("//form[@id='party']/div[@id='Employee_politicalParty_div']/span/input")).getAttribute("value"), employee.get("政治面貌"));
                }
                //入党时间
                Assert.assertEquals(webDriver.findElement(By.xpath("//form[@id='party']/div[@id='Employee_pid_div']/span/input")).getAttribute("value"), employee.get("入党时间"));


            }

        }

//        excel表格中输入结果数据
        new DataOutToExcel().dataOut(importUrl, empTestResult);

    }

    @Test(invocationCount = 1)
    public void testDeleteEmployee() {
        webDriver.findElement(By.xpath("//span[text()='人事信息']")).click();
        //等待职工列表加载完成
        Util.sleep(3000);
        //获取记录文本，如“当前显示 1 - 10 条记录 共 11 条记录”，获取“11”
        String maxNumber = webDriver.findElement(By.xpath("//div[@id='staffs']//div[@class='datagrid-pager pagination']//div[@class='pagination-info']")).getText().split(" ")[6];
        //循环的次数记录，因为i的值在换页之后会重新赋值
        int queryNumber = 0;
        for (int i = 0; i < Integer.parseInt(maxNumber); i++) {
            //循环次数限制，到了职工最大记录数目时结束循环
            queryNumber++;
            if (queryNumber == Integer.parseInt(maxNumber) + 1) {
                break;
            }
            //获取每条记录的员工号
            String no = webDriver.findElement(By.xpath("//div[@id='staffs']//table//tr[@datagrid-row-index='" + i + "']/td[@field='no']")).getText();
            if ("10022".equals(no)) {
                //检索到，点击，结束循环
                webDriver.findElement(By.xpath("//div[@id='staffs']//table//tr[@datagrid-row-index='" + i + "']")).click();
                break;
            }
            //职工列表每页显示10条数据(0-9),当i为9,19,29......时要点击下一页
            if ((i + 1) % 10 == 0) {
                webDriver.findElement(By.xpath("//div[@id='staffs']//div[@class='datagrid-pager pagination']//span[@class='l-btn-icon pagination-next']")).click();
                //每一页的row-index为(0-9)，所以重新赋值
                i = -1;
                //让下一页加载完成
                Util.sleep(1000);
            }
        }
        //点击删除按钮
        webDriver.findElement(By.xpath("//a[@id='Employee_delete_linkbutton']")).click();
        //确认对话框点击确定
        webDriver.findElement(By.xpath("//div[@class='panel window messager-window']//div[@class='dialog-button messager-button']//span[text()='确定']")).click();
        Util.sleep(500);
        //弹框点击确定
        webDriver.findElement(By.xpath("//div[@class='panel window messager-window']//div[@class='dialog-button messager-button']//span[text()='确定']")).click();

        Util.sleep(500);
        //清除页码输入框的数字，输入第一页，点击回车。  因为删除完毕后要从头逐条检索已被删除职工的职工号，删除该职工时页数不一定为第一页
        webDriver.findElement(By.xpath("//div[@id='staffs']//div[@class='datagrid-pager pagination']//td[5]/input")).clear();
        webDriver.findElement(By.xpath("//div[@id='staffs']//div[@class='datagrid-pager pagination']//td[5]/input")).sendKeys("1");
        webDriver.findElement(By.xpath("//div[@id='staffs']//div[@class='datagrid-pager pagination']//td[5]/input")).sendKeys(Keys.ENTER);

        Util.sleep(300);
        //获取最大记录
        maxNumber = webDriver.findElement(By.xpath("//div[@id='staffs']//div[@class='datagrid-pager pagination']//div[@class='pagination-info']")).getText().split(" ")[6];
        //记录查询到的职工记录数目
        int queryResult = 0;
        queryNumber = 0;
        for (int i = 0; i < Integer.parseInt(maxNumber); i++) {
            //结束for循环条件
            queryNumber++;
            if (queryNumber == Integer.parseInt(maxNumber) + 1) {
                break;
            }
            //获取职工号
            String no = webDriver.findElement(By.xpath("//div[@id='staffs']//table//tr[@datagrid-row-index='" + i + "']/td[@field='no']")).getText();
            //检索到，记录
            if ("10022".equals(no)) {
                queryResult++;
            }
            if ((i + 1) % 10 == 0) {
                webDriver.findElement(By.xpath("//div[@id='staffs']//div[@class='datagrid-pager pagination']//span[@class='l-btn-icon pagination-next']")).click();
                i = -1;
                Util.sleep(1000);
            }
        }
        //判断检索到的记录是否为0，为0表示职工已被删除
        Assert.assertEquals(queryResult, 0);

    }

    @Test
    public void testQueryEmployee() {
        webDriver.findElement(By.xpath("//span[text()='人事信息']")).click();
        Util.sleep(3000);
        //获取最大记录
        String maxNumber = webDriver.findElement(By.xpath("//div[@id='staffs']//div[@class='datagrid-pager pagination']//div[@class='pagination-info']")).getText().split(" ")[6];
        //查询记录，和循环记录
        int queryResult = 0, queryNumber = 0;
        for (int i = 0; i < Integer.parseInt(maxNumber); i++) {
            //结束条件
            queryNumber++;
            if (queryNumber == Integer.parseInt(maxNumber) + 1) {
                break;
            }
            //检索到，记录加一
            String no = webDriver.findElement(By.xpath("//div[@id='staffs']//table//tr[@datagrid-row-index='" + i + "']/td[@field='no']")).getText();
            if ("10021".equals(no)) {
                queryResult++;
            }
            //下一页
            if ((i + 1) % 10 == 0) {
                webDriver.findElement(By.xpath("//div[@id='staffs']//div[@class='datagrid-pager pagination']//span[@class='l-btn-icon pagination-next']")).click();
                i = -1;
                Util.sleep(1000);
            }
        }
        //如果检索到的记录为1，则通过
        Assert.assertEquals(queryResult, 1);
    }

    @Test
    public void testEditEmployee() {
        webDriver.findElement(By.xpath("//span[text()='人事信息']")).click();
        Util.sleep(3000);
        //获取最大记录数
        String maxNumber = webDriver.findElement(By.xpath("//div[@id='staffs']//div[@class='datagrid-pager pagination']//div[@class='pagination-info']")).getText().split(" ")[6];
        int queryNumber = 0;
        for (int i = 0; i < Integer.parseInt(maxNumber); i++) {
            //结束条件
            queryNumber++;
            if (queryNumber == Integer.parseInt(maxNumber) + 1) {
                break;
            }
            //检索到，点击，并结束循环
            String no = webDriver.findElement(By.xpath("//div[@id='staffs']//table//tr[@datagrid-row-index='" + i + "']/td[@field='no']")).getText();
            if ("10021".equals(no)) {
                webDriver.findElement(By.xpath("//div[@id='staffs']//table//tr[@datagrid-row-index='" + i + "']")).click();
                break;
            }
            //下一页
            if ((i + 1) % 10 == 0) {
                webDriver.findElement(By.xpath("//div[@id='staffs']//div[@class='datagrid-pager pagination']//span[@class='l-btn-icon pagination-next']")).click();
                i = -1;
                Util.sleep(1000);
            }
        }

        //姓名
        //根据id定位到姓名的输入框，并从employee集合中根据key“姓名”获取对应的值输入，如“李四”。
        WebElement name = webDriver.findElement(By.xpath("//input[@id='Employee_name_validatebox']"));
        //输入框需要首先清除数据
        name.clear();
        name.sendKeys("周天");

        Util.sleep(200);

        //性别
        //首先定位到下拉框的倒三角符号（a标签），来弹出下拉框的信息
        webDriver.findElement(By.xpath("//form[@id='Actor']/div[@id='Employee_sex_div']/span/span/a")).click();
        //当下拉框弹出后，此下拉框的z-index会变成1100××，据此来定位div。    根据性别（如“男”）来点击相对应的Option
        webDriver.findElement(By.xpath("//div[@class='panel combo-p' and contains(@style,'z-index: 110')]//div[text()='女']")).click();

        //选择图片   只有i+1张照片
        webDriver.findElement(By.xpath("//input[@id='Employee_add_photoId']")).sendKeys("/home/kene/Desktop/qq.png");

        //照片选择成功后 点击提示对话框的确定按钮
        webDriver.findElement(By.xpath("//span[@class='l-btn-text' and text()='确定']")).click();


        //部门    下拉框
        webDriver.findElement(By.xpath("//form[@id='Actor']/div[@id='Employee_department_div']//a")).click();
        webDriver.findElement(By.xpath("//div[@class='panel combo-p' and contains(@style,'z-index: 110')]//div[text()='工程管理']")).click();

        //员工号     输入框
        WebElement no = webDriver.findElement(By.xpath("//input[@id='Employee_no_validatebox']"));
        no.clear();
        no.sendKeys("20021");

        //出生日期   时间选择器
        webDriver.findElement(By.xpath("//form[@id='Actor']/div[@id='Employee_birth_div']//a")).click();
        Util.chooseCalendar("2012-12-12", webDriver);

        //民族     下拉框
        webDriver.findElement(By.xpath("//form[@id='Actor']/div[@id='Employee_nation_div']//a")).click();
        webDriver.findElement(By.xpath("//div[@class='panel combo-p' and contains(@style,'z-index: 110')]//div[text()='回']")).click();

        //籍贯    文本框
        WebElement grandpaBirthPlace = webDriver.findElement(By.xpath("//input[@id='Employee_grandpaBirthPlace_validatebox']"));
        grandpaBirthPlace.clear();
        grandpaBirthPlace.sendKeys("青岛");

        //身份证号   文本框
        WebElement idNo = webDriver.findElement(By.xpath("//input[@id='Employee_idNo_validatebox']"));
        idNo.clear();
        idNo.sendKeys("370786199901012132");

        //联系电话   文本框
        WebElement mobile = webDriver.findElement(By.xpath("//input[@id='Employee_mobile_validatebox']"));
        mobile.clear();
        mobile.sendKeys("18823123321");

        //职工类型   下拉框
        webDriver.findElement(By.xpath("//form[@id='Actor']/div[@id='Employee_employmentCategory_div']//a")).click();
        webDriver.findElement(By.xpath("//div[@class='panel combo-p' and contains(@style,'z-index: 110')]//div[text()='合同']")).click();

        //来校时间     时间选择器
        webDriver.findElement(By.xpath("//form[@id='Actor']/div[@id='Employee_dateToSchool_div']//a")).click();
        Util.chooseCalendar("1900-01-01", webDriver);

        //参加工作时间    时间选择器
        webDriver.findElement(By.xpath("//form[@id='Actor']/div[@id='Employee_dateToWork_div']//a")).click();
        Util.chooseCalendar("1990-01-12", webDriver);

        //是否华侨     下拉框
        webDriver.findElement(By.xpath("//form[@id='Actor']/div[@id='Employee_overseasChineseOrNot_div']//a")).click();
        webDriver.findElement(By.xpath("//div[@class='panel combo-p' and contains(@style,'z-index: 110')]//div[text()='是']")).click();


        //退休时间   时间选择器
        webDriver.findElement(By.xpath("//form[@id='Actor']/div[@id='Employee_dateToRetire_div']/span/span/a")).click();
        Util.chooseCalendar("2018-11-09", webDriver);
        //滚动条下移，相对于滚动条顶部偏移1000个像素
        ((JavascriptExecutor) webDriver).executeScript("$('#personalMessage').scrollTop(1000);");

        //紧急联系人电话    文本框
        WebElement emergrncyMobile = webDriver.findElement(By.xpath("//input[@id='Employee_emergencyMobile_validatebox']"));
        emergrncyMobile.clear();
        emergrncyMobile.sendKeys("18900000000");

        //家庭住址      文本框
        WebElement address = webDriver.findElement(By.xpath("//input[@id='Employee_address_validatebox']"));
        address.clear();
        address.sendKeys("北京");

        //邮箱     文本框
        WebElement email = webDriver.findElement(By.xpath("//input[@id='Employee_email_validatebox']"));
        email.clear();
        email.sendKeys("123@163.com");

        //QQ     文本框
        WebElement qq = webDriver.findElement(By.xpath("//input[@id='Employee_qq_validatebox']"));
        qq.clear();
        qq.sendKeys("0000000");

        //工资编号     文本框
        WebElement salaryNo = webDriver.findElement(By.xpath("//input[@id='Employee_salaryNo_validatebox']"));
        salaryNo.clear();
        salaryNo.sendKeys("000001");

        //微信     文本框
        WebElement weChat = webDriver.findElement(By.xpath("//input[@id='Employee_weChat_validatebox']"));
        weChat.clear();
        weChat.sendKeys("we2221");


        //此处使用css定位，打开党派信息的面板
        webDriver.findElement(By.cssSelector("a.icon-add")).click();
        //党派信息的面板会有一个弹出的动画效果，防止没弹出完全就进行定位的情况
        Util.sleep(500);
        //政治面貌    下拉框
        webDriver.findElement(By.xpath("//form[@id='party']/div[@id='Employee_politicalParty_div']/span/span/a")).click();
        Util.sleep(100);
        webDriver.findElement(By.xpath("//div[@class='panel combo-p' and contains(@style,'z-index: 110') and not(contains(@style,'display: none'))]//div[text()='中国民主同盟']")).click();

        //入党时间    时间选择器
        webDriver.findElement(By.xpath("//form[@id='party']/div[@id='Employee_pid_div']/span/span/a")).click();
        Util.chooseCalendar("2000-01-18", webDriver);

        //点击保存按钮保存人事基本信息
        webDriver.findElement(By.xpath("//button[@id='Employee_save_linkbutton']")).click();

        String message = webDriver.findElement(By.xpath("//div[@class='panel window messager-window']/div[2]/div[2]")).getText();

        Assert.assertEquals(message, "职员信息修改成功");

        //保存后弹出对话框，点击确定按钮
        webDriver.findElement(By.xpath("//span[@class='l-btn-text' and text()='确定']")).click();


    }

}
