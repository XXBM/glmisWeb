package com.glmis.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * Created by xuling on 2016/11/30.
 * 这个主要对应菜单的url调转
 */

@Controller
public class MenuController {
    //菜单页面
    @RequestMapping("/index")
    public String index() {
        return "index";
    }
    @RequestMapping("/bookRoom")
    public String bookRoom(){
        return "bookRoom/bookRoom";
    }
    @RequestMapping("/seeBookedRoom")
    public String seeBookedRoom(){
        return "bookRoom/seeBookedRoom";
    }
    //人事
    @RequestMapping("/personnel")
    public String rsgl(){
        return "personnel/rsgl";
    }
    @RequestMapping("/tjcx")
    public String tjcx(){
        return "personnel/tjcx";
    }
    //科研
    @RequestMapping("/scientificResearch")
    public String kygl(){
        return "scientificResearch/kygl";
    }
    @RequestMapping("/myScientificResearch")
    public String myKY(){
        return "scientificResearch/myKY";
    }
    @RequestMapping(value = "/manager")
    public String manager(){return "attendance/manager3";}
    @RequestMapping(value = "/user")
    public String managerUser(){return "attendance/user";}
    @RequestMapping(value = "/managerAttendanceSummary")
    public String managerSummary(){return "attendance/managerAttendanceSummary";}
    //考勤管理
    @RequestMapping(value = "/attendanceManager")
    public String attendanceManager(){
        return "attendance/attendanceManager";
    }
    @RequestMapping(value = "/attendanceQuery")
    public String attendanceQuery(){
        return "attendance/attendanceQuery";
    }
    @RequestMapping(value = "/attendanceExportExcel")
    public String attendanceExportExcel(){
        return "attendance/attendanceExportExcel";
    }
    @RequestMapping(value = "/leaveManager")
    public String leaveManager(){
        return "attendance/leaveManager";
    }
    //基础信息维护
        //人事信息维护
    @RequestMapping(value = "/educationQualification")
    public String educationQualification(){return "basicService/personnelBasic/educationQualification";}
    @RequestMapping(value = "/academicDegree")
    public String academicDegree(){return "basicService/personnelBasic/academicDegree";}
    @RequestMapping(value = "/professionalTitle")
    public String professionalTitle(){return "basicService/personnelBasic/professionalTitle";}
    @RequestMapping(value = "/post")
    public String post(){return "basicService/personnelBasic/post";}
    @RequestMapping(value = "/practisingCertification")
    public String practisingCertification(){return "basicService/personnelBasic/practisingCertification";}
    @RequestMapping(value = "/organization")
    public String organization(){return "basicService/personnelBasic/organization";}
    @RequestMapping(value = "/department")
    public String department(){
        return "basicService/personnelBasic/department";
    }
    @RequestMapping(value = "/politicalParty")
    public String politicalParty(){
        return "basicService/personnelBasic/politicalParty";
    }
    @RequestMapping(value = "/nation")
    public String nation(){
        return "basicService/personnelBasic/nation";
    }
    @RequestMapping(value = "/employmentCategory")
    public String employmentCategory(){
        return "basicService/personnelBasic/employmentCategory";
    }
    @RequestMapping(value = "/positionRank")
    public String administrativeLevel(){
        return "basicService/personnelBasic/positionRank";
    }
        //科研信息维护
    @RequestMapping(value = "/journalRank")
    public String journalRank(){
        return "basicService/scientificBasic/journalRank";
    }
    @RequestMapping(value = "/awardLevel")
    public String awardLevel(){
        return "basicService/scientificBasic/awardLevel";
    }
    @RequestMapping(value = "/citation")
    public String citation(){
        return "basicService/scientificBasic/citation";
    }
    @RequestMapping(value = "/awardsRank")
    public String awardsRank(){
        return "basicService/scientificBasic/awardsRank";
    }
    @RequestMapping(value = "/monographRank")
    public String monographRank(){
        return "basicService/scientificBasic/monographRank";
    }
    @RequestMapping(value = "/textBookRank")
    public String textBookRank(){
        return "basicService/scientificBasic/textBookRank";
    }
    @RequestMapping(value = "/pFundedByPrivateSectorRank")
    public String pFundedByPrivateSectorRank(){
        return "basicService/scientificBasic/projectFundedByPrivateSectorRank";
    }
    @RequestMapping(value = "/projectFundedByGovernmentRank")
    public String projectFundedByGovernmentRank(){
        return "basicService/scientificBasic/projectFundedByGovernmentRank";
    }
}
