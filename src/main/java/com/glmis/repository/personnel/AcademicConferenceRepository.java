package com.glmis.repository.personnel;

import com.glmis.JpaRepository.MyRepository;
import com.glmis.domain.personnel.AcademicConference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Created by ibs on 16/11/6.
 */

@Repository
public interface AcademicConferenceRepository extends MyRepository<AcademicConference,Long> {

    List<AcademicConference> findByEmployeeId(Long id);

    Page<AcademicConference> findByEmployeeId(Long id, Pageable pageable);

}
