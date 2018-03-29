package com.glmis.service.personnel;

import com.glmis.domain.personnel.SchoolWorkExperience;
import com.glmis.repository.personnel.SchoolWorkExperienceRepository;
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
public class SchoolWorkExperienceService extends BasicService<SchoolWorkExperience,Long>{
    @Autowired
    SchoolWorkExperienceRepository schoolWorkExperienceRepository;
    /**通过选中的下拉框实体id获取所有的自己的所有属性*/
    public List<SchoolWorkExperience> findByEmployeeId(Long id){
        return this.schoolWorkExperienceRepository.findByEmployeeId(id);
    }
    /**通过选中的下拉框实体id获取所有的自己的所有属性   +  分页显示*/
    public  Page<SchoolWorkExperience> findByEmployeeId(Long id, Pageable pageable){
        return this.schoolWorkExperienceRepository.findByEmployeeId(id,pageable);
    }
}
