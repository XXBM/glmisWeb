package com.glmis.repository.personnel;

import com.glmis.JpaRepository.MyRepository;
import com.glmis.domain.personnel.Department;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by xuling on 2016/10/11.
 */


@Repository
public interface DepartmentRepository  extends MyRepository<Department,Long> {

    Department findByDepartmentName(String departmentName);
    Department findById(Long id);
    List<Department> findBySchoolId(Long id);
    Page<Department> findBySchoolId(Long id, Pageable pageable);

}
