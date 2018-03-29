package com.glmis.domain.scientificResearch;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.glmis.jsonDeserialize.ProjectFundedByGovernmentRankDeserialize;

import javax.persistence.*;

/**
 * Created by dell on 2017-05-10 .
 * 纵向
 */
@Entity
@DiscriminatorValue("projectFundedByGovernment")
public class ProjectFundedByGovernment extends Project {
    @ManyToOne
    @JoinColumn(name = "projectFundedByGovernmentRank_id")
    @JsonDeserialize(using = ProjectFundedByGovernmentRankDeserialize.class)
    private ProjectFundedByGovernmentRank projectFundedByGovernmentRank;//纵向项目级别
    @Override
    String getProject() {
        return "纵向";
    }
    public ProjectFundedByGovernmentRank getProjectFundedByGovernmentRank() {
        return projectFundedByGovernmentRank;
    }
    public void setProjectFundedByGovernmentRank(ProjectFundedByGovernmentRank projectFundedByGovernmentRank) {
        this.projectFundedByGovernmentRank = projectFundedByGovernmentRank;
    }
}
