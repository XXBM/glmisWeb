package com.glmis.domain.scientificResearch;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Created by dell on 2017-05-10 .
 * 专著级别
 */
@Entity
@DiscriminatorValue("monographRank")
@DynamicInsert(true)
@DynamicUpdate(true)
public class MonographRank extends Rank {
    public MonographRank() {
    }
    public MonographRank(Long id) {
        super(id);
    }
}
