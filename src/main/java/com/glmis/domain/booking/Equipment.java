package com.glmis.domain.booking;


import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 设备
 */

@Entity
@Table(name = "equipment")
@DynamicUpdate(true)
@DynamicInsert(true)
public class Equipment extends BookingResources
{
	public Equipment() {}
	public Equipment(Integer id) {
		this.id = id;
	}

	public Equipment(String num, String name) {
		super(num, name);
	}
//	@OneToMany(mappedBy = "bookingEquipments",fetch = FetchType.EAGER)
//	protected Set<Appointment> appointments;
//	public Equipment(String num, String name, Set<Appointment> appointments) {
//		super(num, name);
//		this.appointments=appointments;
//	}
//
//	public Set<Appointment> getAppointments() {
//		return appointments;
//	}

//	public void setAppointments(Set<Appointment> appointments) {
//		this.appointments = appointments;
//	}
}

