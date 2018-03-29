package com.glmis.controller.personnel;

import com.glmis.domain.personnel.AcademicDegree;
import com.glmis.service.personnel.AcademicDegreeService;
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
public class AcademicDegreeController {
    @Autowired
    AcademicDegreeService academicDegreeService;

    /**
     * 获取到所有的学位等级名称
     */
    @RequestMapping(value = "/findAllAcademicDegrees", method = RequestMethod.GET)
    public List<AcademicDegree> findAllDegreeLevel()throws Exception {
        List<AcademicDegree> academicDegrees = academicDegreeService.findAllT();
        return academicDegrees;
    }

    //实现分页
    @RequestMapping(value = "/displayAllAcademicDegrees", method = RequestMethod.GET)
    public Map<String, Object> displayAllAcademicDegrees(@RequestParam(value = "page") Integer page,
                                                         @RequestParam(value = "rows") Integer size)throws Exception {
        Page<AcademicDegree> list = this.academicDegreeService.findAllT(new PageRequest(page - 1, size));
        Map<String, java.lang.Object> map = new HashMap<String, java.lang.Object>();
        int total = this.academicDegreeService.findAllT().size();
        map.put("total", total);
        map.put("rows", list.getContent());
        return map;
    }

    //添加一个新的学院  完成 增
    @RequestMapping(value = "/addAcademicDegree", method = RequestMethod.POST)
    public Map<String, java.lang.Object> addDepartment(@RequestBody AcademicDegree academicDegree) throws Exception{
        this.academicDegreeService.add(academicDegree);
        Map<String, java.lang.Object> map = new HashMap<String, java.lang.Object>();
        map.put("academicDegree", academicDegree);
        return map;
    }

    //修改学院信息    完成 改
    @RequestMapping(value = "/updateAcademicDegree", method = RequestMethod.PUT)
    public Map<String, java.lang.Object> updateAcademicDegree(@RequestBody AcademicDegree academicDegree) throws Exception{
        this.academicDegreeService.update(academicDegree);
        Map<String, java.lang.Object> map = new HashMap<String, Object>();
        map.put("academicDegree", academicDegree);
        return map;
    }

    //删除一个学院   完成 删
    @RequestMapping(value = "/deleteAcademicDegree", method = RequestMethod.DELETE)
    public void deleteAcademicDegree(@RequestParam("ids") String ids)throws Exception {
        String deleteIds[] = ids.split(",");
        for (int i = 0; i < deleteIds.length; i++) {
            this.academicDegreeService.deleteById(Long.parseLong(deleteIds[i]));
        }
    }
}
