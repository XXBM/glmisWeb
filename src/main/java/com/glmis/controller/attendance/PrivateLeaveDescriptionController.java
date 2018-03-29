package com.glmis.controller.attendance;

import com.glmis.domain.attendance.PrivateLeaveDescription;
import com.glmis.service.attendance.PrivateLeaveDescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by inkskyu428 on 17-6-3.
 */
@RestController
public class PrivateLeaveDescriptionController {
    @Autowired
    PrivateLeaveDescriptionService privateLeaveDescriptionService;
    @RequestMapping(value = "/findAllPrivateLeaveDescriptions", method = RequestMethod.GET)
    public List<PrivateLeaveDescription> findAllPrivateLeaveDescriptions() throws Exception{
        List<PrivateLeaveDescription> privateLeaveDescriptions = this.privateLeaveDescriptionService.findAllT();
        return privateLeaveDescriptions;
    }
}
