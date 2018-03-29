package com.glmis.domain.minor;

import com.glmis.domain.personnel.Actor;
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
@Table(name = "student")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Student extends Actor
{

	/**
	 *主修班级
	 * 双向
	 */
	@ManyToOne
	@JoinColumn(name = "class_id")
	protected Classes class1;

    /**
	 *辅修班级
	 * 双向
	 */
	@ManyToOne
	@JoinColumn(name = "minor_class_id")
	protected MinorClass minorClass;

    /**
     * 学生_课程
     * 双向一对多
     */
    @OneToMany(mappedBy = "student")
    protected List<Student_Course> student_courses;


    public Student() {}
//    public Student(Long num, String name, String sex, Calendar birth, Integer age, Long idNumber, String natives, Long mobile, Calendar workSchoolT, Calendar workTime, Calendar dateToRetire, Integer workAge, String overseasChineseOrNot, Long emergencyMobile, String address, String email, String qq, String weChat, User user, Classes class1, MinorClass minorClass, List<Student_Course> student_courses) {
//        super(num, name, sex, birth, age, idNumber, natives, mobile, workSchoolT, workTime, dateToRetire, workAge, overseasChineseOrNot, emergencyMobile, address, email, qq, weChat, user);
//        this.class1 = class1;
//        this.minorClass = minorClass;
//        this.student_courses = student_courses;
//    }


    public Classes getClass1() {
        return class1;
    }

    public void setClass1(Classes class1) {
        this.class1 = class1;
    }

    public MinorClass getMinorClass() {
        return minorClass;
    }

    public void setMinorClass(MinorClass minorClass) {
        this.minorClass = minorClass;
    }

    public List<Student_Course> getStudent_courses() {
        return student_courses;
    }

    public void setStudent_courses(List<Student_Course> student_courses) {
        this.student_courses = student_courses;
    }
}

