package com.glmis.domain.attendance;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by dell on 2017-05-23 .
 */
@Entity
@Table(name = "k_description")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type",discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("AbstractDescription")
public abstract class AbstractDescription implements Serializable {
    protected Long id;
    protected String description;


    public abstract String findDescription();
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
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
