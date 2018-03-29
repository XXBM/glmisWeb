package com.glmis.domain.scientificResearch;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.glmis.domain.personnel.Employee;
import com.glmis.jsonDeserialize.ScienReasCheckingStatusDeserialize;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by dell on 2016/12/24.
 * 科研抽象
 * 如果实体类的一个实例需要用传值的方式调用（例如，远程调用），则这个实体类必须实现（implements）java.io.Serializable 接口
 */

//@Entity
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@MappedSuperclass
@DynamicInsert(true)
@DynamicUpdate(true)
public abstract class ScientificResearch implements Serializable{
    @Id
    @GeneratedValue(strategy=GenerationType.TABLE)
    protected Long id;
    @ManyToOne
    @JoinColumn(name = "employee_id")
    protected Employee employee;

    @ManyToOne
    @JoinColumn(name = "state_id")
    @JsonDeserialize(using = ScienReasCheckingStatusDeserialize.class)
    private ScienReasCheckingStatus checkingStatus;//审核状态

    private String seating;//本人位次
    private String numOfParticipants;//参加人数

//  JPA在进行面向对象查询的时候，需要用到默认的无参构造器，然后再调用set方法对对象进行复制
    public ScientificResearch() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public ScienReasCheckingStatus getCheckingStatus() {
        return checkingStatus;
    }

    public void setCheckingStatus(ScienReasCheckingStatus checkingStatus) {
        this.checkingStatus = checkingStatus;
    }

    public String getSeating() {
        return seating;
    }

    public void setSeating(String seating) {
        this.seating = seating;
    }

    public String getNumOfParticipants() {
        return numOfParticipants;
    }

    public void setNumOfParticipants(String numOfParticipants) {
        this.numOfParticipants = numOfParticipants;
    }
}
