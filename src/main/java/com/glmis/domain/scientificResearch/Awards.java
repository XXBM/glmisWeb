package com.glmis.domain.scientificResearch;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.glmis.jsonDeserialize.AwardLevelDeserialize;
import com.glmis.jsonDeserialize.AwardsRankDeserialize;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Calendar;

/**
 * Created by dell on 2016/12/24.
 */
/**
 * 放在get方法前使用或者属性
 * @Id为映射主键，@GeneratedValue配合使用，标注主键的生成策略
 * @Basic为get方法，默认加的注解
 * 数据库与get方法名不对应时，@Column为对应列名注解
 * @Transient这个属性不需要与数据库映射
 * @Temporal指定date类型对应列的精度（年月日，时分秒）
 */
//持久化类（实体类），和数据表的映射关系，如果没有table，则建一个同类名的表
@Entity
//指明数据表（awards表）
@Table(name = "s_awards")
public class Awards extends ScientificResearch{
    /**
     * 单向：
     * @ManyToOne指明n：1关系
     * @JoinColumn指明外键列名
     *
     * @OneToMany映射1：n关系
     *
     * 双向：mappedBy维护关联关系
     * 1：n   注意2个实体类的外键名一致
     * 1：1   @OneToOne
     * */
    @ManyToOne
    @JoinColumn(name = "awardLevel_id")
    //反序列化
    @JsonDeserialize(using = AwardLevelDeserialize.class)
    private AwardLevel awardLevel;//获奖等级

    private String title;//成果名称
    private String author;//代表作者
    private String name;//奖项名称
    @ManyToOne
    @JoinColumn(name = "awards_rank_id")
    //反序列化
    @JsonDeserialize(using = AwardsRankDeserialize.class)
    private AwardsRank awardsRank;//奖项级别
    private String sponsor;//批准部门

    @javax.persistence.Temporal(javax.persistence.TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Calendar dateOfAward;//获奖时间

    public Awards() {}

    public AwardLevel getAwardLevel() {
        return awardLevel;
    }

    public void setAwardLevel(AwardLevel awardLevel) {
        this.awardLevel = awardLevel;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AwardsRank getAwardsRank() {
        return awardsRank;
    }

    public void setAwardsRank(AwardsRank awardsRank) {
        this.awardsRank = awardsRank;
    }

    public String getSponsor() {
        return sponsor;
    }

    public void setSponsor(String sponsor) {
        this.sponsor = sponsor;
    }

    public Calendar getDateOfAward() {
        return dateOfAward;
    }

    public void setDateOfAward(Calendar dateOfAward) {
        this.dateOfAward = dateOfAward;
    }
}
