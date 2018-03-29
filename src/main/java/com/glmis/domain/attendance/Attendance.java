package com.glmis.domain.attendance;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.glmis.domain.personnel.Employee;
import com.glmis.jsonDeserialize.AttendanceSummaryDeserialize;
import com.glmis.jsonDeserialize.EmployeeDeserialize;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by dell on 2017-05-23 .
 */
@Entity
@Table(name = "k_attendance")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type",discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("Attendance")
public abstract class Attendance implements Serializable {
    protected Long id;
    protected Employee employee;
    protected AttendanceSummary attendanceSummary;
    public Attendance() {
    }
    public Attendance(Long id) {
        this.id = id;
    }
    public abstract String toDescription();
    public abstract String toSign();

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    @ManyToOne
    @JoinColumn(name = "employee_id")
    @JsonDeserialize(using = EmployeeDeserialize.class)
    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    @ManyToOne
    @JoinColumn(name = "attendanceSummary_id")
    @JsonDeserialize(using = AttendanceSummaryDeserialize.class)
    public AttendanceSummary getAttendanceSummary() {
        return attendanceSummary;
    }

    public void setAttendanceSummary(AttendanceSummary attendanceSummary) {
        this.attendanceSummary = attendanceSummary;
    }
}
