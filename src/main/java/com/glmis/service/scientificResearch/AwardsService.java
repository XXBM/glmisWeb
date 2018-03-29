package com.glmis.service.scientificResearch;

import com.glmis.domain.scientificResearch.Awards;
import com.glmis.repository.scientificResearch.AwardsRepository;
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
 * Created by dell on 2016/12/24.
 */

@Service
public class AwardsService extends BasicService<Awards, Long> {

    @Autowired
    AwardsRepository awardsRepository;

    /**
     * 通过职员id获取所有的自己的所有属性
     */
    public List<Awards> findByEmployeeId(Long id) {
        return this.awardsRepository.findByEmployeeId(id);
    }

    /**
     * 通过职员id获取所有的自己的所有属性   +  分页显示
     */
    public Page<Awards> findByEmployeeId(Long id, Pageable pageable) {
        return this.awardsRepository.findByEmployeeId(id, pageable);
    }

    /**
     * 分页+ 多条件查询
     */
    public Page<Awards> findBySepc(Specification<Awards> specification, Pageable pageable) {
        return this.awardsRepository.findAll(specification, pageable);
    }

    /**
     * 多条件查询
     */
    public List<Awards> findBySepc(Specification<Awards> specification) {
        return this.awardsRepository.findAll(specification);
    }

    /**
     * in and 多条件查询
     */
    public Specification<Awards> awardsSpecification(Long id,
                                                     String propertyName1, List<Long> propertyValue1,
                                                     String propertyName2, String propertyValue2,
                                                     String propertyName3, String propertyValue3,
                                                     String propertyName4, String propertyValue4,
                                                     String propertyName5, List<Long> propertyValue5) {
        return new Specification<Awards>() {
            @Override
            public Predicate toPredicate(Root<Awards> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
//                Predicate predicate = cb.and(
//                        cb.equal(root.get("employee"), id),
//                        cb.in(root.join(propertyName1).get("id")).value(propertyValue1),
//                        cb.greaterThanOrEqualTo(root.get(propertyName2).as(String.class),propertyValue2),
//                        cb.lessThanOrEqualTo(root.get(propertyName3).as(String.class),propertyValue3),
//                        cb.equal(root.get(propertyName4), propertyValue4),
//                        cb.in(root.join(propertyName5).get("id")).value(propertyValue5)
//                );
                //query.where(predicate);
                List<Predicate> predicate = new ArrayList<>();
                predicate.add(cb.equal(root.get("employee"), id));
                if(!propertyValue1.isEmpty()){
                    predicate.add(cb.in(root.join(propertyName1).get("id")).value(propertyValue1));
                }
                if (propertyValue2!=""){
                    predicate.add(cb.greaterThanOrEqualTo(root.get(propertyName2).as(String.class), propertyValue2));
                }
                if(propertyValue3!=""){
                    predicate.add(cb.lessThanOrEqualTo(root.get(propertyName3).as(String.class), propertyValue3));
                }
                if(propertyValue4!=""){
                    predicate.add(cb.equal(root.get(propertyName4), propertyValue4));
                }
                if(!propertyValue5.isEmpty()){
                    predicate.add(cb.in(root.join(propertyName5).get("id")).value(propertyValue5));
                }
                Predicate[] pre = new Predicate[predicate.size()];
                return query.where(predicate.toArray(pre)).getRestriction();
            }
        };
    }
}
