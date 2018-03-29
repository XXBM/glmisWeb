package com.glmis.service.personnel;

import com.glmis.domain.personnel.Department;
import com.glmis.repository.personnel.DepartmentRepository;
import com.glmis.service.BasicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by xuling on 2016/10/11.
 */

@Service
public class DepartmentService extends BasicService<Department, Long> {

    @Autowired
    DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
    }

    public Department findByDepartmentName(String departmentname){
        return departmentRepository.findByDepartmentName(departmentname);
    }

//    public List<Department> findAllDepartment(){
//        return this.departmentRepository.findAll();
//    }
    //    通过学校的id查找学院
    public  List<Department> findBySchoolId(Long id){
        return this.departmentRepository.findBySchoolId(id);
    }

    public void addDepartment(Department department){
         this.departmentRepository.save(department);
    }

    public void update(Department department){
        this.departmentRepository.saveAndFlush(department);}

    public Department findById(Long id){
        return departmentRepository.findById(id);
    }

    public void deleteDepartment(Long id){
        this.departmentRepository.delete(id);
    }

    public DepartmentRepository getDepartmentRepository() {
        return departmentRepository;
    }

    public void setDepartmentRepository(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }
    //分页显示
    public Page<Department> findAllDepartment(Pageable pageable){
        return this.departmentRepository.findAll(pageable);
    }
    public  Page<Department> findBySchoolId(Long id,Pageable pageable){
        return this.departmentRepository.findBySchoolId(id,pageable);
    }

}
