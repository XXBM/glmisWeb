package com.glmis.repository.attendance;

import com.glmis.JpaRepository.MyRepository;
import com.glmis.domain.attendance.Leave;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by dell on 2017-05-23 .
 */
@Repository
public interface LeaveRepository extends MyRepository<Leave,Long>{
    List<Leave> findByEmployeeId(Long id);
    Page<Leave> findByEmployeeId(Long id, Pageable pageable);
}
