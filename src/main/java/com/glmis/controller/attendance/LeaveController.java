package com.glmis.controller.attendance;

import com.glmis.domain.attendance.Leave;
import com.glmis.domain.personnel.VisitingAcademic;
import com.glmis.service.attendance.LeaveService;
import com.glmis.service.personnel.EmployeeService;
import com.glmis.service.personnel.VisitingAcademicService;
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
public class LeaveController {
    @Autowired
    LeaveService leaveService;
    @Autowired
    EmployeeService employeeService;
    long empId = 0;

    // 根据职员id查询
    @RequestMapping(value = "/displayLeaveByEmp", method = RequestMethod.GET)
    public Map<String, Object> findByEmployeeId(@RequestParam("id") Long id,
                                                @RequestParam(value = "page") Integer page,
                                                @RequestParam(value = "rows") Integer size)throws Exception {
        empId = id;
        Map<String, Object> map = new HashMap<String, Object>();
        Pageable pageable = new PageRequest(page - 1, size);
        Page<Leave> list = this.leaveService.findByEmployeeId(id, pageable);
        int total = this.leaveService.findByEmployeeId(id).size();
        map.put("total", total);
        map.put("rows", list.getContent());
        return map;
    }

    //添加
    @RequestMapping(value = "/addLeave", method = RequestMethod.POST)
    public Map<String, Object> addLeave(@RequestBody Leave leave) throws Exception{
        leave.setEmployee(employeeService.findOne(empId));
        this.leaveService.add(leave);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("leave", leave);
        return map;
    }

    //修改
    @RequestMapping(value = "/updateLeave", method = RequestMethod.PUT)
    public Map<String, Object> updateLeave(@RequestBody Leave leave)throws Exception {
        leave.setEmployee(employeeService.findOne(empId));
        this.leaveService.update(leave);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("leave", leave);
        return map;
    }

    //删除
    @RequestMapping(value = "/deleteLeave", method = RequestMethod.DELETE)
    public void deleteLeave(@RequestParam("id") Long id)throws Exception {
        this.leaveService.deleteById(id);
    }
}

