package com.glmis.domain.scientificResearch;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Created by dell on 2017-05-10 .
 * 期刊级别
 */
@Entity
@DiscriminatorValue("journalRank")
@DynamicInsert(true)
@DynamicUpdate(true)
public class JournalRank extends Rank {
    public JournalRank() {
    }
    public JournalRank(Long id) {
        super(id);
    }
}
