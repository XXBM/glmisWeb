package com.glmis.repository.minor;

import com.glmis.JpaRepository.MyRepository;
import com.glmis.domain.minor.Grade;
import org.springframework.stereotype.Repository;

/**
 * Created by ibs on 16/11/6.
 */
@Repository


public interface GradeRepository extends MyRepository<Grade,Integer> {
}
