package com.glmis.domain.scientificResearch;

import com.glmis.domain.personnel.AbstractCategory;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by dell on 2017-05-10 .
 * 收录情况
 * 值域EI，SCI，CSCI
 */
@Entity
@Table(name = "s_citation")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Citation extends AbstractCategory {
    public Citation() {
    }
    public Citation(Long id) {
        super(id);
    }
}
