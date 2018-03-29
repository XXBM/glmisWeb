package com.glmis.repository.minor;


import com.glmis.JpaRepository.MyRepository;
import com.glmis.domain.minor.Course;
import org.springframework.stereotype.Repository;


/**
 * Created by ibs on 16/11/6.
 */
@Repository

public interface CourseRepository extends MyRepository<Course,Integer> {
}
