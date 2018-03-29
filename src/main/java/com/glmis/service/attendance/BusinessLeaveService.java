package com.glmis.service.attendance;

import com.glmis.domain.attendance.BusinessLeave;
import com.glmis.repository.attendance.BusinessLeaveRepository;
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
public class BusinessLeaveService extends BasicService<BusinessLeave,Long>{
    @Autowired
    BusinessLeaveRepository businessLeaveRepository;
    /**通过选中的下拉框实体id获取所有的自己的所有属性*/
    public List<BusinessLeave> findByBusinessLeaveSummaryId(Long id){
        return this.businessLeaveRepository.findByAttendanceSummaryId(id);
    }
    /**通过选中的下拉框实体id获取所有的自己的所有属性   +  分页显示*/
    public Page<BusinessLeave> findByBusinessLeaveSummaryId(Long id, Pageable pageable){
        return this.businessLeaveRepository.findByAttendanceSummaryId(id,pageable);
    }
}
