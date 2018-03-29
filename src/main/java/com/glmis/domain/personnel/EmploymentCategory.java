package com.glmis.domain.personnel;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;


/**
 * 职工类型
 */

@Entity
@Table(name = "p_employmentCategory")
@DynamicInsert(true)
@DynamicUpdate(true)

public class EmploymentCategory extends AbstractCategory {
	public EmploymentCategory() {}
	public EmploymentCategory(Long id) {
		this.id = id;
	}
	public EmploymentCategory(Long id, String no, String description) {
		super(id, no, description);
	}
}

