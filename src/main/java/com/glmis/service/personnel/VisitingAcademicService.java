package com.glmis.service.personnel;

import com.glmis.domain.personnel.VisitingAcademic;
import com.glmis.repository.personnel.VisitingAcademicRepository;
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
public class VisitingAcademicService extends BasicService<VisitingAcademic,Long>{
    @Autowired
    VisitingAcademicRepository visitingAcademicRepository;
    /**通过选中的下拉框实体id获取所有的自己的所有属性*/
    public List<VisitingAcademic> findByEmployeeId(Long id){
        return this.visitingAcademicRepository.findByEmployeeId(id);
    }
    /**通过选中的下拉框实体id获取所有的自己的所有属性   +  分页显示*/
    public  Page<VisitingAcademic> findByEmployeeId(Long id, Pageable pageable){
        return this.visitingAcademicRepository.findByEmployeeId(id,pageable);
    }
}
