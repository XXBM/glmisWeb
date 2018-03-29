package com.glmis.repository.personnel;

import com.glmis.JpaRepository.MyRepository;
import com.glmis.domain.personnel.EmploymentCategory;
import org.springframework.stereotype.Repository;

/**
 * Created by dell on 2016/11/17.
 */

@Repository
public interface EmploymentCategoryRepository extends MyRepository<EmploymentCategory,Long>{
    EmploymentCategory findByDescription(String description);
}
