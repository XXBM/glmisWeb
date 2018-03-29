package com.glmis.domain.attendance;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Created by dell on 2017-05-23 .
 * 值域：国外访学，国际访学，病假，事假，产假
 */

@Entity
@DiscriminatorValue("UniversityAbsenceDescription")
@DynamicInsert(true)
@DynamicUpdate(true)
public class UniversityAbsenceDescription extends AbstractDescription {
    public UniversityAbsenceDescription(){}
    public UniversityAbsenceDescription(Long id) {
        this.id = id;
    }

    @Override
    public String findDescription() {
        return "学校缺勤-"+super.getDescription();
    }
}
