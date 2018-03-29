package com.glmis.service.personnel;


import com.glmis.domain.personnel.School;
import com.glmis.repository.personnel.SchoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Created by xuling on 2016/10/11.
 *
 */


@Service
public class SchoolService {
    @Autowired
    SchoolRepository schoolRepository;
    public School findBySchoolName(String schoolName){
        return this.schoolRepository.findBySchoolName(schoolName);
    }

    public List<School> findAll(){return (List<School>)this.schoolRepository.findAll();}
    public String quanju(){
        return "这是个全局共享的方法";
    }
}