package com.glmis.domain.booking;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.glmis.domain.authority.User;
import com.glmis.jsonDeserialize.AdressDeserialize;
import com.glmis.jsonDeserialize.EquipmentDeserialize;
import com.glmis.jsonDeserialize.TimeDeserialize;
import com.glmis.jsonDeserialize.UserDeserialize;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 预约信息表
 */

@Entity
@Table(name = "appointment")
@DynamicUpdate(true)
@DynamicInsert(true)
public class Appointment implements Serializable {
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Integer id;
    /**
     * 日期（年月日=某天）
     */
    @javax.persistence.Temporal(javax.persistence.TemporalType.DATE)
    protected Date day;
    /**
     * 事由
     */
    protected String reason;
    /**
     * 参加人数
     */
    protected int numOfPerson;
    /**
     * 时间段
     */
//	@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property="begin,end", scope=ServerRequest.class)
    @ManyToOne
    @JoinColumn(name = "time_id")
    @JsonDeserialize(using = TimeDeserialize.class)
    protected Time time;

    //用户
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonDeserialize(using = UserDeserialize.class)
    protected User user;
    /**
     * 教室或设备资源
     */

    @ManyToOne
    //@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property="name", scope=ServerRequest.class)
    @JoinColumn(name = "booking_adress_id")
    @JsonDeserialize(as = Adress.class, using = AdressDeserialize.class)
    protected BookingResources bookingAdress;

    @ManyToOne
//	@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property="name", scope=ServerRequest.class)
    @JoinColumn(name = "booking_equipment_id")
    @JsonDeserialize(as = Equipment.class, using = EquipmentDeserialize.class)
    protected BookingResources bookingEquipments;

    public Appointment() {
    }

    public Appointment(Date day, Time time, BookingResources bookingAdress) {
        this.day = day;
        this.time = time;
        this.bookingAdress = bookingAdress;
    }

    public Appointment(Date day, String reason, int numOfPerson, Time time, User user, BookingResources bookingAdress, BookingResources bookingEquipments) {
        this.day = day;
        this.reason = reason;
        this.numOfPerson = numOfPerson;
        this.time = time;
        this.user = user;
        this.bookingAdress = bookingAdress;
        this.bookingEquipments = bookingEquipments;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getNumOfPerson() {
        return numOfPerson;
    }

    public void setNumOfPerson(int numOfPerson) {
        this.numOfPerson = numOfPerson;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }


    public BookingResources getBookingAdress() {
        return bookingAdress;
    }

    public void setBookingAdress(BookingResources bookingAdress) {
        this.bookingAdress = bookingAdress;
    }

    public BookingResources getBookingEquipments() {
        return bookingEquipments;
    }

    public void setBookingEquipments(BookingResources bookingEquipments) {
        this.bookingEquipments = bookingEquipments;
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "id=" + id +
                ", day=" + day +
                ", reason='" + reason + '\'' +
                ", numOfPerson=" + numOfPerson +
                ", time=" + time +
                ", user=" + user +
                ", bookingAdress=" + bookingAdress +
                ", bookingEquipments=" + bookingEquipments +
                '}';
    }
}


