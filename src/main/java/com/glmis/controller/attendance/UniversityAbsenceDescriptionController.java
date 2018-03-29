package com.glmis.controller.attendance;

import com.glmis.domain.attendance.BusinessLeaveDescription;
import com.glmis.domain.attendance.UniversityAbsenceDescription;
import com.glmis.service.attendance.BusinessLeaveDescriptionService;
import com.glmis.service.attendance.UniversityAbsenceDescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by inkskyu428 on 17-6-3.
 */
@RestController
public class UniversityAbsenceDescriptionController {
    @Autowired
    UniversityAbsenceDescriptionService universityAbsenceDescriptionService;
    @RequestMapping(value = "/findAllUniversityAbsenceDescriptions", method = RequestMethod.GET)
    public List<UniversityAbsenceDescription> findAllUniversityAbsenceSummary() throws Exception{
        List<UniversityAbsenceDescription> universityAbsenceDescriptions = this.universityAbsenceDescriptionService.findAllT();
        return universityAbsenceDescriptions;
    }
}
