package com.glmis.repository.personnel;

import com.glmis.JpaRepository.MyRepository;
import com.glmis.domain.personnel.Post;
import org.springframework.stereotype.Repository;

/**
 * Created by dell on 2016/11/17.
 */

@Repository
public interface PostRepository extends MyRepository<Post,Long>{
}