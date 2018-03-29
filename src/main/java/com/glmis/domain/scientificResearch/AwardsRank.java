package com.glmis.domain.scientificResearch;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Created by dell on 2017-05-10 .
 * 奖项级别
 */
@Entity
@DiscriminatorValue("awardsRank")
@DynamicInsert(true)
@DynamicUpdate(true)
public class AwardsRank extends Rank {
    public AwardsRank() {
    }
    public AwardsRank(Long id) {
        super(id);
    }
}

