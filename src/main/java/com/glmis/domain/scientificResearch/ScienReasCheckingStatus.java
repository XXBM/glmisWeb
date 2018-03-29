package com.glmis.domain.scientificResearch;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by dell on 2016/12/25.
 * 科研类审核状态
 */

@Entity
@Table(name = "s_scienReasCheckingStatus")
@DynamicUpdate(true)
@DynamicInsert(true)
public class ScienReasCheckingStatus implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String state;//审核状态

    public ScienReasCheckingStatus() {}

    public ScienReasCheckingStatus(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
