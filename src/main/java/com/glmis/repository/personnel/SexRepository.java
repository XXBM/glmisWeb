package com.glmis.repository.personnel;

import com.glmis.JpaRepository.MyRepository;
import com.glmis.domain.personnel.Sex;
import org.springframework.stereotype.Repository;


/**
 * Created by ibs on 16/11/6.
 */

@Repository
public interface SexRepository extends MyRepository<Sex,Long> {
}
