package com.glmis.controller.personnel;

import com.glmis.domain.personnel.AcademicDegreeType;
import com.glmis.service.personnel.AcademicDegreeTypeService;
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
public class AcademicDegreeTypeController {
    @Autowired
    AcademicDegreeTypeService academicDegreeTypeService;

    /**
     * 获取到所有的学位学科
     */
    @RequestMapping(value = "/findAllAcademicDegreeTypes", method = RequestMethod.GET)
    public List<AcademicDegreeType> findAllSubjects() throws Exception{
        List<AcademicDegreeType> academicDegreeTypes = academicDegreeTypeService.findAllT();
        return academicDegreeTypes;
    }
    //实现分页
    @RequestMapping(value = "/displayAllAcademicDegreeTypes", method = RequestMethod.GET)
    public Map<String, Object> displayAllAcademicDegreeTypes(@RequestParam(value = "page") Integer page,
                                                         @RequestParam(value = "rows") Integer size)throws Exception {
        Page<AcademicDegreeType> list = this.academicDegreeTypeService.findAllT(new PageRequest(page - 1, size));
        Map<String, Object> map = new HashMap<String, Object>();
        int total = this.academicDegreeTypeService.findAllT().size();
        map.put("total", total);
        map.put("rows", list.getContent());
        return map;
    }

    //添加一个新的学位学科  完成 增
    @RequestMapping(value = "/addAcademicDegreeType", method = RequestMethod.POST)
    public Map<String, Object> addAcademicDegreeType(@RequestBody AcademicDegreeType academicDegreeType)throws Exception {
        this.academicDegreeTypeService.add(academicDegreeType);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("academicDegreeType", academicDegreeType);
        return map;
    }

    //修改学位学科信息    完成 改
    @RequestMapping(value = "/updateAcademicDegreeType", method = RequestMethod.PUT)
    public Map<String, Object> updateAcademicDegreeType(@RequestBody AcademicDegreeType academicDegreeType)throws Exception {
        this.academicDegreeTypeService.update(academicDegreeType);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("academicDegreeType", academicDegreeType);
        return map;
    }

    //删除一个学位学科   完成 删
    @RequestMapping(value = "/deleteAcademicDegreeType", method = RequestMethod.DELETE)
    public void deleteAcademicDegreeType(@RequestParam("ids") String ids)throws Exception {
        String deleteIds[] = ids.split(",");
        for (int i = 0; i < deleteIds.length; i++) {
            this.academicDegreeTypeService.deleteById(Long.parseLong(deleteIds[i]));
        }
    }
}
