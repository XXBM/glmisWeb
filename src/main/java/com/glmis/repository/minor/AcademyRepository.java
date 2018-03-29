package com.glmis.repository.minor;

import com.glmis.JpaRepository.MyRepository;
import com.glmis.domain.minor.Academy;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Repository;

/**
 * Created by dell on 2016/10/17.
 */

@Repository
public interface AcademyRepository extends MyRepository<Academy,Integer> {

    @EntityGraph(attributePaths = {"minorMajor","major"})
    Academy findById(Integer id);
}
