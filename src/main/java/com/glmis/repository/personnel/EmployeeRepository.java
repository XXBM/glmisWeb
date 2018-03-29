package com.glmis.repository.personnel;

import com.glmis.JpaRepository.MyRepository;
import com.glmis.domain.personnel.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by ibs on 16/11/6.
 */

@Repository
public interface EmployeeRepository extends MyRepository<Employee,Long>,JpaSpecificationExecutor<Employee> {
    List<Employee> findByDepartmentId(Long id);
    Page<Employee> findByDepartmentId(Long id, Pageable pageable);
    Page<Employee> findByAttendanceSummariesId(Long id,Pageable pageable);
    List<Employee> findByAttendanceSummariesId(Long id);
    Employee findByName(String name);
    Employee findByUserId(Integer id);
}
