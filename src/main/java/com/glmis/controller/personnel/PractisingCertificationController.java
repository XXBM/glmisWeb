package com.glmis.controller.personnel;

import com.glmis.domain.personnel.PractisingCertification;
import com.glmis.service.personnel.PractisingCertificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2016/11/17.
 */
@RestController
public class PractisingCertificationController {
    @Autowired
    PractisingCertificationService practisingCertificationService;

    /**获取到所有的发证机构名字*/
    @RequestMapping(value = "/findAllQualificationName",method = RequestMethod.GET)
    public List<PractisingCertification> findAllQualificationName()throws Exception{
        List<PractisingCertification> qualificationNames = practisingCertificationService.findAllT();
        return qualificationNames;
    }

    //添加发证机构名字
    @RequestMapping(value = "/addQualificationName",method = RequestMethod.POST)
    public Map<String, Object> addQualificationName(@RequestBody  PractisingCertification practisingCertification)throws Exception{
        this.practisingCertificationService.add(practisingCertification);
        Map<String, Object> map = new HashMap<String,Object>();
        map.put("num",practisingCertification);
        return map;
    }

    //修改发证机构名字
    @RequestMapping(value = "/updateQualificationName",method = RequestMethod.PUT)
    public Map<String, Object> updateQualificationName(@RequestBody PractisingCertification practisingCertification)throws Exception{
        this.practisingCertificationService.update(practisingCertification);
        Map<String, Object> map = new HashMap<String,Object>();
        map.put("num",practisingCertification);
        return map;
    }

    //删除发证机构名字
    @RequestMapping(value = "/deleteQualificationName",method = RequestMethod.DELETE)
    public void deleteQualificationName(@RequestParam("ids") String ids)throws Exception{
        String deleteIds[] = ids.split(",");
        for(int i = 0; i<deleteIds.length; i++){
            this.practisingCertificationService.deleteById(Long.parseLong(deleteIds[i]));
        }
    }
}

