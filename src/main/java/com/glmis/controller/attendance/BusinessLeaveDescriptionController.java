package com.glmis.controller.attendance;

import com.glmis.domain.attendance.BusinessLeaveDescription;
import com.glmis.domain.attendance.PresenceDescription;
import com.glmis.service.attendance.BusinessLeaveDescriptionService;
import com.glmis.service.attendance.PresenceDescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by inkskyu428 on 17-6-3.
 */
@RestController
public class BusinessLeaveDescriptionController {
    @Autowired
    BusinessLeaveDescriptionService businessLeaveDescriptionService;
    @RequestMapping(value = "/findAllBusinessLeaveDescriptions", method = RequestMethod.GET)
    public List<BusinessLeaveDescription> findAllPresenceSummary() throws Exception{
        List<BusinessLeaveDescription> businessLeaveDescriptions = this.businessLeaveDescriptionService.findAllT();
        return businessLeaveDescriptions;
    }
}
