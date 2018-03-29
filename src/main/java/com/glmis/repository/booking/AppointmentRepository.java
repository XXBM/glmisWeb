package com.glmis.repository.booking;
import com.glmis.JpaRepository.MyRepository;
import com.glmis.domain.booking.Appointment;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Created by dell on 2016/11/6.
 */

@Repository
public interface AppointmentRepository extends MyRepository<Appointment,Integer>{
   List<Appointment> findAll();
}
