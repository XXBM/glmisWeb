package com.glmis.service.personnel;

import com.glmis.domain.personnel.EmployeeAssPractisingCertification;
import com.glmis.repository.personnel.EmployeeAssPractisingCertificationRepository;
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
public class EmployeeAssPractisingCertificationService extends BasicService<EmployeeAssPractisingCertification,Long>{
    @Autowired
    EmployeeAssPractisingCertificationRepository employeeAssPractisingCertificationRepository;
    /**通过选中的下拉框实体id获取所有的自己的所有属性*/
    public List<EmployeeAssPractisingCertification> findByEmployeeId(Long id){
        return this.employeeAssPractisingCertificationRepository.findByEmployeeId(id);
    }
    /**通过选中的下拉框实体id获取所有的自己的所有属性   +  分页显示*/
    public  Page<EmployeeAssPractisingCertification> findByEmployeeId(Long id, Pageable pageable){
        return this.employeeAssPractisingCertificationRepository.findByEmployeeId(id,pageable);
    }
}
