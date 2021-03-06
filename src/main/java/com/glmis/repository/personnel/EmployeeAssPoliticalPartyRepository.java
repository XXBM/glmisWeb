package com.glmis.repository.personnel;

import com.glmis.JpaRepository.MyRepository;
import com.glmis.domain.personnel.EmployeeAssPoliticalParty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Created by ibs on 16/11/6.
 */

@Repository
public interface EmployeeAssPoliticalPartyRepository extends MyRepository<EmployeeAssPoliticalParty,Long> {
    /**  通过选中的下拉框实体id获取所有的自己的所有属性*/
    List<EmployeeAssPoliticalParty> findByEmployeeId(Long id);
    /**  通过选中的下拉框实体id获取所有的自己的所有属性
     并进行分页*/
    Page<EmployeeAssPoliticalParty> findByEmployeeId(Long id, Pageable pageable);
}
