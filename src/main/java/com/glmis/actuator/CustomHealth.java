package com.glmis.actuator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Created by xuling on 2016/10/22.
 * 自定义健康指示器
 */


@Component
public class CustomHealth implements HealthIndicator{
    @Override
    public Health health() {
        try {
            RestTemplate restTemplate = new RestTemplate();
//            如果这里写一个不存在的URL status为down
            restTemplate.getForObject("http://localhost:8080/index",String.class);
            return Health.up().build();
        }catch (Exception e){
            return Health.down().withDetail("down的原因：",e.getMessage()).build();
        }


    }
}
