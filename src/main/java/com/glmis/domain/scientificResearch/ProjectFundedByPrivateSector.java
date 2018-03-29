package com.glmis.domain.scientificResearch;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.glmis.jsonDeserialize.ProjectFundedByPrivateSectorRankDeserialize;

import javax.persistence.*;

/**
 * Created by dell on 2017-05-10 .
 * 横向
 */
@Entity
@DiscriminatorValue("projectFundedByPrivateSector")
public class ProjectFundedByPrivateSector extends Project {
    @ManyToOne
    @JoinColumn(name = "projectFundedByPrivateSectorRank_id")
    @JsonDeserialize(using = ProjectFundedByPrivateSectorRankDeserialize.class)
    private ProjectFundedByPrivateSectorRank projectFundedByPrivateSectorRank;//横向项目级别
    @Override
    String getProject() {
        return "横向";
    }

    public ProjectFundedByPrivateSectorRank getProjectFundedByPrivateSectorRank() {
        return projectFundedByPrivateSectorRank;
    }

    public void setProjectFundedByPrivateSectorRank(ProjectFundedByPrivateSectorRank projectFundedByPrivateSectorRank) {
        this.projectFundedByPrivateSectorRank = projectFundedByPrivateSectorRank;
    }
}
