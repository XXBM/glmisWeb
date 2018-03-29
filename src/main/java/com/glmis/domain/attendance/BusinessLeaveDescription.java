package com.glmis.domain.attendance;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Created by dell on 2017-05-23 .
 * 值域：上课，开会，带实习
 */

@Entity
@DiscriminatorValue("BusinessLeaveDescription")
@DynamicInsert(true)
@DynamicUpdate(true)
public class BusinessLeaveDescription extends AbstractDescription {
    public BusinessLeaveDescription() {
    }
    public BusinessLeaveDescription(Long id) {
        this.id = id;

    }
    @Override
    public String findDescription() {
        return "公假-"+super.getDescription();
    }
}
