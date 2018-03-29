package com.glmis.domain.minor;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * 专业学科
 * @generated
 */

@Entity
@Table(name = "subject")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Subjects extends Classify
{
//	/**
//	 * 辅修专业
//	 */
//	@javax.persistence.OneToMany(mappedBy = "subject")
//	protected Set<MinorMajor> major;

	public Subjects(){}

//	public Set<MinorMajor> getMajor() {
//		return major;
//	}
//
//	public void setMajor(Set<MinorMajor> major) {
//		this.major = major;
//	}
}

