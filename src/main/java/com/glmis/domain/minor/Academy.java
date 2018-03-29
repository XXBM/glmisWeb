package com.glmis.domain.minor;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Set;


/**
 * 学院
 * @generated
 */


@Entity
@Table(name = "academy")
@DynamicInsert(true)
@DynamicUpdate(true)
//@NamedEntityGraph(
//		name = "Academy.detail", attributeNodes = {
//		@NamedAttributeNode("minorMajor"),
//		@NamedAttributeNode("major")})
public class Academy extends Classify
{
	/**
	 * 主修专业
	 * 双向一对多
	 */
	@OneToMany(mappedBy = "academy")
	private Set<Major> major;

//	/**
//	 * 学生
//	 */
//	@javax.persistence.OneToMany(mappedBy = "academy")
//	protected Set<Student> student;


	public Academy() {
	}
	public Academy(String description,Set<Major> major) {
		super(description);
		this.major = major;
	}

	public Set<Major> getMajor() {
		return major;
	}

	public void setMajor(Set<Major> major) {
		this.major = major;
	}
}

