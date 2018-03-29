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
 * 职位类别
 */

@Entity
@Table(name = "p_professionalTitle")
@DynamicInsert(true)
@DynamicUpdate(true)
public class ProfessionalTitle extends AbstractCategory {
	@JsonIgnore
	@javax.persistence.OneToMany(mappedBy = "professionalTitle", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<EmployeeAssProfessionalTitle> employeeAssProfessionalTitles;


	public ProfessionalTitle() {}

	public ProfessionalTitle(Long id) {
		super(id);
	}

	public ProfessionalTitle(Long id, String no, String description) {
		super(id, no, description);
	}

	public Set<EmployeeAssProfessionalTitle> getEmployeeAssProfessionalTitles() {
		return employeeAssProfessionalTitles;
	}

	public void setEmployeeAssProfessionalTitles(Set<EmployeeAssProfessionalTitle> employeeAssProfessionalTitles) {
		this.employeeAssProfessionalTitles = employeeAssProfessionalTitles;
	}
}

