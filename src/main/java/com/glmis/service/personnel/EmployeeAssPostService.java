package com.glmis.service.personnel;

import com.glmis.domain.personnel.EmployeeAssPost;
import com.glmis.repository.personnel.EmployeeAssPostRepository;
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
public class EmployeeAssPostService extends BasicService<EmployeeAssPost,Long>{
    @Autowired
    EmployeeAssPostRepository employeeAssPostRepository;
    /**通过选中的下拉框实体id获取所有的自己的所有属性*/
    public List<EmployeeAssPost> findByEmployeeId(Long id){
        return this.employeeAssPostRepository.findByEmployeeId(id);
    }
    /**通过选中的下拉框实体id获取所有的自己的所有属性   +  分页显示*/
    public  Page<EmployeeAssPost> findByEmployeeId(Long id, Pageable pageable){
        return this.employeeAssPostRepository.findByEmployeeId(id,pageable);
    }
}
