package com.glmis.repository.authority;

import com.glmis.JpaRepository.MyRepository;
import com.glmis.domain.authority.Role;
import org.springframework.stereotype.Repository;


/**
 * Created by dell on 2016/10/16.
 */

@Repository
public interface RoleRepository extends MyRepository<Role,Integer> {

}
