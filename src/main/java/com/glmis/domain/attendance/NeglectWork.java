package com.glmis.domain.attendance;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.glmis.jsonDeserialize.NeglectWorkDescriptionDeserialize;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Created by dell on 2017-05-23 .
 */
@Entity
@DiscriminatorValue("NeglectWork")
public class NeglectWork extends SchoolAbsence {
    private NeglectWorkDescription neglectWorkDescription;

    public NeglectWork(NeglectWorkDescription neglectWorkDescription) {
        this.neglectWorkDescription = neglectWorkDescription;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public NeglectWork() {
    }
    public NeglectWork(Long id) {
        this.id = id;
    }

    @ManyToOne

    @JoinColumn(name = "neglectWorkDescription_id")
    @JsonDeserialize(using = NeglectWorkDescriptionDeserialize.class)
    public NeglectWorkDescription getNeglectWorkDescription() {
        return neglectWorkDescription;
    }

    public void setNeglectWorkDescription(NeglectWorkDescription neglectWorkDescription) {
        this.neglectWorkDescription = neglectWorkDescription;
    }

    @Override
    public String toDescription() {
        return this.getNeglectWorkDescription().getDescription();
    }

    @Override
    public String toSign() {
        return "学校缺勤";
    }
}
