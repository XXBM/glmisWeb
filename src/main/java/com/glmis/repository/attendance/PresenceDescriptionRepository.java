package com.glmis.repository.attendance;

import com.glmis.JpaRepository.MyRepository;
import com.glmis.domain.attendance.PresenceDescription;
import org.springframework.stereotype.Repository;

/**
 * Created by dell on 2017-05-23 .
 */
@Repository
public interface PresenceDescriptionRepository extends MyRepository<PresenceDescription,Long>{

}
