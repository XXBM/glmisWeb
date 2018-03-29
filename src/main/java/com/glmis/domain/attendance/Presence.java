package com.glmis.domain.attendance;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.glmis.domain.personnel.Employee;
import com.glmis.jsonDeserialize.PresenceDescriptionDeserialize;

import javax.persistence.*;

/**
 * Created by dell on 2017-05-23 .
 */
@Entity
@DiscriminatorValue("Presence")
public class Presence extends Attendance {
    private PresenceDescription presenceDescription;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Presence() {
    }
    public Presence(Long id) {
        super.id = id;
    }

    public Presence(Employee employee, AttendanceSummary attendanceSummary) {
        super.employee = employee;
        super.attendanceSummary = attendanceSummary;
    }

    @ManyToOne
    @JoinColumn(name = "presenceDescription_id")
    @JsonDeserialize(using = PresenceDescriptionDeserialize.class)
    public PresenceDescription getPresenceDescription() {
        return presenceDescription;
    }

    public void setPresenceDescription(PresenceDescription presenceDescription) {
        this.presenceDescription = presenceDescription;
    }

    @Override
    public String toDescription() {
        return this.getPresenceDescription().getDescription();
    }

    @Override
    public String toSign() {
        return "出勤";
    }
}
