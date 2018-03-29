package com.glmis.domain.personnel;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.glmis.jsonDeserialize.EmployeeDeserialize;
import com.glmis.jsonDeserialize.PostDeserialize;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;

/**
 * 职务信息
 */

@Entity
@Table(name = "p_employeeAssPost")
@DynamicInsert(true)
@DynamicUpdate(true)
public class EmployeeAssPost implements Serializable {
	@javax.persistence.Id 
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	/**任命时间
	 */
	@javax.persistence.Temporal(javax.persistence.TemporalType.DATE)
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Calendar appointedDate;
	/**红头文件*文件号
	 * 任命书
	 */
	private String commissionNo;
	/**职位名称
	 */
	@javax.persistence.ManyToOne 
	@JoinColumn(name = "post_id")
	@JsonDeserialize(using = PostDeserialize.class)
	private Post post;

	/**员工
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

	public EmployeeAssPost() {}

	public EmployeeAssPost(Calendar appointedDate, String commissionNo, Post post) {
		this.appointedDate = appointedDate;
		this.commissionNo = commissionNo;
		this.post = post;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Calendar getAppointedDate() {
		return appointedDate;
	}

	public void setAppointedDate(Calendar appointedDate) {
		this.appointedDate = appointedDate;
	}

	public String getCommissionNo() {
		return commissionNo;
	}

	public void setCommissionNo(String commissionNo) {
		this.commissionNo = commissionNo;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}
}

