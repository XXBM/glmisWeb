package com.glmis.JpaRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.List;

/**
 * Created by dell on 2016/10/17.
 */

/**
 * 定义接口的实现类
 * SimpleJpaRepository继承了JpaRepository，保证所有的Repository都具有基本的CRUD等一些方法
 * 和继承了JpaSpecificationExecutor，保证实现一些复杂的查询或者多条件的分页
 *
 */
public class MyCustomerRepository<T,ID extends Serializable>
        extends SimpleJpaRepository<T,ID>
        implements MyRepository<T,ID>{

    private final EntityManager em;

    public MyCustomerRepository(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.em = entityManager;
    }

    public MyCustomerRepository(Class<T> domainClass, EntityManager em) {
        super(domainClass, em);
        this.em = em;
    }

}
