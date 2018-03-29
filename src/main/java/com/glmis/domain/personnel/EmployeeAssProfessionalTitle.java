package com.glmis.domain.personnel;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.glmis.jsonDeserialize.EmployeeDeserialize;
import com.glmis.jsonDeserialize.ProfessionalTitleDeserialize;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;
/**
 * 职称记录
 */

@Entity
@Table(name = "p_employeeAssProfessionalTitle")
@DynamicInsert(true)
@DynamicUpdate(true)
public class EmployeeAssProfessionalTitle implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	/**职称
	 */
	@ManyToOne
	@JoinColumn(name = "professionalTitle_id")
	@JsonDeserialize(using = ProfessionalTitleDeserialize.class)
	private ProfessionalTitle professionalTitle;
	/**评定时间
	 */

	@javax.persistence.Temporal(javax.persistence.TemporalType.DATE)
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Calendar nominatedDate;
	/**评定文号
	 */
	private String nominatedNo;
	/**聘任时间
	 */
	@javax.persistence.Temporal(javax.persistence.TemporalType.DATE)
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Calendar appointedDate;
	/**聘任文号
	 */
	private String appointedNo;
	/**员工
	 */
	@ManyToOne
	@JoinColumn(name = "employee_id")
	@JsonDeserialize(using = EmployeeDeserialize.class)
	private Employee employee;

	public EmployeeAssProfessionalTitle() {}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ProfessionalTitle getProfessionalTitle() {
		return professionalTitle;
	}

	public void setProfessionalTitle(ProfessionalTitle professionalTitle) {
		this.professionalTitle = professionalTitle;
	}



	public Calendar getNominatedDate() {
		return nominatedDate;
	}

	public void setNominatedDate(Calendar nominatedDate) {
		this.nominatedDate = nominatedDate;
	}

	public Calendar getAppointedDate() {
		return appointedDate;
	}

	public void setAppointedDate(Calendar appointedDate) {
		this.appointedDate = appointedDate;
	}

	public String getNominatedNo() {
		return nominatedNo;
	}

	public void setNominatedNo(String nominatedNo) {
		this.nominatedNo = nominatedNo;
	}

	public String getAppointedNo() {
		return appointedNo;
	}

	public void setAppointedNo(String appointedNo) {
		this.appointedNo = appointedNo;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
}

