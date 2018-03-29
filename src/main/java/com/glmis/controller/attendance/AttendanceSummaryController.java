package com.glmis.controller.attendance;

import com.glmis.domain.attendance.AttendanceSummary;
import com.glmis.domain.personnel.Employee;
import com.glmis.service.attendance.AttendanceSummaryService;
import com.glmis.service.personnel.EmployeeService;
import com.glmis.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2017-05-23 .
 */
@Controller
@ResponseBody
//@RestController
public class AttendanceSummaryController {
    @Autowired
    AttendanceSummaryService attendanceSummaryService;
    @Autowired
    EmployeeService employeeService;

    //获取当前Actor
    public String getUserName(javax.servlet.http.HttpServletRequest request){
        String userName = Utils.getCurrentUserName(request);//得到当前User的username
        return userName;
    }
    @RequestMapping(value = "/findAllAttendanceSummaries", method = RequestMethod.GET)
    public List<AttendanceSummary> findAllDescriptions() {
        List<AttendanceSummary> attendanceSummaries = attendanceSummaryService.findAllT();
        return attendanceSummaries;
    }

    @RequestMapping(value = "/displayAllAttendanceSummary", method = RequestMethod.GET)
    public Map<String, Object> findAllAttendanceSummary(@RequestParam(value = "page") Integer page,
                                             @RequestParam(value = "rows") Integer size) throws Exception{
        Page<AttendanceSummary> list = this.attendanceSummaryService.findAllAttendanceSummary(new PageRequest(page - 1, size));
        Map<String, Object> map = new HashMap<String, Object>();
        int total = this.attendanceSummaryService.findAllT().size();
        map.put("total", total);
        map.put("rows", list.getContent());
        return map;
    }
    //根据查询条件显示选中员工的论文信息
    @RequestMapping(value = "/findSummary",method = RequestMethod.GET)
    public Map<String,Object> dispThesisQuerry(
                                               @RequestParam("startTime")String startTime,
                                               @RequestParam(";endTime")String endTime,
                                               @RequestParam(value = "page")Integer page,
                                               @RequestParam(value = "rows")Integer size ){
        Map<String,Object> map = new HashMap<>();
        Pageable pageable = new PageRequest(page-1,size);
        Specification<AttendanceSummary> specification = this.attendanceSummaryService.summaryQuery(startTime,endTime);
        Page<AttendanceSummary> list = this.attendanceSummaryService.findBySepc(specification,pageable);
        int total = this.attendanceSummaryService.findBySepc(specification).size();
        map.put("total",total);
        map.put("rows",list.getContent());
        return map;
    }

    //添加
    @RequestMapping(value = "/addAttendanceSummary", method = RequestMethod.POST)
    public Map<String, Object> addAttendanceSummary(
            javax.servlet.http.HttpServletRequest request,
            @RequestBody AttendanceSummary attendanceSummary)throws Exception {
        //考勤人
        attendanceSummary.setAttendanceManager(employeeService.findOne((long)1));
        //本次考勤的候选人
        Specification<Employee> specification = employeeService.findCandidates();
        List<Employee> candidates = this.employeeService.findBySepc(specification);
        attendanceSummary.setCandidates(candidates);
        this.attendanceSummaryService.add(attendanceSummary);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("attendanceSummary", attendanceSummary);
        return map;
    }

    //修改
    @RequestMapping(value = "/updateAttendanceSummary", method = RequestMethod.PUT)
    public Map<String, Object> updateAttendanceSummary(
            javax.servlet.http.HttpServletRequest request,
            @RequestBody AttendanceSummary attendanceSummary)throws Exception {
        attendanceSummary.setAttendanceManager(employeeService.findOne((long)1));
        Specification<Employee> specification = employeeService.findCandidates();
        List<Employee> candidates = this.employeeService.findBySepc(specification);
        attendanceSummary.setCandidates(candidates);
        this.attendanceSummaryService.update(attendanceSummary);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("attendanceSummary", attendanceSummary);
        return map;
    }

    //删除
    @RequestMapping(value = "/deleteAttendanceSummary", method = RequestMethod.DELETE)
    public void deleteAttendanceSummary(@RequestParam("id") Long id)throws Exception {
        this.attendanceSummaryService.findOne(id).setCandidates(null);
        this.attendanceSummaryService.deleteById(id);
    }
}
