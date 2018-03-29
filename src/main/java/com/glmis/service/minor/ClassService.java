package com.glmis.service.minor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.glmis.repository.minor.ClassesRepository;

/**
 * Created by ibs on 16/11/6.
 */

@Service
public class ClassService {
    @Autowired
    ClassesRepository classesRepository;
}
