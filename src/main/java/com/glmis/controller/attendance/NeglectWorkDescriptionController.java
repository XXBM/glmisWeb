package com.glmis.controller.attendance;

import com.glmis.domain.attendance.NeglectWorkDescription;
import com.glmis.service.attendance.NeglectWorkDescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by inkskyu428 on 17-6-6.
 */
@RestController
public class NeglectWorkDescriptionController {
    @Autowired
    NeglectWorkDescriptionService neglectWorkDescriptionService;
    @RequestMapping(value = "/findAllNeglectWorkDescriptions", method = RequestMethod.GET)
    public List<NeglectWorkDescription> findAllNeglectWorkDescriptions() throws Exception{
        List<NeglectWorkDescription> neglectWorkDescriptions = this.neglectWorkDescriptionService.findAllT();
        return neglectWorkDescriptions;
    }
}
