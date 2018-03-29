package com.glmis.controller.attendance;

import com.glmis.domain.attendance.PresenceDescription;
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
public class PresenceDescriptionController {
    @Autowired
    PresenceDescriptionService presenceDescriptionService;
    @RequestMapping(value = "/findAllPresenceDescriptions", method = RequestMethod.GET)
    public List<PresenceDescription> findAllPresenceSummary() throws Exception{
        List<PresenceDescription> presenceDescriptions = this.presenceDescriptionService.findAllT();
        return presenceDescriptions;
    }
}
