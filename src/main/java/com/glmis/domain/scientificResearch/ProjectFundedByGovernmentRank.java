package com.glmis.domain.scientificResearch;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Created by dell on 2017-05-10 .
 * 纵向项目级别
 */
@Entity
@DiscriminatorValue("projectFundedByGovernmentRank")
@DynamicInsert(true)
@DynamicUpdate(true)
public class ProjectFundedByGovernmentRank extends Rank {
    public ProjectFundedByGovernmentRank() {
    }
    public ProjectFundedByGovernmentRank(Long id) {
        super(id);
    }
}
