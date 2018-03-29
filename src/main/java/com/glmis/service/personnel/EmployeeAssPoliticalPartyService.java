package com.glmis.service.personnel;

import com.glmis.domain.personnel.EmployeeAssPoliticalParty;
import com.glmis.repository.personnel.EmployeeAssPoliticalPartyRepository;
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
public class EmployeeAssPoliticalPartyService extends BasicService<EmployeeAssPoliticalParty,Long> {
    @Autowired
    EmployeeAssPoliticalPartyRepository employeeAssPoliticalPartyRepository;
    /**通过选中的下拉框实体id获取所有的自己的所有属性*/
    public List<EmployeeAssPoliticalParty> findByEmployeeId(Long id){
        return this.employeeAssPoliticalPartyRepository.findByEmployeeId(id);
    }
    /**通过选中的下拉框实体id获取所有的自己的所有属性   +  分页显示*/
    public Page<EmployeeAssPoliticalParty> findByEmployeeId(Long id, Pageable pageable){
        return this.employeeAssPoliticalPartyRepository.findByEmployeeId(id,pageable);
    }
}
