package com.glmis.repository.authority;

import com.glmis.JpaRepository.MyRepository;
import com.glmis.domain.authority.UserRole;
import org.springframework.stereotype.Repository;



@Repository
public interface UserRolesRepository extends MyRepository<UserRole, Integer> {
}