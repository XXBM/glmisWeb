package com.glmis.domain.personnel;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.glmis.jsonDeserialize.EmployeeDeserialize;
import com.glmis.jsonDeserialize.ReviewResultDeserialize;
import com.glmis.jsonDeserialize.SponsorDeserialize;
import com.glmis.jsonDeserialize.YesOrNoDeserialize;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;
/**
 * 访学信息
 */

@Entity
@Table(name = "p_visitingAcademic")
@DynamicInsert(true)
@DynamicUpdate(true)
public class VisitingAcademic implements Serializable {
	@javax.persistence.Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	/**进修名称
	 * 实际上是项目名称
	 */
	private String program;
	/**开始时间
	 */

	@javax.persistence.Temporal(javax.persistence.TemporalType.DATE)
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Calendar startTime;
	/**结束时间
	 */
	@javax.persistence.Temporal(javax.persistence.TemporalType.DATE)
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Calendar endTime;
	/**受访机构
	 */
	private String visitedOrganization;
	/**导师姓名
	 */
	private String supervisorName;
	/**汇报时间
	 */
	@javax.persistence.Temporal(javax.persistence.TemporalType.DATE)
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Calendar reportTime;
	/**审核结果
	 */
	@javax.persistence.ManyToOne
	@JoinColumn(name = "reviewResult_id")
	@JsonDeserialize(using = ReviewResultDeserialize.class)
	private ReviewResult reviewResult;
	/**资助机构
	 */
	@javax.persistence.ManyToOne 
	@JoinColumn(name = "sponsor_id")
	@JsonDeserialize(using = SponsorDeserialize.class)
	private Sponsor sponsor;
	/**是否国内访问
	 */
	@javax.persistence.ManyToOne
	@JoinColumn(name = "domesticOrNot_id")
	@JsonDeserialize(using = YesOrNoDeserialize.class)
	private YesOrNo domesticOrNot;

	/**员工
	 */
	@javax.persistence.ManyToOne
	@JoinColumn(name = "employee_id")
	@JsonDeserialize(using = EmployeeDeserialize.class)
	private Employee employee;


	public VisitingAcademic() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProgram() {
		return program;
	}

	public void setProgram(String program) {
		this.program = program;
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

	public String getVisitedOrganization() {
		return visitedOrganization;
	}

	public void setVisitedOrganization(String visitedOrganization) {
		this.visitedOrganization = visitedOrganization;
	}

	public String getSupervisorName() {
		return supervisorName;
	}

	public void setSupervisorName(String supervisorName) {
		this.supervisorName = supervisorName;
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

	public Sponsor getSponsor() {
		return sponsor;
	}

	public void setSponsor(Sponsor sponsor) {
		this.sponsor = sponsor;
	}

	public YesOrNo getDomesticOrNot() {
		return domesticOrNot;
	}

	public void setDomesticOrNot(YesOrNo domesticOrNot) {
		this.domesticOrNot = domesticOrNot;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
}

