package com.glmis.domain.attendance;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Created by dell on 2017-05-23 .
 * 值域：读博，病假
 */
@Entity
@DiscriminatorValue("PrivateLeaveDescription")
@DynamicInsert(true)
@DynamicUpdate(true)
public class PrivateLeaveDescription extends AbstractDescription {
    public PrivateLeaveDescription(){}
    public PrivateLeaveDescription(Long id) {
        this.id = id;

    }


    @Override
    public String findDescription() {
        return "事假-"+super.getDescription();
    }
}
