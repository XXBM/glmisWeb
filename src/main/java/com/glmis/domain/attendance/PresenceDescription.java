package com.glmis.domain.attendance;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Created by dell on 2017-05-23 .
 */
@Entity
@DiscriminatorValue("PresenceDescription")
@DynamicInsert(true)
@DynamicUpdate(true)
public class PresenceDescription extends AbstractDescription {
    public PresenceDescription(){}



    public PresenceDescription(Long id) {
        this.id = id;
    }
    @Override
    public String findDescription() {
        return "出勤-"+super.getDescription();
    }
}
