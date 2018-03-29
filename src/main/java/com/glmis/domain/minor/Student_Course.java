package com.glmis.domain.minor;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by dell on 2016/10/23.
 */

@Entity
@Table(name = "student_course")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Student_Course implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    /**
     * 学分互认
     */
    private boolean confirm;
    /**
     * 成绩
     */
    private Double score;
    /**
     * 学生
     */
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;
    /**
     * 课程
     */
    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    public Student_Course() {}

    public Student_Course(boolean confirm, Double score, Student student, Course course) {
        this.confirm = confirm;
        this.score = score;
        this.student = student;
        this.course = course;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isConfirm() {
        return confirm;
    }

    public void setConfirm(boolean confirm) {
        this.confirm = confirm;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}

