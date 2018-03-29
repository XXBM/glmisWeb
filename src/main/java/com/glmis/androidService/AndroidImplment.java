package com.glmis.androidService;

import com.glmis.androidService.androidModel.AttendanceSummaryForAndroid;
import com.glmis.androidService.androidModel.EmployeeForAndroid;
import com.glmis.androidService.androidModel.UserForAndroid;
import com.glmis.domain.attendance.*;
import com.glmis.domain.authority.User;
import com.glmis.domain.personnel.Employee;
import com.glmis.utils.Utils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by 杨云召 on 26/05/2017.
 * 为 android 端提供的接口
 */

@Controller
public class AndroidImplment extends AndroidBase {


    /**
     * 为android端提供登录功能
     *
     * @param currentUser android端传递过来的user对象
     * @return 返回当前user，如果没有匹配的用户名和密码则返回null
     */
    @RequestMapping(value = "/login.json", method = RequestMethod.POST)
    @ResponseBody//该注解作用返回json数据
    public EmployeeForAndroid login(@RequestBody UserForAndroid currentUser) {
        User user = userService.findByUserName(currentUser.getUsername());//根据当前用户名获取对应的user
        //如果user不为空且密码正确则返回 手机端传来的currentUser,否则返回null
        if (user == null) {
            return null;
        } else {
            if (!user.getPassword().equals(currentUser.getPassword())) {
                return null;
            } else {
                Employee employee = employeeService.findByUserId(user.getId());
                return getAndroidEmployeeByEmployee(employee);
            }
        }

    }


    /**
     * 为android端提供获取所有考勤候选人的功能
     *
     * @return 返回android需要的employee集合（只需三个属性）
     */
    @RequestMapping(value = "/getCandidates.json", method = RequestMethod.POST)
    @ResponseBody
    public Collection<EmployeeForAndroid> getCandidates() {
        Specification<Employee> specification = employeeService.findCandidates();
//        Sort sort = new Sort(Sort.Direction.DESC, "department");
        List<Employee> candidatesFromWeb = this.employeeService.findBySepc(specification);
        Collection<EmployeeForAndroid> employeesForAndroid = new ArrayList();
        for (Employee employee : candidatesFromWeb) {
            employeesForAndroid.add(getAndroidEmployeeByEmployee(employee));
        }
        return employeesForAndroid;
    }

    @RequestMapping(value = "/getAbsenceEmployeeIds.json")
    @ResponseBody
    public Collection<Long> getAbsenceEmployeeIds(long summaryId) {
        //根据summaryId获取未出勤employee的id
        List<Attendance> attendances = attendanceService.findByAttendanceSummaryId(summaryId);
        Collection<Long> absenceIds = new ArrayList<>();
        for (int i = 1; i < attendances.size(); i++) {
            if (!"出勤".equals(attendances.get(i).toDescription())) {
                absenceIds.add(attendances.get(i).getId());
            }
        }
        return absenceIds;
    }

    /**
     * android 端添加考勤功能
     *
     * @param summaryFromAndroid android传来的有关AttendanceSummary的对象
     *                           属性有AttendanceName,attendanceTime,attendanceManager和未出勤employee的id
     * @return 成功返回AttendanceSummaryId，失败返回null
     */
    @RequestMapping(value = "/addAttendanceSummary.json", method = RequestMethod.POST)
    @ResponseBody
    @Transactional
    public Long addAttendanceSummary(@RequestBody AttendanceSummaryForAndroid summaryFromAndroid) throws ParseException {
        //考勤名称
        String attendanceName = summaryFromAndroid.getAttendanceName();//考勤名称
        //考勤时间
        String summaryTime = summaryFromAndroid.getAttendanceTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse(summaryTime);
        Calendar attendanceTime = Calendar.getInstance();
        attendanceTime.setTime(date);
        //实际考勤的员工id
        List<Long> employeeIds = summaryFromAndroid.getEmployeeIds();

        //TODO 这里直接设置成管理员了，之后拓展时要注意。
        Employee attendanceManager = employeeService.findByName("管理员");
        //add AttendanceSummary
        AttendanceSummary attendanceSummary = new AttendanceSummary(attendanceName, attendanceTime, attendanceManager);
        Specification<Employee> specification = employeeService.findCandidates();
        List<Employee> candidates = this.employeeService.findBySepc(specification);
        attendanceSummary.setCandidates(candidates);
        summaryService.add(attendanceSummary);

        //add实际缺勤记录
        //查询缺勤记录中是否有学校缺勤
        Specification<Employee> universityAbenceSpec = employeeService.findUniversityAbsences();
        List<Employee> universityAbsences = this.employeeService.findBySepc(universityAbenceSpec);
        List<Long> universityAbsenceIds = new ArrayList<>();
        for(int i=0;i<universityAbsences.size();i++){
            universityAbsenceIds.add(universityAbsences.get(i).getId());
        }
        employeeIds.removeAll(universityAbsenceIds);
        //增加事假
        for (int i = 0; i < employeeIds.size(); i++) {
            PrivateLeave privateLeave = new PrivateLeave(employeeService.findOne(employeeIds.get(i)), summaryService.findByAttendanceName(attendanceName));
            PrivateLeaveDescription privateLeaveDescription = privateLeaveDescriptionService.findOne((long)8);
            privateLeave.setPrivateLeaveDescription(privateLeaveDescription);
            privateLeaveService.add(privateLeave);
        }
        //增加学校缺勤
        for (int i = 0; i < universityAbsenceIds.size(); i++) {
            UniversityAbsence universityAbsence = new UniversityAbsence(employeeService.findOne(universityAbsenceIds.get(i)), summaryService.findByAttendanceName(attendanceName));
            UniversityAbsenceDescription universityAbsenceDescription = universityAbsenceDescriptionService.findOne((long)3);
            universityAbsence.setUniversityAbsenceDescription(universityAbsenceDescription);
            universityAbsenceService.add(universityAbsence);
        }

        //候选人ids
        List<Long> candidateIds = new ArrayList<>();
        for (int i = 0; i < candidates.size(); i++) {
            candidateIds.add(candidates.get(i).getId());
        }
        //出勤记录ids
        candidateIds.removeAll(employeeIds);

        //add实际出勤记录
        for (int i = 0; i < candidateIds.size(); i++) {
            Presence presence = new Presence(employeeService.findOne(candidateIds.get(i)), summaryService.findByAttendanceName(attendanceName));
            PresenceDescription presenceDescription = presenceDescriptionService.findOne((long)1);
            presence.setPresenceDescription(presenceDescription);
            presenceService.add(presence);
        }
        //为手机端返回纸的id
        Long id = summaryService.findByAttendanceName(attendanceSummary.getAttendanceName()).getId();
        return id;
    }

    /**
     * android 端更新考勤记录的方法
     *
     * 先删除上次所有考勤记录，再增加新的考勤
     *
     * @param summaryFromAndroid android传来的有关AttendanceSummary的对象，属性有AttendanceSummaryId
     * @return 是否成功操作数据库，成功则返回true，反之false
     */
    @RequestMapping(value = "/updateAttendanceSummary.json", method = RequestMethod.POST)
    @ResponseBody
    @Transactional
    public Long updateAttendanceSummary(@RequestBody AttendanceSummaryForAndroid summaryFromAndroid) {
        //对更新考勤做异常处理，更新成功返回true，失败false
        /*
        try {
            Long summaryId = summaryFromAndroid.getId();
            AttendanceSummary attendanceSummary = summaryService.findOne(summaryId);
            List<Long> universityIds = summaryFromAndroid.getEmployeeIds();
            // 1.先找上一次的缺勤  缺勤改为出勤
            List<PrivateLeave> privateLeaves = privateLeaveService.findByPrivateLeaveSummaryId(summaryId);
            long last = privateLeaves.size();
            for (int i = 0; i < universityIds.size(); i++) {
                for (int x = 0; x < privateLeaves.size(); x++) {
                    if (privateLeaves.get(x).getId() == universityIds.get(i)) {
                        privateLeaves.remove(x);
                    }
                }
            }
            List<Long> changePresenceIds = new ArrayList<>();
            if(last!=privateLeaves.size()){
                for (int i = 0; i < privateLeaves.size(); i++) {
                    changePresenceIds.add(privateLeaves.get(i).getId());
                    Presence presence = new Presence(privateLeaves.get(i).getEmployee(), attendanceSummary);
                    presenceService.add(presence);
                    privateLeaveService.deleteById(changePresenceIds.get(i));
                }
            }
            // 2.找到本次缺勤 如果上次为出勤 出勤改为缺勤
            List<Attendance> attendances = new ArrayList<>();
            for (int i = 0; i < universityIds.size(); i++) {
                attendances.add(attendanceService.findByEmployeeId(universityIds.get(i)));
                if ("出勤".equals(attendances.get(i).toSign())) {
                    UniversityAbsence universityAbsence = new UniversityAbsence(attendances.get(i).getEmployee(), attendanceSummary);
                    universityAbsenceService.add(universityAbsence);
                    presenceService.deleteById(attendances.get(i).getId());
                }
            }
            Long id = summaryService.findByAttendanceName(attendanceSummary.getAttendanceName()).getId();
            return id;
        } catch (Exception e) {
            return null;
        }
        */

        /*
        *
        * 暴力式
        */

        try {
            Long summaryId = summaryFromAndroid.getId();
            AttendanceSummary attendanceSummary = summaryService.findOne(summaryId);
            summaryService.deleteById(summaryId);
            //考勤名称
            String attendanceName = attendanceSummary.getAttendanceName();//考勤名称
            //考勤时间
            Calendar attendanceTime = attendanceSummary.getAttendanceTime();
            //实际考勤的员工id
            List<Long> employeeIds = summaryFromAndroid.getEmployeeIds();

            Employee attendanceManager = employeeService.findByName("管理员");
            //add AttendanceSummary
            AttendanceSummary newAttendanceSummary = new AttendanceSummary(attendanceName, attendanceTime,attendanceManager);
            Specification<Employee> specification = employeeService.findCandidates();
            List<Employee> candidates = this.employeeService.findBySepc(specification);
            newAttendanceSummary.setCandidates(candidates);
            summaryService.add(newAttendanceSummary);

            //add实际缺勤记录
            //查询缺勤记录中是否有学校缺勤
            Specification<Employee> universityAbenceSpec = employeeService.findUniversityAbsences();
            List<Employee> universityAbsences = this.employeeService.findBySepc(universityAbenceSpec);
            List<Long> universityAbsenceIds = new ArrayList<>();
            for(int i=0;i<universityAbsences.size();i++){
                universityAbsenceIds.add(universityAbsences.get(i).getId());
            }
            employeeIds.removeAll(universityAbsenceIds);
            //增加事假
            for (int i = 0; i < employeeIds.size(); i++) {
                PrivateLeave privateLeave = new PrivateLeave(employeeService.findOne(employeeIds.get(i)), summaryService.findByAttendanceName(attendanceName));
                PrivateLeaveDescription privateLeaveDescription = privateLeaveDescriptionService.findOne((long)8);
                privateLeave.setPrivateLeaveDescription(privateLeaveDescription);
                privateLeaveService.add(privateLeave);
            }
            //增加学校缺勤
            for (int i = 0; i < universityAbsenceIds.size(); i++) {
                UniversityAbsence universityAbsence = new UniversityAbsence(employeeService.findOne(universityAbsenceIds.get(i)), summaryService.findByAttendanceName(attendanceName));
                UniversityAbsenceDescription universityAbsenceDescription = universityAbsenceDescriptionService.findOne((long)3);
                universityAbsence.setUniversityAbsenceDescription(universityAbsenceDescription);
                universityAbsenceService.add(universityAbsence);
            }

            //候选人ids
            List<Long> candidateIds = new ArrayList<>();
            for (int i = 0; i < candidates.size(); i++) {
                candidateIds.add(candidates.get(i).getId());
            }
            //出勤记录ids
            candidateIds.removeAll(employeeIds);

            //add实际出勤记录
            for (int i = 0; i < candidateIds.size(); i++) {
                Presence presence = new Presence(employeeService.findOne(candidateIds.get(i)), summaryService.findByAttendanceName(attendanceName));
                PresenceDescription presenceDescription = presenceDescriptionService.findOne((long)1);
                presence.setPresenceDescription(presenceDescription);
                presenceService.add(presence);
            }
            //为手机端返回纸的id
            Long id = summaryService.findByAttendanceName(attendanceSummary.getAttendanceName()).getId();
            return id;
        } catch (Exception e) {
            return null;
        }
    }
}