package com.glmis.repository.scientificResearch;

import com.glmis.JpaRepository.MyRepository;
import com.glmis.domain.scientificResearch.TextBook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by apple on 2017/3/3.
 */

@Repository
public interface TextBookRepository extends MyRepository<TextBook,Long>{
    /**  通过职员id获取所有的自己的所有属性*/
    List<TextBook> findByEmployeeId(Long id);
    /**  通过职员id获取所有的自己的所有属性
     并进行分页*/
    Page<TextBook> findByEmployeeId(Long id, Pageable pageable);
}

