package com.glmis.domain.personnel;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * 学科
 */



@Entity
@Table(name = "p_academicDegreeType")
@DynamicInsert(true)
@DynamicUpdate(true)
public class AcademicDegreeType extends AbstractCategory
{

	public AcademicDegreeType() {}

	public AcademicDegreeType(Long id) {
		super(id);
	}

	public AcademicDegreeType(Long id, String no, String description) {
		super(id, no, description);
	}
}

