package com.glmis.domain.personnel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Set;

/**
 * 政治党派
 */

@Entity
@Table(name = "p_politicalParty")
@DynamicInsert(true)
@DynamicUpdate(true)
public class PoliticalParty extends AbstractCategory{

    public PoliticalParty() {
    }

    //反序列化时用到的构造器
    public PoliticalParty(Long id) {
        super(id);
    }

    /**
     * 政治面貌
     */
    @JsonIgnore
    @OneToMany(mappedBy = "politicalParty", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<EmployeeAssPoliticalParty> employeeAssPoliticalParties;

    public Set<EmployeeAssPoliticalParty> getEmployeeAssPoliticalParties() {
        return employeeAssPoliticalParties;
    }

    public void setEmployeeAssPoliticalParties(Set<EmployeeAssPoliticalParty> employeeAssPoliticalParties) {
        this.employeeAssPoliticalParties = employeeAssPoliticalParties;
    }
}

