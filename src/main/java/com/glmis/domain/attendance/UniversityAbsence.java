package com.glmis.domain.attendance;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.glmis.domain.personnel.Employee;
import com.glmis.jsonDeserialize.UniversityAbsenceDescriptionDeserialize;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Created by dell on 2017-05-23 .
 */
@Entity
@DiscriminatorValue("UniversityAbsence")
public class UniversityAbsence extends Absence {
    private UniversityAbsenceDescription universityAbsenceDescription;

    public UniversityAbsence() {
    }
    public UniversityAbsence(Employee employee,AttendanceSummary attendanceSummary) {
        this.employee = employee;
        this.attendanceSummary = attendanceSummary;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public UniversityAbsence(Long id) {
        super(id);
    }

    @ManyToOne
    @JoinColumn(name = "universityAbsenceDescription_id")
    @JsonDeserialize(using = UniversityAbsenceDescriptionDeserialize.class)
    public UniversityAbsenceDescription getUniversityAbsenceDescription() {
        return universityAbsenceDescription;
    }

    public void setUniversityAbsenceDescription(UniversityAbsenceDescription universityAbsenceDescription) {
        this.universityAbsenceDescription = universityAbsenceDescription;
    }

    public UniversityAbsence(UniversityAbsenceDescription universityAbsenceDescription) {
        this.universityAbsenceDescription = universityAbsenceDescription;
    }

    @Override
    public String toDescription() {
        return this.getUniversityAbsenceDescription().getDescription();
    }

    @Override
    public String toSign() {
        return "学校缺勤";
    }
}
