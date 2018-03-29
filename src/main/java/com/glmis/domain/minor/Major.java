package com.glmis.domain.minor;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Set;


/**
 * <!-- begin-user-doc -->
 * <!--  end-user-doc  -->
 * @generated
 */


@Entity
@Table(name = "major")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Major extends Classify
{
	/**
	*主修班级
	 * 双向一对多
	**/
	@OneToMany(mappedBy = "major")
	protected Set<Classes> class1;

	/**
	 * 双向一对一关系
	 * 辅修专业
	 */
	@OneToOne(mappedBy = "major",cascade = CascadeType.ALL)
	private MinorMajor minorMajor;

	/**
	 *学院,双向多对一关系
	 **/
	@javax.persistence.ManyToOne
	@javax.persistence.JoinColumn(name = "academy_id")
	protected Academy academy;

	/**
	 *专业学科
	 * 单向多对一
	 */
	@ManyToOne
	@JoinColumn(name = "subject_id")
	protected Subjects subjects;

	/**
	 * 专业所属
	 * 单向多对一
	 */
	@ManyToOne
	@JoinColumn(name = "affiliation_id")
	protected Affiliation affiliation;

	public Major() {}

	public Major(String description, Set<Classes> class1, MinorMajor minorMajor, Subjects subjects, Affiliation affiliation) {
		super(description);
		this.class1 = class1;
		this.minorMajor = minorMajor;
		this.subjects = subjects;
		this.affiliation = affiliation;
	}

	public Set<Classes> getClass1() {
		return class1;
	}

	public void setClass1(Set<Classes> class1) {
		this.class1 = class1;
	}

	public MinorMajor getMinorMajor() {
		return minorMajor;
	}

	public void setMinorMajor(MinorMajor minorMajor) {
		this.minorMajor = minorMajor;
	}

	public Subjects getSubjects() {
		return subjects;
	}

	public void setSubjects(Subjects subjects) {
		this.subjects = subjects;
	}

	public Affiliation getAffiliation() {
		return affiliation;
	}

	public void setAffiliation(Affiliation affiliation) {
		this.affiliation = affiliation;
	}
}

