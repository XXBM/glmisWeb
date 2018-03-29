package com.glmis.domain.personnel;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * 审核结果
 */

@Entity
@Table(name = "p_reviewResult")
@DynamicInsert(true)
@DynamicUpdate(true)

public class ReviewResult extends AbstractCategory {
	public ReviewResult() {}

	public ReviewResult(Long id) {
		super(id);
	}

	public ReviewResult(Long id, String no, String description) {
		super(id, no, description);
	}
}

