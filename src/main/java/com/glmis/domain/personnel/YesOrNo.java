package com.glmis.domain.personnel;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by dell on 2016/11/21.
 * 是或否
 */

@Entity
@Table(name = "p_yes_no")
@DynamicInsert(true)
@DynamicUpdate(true)
public class YesOrNo implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String description;

    public YesOrNo() {}
    public YesOrNo(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
