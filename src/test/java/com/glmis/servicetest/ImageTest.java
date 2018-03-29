package com.glmis.servicetest;

import com.glmis.domain.personnel.Employee;
import com.glmis.service.personnel.EmployeeService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author: LihuaHuang
 * @Description:
 * @Date: Created in 9:38 2017-07-14 .
 * @Modified by:
 */
public class ImageTest {
    @Autowired
    EmployeeService employeeService;
    @Test
    public void getImage(){
        Employee employee = employeeService.findOne((long)1);
        byte[] image = employee.getPhoto();
        StringBuffer sb = new StringBuffer();
        String stmp = "";
        for (int n = 0; n < image.length; n++) {
            stmp = Integer.toHexString(image[n] & 0XFF);
            if (stmp.length() == 1) {
                sb.append("0" + stmp);
            } else {
                sb.append(stmp);
            }

        }
        System.out.println(sb.toString());
    }
}
