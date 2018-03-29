package com.glmis.service.personnel;

import com.glmis.domain.personnel.EmployeeAssAcademicDegree;
import com.glmis.repository.personnel.EmployeeAssAcademicDegreeRepository;
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
public class EmployeeAssAcademicDegreeService extends BasicService<EmployeeAssAcademicDegree,Long>{
    @Autowired
    EmployeeAssAcademicDegreeRepository employeeAssAcademicDegreeRepository;
    /**通过选中的下拉框实体id获取所有的自己的所有属性*/
    public List<EmployeeAssAcademicDegree> findByEmployeeId(Long id){
        return this.employeeAssAcademicDegreeRepository.findByEmployeeId(id);
    }
    public List<EmployeeAssAcademicDegree> findByAcademicDegreeId(Long id){
        return this.employeeAssAcademicDegreeRepository.findByAcademicDegreeId(id);
    }
    /**通过选中的下拉框实体id获取所有的自己的所有属性   +  分页显示*/
    public  Page<EmployeeAssAcademicDegree> findByEmployeeId(Long id, Pageable pageable){
        return this.employeeAssAcademicDegreeRepository.findByEmployeeId(id,pageable);
    }
}
