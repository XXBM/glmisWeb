package com.glmis.domain.personnel;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.glmis.jsonDeserialize.EmployeeDeserialize;
import com.glmis.jsonDeserialize.HostDeserialize;
import com.glmis.jsonDeserialize.ReviewResultDeserialize;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;

/**
 * 学术会议
 */



@Entity
@Table(name = "p_academicConference")
@DynamicInsert(true)
@DynamicUpdate(true)
public class AcademicConference implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * 开始时间
     */
    @javax.persistence.Temporal(javax.persistence.TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Calendar startTime;

    /**
     * 结束时间
     */
    @javax.persistence.Temporal(javax.persistence.TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Calendar endTime;

    /**
     * 会议名称
     */
    private String name;

    /**主办机构
     */
    @javax.persistence.ManyToOne
    @JoinColumn(name = "host_id")
    @JsonDeserialize(using = HostDeserialize.class)
    private Host host;

    /**
     * 汇报时间
     */
    @javax.persistence.Temporal(javax.persistence.TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Calendar reportTime;

    /**
     * 审核结果
     */
    @javax.persistence.ManyToOne
    @JoinColumn(name = "reviewResult_id")
    @JsonDeserialize(using = ReviewResultDeserialize.class)
    private ReviewResult reviewResult;

    /**
     * 职工
     */
    @ManyToOne
    @JoinColumn(name = "employee_id")
    @JsonDeserialize(using = EmployeeDeserialize.class)
    private Employee employee;



    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Calendar getReportTime() {
        return reportTime;
    }

    public void setReportTime(Calendar reportTime) {
        this.reportTime = reportTime;
    }

    public ReviewResult getReviewResult() {
        return reviewResult;
    }

    public void setReviewResult(ReviewResult reviewResult) {
        this.reviewResult = reviewResult;
    }

    public AcademicConference() {}

	public AcademicConference(Long id) {
		this.id = id;
	}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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


    public Host getHost() {
        return host;
    }

    public void setHost(Host host) {
        this.host = host;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

