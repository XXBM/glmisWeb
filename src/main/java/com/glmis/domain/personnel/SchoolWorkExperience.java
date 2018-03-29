package com.glmis.domain.personnel;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.glmis.jsonDeserialize.EmployeeDeserialize;
import com.glmis.jsonDeserialize.PostDeserialize;
import com.glmis.jsonDeserialize.PositionRankDeserialize;
import com.glmis.jsonDeserialize.SchoolDeserialize;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;



/**
 *在校工作经历
 */

@Entity
@Table(name = "p_schoolWorkExperience")
@DynamicInsert(true)
@DynamicUpdate(true)
public class SchoolWorkExperience implements Serializable {

	@javax.persistence.Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	/**开始工作时间
	 */

	@javax.persistence.Temporal(javax.persistence.TemporalType.DATE)
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Calendar startTime;
	/**结束工作时间
	 */
	@javax.persistence.Temporal(javax.persistence.TemporalType.DATE)
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Calendar endTime;
	/**部门名称
	 */
	@ManyToOne
	@JoinColumn(name = "school_id")
	@JsonDeserialize(using = SchoolDeserialize.class)
	private School school;
	/**级别
	 */
	@javax.persistence.ManyToOne 
	@JoinColumn(name = "rank_id")
	@JsonDeserialize(using = PositionRankDeserialize.class)
	private PositionRank positionRank;
	/**担任职务
	 */
	@javax.persistence.ManyToOne 
	@JoinColumn(name = "post_id")
	@JsonDeserialize(using = PostDeserialize.class)
	private Post post;

	/**员工
	 */
	@javax.persistence.ManyToOne
	@JoinColumn(name = "employee_id")
	@JsonDeserialize(using = EmployeeDeserialize.class)
	private Employee employee;

	public SchoolWorkExperience() {}

	public SchoolWorkExperience(Calendar startTime, Calendar endTime, School school, PositionRank positionRank, Post post) {
		this.startTime = startTime;
		this.endTime = endTime;
		this.school = school;
		this.positionRank = positionRank;
		this.post = post;
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

	public School getSchool() {
		return school;
	}

	public void setSchool(School school) {
		this.school = school;
	}

	public PositionRank getPositionRank() {
		return positionRank;
	}

	public void setPositionRank(PositionRank positionRank) {
		this.positionRank = positionRank;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}
}

