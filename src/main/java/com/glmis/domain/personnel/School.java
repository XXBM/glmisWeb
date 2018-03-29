package com.glmis.domain.personnel;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

/**
 * Created by xuling on 2016/10/11.
 */

@Table(name = "p_school")
@Entity
public class School implements Serializable{
    private Long id;
    private String schoolName;
    private Collection<Department> departments;


    /**一定要有的**/
    public School() {}
    public School(Long id) {
        this.id = id;
    }
    public School(Long id, String schoolName, Collection<Department> departments) {
        this.id = id;
        this.schoolName = schoolName;
        this.departments = departments;
    }

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }
    @JsonIgnore
    @OneToMany(mappedBy = "school",fetch = FetchType.EAGER)
    public Collection<Department> getDepartments() {
        return departments;
    }
    public void setDepartments(Collection<Department> departments) {
        this.departments = departments;
    }
}


