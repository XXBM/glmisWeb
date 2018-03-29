package com.glmis.service.attendance;


import com.glmis.domain.attendance.PrivateLeave;
import com.glmis.repository.attendance.PrivateLeaveRepository;
import com.glmis.service.BasicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ibs on 16/11/6.
 */

@Service
public class PrivateLeaveService extends BasicService<PrivateLeave,Long>{
    @Autowired
    PrivateLeaveRepository privateLeaveRepository;
    /**通过选中的下拉框实体id获取所有的自己的所有属性*/
    public List<PrivateLeave> findByPrivateLeaveSummaryId(Long id){
        return this.privateLeaveRepository.findByAttendanceSummaryId(id);
    }
    /**通过选中的下拉框实体id获取所有的自己的所有属性   +  分页显示*/
    public Page<PrivateLeave> findByPrivateLeaveSummaryId(Long id, Pageable pageable){
        return this.privateLeaveRepository.findByAttendanceSummaryId(id,pageable);
    }
}
