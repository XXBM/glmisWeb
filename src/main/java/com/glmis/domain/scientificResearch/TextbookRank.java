package com.glmis.domain.scientificResearch;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Created by dell on 2017-05-10 .
 * 教材级别
 */
@Entity
@DiscriminatorValue("textbookRank")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TextbookRank extends Rank {
    public TextbookRank() {
    }
    public TextbookRank(Long id) {
        super(id);
    }
}
