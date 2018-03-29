package com.glmis.domain.personnel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Set;
/**
 * 执业资格证书
 */

@Entity
@Table(name = "p_practisingCertification")
@DynamicInsert(true)
@DynamicUpdate(true)
public class PractisingCertification extends AbstractCategory {
	/**执业资格证书信息
	 */

	@javax.persistence.OneToMany(mappedBy = "practisingCertification")
	@JsonIgnore
	private Set<EmployeeAssPractisingCertification> employeeAssPractisingCertifications;

	public PractisingCertification() {}
	public PractisingCertification(Long id, String no, String description, Set<EmployeeAssPractisingCertification> employeeAssPractisingCertifications) {
		super(id, no, description);
		this.employeeAssPractisingCertifications = employeeAssPractisingCertifications;
	}
	//反序列化时用到的
	public PractisingCertification(Long id) {
		super(id);
	}

	public Set<EmployeeAssPractisingCertification> getEmployeeAssPractisingCertifications() {
		return employeeAssPractisingCertifications;
	}

	public void setEmployeeAssPractisingCertifications(Set<EmployeeAssPractisingCertification> employeeAssPractisingCertifications) {
		this.employeeAssPractisingCertifications = employeeAssPractisingCertifications;
	}
}

