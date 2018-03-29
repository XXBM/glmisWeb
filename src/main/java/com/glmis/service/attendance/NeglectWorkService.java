package com.glmis.service.attendance;

import com.glmis.domain.attendance.NeglectWork;
import com.glmis.repository.attendance.NeglectWorkRepository;
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
public class NeglectWorkService extends BasicService<NeglectWork,Long>{
    @Autowired
    NeglectWorkRepository neglectWorkRepository;
    /**通过选中的下拉框实体id获取所有的自己的所有属性*/
    public List<NeglectWork> findByNeglectWorkSummaryId(Long id){
        return this.neglectWorkRepository.findByAttendanceSummaryId(id);
    }
    /**通过选中的下拉框实体id获取所有的自己的所有属性   +  分页显示*/
    public Page<NeglectWork> findByNeglectWorkSummaryId(Long id, Pageable pageable){
        return this.neglectWorkRepository.findByAttendanceSummaryId(id,pageable);
    }
}
