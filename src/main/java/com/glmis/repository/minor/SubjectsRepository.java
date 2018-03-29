package com.glmis.repository.minor;

import com.glmis.JpaRepository.MyRepository;
import com.glmis.domain.minor.Subjects;
import org.springframework.stereotype.Repository;

/**
 * Created by dell on 2016/10/17.
 */

@Repository
public interface SubjectsRepository extends MyRepository<Subjects,Integer> {
}
