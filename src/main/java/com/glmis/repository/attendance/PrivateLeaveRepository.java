package com.glmis.repository.attendance;

import com.glmis.JpaRepository.MyRepository;
import com.glmis.domain.attendance.PrivateLeave;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by dell on 2017-05-23 .
 */
@Repository
public interface PrivateLeaveRepository extends MyRepository<PrivateLeave,Long>{
    /**  通过选中的下拉框实体id获取所有的自己的所有属性*/
    List<PrivateLeave> findByAttendanceSummaryId(Long id);
    /**  通过选中的下拉框实体id获取所有的自己的所有属性
     并进行分页*/
    Page<PrivateLeave> findByAttendanceSummaryId(Long id, Pageable pageable);
}
