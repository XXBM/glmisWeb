package com.glmis.service.scientificResearch;

import com.glmis.domain.scientificResearch.Thesis;
import com.glmis.repository.scientificResearch.ThesisRepository;
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
 * Created by dell on 2016/12/24.(ok)
 */

@Service
public class ThesisService extends BasicService<Thesis, Long> {

    @Autowired
    ThesisRepository thesisRepository;

    /**
     * 通过职员id获取所有的自己的所有属性
     */
    public List<Thesis> findByEmployeeId(Long id) {
        return this.thesisRepository.findByEmployeeId(id);
    }

    /**
     * 通过职员id获取所有的自己的所有属性   +  分页显示
     */
    public Page<Thesis> findByEmployeeId(Long id, Pageable pageable) {
        return this.thesisRepository.findByEmployeeId(id, pageable);
    }

    /**
     * 多条件查询
     */
    public Page<Thesis> findBySepc(Specification<Thesis> specification, Pageable pageable) {
        return this.thesisRepository.findAll(specification, pageable);
    }

    /**
     * 多条件查询
     */
    public List<Thesis> findBySepc(Specification<Thesis> specification) {
        return this.thesisRepository.findAll(specification);
    }

    /**
     * in and 多条件查询
     */
    public Specification<Thesis> thesisQuery(Long id,
                                             String propertyName1,
                                             List<Long> propertyValue1,
                                             String propertyName2,
                                             List<Long> propertyValue2,
                                             String propertyName3,
                                             String propertyValue3,
                                             String propertyValue4) {
        return new Specification<Thesis>() {
            @Override
            public Predicate toPredicate(Root<Thesis> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
//                Predicate predicate = cb.and(
//                        cb.equal(root.get("employee"), id),
//                        cb.in(root.get(propertyName1)).value(propertyValue1),
//                        cb.in(root.get(propertyName2)).value(propertyValue2),
//                        cb.greaterThanOrEqualTo(root.get(propertyName3), propertyValue3),
//                        cb.lessThanOrEqualTo(root.get(propertyName3), propertyValue4));
//                query.where(predicate);
                //动态查询
                List<Predicate> predicate = new ArrayList<>();
                predicate.add(cb.equal(root.get("employee"), id));
                if(!propertyValue1.isEmpty()){
                    predicate.add(cb.in(root.join(propertyName1).get("id")).value(propertyValue1));
                }
                if(!propertyValue2.isEmpty()){
                    predicate.add(cb.in(root.join(propertyName2).get("id")).value(propertyValue2));
                }
                if(propertyValue3!=""){
                    predicate.add(cb.greaterThanOrEqualTo(root.get(propertyName3), propertyValue3));
                }
                if(propertyValue4!=""){
                    predicate.add(cb.lessThanOrEqualTo(root.get(propertyName3), propertyValue4));
                }
                Predicate[] pre = new Predicate[predicate.size()];
                return query.where(predicate.toArray(pre)).getRestriction();
            }
        };
    }
}
