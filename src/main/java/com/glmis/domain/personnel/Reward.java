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
 * 奖励信息
 */

@Entity
@Table(name = "p_reward")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Reward implements Serializable {

	@javax.persistence.Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	/**授予日期
	 */

	@javax.persistence.Temporal(javax.persistence.TemporalType.DATE)
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Calendar grantedDate;
	/**名称
	 */
	private String name;
	/**级别
	 */
	private String rank;
	/**位次
	 */
	private String precedence;

	public String getNumOfParticipants() {
		return numOfParticipants;
	}

	public void setNumOfParticipants(String numOfParticipants) {
		this.numOfParticipants = numOfParticipants;
	}

	/*
        * 总人数
        * */
	private String numOfParticipants;
	/**授予机关
	 */
	private String grantedBy;
	/**员工
	 */
	@javax.persistence.ManyToOne
	@JoinColumn(name = "employee_id")
	@JsonDeserialize(using = EmployeeDeserialize.class)
	private Employee employee;

	public Reward() {}

	public Reward(Calendar grantedDate, String name, String rank, String precedence, String grantedBy) {
		this.grantedDate = grantedDate;
		this.name = name;
		this.rank = rank;
		this.precedence = precedence;
		this.grantedBy = grantedBy;
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

	public Calendar getGrantedDate() {
		return grantedDate;
	}

	public void setGrantedDate(Calendar grantedDate) {
		this.grantedDate = grantedDate;
	}

	public String getPrecedence() {
		return precedence;
	}

	public void setPrecedence(String precedence) {
		this.precedence = precedence;
	}

	public String getGrantedBy() {
		return grantedBy;
	}

	public void setGrantedBy(String grantedBy) {
		this.grantedBy = grantedBy;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

}

