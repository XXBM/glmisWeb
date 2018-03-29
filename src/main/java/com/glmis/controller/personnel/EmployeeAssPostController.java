package com.glmis.controller.personnel;

import com.glmis.domain.personnel.EmployeeAssPost;
import com.glmis.service.personnel.EmployeeAssPostService;
import com.glmis.service.personnel.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dell on 2016/11/7.
 */
@RestController
public class EmployeeAssPostController {

    @Autowired
    EmployeeAssPostService employeeAssPostService;
    @Autowired
    EmployeeService employeeService;
    long empId = 0;

    // 获得所有的职务信息
    @RequestMapping(value = "/displayPost",method = RequestMethod.GET)
    public Map<String,Object> findAll(  @RequestParam(value = "page")Integer page, @RequestParam(value = "rows")Integer size )throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        Pageable pageable = new PageRequest(page-1,size);
        Page<EmployeeAssPost> list = this.employeeAssPostService.findAllT(pageable);
        int total = this.employeeAssPostService.findAllT(pageable).getSize();
        map.put("total",total);
        map.put("rows",list.getContent());
        return map;
    }
    // 根据职员id查询相应的职位信息
    @RequestMapping(value = "/displayPostByEmp",method = RequestMethod.GET)
    public Map<String,Object> findByEmployeeId(@RequestParam("id") Long id,
                                               @RequestParam(value = "page")Integer page,
                                               @RequestParam(value = "rows")Integer size )throws Exception{
        empId = id;
        Map<String,Object> map = new HashMap<String,Object>();
        Pageable pageable = new PageRequest(page-1,size);
        Page<EmployeeAssPost> list = this.employeeAssPostService.findByEmployeeId(id,pageable);
        int total = this.employeeAssPostService.findByEmployeeId(id).size();
        map.put("total",total);
        map.put("rows",list.getContent());
        return map;
    }
    //添加
    @RequestMapping(value = "/addEmploymentPost",method = RequestMethod.POST)
    public Map<String, Object> addEmploymentPost(@RequestBody EmployeeAssPost employeeAssPost)throws Exception{
        employeeAssPost.setEmployee(employeeService.findOne(empId));
        this.employeeAssPostService.add(employeeAssPost);
        Map<String, Object> map = new HashMap<String,Object>();
        map.put("employmentPost", employeeAssPost);
        return map;
    }
    //修改
    @RequestMapping(value = "/updateEmploymentPost",method = RequestMethod.PUT)
    public Map<String, Object> updateEmploymentPost(@RequestBody EmployeeAssPost employeeAssPost)throws Exception{
        employeeAssPost.setEmployee(employeeService.findOne(empId));
        this.employeeAssPostService.update(employeeAssPost);
        Map<String, Object> map = new HashMap<String,Object>();
        map.put("employmentPost", employeeAssPost);
        return map;
    }
    //删除
    @RequestMapping(value = "/deleteEmploymentPost",method = RequestMethod.DELETE)
    public void deleteEmploymentPost(@RequestParam("id") Long id)throws Exception{
        this.employeeAssPostService.deleteById(id);
    }
}
