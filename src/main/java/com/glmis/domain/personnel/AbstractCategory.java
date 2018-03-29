package com.glmis.domain.personnel;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;


/**
 * 所有有id/编号/类别的实体类的抽象
 */



@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@DynamicInsert(true)
@DynamicUpdate(true)
public abstract class AbstractCategory implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	protected Long id;
	/**编号
	 */
	@Column(unique = true)
	protected String no;

	public AbstractCategory(Long id,String no, String description) {
		this.id = id;
		this.no = no;
		this.description = description;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	/**类别
	 */
	protected String description;
	public AbstractCategory() {}

	public AbstractCategory(Long id) {
		this.id = id;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

}

