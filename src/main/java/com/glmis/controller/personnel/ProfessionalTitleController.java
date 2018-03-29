package com.glmis.controller.personnel;

import com.glmis.domain.personnel.ProfessionalTitle;
import com.glmis.service.personnel.ProfessionalTitleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2016/11/17.
 */

@RestController
public class ProfessionalTitleController {
    @Autowired
    ProfessionalTitleService professionalTitleService;

    /**
     * 获取到所有的职位名称
     */
    @RequestMapping(value = "/findAllPostNames", method = RequestMethod.GET)
    public List<ProfessionalTitle> findAllPostNames()throws Exception {
        List<ProfessionalTitle> professionalTitles = professionalTitleService.findAllT();
        return professionalTitles;
    }

    @RequestMapping(value = "/addProfessionalTitle",method = RequestMethod.POST)
    public Map<String,Object> addPostName(@RequestBody ProfessionalTitle professionalTitle)throws Exception{
        this.professionalTitleService.add(professionalTitle);
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("professionalName",professionalTitle);
        return map;
    }
    //修改
    @RequestMapping(value = "/updateProfessionalTitle",method = RequestMethod.PUT)
    public Map<String, Object> updatePostName(@RequestBody ProfessionalTitle professionalTitle)throws Exception{
        this.professionalTitleService.update(professionalTitle);
        Map<String, Object> map = new HashMap<String,Object>();
        map.put("professionalName", professionalTitle);
        return map;
    }
    //删除
    @RequestMapping(value="/deleteProfessionalTitle",method = RequestMethod.DELETE)
    public void deleteProfessionalTitle(@RequestParam("id") Long id)throws Exception{
        this.professionalTitleService.deleteById(id);
    }




}
