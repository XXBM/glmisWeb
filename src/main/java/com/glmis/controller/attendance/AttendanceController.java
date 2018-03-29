package com.glmis.controller.attendance;

import com.glmis.domain.attendance.Attendance;
import com.glmis.domain.attendance.AttendanceSummary;
import com.glmis.domain.personnel.Employee;
import com.glmis.service.attendance.AttendanceService;
import com.glmis.service.attendance.AttendanceSummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * @Author: LihuaHuang
 * @Description:
 * @Date: Created in 2017-05-27
 * @Modified by:
 */
@RestController
public class AttendanceController {
    @Autowired
    AttendanceService attendanceService;
    @Autowired
    AttendanceSummaryService attendanceSummaryService;


    @RequestMapping(value = "/displaySpecificEmployee", method = RequestMethod.GET)
    public List<Employee> displaySpecificEmployee(@RequestParam(value = "id") Long id) throws Exception{
        AttendanceSummary attendanceSummary = attendanceSummaryService.findOne(id);
        List<Employee> candidates = attendanceSummary.getCandidates();
        List<Attendance> attendances = attendanceSummary.getAttendances();
        Collection<Employee> trueEmployees = new ArrayList<>();
        for(int i=0;i<attendances.size();i++){
            trueEmployees.add(attendances.get(i).getEmployee());
        }
        candidates.removeAll(trueEmployees);
        return candidates;
    }

    @RequestMapping(value = "/displayAllAttendance", method = RequestMethod.GET)
    public Map<String, Object> findAllAttendanceSummary(@RequestParam(value = "page") Integer page,
                                             @RequestParam(value = "rows") Integer size) throws Exception{
        Page<Attendance> list = this.attendanceService.findAllT(new PageRequest(page - 1, size));
        Map<String, Object> map = new HashMap<String, Object>();
        int total = this.attendanceService.findAllT().size();
        map.put("total", total);
        map.put("rows", list.getContent());
        return map;
    }




    /**
     *
     * @param id
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "/displayAttendanceBySummary",method = RequestMethod.GET)
    public Map<String,Object> findByEmployeeId(@RequestParam("id") Long id,
                                               @RequestParam(value = "page")Integer page,
                                               @RequestParam(value = "rows")Integer size ){
        //empId = id;
        Map<String,Object> map = new HashMap<String,Object>();
        Pageable pageable = new PageRequest(page-1,size);
        Page<Attendance> list = this.attendanceService.findByAttendanceSummaryId(id,pageable);
        int total = this.attendanceService.findByAttendanceSummaryId(id).size();
        map.put("total",total);
        map.put("rows",list.getContent());
        return map;
    }
    @RequestMapping(value = "/displayAttByCan",method = RequestMethod.GET)
    public Map<String,Object> displayAttByCan(
            @RequestParam("candidateId")Long candidateId,
            @RequestParam(";summaryId")Long summaryId,
            @RequestParam(value = "page")Integer page,
            @RequestParam(value = "rows")Integer size ){
        Map<String,Object> map = new HashMap<>();
        Pageable pageable = new PageRequest(page-1,size);
        Specification<Attendance> specification = this.attendanceService.findAttendanceSpec(candidateId,summaryId);
        Page<Attendance> list = this.attendanceService.findBySepc(specification,pageable);
        int total = this.attendanceService.findBySepc(specification).size();
        map.put("total",total);
        map.put("rows",list.getContent());
        return map;
    }
}
