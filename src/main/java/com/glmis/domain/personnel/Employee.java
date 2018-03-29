package com.glmis.domain.personnel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.glmis.domain.attendance.Attendance;
import com.glmis.domain.attendance.AttendanceSummary;
import com.glmis.domain.attendance.Leave;
import com.glmis.jsonDeserialize.DepartmentDeserialize;
import com.glmis.jsonDeserialize.EmploymentCategoryDeserialize;
import com.glmis.jsonDeserialize.NationDeserialize;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

/**
 * 员工
 */

@Entity
@Table(name = "p_employee")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Employee extends Actor {
    /**
     * 照片
     */
//	@Lob 注解属性将被持久化为 Blog 或 Clob 类型。具体的java.sql.Clob, Character[], char[] 和 java.lang.String 将被持久化为 Clob 类型. java.sql.Blob, Byte[], byte[] 和 serializable type 将被持久化为 Blob 类型。
//	@Lob 持久化为Blob或者Clob类型,根据get方法的不同,自动进行Clob和Blob的转换
    @Lob
    @Basic
    @Column(name = "photo", columnDefinition = "LongBlob")
//	@JsonIgnore
    private byte[] photo;


    /**
     * 工资号
     */
    private String salaryNo;
    /*
     * 民族
    */
    @javax.persistence.ManyToOne
    @JoinColumn(name = "nation_id")
    @JsonDeserialize(using = NationDeserialize.class)
    private Nation nation;
    /**
     * 职工类别
     */

    @javax.persistence.ManyToOne
    @JoinColumn(name = "employmentCategory_id")
    @JsonDeserialize(using = EmploymentCategoryDeserialize.class)
    private EmploymentCategory employmentCategory;
    /**
     * 学历信息
     * 加上	@JsonIgnore注解，表示在转化为json的时候，忽略该关联属性
     * 如果没有这个属性，就会出现死循环情况，因为他的实体对象包括的其他实体对象，其他实体对象也包括其他的实体对象，就会陷入死循环中
     * 即通过该实体对象获取到关联实体对象json数据，关联实体对象里又关联该实体对象，就会无限循环下去。
     */
    @JsonIgnore
    @javax.persistence.OneToMany(mappedBy = "employee", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<EmployeeAssEducationQualification> employeeAssEducationQualifications;
    /**
     * 员工职务信息记录
     */
    @JsonIgnore
    @javax.persistence.OneToMany(mappedBy = "employee", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<EmployeeAssPost> employeeAssPosts;
    /**
     * 来校前工作经历
     */
    @JsonIgnore
    @javax.persistence.OneToMany(mappedBy = "employee",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<PreviousWorkExperience> previousWorkExperiences;
    /**
     * 在校工作经历
     */
    @JsonIgnore
    @javax.persistence.OneToMany(mappedBy = "employee",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<SchoolWorkExperience> schoolWorkExperiences;
    /**
     * 奖励信息记录
     */
    @JsonIgnore
    @javax.persistence.OneToMany(mappedBy = "employee",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Reward> rewards;
    /**
     * 访学记录
     */
    @JsonIgnore
    @javax.persistence.OneToMany(mappedBy = "employee",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<VisitingAcademic> visitingAcademics;

    /**
     * 学术会议信息记录
     */
    @JsonIgnore
    @javax.persistence.OneToMany(fetch = FetchType.LAZY, mappedBy = "employee", cascade = CascadeType.ALL)
    private Set<AcademicConference> academicConferences;

    /**
     * 职称信息记录
     */
    @JsonIgnore
    @javax.persistence.OneToMany(mappedBy = "employee", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<EmployeeAssProfessionalTitle> employeeAssProfessionalTitles;
    /**
     * 执业资格信息记录
     */
    @JsonIgnore
    @javax.persistence.OneToMany(mappedBy = "employee", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<EmployeeAssPractisingCertification> employeeAssPractisingCertifications;
    /**
     * 学位信息记录
     */
    @JsonIgnore
    @javax.persistence.OneToMany(fetch = FetchType.LAZY, mappedBy = "employee", cascade = CascadeType.ALL)
    private Set<EmployeeAssAcademicDegree> employeeAssAcademicDegrees;

    /*
    * 考勤信息
    * */
    @JsonIgnore
    @javax.persistence.OneToMany(fetch = FetchType.LAZY, mappedBy = "employee", cascade = CascadeType.ALL)
    private Set<Attendance> attendances;

    //考勤纸
    @JsonIgnore
    @javax.persistence.ManyToMany(mappedBy = "candidates",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Collection<AttendanceSummary> attendanceSummaries;

    /*
    * 请假信息
    * */
    @JsonIgnore
    @javax.persistence.OneToMany(fetch = FetchType.LAZY, mappedBy = "employee", cascade = CascadeType.ALL)
    private Set<Leave> leaves;

    /**
     * 政治面貌
     */
    @JsonIgnore
    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<EmployeeAssPoliticalParty> employeeAssPoliticalParties;

    /**
     * 部门/教研室
     */
    @JsonDeserialize(using = DepartmentDeserialize.class)
    @javax.persistence.ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    public Employee() {
    }

    //用于反序列化时调用的构造函数
    public Employee(Long id) {
        this.id = id;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getSalaryNo() {
        return salaryNo;
    }

    public void setSalaryNo(String salaryNo) {
        this.salaryNo = salaryNo;
    }

    public Set<EmployeeAssEducationQualification> getEmployeeAssEducationQualifications() {
        return employeeAssEducationQualifications;
    }

    public void setEmployeeAssEducationQualifications(Set<EmployeeAssEducationQualification> employeeAssEducationQualifications) {
        this.employeeAssEducationQualifications = employeeAssEducationQualifications;
    }

    public Nation getNation() {
        return nation;
    }

    public void setNation(Nation nation) {
        this.nation = nation;
    }

    public EmploymentCategory getEmploymentCategory() {
        return employmentCategory;
    }
    public void setEmploymentCategory(EmploymentCategory employmentCategory) {
        this.employmentCategory = employmentCategory;
    }

    public Set<EmployeeAssPoliticalParty> getEmployeeAssPoliticalParties() {
        return employeeAssPoliticalParties;
    }

    public void setEmployeeAssPoliticalParties(Set<EmployeeAssPoliticalParty> employeeAssPoliticalParties) {
        this.employeeAssPoliticalParties = employeeAssPoliticalParties;
    }
    public Set<EmployeeAssPost> getEmployeeAssPosts() {
        return employeeAssPosts;
    }

    public void setEmployeeAssPosts(Set<EmployeeAssPost> employeeAssPosts) {
        this.employeeAssPosts = employeeAssPosts;
    }

    public Set<PreviousWorkExperience> getPreviousWorkExperiences() {
        return previousWorkExperiences;
    }

    public void setPreviousWorkExperiences(Set<PreviousWorkExperience> previousWorkExperiences) {
        this.previousWorkExperiences = previousWorkExperiences;
    }

    public Set<SchoolWorkExperience> getSchoolWorkExperiences() {
        return schoolWorkExperiences;
    }

    public void setSchoolWorkExperiences(Set<SchoolWorkExperience> schoolWorkExperiences) {
        this.schoolWorkExperiences = schoolWorkExperiences;
    }

    public Set<Reward> getRewards() {
        return rewards;
    }

    public void setRewards(Set<Reward> rewards) {
        this.rewards = rewards;
    }

    public Set<VisitingAcademic> getVisitingAcademics() {
        return visitingAcademics;
    }

    public void setVisitingAcademics(Set<VisitingAcademic> visitingAcademics) {
        this.visitingAcademics = visitingAcademics;
    }

    public Set<EmployeeAssProfessionalTitle> getEmployeeAssProfessionalTitles() {
        return employeeAssProfessionalTitles;
    }

    public void setEmployeeAssProfessionalTitles(Set<EmployeeAssProfessionalTitle> employeeAssProfessionalTitles) {
        this.employeeAssProfessionalTitles = employeeAssProfessionalTitles;
    }

    public Set<EmployeeAssPractisingCertification> getEmployeeAssPractisingCertifications() {
        return employeeAssPractisingCertifications;
    }

    public void setEmployeeAssPractisingCertifications(Set<EmployeeAssPractisingCertification> employeeAssPractisingCertifications) {
        this.employeeAssPractisingCertifications = employeeAssPractisingCertifications;
    }

    public Set<EmployeeAssAcademicDegree> getEmployeeAssAcademicDegrees() {
        return employeeAssAcademicDegrees;
    }

    public void setEmployeeAssAcademicDegrees(Set<EmployeeAssAcademicDegree> employeeAssAcademicDegrees) {
        this.employeeAssAcademicDegrees = employeeAssAcademicDegrees;
    }

    public Set<AcademicConference> getAcademicConferences() {
        return academicConferences;
    }

    public void setAcademicConferences(Set<AcademicConference> academicConferences) {
        this.academicConferences = academicConferences;
    }
    public Collection<AttendanceSummary> getAttendanceSummaries() {
        return attendanceSummaries;
    }

    public void setAttendanceSummaries(Collection<AttendanceSummary> attendanceSummaries) {
        this.attendanceSummaries = attendanceSummaries;
    }


    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}

