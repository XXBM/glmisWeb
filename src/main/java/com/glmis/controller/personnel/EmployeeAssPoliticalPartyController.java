package com.glmis.controller.personnel;

import com.glmis.domain.personnel.EmployeeAssPoliticalParty;
import com.glmis.service.personnel.EmployeeAssPoliticalPartyService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by dell on 2016/11/7.
 */

@RestController
public class EmployeeAssPoliticalPartyController {
    final org.slf4j.Logger logger = LoggerFactory.getLogger(EmployeeAssPoliticalPartyController.class);
    @Autowired
    EmployeeAssPoliticalPartyService employeeAssPoliticalPartyService;

    @RequestMapping(value = "/displayAllParty", method = RequestMethod.GET)
    public List<EmployeeAssPoliticalParty> findAllParty(@RequestParam("id") Long id) throws Exception{
        logger.debug("--**--**{}",id);
        List<EmployeeAssPoliticalParty> employeeAssPoliticalParties = employeeAssPoliticalPartyService.findByEmployeeId(id);
        logger.debug("--**--**{}",employeeAssPoliticalParties.size());
        return employeeAssPoliticalParties;
    }
}
