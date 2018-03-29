package com.glmis.controller;

import com.glmis.domain.personnel.Employee;
import com.glmis.domain.personnel.School;
import com.glmis.service.personnel.EmployeeService;
import com.glmis.service.personnel.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
/**
 * Created by xuling on 2016/10/26.
 */

@RestController
public class SchoolController {
    @Autowired
    private SchoolService schoolService;
    @Autowired
    private EmployeeService employeeService;

    @RequestMapping(value = "/findAllSchoolName",method = RequestMethod.GET)
    public List<School> findAllSchoolName(){
        List<School> schoolList = schoolService.findAll();
        return schoolList ;
    }

    @RequestMapping(value = "/findUniversityAbsence",method = RequestMethod.GET)
    public List<Employee> findUniversityAbsence(){
        Specification<Employee> universityAbenceSpec = employeeService.findUniversityAbsences();
        List<Employee> universityAbsences = this.employeeService.findBySepc(universityAbenceSpec);
        return universityAbsences ;
    }

    @RequestMapping(value = "/findImages",method = RequestMethod.GET)
    public String getImage(){
        Employee employee = employeeService.findOne((long)1);
        byte[] imageByte = employee.getPhoto();
//        StringBuffer sb = new StringBuffer();
//        String stmp = "";
//        for (int n = 0; n < image.length; n++) {
//            stmp = Integer.toHexString(image[n] & 0XFF);
//            if (stmp.length() == 1) {
//                sb.append("0" + stmp);
//            } else {
//                sb.append(stmp);
//            }
//
//        }
        //System.out.println(sb.toString());
        //return sb.toString();
        //InputStream in = new ByteArrayInputStream(imageByte);
        //File file = new File();

        return null;
    }
}
