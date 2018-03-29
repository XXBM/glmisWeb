package com.glmis.domain.attendance;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.MappedSuperclass;

/**
 * Created by dell on 2017-05-23 .
 */
@MappedSuperclass
@DynamicInsert(true)
@DynamicUpdate(true)
public abstract class SchoolAbsence extends Absence {

}
