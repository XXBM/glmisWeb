package com.glmis.actuator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.boot.actuate.metrics.GaugeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by xuling on 2016/10/11.
 */


@Controller
public class ActuatorTest {
    //注入actuator提供的实现
    @Autowired
    private CounterService counterService;
    @Autowired
    private GaugeService gaugeService;

//    每次处理login（）方法时，都会通过counterService.increment("logintimes:");
//    gaugeService.submit("loginLastTime:",System.currentTimeMillis());来调整度量值
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
//        每次调用数值会增加1
        counterService.increment("logintimes:");
//        最后一次运行的时间戳
        gaugeService.submit("loginLastTime:",System.currentTimeMillis());
        return "login";
    }
}
