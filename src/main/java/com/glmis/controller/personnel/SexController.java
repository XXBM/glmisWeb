package com.glmis.controller.personnel;

import com.glmis.domain.personnel.Sex;
import com.glmis.service.personnel.SexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by dell on 2016/11/17.
 */

@RestController
public class SexController {
    @Autowired
    SexService sexService;

    @RequestMapping(value = "/findAllSex", method = RequestMethod.GET)
    public List<Sex> findAllSex() throws Exception{
        List<Sex> sexList = sexService.findAllT();
        return sexList;
    }
}
