package com.glmis.domain.attendance;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.glmis.jsonDeserialize.BusinessLeaveDescriptionDeserialize;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Created by dell on 2017-05-23 .
 */
@Entity
@DiscriminatorValue("BusinessLeave")
public class BusinessLeave extends SchoolAbsence {
    private BusinessLeaveDescription businessLeaveDescription;

    public BusinessLeave() {
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BusinessLeave(Long id) {
        this.id = id;
    }
    public BusinessLeave(BusinessLeaveDescription businessLeaveDescription) {
        this.businessLeaveDescription = businessLeaveDescription;
    }

    @ManyToOne
    @JoinColumn(name = "businessLeaveDescription_id")
    @JsonDeserialize(using = BusinessLeaveDescriptionDeserialize.class)
    public BusinessLeaveDescription getBusinessLeaveDescription() {
        return businessLeaveDescription;
    }

    public void setBusinessLeaveDescription(BusinessLeaveDescription businessLeaveDescription) {
        this.businessLeaveDescription = businessLeaveDescription;
    }

    @Override
    public String toDescription() {
        return this.getBusinessLeaveDescription().getDescription();
    }

    @Override
    public String toSign() {
        return "学院缺勤";
    }
}
