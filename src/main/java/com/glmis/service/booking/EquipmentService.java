package com.glmis.service.booking;

import com.glmis.domain.booking.Equipment;
import com.glmis.repository.booking.EquipmentRepository;
import com.glmis.service.BasicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by xuling on 2016/11/11.
 */

@Service
public class EquipmentService extends BasicService<Equipment,Integer>{
    @Autowired
    EquipmentRepository equipmentRepository;
}
