package com.glmis.domain.attendance;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.glmis.domain.personnel.Employee;
import com.glmis.jsonDeserialize.PrivateLeaveDescriptionDeserialize;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Created by dell on 2017-05-23 .
 */
@Entity
@DiscriminatorValue("PrivateLeave")
public class PrivateLeave extends SchoolAbsence {
    private PrivateLeaveDescription privateLeaveDescription;

    public PrivateLeave() {
    }
    public PrivateLeave(Long id) {
        this.id = id;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public PrivateLeave(Employee employee, AttendanceSummary attendanceSummary) {
        super.employee = employee;
        super.attendanceSummary = attendanceSummary;
    }

    @ManyToOne
    @JoinColumn(name = "privateLeaveDescription_id")
    @JsonDeserialize(using = PrivateLeaveDescriptionDeserialize.class)
    public PrivateLeaveDescription getPrivateLeaveDescription() {
        return privateLeaveDescription;
    }

    public void setPrivateLeaveDescription(PrivateLeaveDescription privateLeaveDescription) {
        this.privateLeaveDescription = privateLeaveDescription;
    }

    public PrivateLeave(PrivateLeaveDescription privateLeaveDescription) {
        this.privateLeaveDescription = privateLeaveDescription;
    }

    @Override
    public String toDescription() {
        return this.getPrivateLeaveDescription().getDescription();
    }

    @Override
    public String toSign() {
        return "学校缺勤";
    }
}
