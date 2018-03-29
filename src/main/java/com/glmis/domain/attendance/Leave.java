package com.glmis.domain.attendance;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.glmis.domain.personnel.Employee;
import com.glmis.jsonDeserialize.EmployeeDeserialize;
import com.sun.istack.internal.NotNull;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;

/**
 * Created by dell on 2017-05-23 .
 */
@Entity
@Table(name = "k_Leave")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Leave implements Serializable {
    private Long id;
    private Calendar startTime;
    private Calendar endTime;
    private Calendar realTime;
    private String reason;
    @NotNull
    private Employee employee;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @javax.persistence.Temporal(javax.persistence.TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    public Calendar getStartTime() {
        return startTime;
    }

    public void setStartTime(Calendar startTime) {
        this.startTime = startTime;
    }

    @javax.persistence.Temporal(javax.persistence.TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    public Calendar getEndTime() {
        return endTime;
    }

    public void setEndTime(Calendar endTime) {
        this.endTime = endTime;
    }

    @javax.persistence.Temporal(javax.persistence.TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    public Calendar getRealTime() {
        return realTime;
    }

    public void setRealTime(Calendar realTime) {
        this.realTime = realTime;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
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
}
