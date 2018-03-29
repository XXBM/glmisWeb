package com.glmis.domain.attendance;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.glmis.domain.personnel.Employee;
import com.glmis.jsonDeserialize.EmployeeDeserialize;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

/**
 * Created by dell on 2017-05-23 .
 */
@Entity
@Table(name = "k_AttendanceSummary")
@DynamicInsert(true)
@DynamicUpdate(true)
public class AttendanceSummary implements Serializable {
    private Long id;
    String attendanceName;//考勤名称
    Calendar attendanceTime;//考勤时间
    Employee attendanceManager;//考勤人(管理员)
    String minutes;//会议纪要
    List<Attendance> attendances;//实际考勤
    List<Employee> candidates;//候选人

    public AttendanceSummary() {
    }

    public AttendanceSummary(Long id) {
        this.id = id;
    }

    public AttendanceSummary(String attendanceName, Calendar attendanceTime) {
        this.attendanceName = attendanceName;
        this.attendanceTime = attendanceTime;
    }

    public AttendanceSummary(String attendanceName, Calendar attendanceTime, Employee attendanceManager) {
        this.attendanceName = attendanceName;
        this.attendanceTime = attendanceTime;
        this.attendanceManager = attendanceManager;
    }


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAttendanceName() {
        return attendanceName;
    }

    public void setAttendanceName(String attendanceName) {
        this.attendanceName = attendanceName;
    }

    @javax.persistence.Temporal(javax.persistence.TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    public Calendar getAttendanceTime() {
        return attendanceTime;
    }

    public void setAttendanceTime(Calendar attendanceTime) {
        this.attendanceTime = attendanceTime;
    }


    @ManyToOne
    @JoinColumn(name = "attendanceManager_id")
    @JsonDeserialize(using = EmployeeDeserialize.class)
    public Employee getAttendanceManager() {
        return attendanceManager;
    }

    public void setAttendanceManager(Employee attendanceManager) {
        this.attendanceManager = attendanceManager;
    }

    public String getMinutes() {
        return minutes;
    }

    public void setMinutes(String minutes) {
        this.minutes = minutes;
    }

    @JsonIgnore
    @javax.persistence.ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
//    @JoinTable(name = "k_AttendanceSummary_Candidate",
//            joinColumns = {@JoinColumn(name = "AttendanceSummary_id", referencedColumnName = "id")},
//            inverseJoinColumns = {@JoinColumn(name = "Candidate_id", referencedColumnName = "id")})
    public List<Employee> getCandidates() {
        return candidates;
    }

    public void setCandidates(List<Employee> candidates) {
        this.candidates = candidates;
    }

    @JsonIgnore
    @javax.persistence.OneToMany(mappedBy = "attendanceSummary", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public List<Attendance> getAttendances() {
        return attendances;
    }

    public void setAttendances(List<Attendance> attendances) {
        this.attendances = attendances;
    }
}
