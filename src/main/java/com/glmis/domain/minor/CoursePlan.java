package com.glmis.domain.minor;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import javax.persistence.*;

/**
 * Created by dell on 2016/10/29.
 */

@Entity
@Table(name = "course_plan")
@DynamicInsert(true)
@DynamicUpdate(true)
public class CoursePlan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    /**
     * 课程
     */
    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;
    /**
     * 辅修专业
     */
    @ManyToOne
    @JoinColumn(name = "minor_major_id")
    private MinorMajor minorMajor;
    /**
     * 年级
     */
    @ManyToOne
    @JoinColumn(name = "grade_id")
    protected Grade grade;
    /**
     * 学年
     */
    @ManyToOne
    @JoinColumn(name = "year_id")
    protected Year year;
    /**
     * 学期
     */
    @ManyToOne
    @JoinColumn(name = "season_id")
    protected Season season;
    /**
     * 教室
     */
    @Column(name = "class_room")
    protected String classRoom;
    /**
     * 上课时间
     */
    @Column(name = "class_time")
    protected String classTime;
    /**
     * 老师
     */
    @Column(name = "teacher")
    protected String teacher;

    public CoursePlan() {}

    public CoursePlan(Course course, MinorMajor minorMajor, Grade grade, Year year, Season season, String classRoom
            , String classTime, String teacher) {
        this.course = course;
        this.minorMajor = minorMajor;
        this.grade = grade;
        this.year = year;
        this.season = season;
        this.classRoom = classRoom;
        this.classTime = classTime;
        this.teacher = teacher;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public MinorMajor getMinorMajor() {
        return minorMajor;
    }

    public void setMinorMajor(MinorMajor minorMajor) {
        this.minorMajor = minorMajor;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public Year getYear() {
        return year;
    }

    public void setYear(Year year) {
        this.year = year;
    }

    public Season getSeason() {
        return season;
    }

    public void setSeason(Season season) {
        this.season = season;
    }

    public String getClassRoom() {
        return classRoom;
    }

    public void setClassRoom(String classRoom) {
        this.classRoom = classRoom;
    }

    public String getClassTime() {
        return classTime;
    }

    public void setClassTime(String classTime) {
        this.classTime = classTime;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }
}
