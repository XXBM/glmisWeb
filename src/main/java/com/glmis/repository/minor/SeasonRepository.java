package com.glmis.repository.minor;

import com.glmis.JpaRepository.MyRepository;
import com.glmis.domain.minor.Season;
import org.springframework.stereotype.Repository;


/**
 * Created by ibs on 16/11/6.
 */
@Repository
public interface SeasonRepository extends MyRepository<Season,Integer>{
}
