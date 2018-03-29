package com.glmis.repository.minor;

import com.glmis.JpaRepository.MyRepository;
import com.glmis.domain.minor.Year;
import org.springframework.stereotype.Repository;

/**
 * Created by ibs on 16/11/6.
 */

@Repository
public interface YearRepository  extends MyRepository<Year,Integer>{
}
