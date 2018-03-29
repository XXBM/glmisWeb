package com.glmis.actuator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.PublicMetrics;
import org.springframework.boot.actuate.metrics.Metric;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by xuling on 2016/10/23.
 * 自定义度量信息
 */


@Component
public class CustomMetrics implements PublicMetrics {
   private ApplicationContext applicationContext;
    @Autowired
    public CustomMetrics(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }



    @Override
    public Collection<Metric<?>> metrics() {
        List<Metric<?>> metrics = new ArrayList<Metric<?>>();
//       记录启动时间
        metrics.add(new Metric<Long>("spring.startup-date",applicationContext.getStartupDate()));
//        记录Bean定义的数量
        metrics.add(new Metric<Integer>("spring.bean.definitions",applicationContext.getBeanDefinitionCount()));
//        记录Bean数量
        metrics.add(new Metric<Integer>("spring.beans",applicationContext.getBeanNamesForType(Object.class).length));
//       记录控制器类型的Bean数量
        metrics.add(new Metric<Integer>("spring.controllers",applicationContext.getBeanNamesForAnnotation(Controller.class).length));
        return metrics;
    }
}
