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
 * 学历
 */


@Entity
@Table(name = "p_educationQualification")
@DynamicInsert(true)
@DynamicUpdate(true)
public class EducationQualification extends AbstractCategory {
	@JsonIgnore
	@javax.persistence.OneToMany(mappedBy = "educationQualification", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<EmployeeAssEducationQualification> employeeAssEducationQualifications;
	//无参构造器
	public EducationQualification() {}
	//反序列化时用到的构造器
	public EducationQualification(Long id) {
		this.id = id;
	}
	public Set<EmployeeAssEducationQualification> getEmployeeAssEducationQualifications() {
		return employeeAssEducationQualifications;
	}

	public void setEmployeeAssEducationQualifications(Set<EmployeeAssEducationQualification> employeeAssEducationQualifications) {
		this.employeeAssEducationQualifications = employeeAssEducationQualifications;
	}
}

