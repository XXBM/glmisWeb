package com.glmis.service;

import com.glmis.JpaRepository.MyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.List;



/**
 * Created by dell on 2016/11/12.
 */
public abstract class BasicService<T extends Serializable,ID extends Serializable>{
    @Autowired
    MyRepository<T,ID> basicDao;

    /**按照id获取实体*/
    public T findOne(ID id){
        return basicDao.findOne(id);
    }
    /**获取所有的该实体类所有的属性*/
    public List<T> findAllT(){
        return basicDao.findAll();
    }
    /**获取所有的该实体类所有的属性  +  分页显示*/
    public Page<T> findAllT(Pageable pageable){
        return this.basicDao.findAll(pageable);
    }
    /**添加*/
    public void add(T t){
        this.basicDao.save(t);
    }
    /**更新*/
    public void update(T t){
        this.basicDao.saveAndFlush(t);
    }
    /**删除*/
    public void deleteById(ID id){
        this.basicDao.delete(id);
    }
    /**in and 多条件查询*/
    public Specification<T> inAndQuery(Integer id,
                                            String propertyName1,
                                            List<String> propertyValue1,
                                            String propertyName2,
                                            List<String> propertyValue2,
                                            String propertyName3,
                                            String propertyValue3,
                                            String propertyValue4){
        return new Specification<T>() {
            @Override
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.and(
                        cb.equal(root.get("employee"),id),
                        cb.in(root.get(propertyName1)).value(propertyValue1),
                        cb.in(root.get(propertyName2)).value(propertyValue2),
                        cb.greaterThanOrEqualTo(root.get(propertyName3),propertyValue3),
                        cb.lessThanOrEqualTo(root.get(propertyName3),propertyValue4)
                );
                query.where(predicate);
                return query.getRestriction();
            }
        };
    }
}
