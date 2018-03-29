package com.glmis.service.attendance;

import com.glmis.domain.attendance.Presence;
import com.glmis.repository.attendance.PresenceRepository;
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
public class PresenceService extends BasicService<Presence,Long>{
    @Autowired
    PresenceRepository presenceRepository;
    /**通过选中的下拉框实体id获取所有的自己的所有属性*/
    public List<Presence> findByPresenceSummaryId(Long id){
        return this.presenceRepository.findByAttendanceSummaryId(id);
    }
    /**通过选中的下拉框实体id获取所有的自己的所有属性   +  分页显示*/
    public Page<Presence> findByPresenceSummaryId(Long id, Pageable pageable){
        return this.presenceRepository.findByAttendanceSummaryId(id,pageable);
    }
}
