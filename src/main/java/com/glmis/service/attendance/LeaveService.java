package com.glmis.service.attendance;

import com.glmis.domain.attendance.Leave;
import com.glmis.repository.attendance.LeaveRepository;
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
public class LeaveService extends BasicService<Leave,Long>{
    @Autowired
    LeaveRepository leaveRepository;
    /**通过选中的下拉框实体id获取所有的自己的所有属性*/
    public List<Leave> findByEmployeeId(Long id){
        return this.leaveRepository.findByEmployeeId(id);
    }
    /**通过选中的下拉框实体id获取所有的自己的所有属性   +  分页显示*/
    public  Page<Leave> findByEmployeeId(Long id, Pageable pageable){
        return this.leaveRepository.findByEmployeeId(id,pageable);
    }
}
