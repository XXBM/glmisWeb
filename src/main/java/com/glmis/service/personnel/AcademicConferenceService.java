package com.glmis.service.personnel;

import com.glmis.domain.personnel.AcademicConference;
import com.glmis.repository.personnel.AcademicConferenceRepository;
import com.glmis.service.BasicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ibs on 16/11/6.
 */

@Service
public class AcademicConferenceService extends BasicService<AcademicConference,Long>{
    @Autowired
    AcademicConferenceRepository academicConferenceRepository;


    public Page<AcademicConference> findAllAcademicConferences(Pageable pageable){
        return this.academicConferenceRepository.findAll(pageable);
    }

    public List<AcademicConference> findAllAcademicConferences(){
        return this.academicConferenceRepository.findAll();
    }

    public List<AcademicConference> findAcademicConferenceByEmployeeId(Long id){
        return this.academicConferenceRepository.findByEmployeeId(id);
    }

    public Page<AcademicConference> findAcademicConferenceByEmployeeId(Long id, Pageable pageable){
        return this.academicConferenceRepository.findByEmployeeId(id, pageable);
    }
}
