package com.glmis.repository.personnel;

import com.glmis.JpaRepository.MyRepository;
import com.glmis.domain.personnel.YesOrNo;
import org.springframework.stereotype.Repository;


/**
 * Created by ibs on 16/11/6.
 */

@Repository
public interface YesOrNoRepository extends MyRepository<YesOrNo,Long> {
}
