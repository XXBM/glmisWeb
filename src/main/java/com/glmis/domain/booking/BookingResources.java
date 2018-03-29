package com.glmis.domain.booking;


import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

/**
 * <!-- begin-user-doc -->
 * <!--  end-user-doc  -->
 *
 * @generated
 */


@javax.persistence.Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@DynamicInsert(true)
@DynamicUpdate(true)
public abstract class BookingResources implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    protected Integer id;
    /**
     * 编号
     */
    protected String num;
    /**
     * 名称
     */
    protected String name;


    public BookingResources() {
    }


    public BookingResources(String num, String name) {
        this.num = num;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "BookingResources{" +
                "id=" + id +
                ", num='" + num + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}

