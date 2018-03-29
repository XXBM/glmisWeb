package com.glmis.domain.personnel;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.glmis.jsonDeserialize.EmployeeDeserialize;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;


/**
 * 来校前工作经历
 */

@Entity
@Table(name = "p_previousWorkExperience")
@DynamicInsert(true)
@DynamicUpdate(true)
public class PreviousWorkExperience implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    /**
     * 开始工作时间
     */

    @javax.persistence.Temporal(javax.persistence.TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Calendar startTime;
    /**
     * 结束工作时间
     */
    @javax.persistence.Temporal(javax.persistence.TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Calendar endTime;
    /**
     * 单位名称
     */
    private String hiredByCompany;
    /**
     * 部门名称
     */
    private String departmentName;
    /**
     * 级别
     */
    private String rank;
    /**
     * 担任职务
     */
    private String post;

    /**
     * 员工
     */
    @javax.persistence.ManyToOne
    @JoinColumn(name = "employee_id")
    @JsonDeserialize(using = EmployeeDeserialize.class)
    private Employee employee;

    public PreviousWorkExperience() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Calendar getStartTime() {
        return startTime;
    }

    public void setStartTime(Calendar startTime) {
        this.startTime = startTime;
    }

    public Calendar getEndTime() {
        return endTime;
    }

    public void setEndTime(Calendar endTime) {
        this.endTime = endTime;
    }

    public String getHiredByCompany() {
        return hiredByCompany;
    }

    public void setHiredByCompany(String hiredByCompany) {
        this.hiredByCompany = hiredByCompany;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }
}

