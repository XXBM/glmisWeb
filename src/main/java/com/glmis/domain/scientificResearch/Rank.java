package com.glmis.domain.scientificResearch;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by dell on 2017-05-10 .
 * 级别（国家级 省部级）
 */
@Entity
@Table(name = "s_rank")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type",discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("rank")
public abstract class Rank implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    protected Long id;
    /**编号
     */
    @Column(unique = true)
    protected String no;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**类别

     */
    protected String description;

    public Rank() {
    }
    public Rank(Long id) {
        this.id = id;
    }

}
