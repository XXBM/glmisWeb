package com.glmis.controller.personnel;

import com.glmis.domain.personnel.YesOrNo;
import com.glmis.service.personnel.YesOrNoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by dell on 2016/11/17.
 */

@RestController
public class YesOrNoController {
    @Autowired
    YesOrNoService yesOrNoService;

    @RequestMapping(value = "/findAllYesOrNo", method = RequestMethod.GET)
    public List<YesOrNo> findAllOverSeas()throws Exception {
        List<YesOrNo> overSeases = yesOrNoService.findAllT();
        return overSeases;
    }
}
