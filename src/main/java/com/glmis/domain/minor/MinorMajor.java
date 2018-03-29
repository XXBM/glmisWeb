package com.glmis.domain.minor;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.lang.*;
import java.util.List;
import java.util.Set;


/**
 * <!-- begin-user-doc -->
 * <!--  end-user-doc  -->
 * @generated
 */


@Entity
@Table(name = "minor_major")
@DynamicInsert(true)
@DynamicUpdate(true)
public class MinorMajor extends Classify
{

	/**
	*辅修班级
	 * 双向一对多
	**/
	@OneToMany(mappedBy = "minorMajor")
	protected Set<MinorClass> minorClasses;

	/**
	 * 主修专业
	 * 双向一对一
	 */
	@OneToOne
	@JoinColumn(name = "major_id")
	private Major major;
	/**
	 * 教学计划
	 * 一对多
	 */
	@OneToMany(mappedBy = "minorMajor")
	protected List<CoursePlan> coursePlanList;


//	/**
//	 *学院
//	 **/
//	@javax.persistence.ManyToOne
//	@javax.persistence.JoinColumn(name = "academy_id")
//	protected Academy academy;

//	/**
//	 *专业学科
//	 * 单向多对一
//	 */
//	@ManyToOne
//	@JoinColumn(name = "subject_id")
//	protected Subjects subject;

//	/**
//	 * 专业所属
//	 * 单向多对一
//	 */
//	@ManyToOne
//	@JoinColumn(name = "affiliation_id")
//	protected Affiliation affiliation;

//	/**
//	 * 学生
//	 */
//	@javax.persistence.OneToMany(mappedBy = "minorMajor")
//	protected Set<Student> student;


	public MinorMajor() {}

	public MinorMajor(String description, Set<MinorClass> minorClasses, Major major, List<CoursePlan> coursePlanList) {
		super(description);
		this.minorClasses = minorClasses;
		this.major = major;
		this.coursePlanList = coursePlanList;
	}

	public Set<MinorClass> getMinorClasses() {
		return minorClasses;
	}

	public void setMinorClasses(Set<MinorClass> minorClasses) {
		this.minorClasses = minorClasses;
	}

	public Major getMajor() {
		return major;
	}

	public void setMajor(Major major) {
		this.major = major;
	}

	public List<CoursePlan> getCoursePlanList() {
		return coursePlanList;
	}

	public void setCoursePlanList(List<CoursePlan> coursePlanList) {
		this.coursePlanList = coursePlanList;
	}
}

