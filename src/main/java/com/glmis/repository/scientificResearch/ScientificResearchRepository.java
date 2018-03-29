package com.glmis.repository.scientificResearch;

import com.glmis.JpaRepository.MyRepository;
import com.glmis.domain.scientificResearch.ScientificResearch;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Created by dell on 2016/12/24.
 */

@Repository
public interface ScientificResearchRepository extends MyRepository<ScientificResearch, Long>,JpaSpecificationExecutor<ScientificResearch> {
}
