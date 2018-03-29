package com.glmis.domain.scientificResearch;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.glmis.jsonDeserialize.MonographRankDeserialize;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Created by dell on 2017-05-10 .
 * 专著
 */
@Entity
@DiscriminatorValue("monograph")
public class Monograph extends PublishedBook{
    private String author;
    @ManyToOne
    @JoinColumn(name = "monographRank_id")
    @JsonDeserialize(using = MonographRankDeserialize.class)
    private MonographRank monographRank;//专著级别

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public MonographRank getMonographRank() {
        return monographRank;
    }
    public void setMonographRank(MonographRank monographRank) {
        this.monographRank = monographRank;
    }
    @Override
    String getCategory() {
        return "专著";
    }
    @Override
    String getPersonInCharge() {
        return this.getAuthor();
    }
}
