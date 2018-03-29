package com.glmis.service.minor;

import com.glmis.repository.minor.AcademyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by dell on 2016/10/17.
 */

@Service
public class AcademyService{
    @Autowired
    AcademyRepository academyReposity;
}
