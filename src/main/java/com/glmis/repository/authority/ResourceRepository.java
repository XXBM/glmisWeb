package com.glmis.repository.authority;

import com.glmis.JpaRepository.MyRepository;
import com.glmis.domain.authority.Resource;
import org.springframework.stereotype.Repository;

/**
 * Created by ibs on 16/11/6.
 */

@Repository

public interface ResourceRepository extends MyRepository<Resource,Integer>{
}
