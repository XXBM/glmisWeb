package com.glmis.operateExcel;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by dell on 2016/10/31.
 */
@Entity
@Table(name = "test_domain")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TestDomain implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    private Integer num;

    public TestDomain() {
    }

    public TestDomain(String name, Integer num) {
        this.name = name;
        this.num = num;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }
}
