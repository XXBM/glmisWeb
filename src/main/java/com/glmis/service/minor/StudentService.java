package com.glmis.service.minor;

import com.glmis.repository.minor.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by ibs on 16/11/6.
 */

@Service

public class StudentService {
    @Autowired
    StudentRepository studentReposity;
}
