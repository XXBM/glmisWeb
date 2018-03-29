package com.glmis.domain.minor;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Set;



/**
 * <!-- begin-user-doc -->
 * <!--  end-user-doc  -->
 * @generated
 */


@Entity
@Table(name = "classes")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Classes extends Classify
{
	/**
	 * @generated
	 * @ordered
	 * 一个班级有多个学生
	 */

	@javax.persistence.OneToMany(mappedBy = "class1")
	protected Set<Student> student;
	/**
	 * 多个班级对应一个专业
     * 双向关联
	 */
	@javax.persistence.ManyToOne
	@javax.persistence.JoinColumn(name = "major_id")
	protected Major major;



    public Classes() {}

    public Classes(String description, Set<Student> student) {
        super(description);
        this.student = student;
    }

    public Set<Student> getStudent() {
        return student;
    }

    public void setStudent(Set<Student> student) {
        this.student = student;
    }

    public Major getMajor() {
        return major;
    }

    public void setMajor(Major major) {
        this.major = major;
    }
}

