package com.glmis.repository.personnel;

import com.glmis.JpaRepository.MyRepository;
import com.glmis.domain.personnel.Actor;
import org.springframework.stereotype.Repository;

/**
 * Created by dell on 2016/10/21.
 */

@Repository
public interface ActorRepository extends MyRepository<Actor,Long>{
}
