package com.glmis.domain.scientificResearch;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Created by dell on 2017-05-10 .
 * 横向项目级别
 */
@Entity
@DiscriminatorValue("pFundedByPrivateSectorRank")
@DynamicInsert(true)
@DynamicUpdate(true)
public class ProjectFundedByPrivateSectorRank extends Rank {
    public ProjectFundedByPrivateSectorRank() {
    }
    public ProjectFundedByPrivateSectorRank(Long id) {
        super(id);
    }
}
