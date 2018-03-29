package com.glmis.service.booking;

import com.glmis.domain.booking.Adress;
import com.glmis.repository.booking.AdressRepository;
import com.glmis.service.BasicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by xuling on 2016/11/11.
 */

@Service
public class AdressService extends BasicService<Adress,Integer>{
    @Autowired
    AdressRepository adressRepository;
}
