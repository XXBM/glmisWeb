package com.glmis.service.booking;
import com.glmis.domain.booking.Time;
import com.glmis.repository.booking.TimeRepository;
import com.glmis.service.BasicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by xuling on 2016/11/11.
 */

@Service
public class TimeService extends BasicService<Time,Integer> {
    @Autowired
    private TimeRepository timeRepository;
}
