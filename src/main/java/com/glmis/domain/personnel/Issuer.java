package com.glmis.domain.personnel;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *发证机构
 */

@Entity
@Table(name = "p_issuer")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Issuer extends AbstractCategory {


	public Issuer() {}
	public Issuer(Long id) {
		super(id);
	}
	public Issuer(Long id, String no, String description) {
		super(id, no, description);
	}
}

