package com.glmis.androidService.androidModel;

import com.glmis.domain.personnel.Employee;

import java.util.List;

/**
 * Created by 杨云召 on 26/05/2017.
 * 为android创建的AttendanceSummary实体类
 */
public class AttendanceSummaryForAndroid {
    private Long id;
    private String attendanceName;//考勤名称
    private String attendanceTime;//考勤时间，android端传来的是String
    private Employee attendanceManager;//考勤人
    private List<Long> employeeIds;//未出勤人的id

    public AttendanceSummaryForAndroid() {

    }

    public AttendanceSummaryForAndroid(String attendanceName, Employee attendanceManager, List<Long> employeeIds) {
        this.attendanceName = attendanceName;
        this.attendanceManager = attendanceManager;
        this.employeeIds = employeeIds;
    }

    public AttendanceSummaryForAndroid(String attendanceName, List<Long> employeeIds) {
        this.attendanceName = attendanceName;
        this.employeeIds = employeeIds;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAttendanceName() {
        return attendanceName;
    }

    public void setAttendanceName(String attendanceName) {
        this.attendanceName = attendanceName;
    }

    public List<Long> getEmployeeIds() {
        return employeeIds;
    }


    public String getAttendanceTime() {
        return attendanceTime;
    }

    public void setAttendanceTime(String attendanceTime) {
        this.attendanceTime = attendanceTime;
    }

    public void setAttendanceManager(Employee attendanceManager) {
        this.attendanceManager = attendanceManager;
    }

    public Employee getAttendanceManager() {
        return attendanceManager;
    }


}
