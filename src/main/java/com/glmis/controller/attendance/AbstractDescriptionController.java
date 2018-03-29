package com.glmis.controller.attendance;

import com.glmis.domain.attendance.AbstractDescription;
import com.glmis.service.attendance.AbstractDescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by dell on 2017-05-23 .
 */
@RestController
public class AbstractDescriptionController {
    @Autowired
    AbstractDescriptionService abstractDescriptionService;

    @RequestMapping(value = "/findAllDescriptions", method = RequestMethod.GET)
    public List<AbstractDescription> findAllDescriptions() {
        List<AbstractDescription> abstractDescriptions = abstractDescriptionService.findAllT();
        return abstractDescriptions;
    }
}
