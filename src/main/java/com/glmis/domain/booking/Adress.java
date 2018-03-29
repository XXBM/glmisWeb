package com.glmis.domain.booking;


import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

/**
 * 地点
 */

@Entity
@Table(name = "adress")
@DynamicUpdate(true)
@DynamicInsert(true)
public class Adress extends BookingResources
{
	public Adress() {}
	public Adress(Integer id){this.id=id;}
	public Adress(String num, String name) {
		super(num, name);
	}
//	@OneToMany(mappedBy = "bookingAdress",fetch = FetchType.EAGER)
//	protected Set<Appointment> appointments;


}

