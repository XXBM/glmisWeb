package com.glmis.repository.minor;

import com.glmis.JpaRepository.MyRepository;
import com.glmis.domain.minor.Affiliation;
import org.springframework.stereotype.Repository;


/**
 * Created by dell on 2016/10/21.
 */

@Repository
public interface AffiliationRepository extends MyRepository<Affiliation,Integer> {
}
