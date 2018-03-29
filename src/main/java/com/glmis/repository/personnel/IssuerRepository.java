package com.glmis.repository.personnel;

import com.glmis.JpaRepository.MyRepository;
import com.glmis.domain.personnel.Issuer;
import org.springframework.stereotype.Repository;

/**
 * Created by dell on 2016/11/17.
 */

@Repository
public interface IssuerRepository extends MyRepository<Issuer,Long>{
}
