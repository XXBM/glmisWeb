package com.glmis.domain.personnel;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Table;



/**
 * 主办机构
 */

@Entity
@Table(name = "p_host")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Host extends AbstractCategory {


	public Host() {}

	public Host(Long id) {
		super(id);
	}

}

