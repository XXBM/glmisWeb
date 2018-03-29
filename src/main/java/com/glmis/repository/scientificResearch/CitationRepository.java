package com.glmis.repository.scientificResearch;

import com.glmis.JpaRepository.MyRepository;
import com.glmis.domain.scientificResearch.Citation;
import org.springframework.stereotype.Repository;

/**
 * Created by inkskyu428 on 17-5-11.
 */
@Repository
public interface CitationRepository extends MyRepository<Citation,Long> {
}
