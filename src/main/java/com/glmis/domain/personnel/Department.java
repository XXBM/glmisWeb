package com.glmis.domain.personnel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.glmis.jsonDeserialize.SchoolDeserialize;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * Created by xuling on 2016/10/11.
 */



@Table(name = "p_department")
@Entity
@DynamicInsert(true)
@DynamicUpdate(true)
public class Department implements Serializable{
    private Long id;
    /**
     * 部门名字
     */
    private String departmentName;

    private School school;
    /**员工
     */
    private Set<Employee> employees;

    public Department() {}
    //反序列化自定义返回对象的时候用到
    public Department(Long id) {
        this.id = id;
    }
    public Department(Long id, School school){
        this.id=id;
        this.school=school;
    }
    public Department(Long id, String departmentName, School school, Set<Employee> employees) {
        this.id = id;
        this.departmentName = departmentName;
        this.school = school;
        this.employees = employees;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getDepartmentName() {
        return departmentName;
    }
    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
    /**
     * If you're using Jackson and it is JSON that your converter is trying to write
     * (the stack doesn't make that clear), put @JsonIdentityInfo annotation on your
     * geter() method.*/
    @ManyToOne
    @JoinColumn(name = "school_id")
    @JsonDeserialize(using = SchoolDeserialize.class)
    public School getSchool() {
        return school;
    }
    public void setSchool(School school) {
        this.school = school;
    }


    @JsonIgnore
    @OneToMany(mappedBy = "department")
    public Set<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }

    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", departmentName='" + departmentName + '\'' +
                ", school=" + school +
                ", employees=" + employees +
                '}';
    }
}
