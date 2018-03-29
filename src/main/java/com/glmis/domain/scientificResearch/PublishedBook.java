package com.glmis.domain.scientificResearch;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Calendar;

/**
 * Created by dell on 2017-05-09 .
 */
/*
*
* 超类
* PublishedBook
*
* 1.超类中有抽象方法 getCategory(), TextBook中重写：return "教材",MonoGraph中重写: return "专著"
* 2.超类中有抽象方法 getAuthor(), TextBook中重写：return this.getEditor(); Monograph中
   重写: return this.getAuthor()
*
* 子类
* 教材TextBook，它的作者叫editor
* 专著：Monograph，它的作者叫author
*
*
*
*/
@Entity
@Table(name = "s_publishedBook")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type",discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("publishedBook")
public abstract class PublishedBook extends ScientificResearch {
    protected String name;//名称
    protected String press;//出版社
    @javax.persistence.Temporal(javax.persistence.TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    protected Calendar publicationTime;//出版时间;

    protected String isbn;//ISBN

    protected int words;//千字数

    abstract String getCategory();
    abstract String getPersonInCharge();



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getPress() {
        return press;
    }

    public void setPress(String press) {
        this.press = press;
    }

    public Calendar getPublicationTime() {
        return publicationTime;
    }

    public void setPublicationTime(Calendar publicationTime) {
        this.publicationTime = publicationTime;
    }


    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getWords() {
        return words;
    }

    public void setWords(int words) {
        this.words = words;
    }
}
