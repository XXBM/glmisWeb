package com.glmis.repository.personnel;

import com.glmis.JpaRepository.MyRepository;
import com.glmis.domain.personnel.School;

/**
 * Created by dell on 2017-03-21 .
 */

public interface SchoolRepository extends MyRepository<School,Long> {
    School findBySchoolName(String schoolName);
}