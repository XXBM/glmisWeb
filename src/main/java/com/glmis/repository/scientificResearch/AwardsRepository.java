package com.glmis.repository.scientificResearch;

import com.glmis.JpaRepository.MyRepository;
import com.glmis.domain.scientificResearch.Awards;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by dell on 2016/12/24.
 */

@Repository
public interface AwardsRepository extends MyRepository<Awards, Long> {
    /**  通过职员id获取所有的自己的所有属性*/
    List<Awards> findByEmployeeId(Long id);
    /**  通过职员id获取所有的自己的所有属性
     并进行分页*/
    Page<Awards> findByEmployeeId(Long id, Pageable pageable);
}