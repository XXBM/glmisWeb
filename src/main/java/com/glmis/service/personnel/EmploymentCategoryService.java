package com.glmis.service.personnel;

import com.glmis.domain.personnel.EmploymentCategory;
import com.glmis.repository.personnel.EmploymentCategoryRepository;
import com.glmis.service.BasicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by dell on 2016/11/17.
 */

@Service
public class EmploymentCategoryService extends BasicService<EmploymentCategory,Long>{
    @Autowired
    EmploymentCategoryRepository employmentCategoryRepository;
    public EmploymentCategory findByDescription(String description){
        return employmentCategoryRepository.findByDescription(description);
    }
}
