package com.glmis.domain.booking;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.glmis.domain.authority.User;
import com.glmis.jsonDeserialize.UserDeserialize;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.omg.CORBA.ServerRequest;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;


/**
 * 时间段
 */


@Entity
@Table(name = "time")
@DynamicUpdate(true)
@DynamicInsert(true)
public class Time implements Serializable {
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Integer id;
    /**
     * 节次
     */
    protected Integer num;
    /**
     * 开始时间
     */
    @javax.persistence.Temporal(TemporalType.TIME)
    protected Date begin;
    /**
     * 结束时间
     */
    @javax.persistence.Temporal(TemporalType.TIME)
    protected Date end;

//	/**预约信息表
//	 */
//	@OneToMany(mappedBy = "time",fetch = FetchType.EAGER)
//	protected Set<Appointment> appointments;

    public Time() {
    }

    public Time(Integer id) {
        this.id = id;
    }

    public Time(Integer num, Date begin, Date end) {
        this.num = num;
        this.begin = begin;
        this.end = end;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Date getBegin() {
        return begin;
    }

    public void setBegin(Date begin) {
        this.begin = begin;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return "Time{" +
                "id=" + id +
                ", num=" + num +
                ", begin=" + begin +
                ", end=" + end +
                '}';
    }
}

