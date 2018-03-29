package com.glmis.domain.personnel;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.glmis.jsonDeserialize.EmployeeDeserialize;
import com.glmis.jsonDeserialize.PoliticalPartyDeserialize;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;

/**
 * 政治面貌
 */

@Entity
@Table(name = "p_employeeAssPoliticalParty")
@DynamicInsert(true)
@DynamicUpdate(true)
public class EmployeeAssPoliticalParty implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long pid;
	/*
	* 入党时间
	 *   @JsonFormat(pattern = "yyyy-MM-dd")注解是日期转化为json数据时，转化为需要的模式
	 *   "yyyy-MM-dd HH-mm-ss"
	 */
	@javax.persistence.Temporal(javax.persistence.TemporalType.DATE)
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Calendar membershipFrom;
	/**党派
	 */

	@ManyToOne
	@JoinColumn(name = "politicalParty_id")
	@JsonDeserialize(using = PoliticalPartyDeserialize.class)
	private PoliticalParty politicalParty;

	/**
	 * 职工
	 */
	@ManyToOne
	@JoinColumn(name = "employeeId")
	@JsonDeserialize(using = EmployeeDeserialize.class)
	private Employee employee;

	public EmployeeAssPoliticalParty() {}
	//反序列化用到的构造器
	public EmployeeAssPoliticalParty(Long pid) {
		this.pid = pid;
	}

	public EmployeeAssPoliticalParty(Calendar membershipFrom, PoliticalParty politicalParty) {
		this.membershipFrom = membershipFrom;
		this.politicalParty = politicalParty;
	}

	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public Calendar getMembershipFrom() {
		return membershipFrom;
	}

	public void setMembershipFrom(Calendar membershipFrom) {
		this.membershipFrom = membershipFrom;
	}

	public PoliticalParty getPoliticalParty() {
		return politicalParty;
	}

	public void setPoliticalParty(PoliticalParty politicalParty) {
		this.politicalParty = politicalParty;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
}

