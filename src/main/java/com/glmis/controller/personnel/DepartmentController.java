package com.glmis.controller.personnel;

import com.glmis.domain.personnel.Department;
import com.glmis.service.personnel.DepartmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xuling on 2016/10/11.
 */

@RestController
public class DepartmentController {
    final Logger logger = LoggerFactory.getLogger(DepartmentController.class);
    @Autowired
    DepartmentService departmentService;

    /**
     * 获取到所有的部门
     */
    @RequestMapping(value = "/findAllDepartment", method = RequestMethod.GET)
    public List<Department> findDepartment()throws Exception {
        List<Department> departments = departmentService.findAllT();
        logger.debug("------------{}{}" , departments.size() , departments.toString());
        return departments;
    }

    // 根据学校查询相应的学院名
    @RequestMapping(value = "/display", method = RequestMethod.GET)
    public Map<String, java.lang.Object> findBySchoolId(@RequestParam("id") Long id, @RequestParam(value = "page") Integer page, @RequestParam(value = "rows") Integer size)throws Exception {
        Map<String, java.lang.Object> map = new HashMap<String, java.lang.Object>();
        Pageable pageable = new PageRequest(page - 1, size);
        Page<Department> list = this.departmentService.findBySchoolId(id, new PageRequest(page - 1, size));
        int total = this.departmentService.findBySchoolId(id).size();
        map.put("total", total);
        map.put("rows", list.getContent());
        return map;
    }

    //实现分页
    @RequestMapping(value = "/displayAll", method = RequestMethod.GET)
    public Map<String, java.lang.Object> findAllDepartments(@RequestParam(value = "page") Integer page, @RequestParam(value = "rows") Integer size)throws Exception {
        Page<Department> list = this.departmentService.findAllDepartment(new PageRequest(page - 1, size));
        Map<String, java.lang.Object> map = new HashMap<String, java.lang.Object>();
        int total = this.departmentService.findAllT().size();
        map.put("total", total);
        map.put("rows", list.getContent());
        return map;
    }

    //添加一个新的学院  完成 增
    @RequestMapping(value = "/addDepartment", method = RequestMethod.POST)
    public Map<String, java.lang.Object> addDepartment(@RequestBody Department department)throws Exception {
        this.departmentService.addDepartment(department);
        Map<String, java.lang.Object> map = new HashMap<String, java.lang.Object>();
        map.put("department", department);
        return map;
    }

    //修改学院信息    完成 改
    @RequestMapping(value = "/updateDepartment", method = RequestMethod.PUT)
    public Map<String, java.lang.Object> updateDepartment(@RequestBody Department department)throws Exception {
        this.departmentService.update(department);
        Map<String, java.lang.Object> map = new HashMap<String, java.lang.Object>();
        map.put("department", department);
        return map;
    }

    //删除一个学院   完成 删
    @RequestMapping(value = "/deleteDepartment", method = RequestMethod.DELETE)
    public void deleteDepartment(@RequestParam("id") Long id)throws Exception {
        this.departmentService.deleteDepartment(id);
    }
}
