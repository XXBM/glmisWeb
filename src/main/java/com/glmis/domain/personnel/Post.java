package com.glmis.domain.personnel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Set;

/**
 * 职位类别
 */

@Entity
@Table(name = "p_post")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Post extends AbstractCategory {
	/**员工职位信息
	 */

	@javax.persistence.OneToMany(mappedBy = "post")
	@JsonIgnore
	private Set<EmployeeAssPost> employeeAssPosts;

	public Post() {}

	public Post(Long id) {
		super(id);
	}

	public Set<EmployeeAssPost> getEmployeeAssPosts() {
		return employeeAssPosts;
	}

	public void setEmployeeAssPosts(Set<EmployeeAssPost> employeeAssPosts) {
		this.employeeAssPosts = employeeAssPosts;
	}

	public void setEmployeeAssPost(Set<EmployeeAssPost> employeeAssPosts) {
		this.employeeAssPosts = employeeAssPosts;
	}
}

