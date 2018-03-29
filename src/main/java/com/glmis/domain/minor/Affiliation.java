package com.glmis.domain.minor;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * 专业所属
 * @generated
 */


@Entity
@Table(name = "affiliation")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Affiliation extends Classify
{
//	/**
//	 * 辅修专业
//	 */
//	@javax.persistence.OneToMany(mappedBy = "affiliation")
//	protected Set<MinorMajor> major;


	public Affiliation(){}

//	public Set<MinorMajor> getMajor() {
//		return major;
//	}
//
//	public void setMajor(Set<MinorMajor> major) {
//		this.major = major;
//	}
}

