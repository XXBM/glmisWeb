package com.glmis.domain.personnel;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Table;
/**
 * 资助机构
 */

@Entity
@Table(name = "p_sponsor")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Sponsor extends AbstractCategory
{

	public Sponsor() {}
	public Sponsor(Long id) {
		super(id);
	}
}

