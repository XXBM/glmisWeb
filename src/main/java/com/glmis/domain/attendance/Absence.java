package com.glmis.domain.attendance;

import javax.persistence.MappedSuperclass;

/**
 * Created by dell on 2017-05-23 .
 */
@MappedSuperclass
public abstract class Absence extends Attendance{
    private String remarks;

    public String getRemarks() {
        return remarks;
    }

    public Absence() {
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Absence(Long id) {
        super(id);
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
