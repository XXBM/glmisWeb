package com.glmis.controller.attendance;

import com.glmis.domain.attendance.UniversityAbsence;
import com.glmis.domain.message.Result;
import com.glmis.domain.personnel.Employee;
import com.glmis.service.attendance.AttendanceExportExcelService;
import com.glmis.service.attendance.UniversityAbsenceService;
import com.glmis.service.personnel.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;


/**
 * Created by dell on 2017-05-23 .
 */
@RestController
public class AttendanceExportExcelController {
    final Logger logger = LoggerFactory.getLogger(AttendanceExportExcelController.class);
    @Autowired
    AttendanceExportExcelService attendanceExportExcelService;
    @GetMapping("/attendanceExportExcel")
    public Result attendanceExportExcel(
            @RequestParam("attendanceStartTime")String attendanceStartTime,
            @RequestParam("attendanceEndTime")String attendanceEndTime,
            HttpServletResponse response,
            HttpServletRequest request) throws ParseException, IOException {
        //学校缺勤人员名单
        Specification<UniversityAbsence> specification = universityAbsenceService.attendanceExportExcel(attendanceStartTime,attendanceEndTime);
        List<UniversityAbsence> universityAbsenceList = this.universityAbsenceService.findBySepc(specification);
        List<Employee> employeeList = new ArrayList<>();
        for(int i=0;i<universityAbsenceList.size();i++){
            employeeList.add(universityAbsenceList.get(i).getEmployee());
        }
        List<Employee> universityAbsenceEmployeeList = new ArrayList(new HashSet(employeeList));
        //某次考勤的候选人
        Specification<Employee> specificationC = employeeService.findCandidates();
        List<Employee> candidates = this.employeeService.findBySepc(specificationC);

        //当前在岗人员-universityAbsenceEmployeeList=全勤人员名单
//        List<Employee> presenceEmployeeList = new ArrayList<>();
//        System.out.println(universityAbsenceEmployeeList.size());
//        System.out.println(candidates.size());
        candidates.removeAll(universityAbsenceEmployeeList);


//        if(universityAbsenceEmployeeList.size()!=0 && candidates.size()!=0){
//            for(int i=0;i<universityAbsenceEmployeeList.size();i++){
//                for(int x=0;x<candidates.size();x++){
//                    //！！！
//                    if(!candidates.get(x).equals(universityAbsenceEmployeeList.get(i))){
//                        System.out.println(candidates.get(x).getId()+"--------------");
//                        presenceEmployeeList.add(candidates.get(x));
//                    }
//                }
//            }
//        }

        //查询要输入的
        Map<Long,List<UniversityAbsence>> universityMap = new HashMap<>();
        for(int y=0;y<universityAbsenceEmployeeList.size();y++){
            Specification<UniversityAbsence> s = universityAbsenceService.findUniversityAbsenceBySpec(universityAbsenceEmployeeList.get(y).getId(),attendanceStartTime,attendanceEndTime);
            List<UniversityAbsence> u = this.universityAbsenceService.findBySepc(s);
            universityMap.put(universityAbsenceEmployeeList.get(y).getId(),u);
        }
        return this.attendanceExportExcelService.exportExcel(candidates,universityAbsenceEmployeeList,attendanceStartTime,universityMap,response,request);
    }

    @Autowired
    EmployeeService employeeService;
    @Autowired
    UniversityAbsenceService universityAbsenceService;

    @RequestMapping("/attendanceExportExcelTest")
    public List<UniversityAbsence> attendanceExportExcelTest(){
        Specification<UniversityAbsence> specification = universityAbsenceService.attendanceExportExcel("2017-06-01","2017-06-30");
        List<UniversityAbsence> universityAbsenceList = this.universityAbsenceService.findBySepc(specification);
        List<Employee> employeeList = new ArrayList<>();
        for(int i=0;i<universityAbsenceList.size();i++){
            employeeList.add(universityAbsenceList.get(i).getEmployee());
        }
        List newList = new ArrayList(new HashSet(employeeList));
        return newList;
    }

    @RequestMapping("/attendanceExportExcelTest2")
    public List<UniversityAbsence> attendanceExportExcelTest2(){
        Specification<UniversityAbsence> specification = universityAbsenceService.findUniversityAbsenceBySpec((long)163840,"2017-06-01","2017-06-30");
        List<UniversityAbsence> universityAbsenceList = this.universityAbsenceService.findBySepc(specification);
        List<Employee> employeeList = new ArrayList<>();
//        for(int i=0;i<universityAbsenceList.size();i++){
//            employeeList.add(universityAbsenceList.get(i).getEmployee());
//        }
//        List newList = new ArrayList(new HashSet(employeeList));
//        return newList;
        logger.debug(""+universityAbsenceList.size());
        return universityAbsenceList;
    }
}
