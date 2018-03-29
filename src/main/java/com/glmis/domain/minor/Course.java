package com.glmis.domain.minor;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;



@Entity
@Table(name = "course")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Course implements Serializable
{
	@Id
	@javax.persistence.Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
	protected Integer id;
	/**
     * 课程名
	 */
	@javax.persistence.Column(nullable = false)
	protected String name;
	/**
     * 教材名
	 */
	@javax.persistence.Column(nullable = false)
	protected String book;
	/**
     * 学分
	 */
	@javax.persistence.Column(nullable = false)
	protected int credit;
	/**
     * 学时
	 */
	@javax.persistence.Column(nullable = false)
	protected String period;
    /**
     * 学费
     */
    protected String money;
    /**
     * 学生_课程
     * 双向一对多
     */
    @OneToMany(mappedBy = "course")
    protected Collection<Student_Course> student_courses;
	/**
	 * 教学计划
	 * 一对多
	 */
	@OneToMany(mappedBy = "course")
	protected List<CoursePlan> coursePlanList;

	public Course() {}
	public Course(String name, String book, int credit, String period, String money, Collection<Student_Course> student_courses, List<CoursePlan> coursePlanList) {
		this.name = name;
		this.book = book;
		this.credit = credit;
		this.period = period;
		this.money = money;
		this.student_courses = student_courses;
		this.coursePlanList = coursePlanList;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBook() {
		return book;
	}

	public void setBook(String book) {
		this.book = book;
	}

	public int getCredit() {
		return credit;
	}

	public void setCredit(int credit) {
		this.credit = credit;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public Collection<Student_Course> getStudent_courses() {
		return student_courses;
	}

	public void setStudent_courses(Collection<Student_Course> student_courses) {
		this.student_courses = student_courses;
	}

	public List<CoursePlan> getCoursePlanList() {
		return coursePlanList;
	}

	public void setCoursePlanList(List<CoursePlan> coursePlanList) {
		this.coursePlanList = coursePlanList;
	}
}



