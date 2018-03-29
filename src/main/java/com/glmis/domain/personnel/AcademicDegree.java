package com.glmis.domain.personnel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Table;
import java.util.Set;


/**
 * 学位
 */



@Entity
@Table(name = "p_academicDegree")
@DynamicInsert(true)
@DynamicUpdate(true)
public class AcademicDegree extends AbstractCategory {
	/**
	 * 学位信息记录
	 */
	@JsonIgnore
	@javax.persistence.OneToMany(fetch = FetchType.LAZY, mappedBy = "academicDegree", cascade = CascadeType.ALL)
	private Set<EmployeeAssAcademicDegree> employeeAssAcademicDegrees;
	public AcademicDegree() {}

	public AcademicDegree(Long id) {
		super(id);
	}
	public AcademicDegree(Long id, String no, String description) {
		super(id, no, description);
	}

	public Set<EmployeeAssAcademicDegree> getEmployeeAssAcademicDegrees() {
		return employeeAssAcademicDegrees;
	}

	public void setEmployeeAssAcademicDegrees(Set<EmployeeAssAcademicDegree> employeeAssAcademicDegrees) {
		this.employeeAssAcademicDegrees = employeeAssAcademicDegrees;
	}
}

