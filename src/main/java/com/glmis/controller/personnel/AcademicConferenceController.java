package com.glmis.controller.personnel;

import com.glmis.domain.personnel.AcademicConference;
import com.glmis.service.personnel.AcademicConferenceService;
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
public class AcademicConferenceController {

    @Autowired
    AcademicConferenceService academicConferenceService;

    @Autowired
    EmployeeService employeeService;

    private Long employeeId = -1L;

    @GetMapping("/displayAllAcademicConference")
    public Map<String, Object> displayAll(@RequestParam("page") Integer page,
                                          @RequestParam("rows") Integer size)throws Exception{
        Map<String, Object> map = new HashMap<>();
        Pageable pageable = new PageRequest(page - 1, size);
        Page<AcademicConference> list = this.academicConferenceService.findAllAcademicConferences(pageable);
        int total = this.academicConferenceService.findAllAcademicConferences().size();
        map.put("total", total);
        map.put("rows", list.getContent());
        return map;
    }


    @GetMapping("/displayAcademicConferenceById")
    public Map<String, Object> displayById(@RequestParam("id") Long id,
                                           @RequestParam("page") Integer page,
                                           @RequestParam("rows") Integer size)throws Exception{
        employeeId = id;
        Map<String, Object> map = new HashMap<>();
        Pageable pageable = new PageRequest(page-1, size);
        Page<AcademicConference> list = this.academicConferenceService.findAcademicConferenceByEmployeeId(id, pageable);
        int total = this.academicConferenceService.findAcademicConferenceByEmployeeId(id).size();
        map.put("total", total);
        map.put("rows", list.getContent());
        return map;
    }

    //添加
    @RequestMapping(value = "/addAcademicConference", method = RequestMethod.POST)
    public Map<String, Object> addVisitingAcademic(@RequestBody AcademicConference academicConference)throws Exception {
        academicConference.setEmployee(this.employeeService.findOne(employeeId));
        this.academicConferenceService.add(academicConference);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("visitingAcademic", academicConference);
        return map;
    }

    //修改
    @RequestMapping(value = "/updateAcademicConference", method = RequestMethod.PUT)
    public Map<String, Object> updateVisitingAcademic(@RequestBody AcademicConference academicConference)throws Exception {
        academicConference.setEmployee(this.employeeService.findOne(employeeId));
        this.academicConferenceService.update(academicConference);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("visitingAcademic", academicConference);
        return map;
    }

    //删除
    @RequestMapping(value = "/deleteAcademicConference", method = RequestMethod.DELETE)
    public void deleteQualification(@RequestParam("id") Long id)throws Exception {
        this.academicConferenceService.deleteById(id);
    }
}
