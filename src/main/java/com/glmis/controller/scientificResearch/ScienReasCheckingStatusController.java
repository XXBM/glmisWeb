package com.glmis.controller.scientificResearch;

import com.glmis.domain.scientificResearch.ScienReasCheckingStatus;
import com.glmis.service.scientificResearch.ScienReasCheckingStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by dell on 2016/12/25.
 * 科研项目审核状态
 */

@RestController
public class ScienReasCheckingStatusController {
    @Autowired
    ScienReasCheckingStatusService scienReasCheckingStatusService;
    /**获取到所有的审核状态*/
    @RequestMapping(value = "/findAllScienReasCheckingStatus",method = RequestMethod.GET)
    public List<ScienReasCheckingStatus> findAllScienReasCheckingStatus(){
        List<ScienReasCheckingStatus> scienReasCheckingStatuses = scienReasCheckingStatusService.findAllT();
        return scienReasCheckingStatuses;
    }
}
