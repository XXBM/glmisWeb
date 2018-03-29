package com.glmis.domain.personnel;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * 民族
 */

@Entity
@Table(name = "p_nation")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Nation extends AbstractCategory {
	public Nation() {}

	public Nation(Long id) {
		this.id = id;
	}
	public Nation(Long id, String no, String description) {
		super(id, no, description);
	}
}

