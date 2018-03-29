package com.glmis.domain.personnel;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 职位级别
 */

@Entity
@Table(name = "p_rank")
@DynamicInsert(true)
@DynamicUpdate(true)
public class PositionRank extends AbstractCategory {


	public PositionRank() {}
	public PositionRank(Long id) {
		super(id);
	}

}

