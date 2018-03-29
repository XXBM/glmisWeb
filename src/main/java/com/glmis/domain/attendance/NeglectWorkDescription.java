package com.glmis.domain.attendance;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;


/**
 * Created by dell on 2017-05-23 .
 * 值域：旷工
 */
@Entity
@DiscriminatorValue("NeglectWorkDescription")
@DynamicInsert(true)
@DynamicUpdate(true)
public class NeglectWorkDescription extends AbstractDescription {
    public NeglectWorkDescription(){}
    public NeglectWorkDescription(Long id) {
        this.id = id;


    }
    @Override
    public String findDescription() {
        return "旷工-"+super.getDescription();
    }
}
