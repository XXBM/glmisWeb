package com.glmis.domain.personnel;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.glmis.jsonDeserialize.EducationQualificationDeserialize;
import com.glmis.jsonDeserialize.EmployeeDeserialize;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;


/**
 * 学历信息
 */

@Entity
@Table(name = "p_employeeAssEducationQualification")
@DynamicInsert(true)
@DynamicUpdate(true)
public class EmployeeAssEducationQualification implements Serializable
{
	@javax.persistence.Id 
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	/**学历名称
	 * 单向关联，多端维护外键（关联关系）
	 * 	@JsonIgnore 当json数据不能被解析的时候
	 * 	@JacksonInject 反序列时标记该属性应经被注入
	 * 	@JsonUnwrapped 将子JSON对象的属性添加到封闭的JSON对象
	 * 	@JsonDeserialize(using = EducationQualificationDeserialize.class) 自定义反序列化
	 */

	@ManyToOne
	@JoinColumn(name = "educationQualification_id")
	@JsonDeserialize(using = EducationQualificationDeserialize.class)
	private EducationQualification educationQualification;
	/**入学时间
	 */
	@javax.persistence.Temporal(javax.persistence.TemporalType.DATE)
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Calendar enrollDate;
	/**获得学历时间
	 */
	@javax.persistence.Temporal(javax.persistence.TemporalType.DATE)
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Calendar graduateDate;
	/**专业
	 */
	private String major;
	/**学制
	 */
	private Integer duration;
	/**导师姓名
	 */
	private String  supervisorName;
	/**毕业证书编号
	 */
	@Column(unique = true)
	private String certificateNo;
	/**学校
	 */
	private String university;
	/**员工
	 */
	@javax.persistence.ManyToOne
	@JoinColumn(name = "employee_id")
	@JsonDeserialize(using = EmployeeDeserialize.class)
	private Employee employee;

	public EmployeeAssEducationQualification() {}

	public EducationQualification getEducationQualification() {
		return educationQualification;
	}

	public void setEducationQualification(EducationQualification educationQualification) {
		this.educationQualification = educationQualification;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Calendar getGraduateDate() {
		return graduateDate;
	}

	public void setGraduateDate(Calendar graduateDate) {
		this.graduateDate = graduateDate;
	}

	public Calendar getEnrollDate() {
		return enrollDate;
	}

	public void setEnrollDate(Calendar enrollDate) {
		this.enrollDate = enrollDate;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}


	public String getSupervisorName() {
		return supervisorName;
	}

	public void setSupervisorName(String supervisorName) {
		this.supervisorName = supervisorName;
	}

	public String getCertificateNo() {
		return certificateNo;
	}

	public void setCertificateNo(String certificateNo) {
		this.certificateNo = certificateNo;
	}

	public String getUniversity() {
		return university;
	}

	public void setUniversity(String university) {
		this.university = university;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
}

