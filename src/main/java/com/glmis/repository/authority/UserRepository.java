package com.glmis.repository.authority;

import com.glmis.JpaRepository.MyRepository;
import com.glmis.domain.authority.User;
import org.springframework.stereotype.Repository;

/**
 * Created by xuling on 2016/10/11.
 */


@Repository
public interface UserRepository extends MyRepository<User, Integer> {
    User findByUserName(String username);
    User findById(Integer id);

}
