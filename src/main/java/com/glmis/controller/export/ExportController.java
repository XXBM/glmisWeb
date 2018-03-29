package com.glmis.controller.export;

import com.glmis.domain.message.Result;
import com.glmis.service.export.ExportService;
import com.glmis.service.personnel.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by kene213 on 2017/4/25.
 */
@RestController
public class ExportController {

    @Autowired
    EmployeeService employeeService;

    @Autowired
    ExportService exportService;
    /**
     * 导出excel
     * @param systemName
     */
    @GetMapping("/exportExcel")
    public Result exportExcel(HttpServletResponse response,
                              HttpServletRequest request) throws IOException {
        //导出excel
       return this.exportService.exportExcel(this.employeeService.employeeList,response,request);
    }

}
