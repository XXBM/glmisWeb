package com.glmis.domain.scientificResearch;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.glmis.jsonDeserialize.TextbookRankDeserialize;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Created by dell on 2017-05-09 .
 * 教材
 */
@Entity
@DiscriminatorValue("textBook")
public class TextBook extends PublishedBook {
    private String editor;
    @ManyToOne
    @JoinColumn(name = "textbookRank_id")
    @JsonDeserialize(using = TextbookRankDeserialize.class)
    private TextbookRank textbookRank;//教材级别
    public String getEditor() {
        return editor;
    }
    public void setEditor(String editor) {
        this.editor = editor;
    }

    public TextbookRank getTextbookRank() {
        return textbookRank;
    }

    public void setTextbookRank(TextbookRank textbookRank) {
        this.textbookRank = textbookRank;
    }

    @Override
    String getCategory() {
        return "教材";
    }
    @Override
    String getPersonInCharge() {
        return this.getEditor();
    }
}
