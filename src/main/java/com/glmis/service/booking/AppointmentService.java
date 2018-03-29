package com.glmis.service.booking;

import com.glmis.domain.booking.Appointment;
import com.glmis.repository.booking.AppointmentRepository;
import com.glmis.service.BasicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by ibs on 16/11/6.
 */

@Service
public class AppointmentService extends BasicService<Appointment,Integer>{
    @Autowired
    AppointmentRepository appointmentRepository;
}
