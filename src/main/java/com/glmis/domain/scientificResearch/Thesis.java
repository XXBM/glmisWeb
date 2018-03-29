package com.glmis.domain.scientificResearch;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.glmis.jsonDeserialize.CitationDeserialize;
import com.glmis.jsonDeserialize.JournalRankDeserialize;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by dell on 2016/12/24.
 */

/*
*
* 论文
*/
@Entity
@Table(name = "s_thesis")
public class Thesis extends ScientificResearch {
    private String title;//题目
    private String name;//期刊名称

    @ManyToOne
    @JoinColumn(name = "journalRank_id")
    @JsonDeserialize(using = JournalRankDeserialize.class)
    private JournalRank journalRank;//类属性--期刊级别--属性名-journalRank***********

    private String year;//发表-年
    private Integer issue;//发表-期--属性名---issue
    private Integer volume;//发表-卷---属性名---volume
    private Integer startingPageNo;//起始页码--属性名--startingPageNo
    private Integer endingPageNo;//结束页码--属性名--endingPageNo
    @ManyToOne
    @JoinColumn(name = "citation_id")
    @JsonDeserialize(using = CitationDeserialize.class)
    private Citation citation;//类属性-收录情况-属性名-citation-值域EI，SCI，CSCI

    public Thesis() {}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public JournalRank getJournalRank() {
        return journalRank;
    }

    public void setJournalRank(JournalRank journalRank) {
        this.journalRank = journalRank;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Integer getIssue() {
        return issue;
    }

    public void setIssue(Integer issue) {
        this.issue = issue;
    }

    public Integer getVolume() {
        return volume;
    }

    public void setVolume(Integer volume) {
        this.volume = volume;
    }

    public Integer getStartingPageNo() {
        return startingPageNo;
    }

    public void setStartingPageNo(Integer startingPageNo) {
        this.startingPageNo = startingPageNo;
    }

    public Integer getEndingPageNo() {
        return endingPageNo;
    }

    public void setEndingPageNo(Integer endingPageNo) {
        this.endingPageNo = endingPageNo;
    }

    public Citation getCitation() {
        return citation;
    }

    public void setCitation(Citation citation) {
        this.citation = citation;
    }
}
