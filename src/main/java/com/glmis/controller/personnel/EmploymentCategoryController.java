package com.glmis.controller.personnel;

import com.glmis.domain.personnel.EmploymentCategory;
import com.glmis.service.personnel.EmploymentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2016/11/17.
 */

@RestController
public class EmploymentCategoryController {
    @Autowired
    EmploymentCategoryService employmentCategoryService;

    /**
     * 获取到所有的职工类型
     */
    @RequestMapping(value = "/findAllEmploymentCategory", method = RequestMethod.GET)
    public List<EmploymentCategory> findAllEmploymentCategory()throws Exception {
        List<EmploymentCategory> employmentCategories = employmentCategoryService.findAllT();
        return employmentCategories;
    }
    //实现分页
    @RequestMapping(value = "/displayAllEmploymentCategories", method = RequestMethod.GET)
    public Map<String, Object> displayAllEmploymentCategories(@RequestParam(value = "page") Integer page,
                                                         @RequestParam(value = "rows") Integer size)throws Exception {
        Page<EmploymentCategory> list = this.employmentCategoryService.findAllT(new PageRequest(page - 1, size));
        Map<String, java.lang.Object> map = new HashMap<String, Object>();
        int total = this.employmentCategoryService.findAllT().size();
        map.put("total", total);
        map.put("rows", list.getContent());
        return map;
    }

    // 增
    @RequestMapping(value = "/addEmploymentCategory", method = RequestMethod.POST)
    public Map<String, java.lang.Object> addDepartment(@RequestBody EmploymentCategory EmploymentCategory) throws Exception{
        this.employmentCategoryService.add(EmploymentCategory);
        Map<String, java.lang.Object> map = new HashMap<String, java.lang.Object>();
        map.put("employmentCategory", EmploymentCategory);
        return map;
    }

    //改
    @RequestMapping(value = "/updateEmploymentCategory", method = RequestMethod.PUT)
    public Map<String, java.lang.Object> updateEmploymentCategory(@RequestBody EmploymentCategory EmploymentCategory) throws Exception{
        this.employmentCategoryService.update(EmploymentCategory);
        Map<String, java.lang.Object> map = new HashMap<String, Object>();
        map.put("employmentCategory", EmploymentCategory);
        return map;
    }

    //删
    @RequestMapping(value = "/deleteEmploymentCategory", method = RequestMethod.DELETE)
    public void deleteEmploymentCategory(@RequestParam("ids") String ids)throws Exception {
        String deleteIds[] = ids.split(",");
        for (int i = 0; i < deleteIds.length; i++) {
            this.employmentCategoryService.deleteById(Long.parseLong(deleteIds[i]));
        }
    }
}
