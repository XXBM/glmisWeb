package com.glmis.service.personnel;

import com.glmis.domain.personnel.PreviousWorkExperience;
import com.glmis.repository.personnel.PreviousWorkExperienceRepository;
import com.glmis.service.BasicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Created by ibs on 16/11/6.
 * 来校前工作经历
 */

@Service
public class PreviousWorkExperienceService extends BasicService<PreviousWorkExperience,Long> {
    @Autowired
    PreviousWorkExperienceRepository previousWorkExperienceRepository;
    /**通过选中的下拉框实体id获取所有的自己的所有属性*/
    public List<PreviousWorkExperience> findByEmployeeId(Long id){
        return this.previousWorkExperienceRepository.findByEmployeeId(id);
    }
    /**通过选中的下拉框实体id获取所有的自己的所有属性   +  分页显示*/
    public Page<PreviousWorkExperience> findByEmployeeId(Long id, Pageable pageable){
        return this.previousWorkExperienceRepository.findByEmployeeId(id,pageable);
    }
}
