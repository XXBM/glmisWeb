package com.glmis.domain.personnel;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.glmis.jsonDeserialize.AcademicDegreeDeserialize;
import com.glmis.jsonDeserialize.AcademicDegreeTypeDeserialize;
import com.glmis.jsonDeserialize.EmployeeDeserialize;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;
/**
 * 学位信息记录
 */

@Entity
@Table(name = "p_employeeAssAcademicDegree")
@DynamicInsert(true)
@DynamicUpdate(true)
public class EmployeeAssAcademicDegree implements Serializable {
	@javax.persistence.Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	/**
	 * 学位层次
	 */
	@ManyToOne
	@JoinColumn(name = "academicDegreeType_id")
	@JsonDeserialize(using = AcademicDegreeTypeDeserialize.class)
	private AcademicDegreeType academicDegreeType;
	/**学位名称
	 * 单向
	 */
	@ManyToOne
	@JoinColumn(name = "academicDegree_id")
	@JsonDeserialize(using = AcademicDegreeDeserialize.class)
	private AcademicDegree academicDegree;
	/**获得时间
	 */
	@javax.persistence.Temporal(javax.persistence.TemporalType.DATE)
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Calendar grantedDate;
	/**
	 * 专业
	 */
	private String major;
	/**证书编号
	 */
	@Column(unique = true)
	private String certificateNo;
	/**学校
	 */
	private String university;
	/**
	 * 员工
 	*/

	@javax.persistence.ManyToOne
	@JoinColumn(name = "employee_id")
	@JsonDeserialize(using = EmployeeDeserialize.class)
	private Employee employee;

	public EmployeeAssAcademicDegree() {}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public AcademicDegreeType getAcademicDegreeType() {
		return academicDegreeType;
	}

	public void setAcademicDegreeType(AcademicDegreeType academicDegreeType) {
		this.academicDegreeType = academicDegreeType;
	}

	public AcademicDegree getAcademicDegree() {
		return academicDegree;
	}

	public void setAcademicDegree(AcademicDegree academicDegree) {
		this.academicDegree = academicDegree;
	}

	public Calendar getGrantedDate() {
		return grantedDate;
	}

	public void setGrantedDate(Calendar grantedDate) {
		this.grantedDate = grantedDate;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
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

