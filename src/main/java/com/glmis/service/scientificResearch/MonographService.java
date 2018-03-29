package com.glmis.service.scientificResearch;

import com.glmis.domain.scientificResearch.Monograph;
import com.glmis.repository.scientificResearch.MonographRepository;
import com.glmis.service.BasicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by apple on 2017/3/3.
 */

@Service
public class MonographService extends BasicService<Monograph, Long> {
    @Autowired
    MonographRepository monographRepository;

    /**
     * 通过职员id获取所有的自己的所有属性
     */
    public List<Monograph> findByEmployeeId(Long id) {
        return this.monographRepository.findByEmployeeId(id);
    }

    /**
     * 通过职员id获取所有的自己的所有属性   +  分页显示
     */
    public Page<Monograph> findByEmployeeId(Long id, Pageable pageable) {
        return this.monographRepository.findByEmployeeId(id, pageable);
    }

    /**
     * 分页+ 多条件查询
     */
    public Page<Monograph> findBySepc(Specification<Monograph> specification, Pageable pageable) {
        return this.monographRepository.findAll(specification, pageable);
    }

    /**
     * 多条件查询
     */
    public List<Monograph> findBySepc(Specification<Monograph> specification) {
        return this.monographRepository.findAll(specification);
    }

    /**
     * in and 多条件查询
     */
    public Specification<Monograph> projectSpecification(Long id,
                                                         String propertyName1, List<Long> propertyValue1,
                                                     String propertyName2, String propertyValue2,
                                                     String propertyName3, String propertyValue3,
                                                     String propertyName4, String propertyValue4,
                                                     String propertyName6, List<Long> propertyValue6) {
        return new Specification<Monograph>() {
            @Override
            public Predicate toPredicate(Root<Monograph> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicate = new ArrayList<>();
                predicate.add(cb.equal(root.get("employee"), id));
                if(!propertyValue1.isEmpty()){
                    predicate.add(cb.in(root.join(propertyName1).get("id")).value(propertyValue1));
                }
                if(propertyValue2!=""){
                    predicate.add(cb.greaterThanOrEqualTo(root.get(propertyName2).as(String.class), propertyValue2));
                }
                if(propertyValue3!=""){
                    predicate.add(cb.lessThanOrEqualTo(root.get(propertyName3).as(String.class), propertyValue3));
                }
                if(propertyValue4!=""){
                    predicate.add(cb.equal(root.get(propertyName4), propertyValue4));
                }
                if(!propertyValue6.isEmpty()){
                    predicate.add(cb.in(root.join(propertyName6).get("id")).value(propertyValue6));
                }
                Predicate[] pre = new Predicate[predicate.size()];
                return query.where(predicate.toArray(pre)).getRestriction();
            }
        };
    }
}
