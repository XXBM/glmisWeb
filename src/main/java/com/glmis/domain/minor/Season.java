package com.glmis.domain.minor;


import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

/**
 * <!-- begin-user-doc -->
 * <!--  end-user-doc  -->
 * @generated
 */


@javax.persistence.Entity
@Table(name = "season")
@DynamicUpdate(true)
@DynamicInsert(true)
public class Season
{
	/**
	 * 学期编号
	 */
	@javax.persistence.Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected Integer id;

	/**
	 * 开设学期
	 */
	protected String season_name;

	/**
	 * 教学计划
	 * 一对多
	 */
	@OneToMany(mappedBy = "season")
	protected List<CoursePlan> coursePlanList;

	public Season(){}

	public Season(String season_name, List<CoursePlan> coursePlanList) {
		this.season_name = season_name;
		this.coursePlanList = coursePlanList;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSeason_name() {
		return season_name;
	}

	public void setSeason_name(String season_name) {
		this.season_name = season_name;
	}

	public List<CoursePlan> getCoursePlanList() {
		return coursePlanList;
	}

	public void setCoursePlanList(List<CoursePlan> coursePlanList) {
		this.coursePlanList = coursePlanList;
	}
}

