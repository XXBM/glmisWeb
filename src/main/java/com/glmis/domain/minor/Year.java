package com.glmis.domain.minor;


import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.List;

/**
 * <!-- begin-user-doc -->
 * <!--  end-user-doc  -->
 * @generated
 */


@Entity
@Table(name = "year")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Year
{
	/**
	 * 学年编号
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	/**
	 * 开设学年
	 */
	private Integer year_name;

	/**
	 * 教学计划
	 * 一对多
	 */
	@OneToMany(mappedBy = "year")
	protected List<CoursePlan> coursePlanList;

	public Year() {}

	public Year(Integer year_name, List<CoursePlan> coursePlanList) {
		this.year_name = year_name;
		this.coursePlanList = coursePlanList;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getYear_name() {
		return year_name;
	}

	public void setYear_name(Integer year_name) {
		this.year_name = year_name;
	}

	public List<CoursePlan> getCoursePlanList() {
		return coursePlanList;
	}

	public void setCoursePlanList(List<CoursePlan> coursePlanList) {
		this.coursePlanList = coursePlanList;
	}
}

