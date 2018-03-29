package com.glmis.domain.personnel;

import java.util.List;

/**
 * Created by kene on 17-5-31.
 */
//@Entity
public class SpecificationConditions {
    //学位
    private List<Long> academicDegree;
    //是否华侨
    private List<Long> overseasChineseOrNot;
    //性别
    private List<Long> sex;
    //职业资格
    private List<Long> practisingCertification;
    //统计截止时间
    private String endDate;
    //出生日期开始时间
    private String startBirth;
    //出生日期截止时间
    private String endBirth;
    //职位
    private List<Long> professionalTitle;
    //部门
    private List<Long> department;
    //是否国内访学
    private List<Long> domesticOrNot;
    //政治面貌
    private List<Long> politicalParty;
    //学术会议
    private List<Long> academicConference;
    //民族
    private List<Long> nation;
    //访问学者资助
    private List<Long> sponsor;


    public List<Long> getAcademicDegree() {
        return academicDegree;
    }

    public void setAcademicDegree(List<Long> academicDegree) {
        this.academicDegree = academicDegree;
    }

    public List<Long> getOverseasChineseOrNot() {
        return overseasChineseOrNot;
    }

    public void setOverseasChineseOrNot(List<Long> overseasChineseOrNot) {
        this.overseasChineseOrNot = overseasChineseOrNot;
    }

    public List<Long> getSex() {
        return sex;
    }

    public List<Long> getPractisingCertification() {
        return practisingCertification;
    }

    public void setPractisingCertification(List<Long> practisingCertification) {
        this.practisingCertification = practisingCertification;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStartBirth() {
        return startBirth;
    }

    public void setStartBirth(String startBirth) {
        this.startBirth = startBirth;
    }

    public String getEndBirth() {
        return endBirth;
    }

    public void setEndBirth(String endBirth) {
        this.endBirth = endBirth;
    }

    public List<Long> getProfessionalTitle() {
        return professionalTitle;
    }

    public void setProfessionalTitle(List<Long> professionalTitle) {
        this.professionalTitle = professionalTitle;
    }

    public List<Long> getDepartment() {
        return department;
    }

    public void setDepartment(List<Long> department) {
        this.department = department;
    }

    public List<Long> getDomesticOrNot() {
        return domesticOrNot;
    }

    public void setDomesticOrNot(List<Long> domesticOrNot) {
        this.domesticOrNot = domesticOrNot;
    }

    public void setSex(List<Long> sex) {
        this.sex = sex;
    }

    public List<Long> getAcademicConference() {
        return academicConference;
    }

    public void setAcademicConference(List<Long> academicConference) {
        this.academicConference = academicConference;
    }

    public List<Long> getNation() {
        return nation;
    }

    public void setNation(List<Long> nation) {
        this.nation = nation;
    }

    public List<Long> getSponsor() {
        return sponsor;
    }

    public void setSponsor(List<Long> sponsor) {
        this.sponsor = sponsor;
    }

    public List<Long> getPoliticalParty() {
        return politicalParty;
    }

    public void setPoliticalParty(List<Long> politicalParty) {
        this.politicalParty = politicalParty;
    }

    @Override
    public String toString() {
        return "SpecificationConditions{" +
                "academicDegrees=" + academicDegree +
                ", overseasChineseOrNot=" + overseasChineseOrNot +
                ", sex=" + sex +
                ", practisingCertifications=" + practisingCertification +
                ", endDate='" + endDate + '\'' +
                ", startBirth='" + startBirth + '\'' +
                ", endBirth='" + endBirth + '\'' +
                ", professionalTitle=" + professionalTitle +
                ", departments=" + department +
                ", domesticOrNot=" + domesticOrNot +
                ", politicalParties=" + politicalParty +
                ", academicConferences=" + academicConference +
                ", nation=" + nation +
                ", sponsor=" + sponsor +
                '}';
    }
}
