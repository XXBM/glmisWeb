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
@Table(name = "minorClass")
@DynamicInsert(true)
@DynamicUpdate(true)
public class MinorClass extends Classify
{
	/**
	 * @generated
	 * @ordered
	 * 一个班级有多个学生
     * 双向关联
	 */
	@javax.persistence.OneToMany(mappedBy = "minorClass")
	protected Set<Student> student;


	/**
	 * 辅修学院，双向多对一
	 */
	@javax.persistence.ManyToOne
	@javax.persistence.JoinColumn(name = "major_id")
	protected MinorMajor minorMajor;

    public MinorClass() {}

    public MinorClass(String description, String grade, Set<Student> student) {
        super(description);
        this.student = student;
    }

    public Set<Student> getStudent() {
        return student;
    }

    public void setStudent(Set<Student> student) {
        this.student = student;
    }

    public MinorMajor getMinorMajor() {
        return minorMajor;
    }

    public void setMinorMajor(MinorMajor minorMajor) {
        this.minorMajor = minorMajor;
    }
}

