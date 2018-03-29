package com.glmis.domain.minor;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


/**
 * <!-- begin-user-doc -->
 * <!--  end-user-doc  -->
 * @generated
 */


@Entity
@Table(name = "grade")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Grade implements Serializable
{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	/**
	 * 年级（获取当前年份，其上三年，其下三年）
	 */
	private Integer grade_name;
	/**
	 * 教学计划
	 * 一对多
	 */
	@OneToMany(mappedBy = "grade")
	protected List<CoursePlan> coursePlanList;

	public Grade() {}

	public Grade(Integer grade_name, List<CoursePlan> coursePlanList) {
		this.grade_name = grade_name;
		this.coursePlanList = coursePlanList;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getGrade_name() {
		return grade_name;
	}

	public void setGrade_name(Integer grade_name) {
		this.grade_name = grade_name;
	}

	public List<CoursePlan> getCoursePlanList() {
		return coursePlanList;
	}

	public void setCoursePlanList(List<CoursePlan> coursePlanList) {
		this.coursePlanList = coursePlanList;
	}
}

