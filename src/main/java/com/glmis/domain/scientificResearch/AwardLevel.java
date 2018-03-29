package com.glmis.domain.scientificResearch;

import com.glmis.domain.personnel.AbstractCategory;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by inkskyu428 on 17-5-10.
 * 获奖等级
 */
@Entity
@Table(name = "s_awardLevel")
@DynamicInsert(true)
@DynamicUpdate(true)
public class AwardLevel extends AbstractCategory{
    public AwardLevel(){}
    public AwardLevel(Long id){
        super(id);
    }
}
