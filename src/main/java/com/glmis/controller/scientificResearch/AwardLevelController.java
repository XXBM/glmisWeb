package com.glmis.controller.scientificResearch;


import com.glmis.domain.scientificResearch.AwardLevel;
import com.glmis.service.scientificResearch.AwardLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by inkskyu428 on 17-5-11.
 */
@RestController
public class AwardLevelController {
    @Autowired
    AwardLevelService awardLevelService;
    /**
     * 获取到所有的获奖等级
     */
    @RequestMapping(value = "/findAllAwardLevel", method = RequestMethod.GET)
    public List<AwardLevel> findAllAwardLevel() {
        List<AwardLevel> awardLevels = awardLevelService.findAllT();
        return awardLevels;
    }
    //实现分页
    @RequestMapping(value = "/displayAllAwardLevels", method = RequestMethod.GET)
    public Map<String, Object> displayAllAwardLevels(@RequestParam(value = "page") Integer page,
                                                 @RequestParam(value = "rows") Integer size)throws Exception {
        Page<AwardLevel> list = this.awardLevelService.findAllT(new PageRequest(page - 1, size));
        Map<String, java.lang.Object> map = new HashMap<String, Object>();
        int total = this.awardLevelService.findAllT().size();
        map.put("total", total);
        map.put("rows", list.getContent());
        return map;
    }

    //增
    @RequestMapping(value = "/addAwardLevel", method = RequestMethod.POST)
    public Map<String, java.lang.Object> addDepartment(@RequestBody AwardLevel AwardLevel) throws Exception{
        this.awardLevelService.add(AwardLevel);
        Map<String, java.lang.Object> map = new HashMap<String, java.lang.Object>();
        map.put("AwardLevel", AwardLevel);
        return map;
    }

    //改
    @RequestMapping(value = "/updateAwardLevel", method = RequestMethod.PUT)
    public Map<String, java.lang.Object> updateAwardLevel(@RequestBody AwardLevel AwardLevel) throws Exception{
        this.awardLevelService.update(AwardLevel);
        Map<String, java.lang.Object> map = new HashMap<String, Object>();
        map.put("AwardLevel", AwardLevel);
        return map;
    }

    //删
    @RequestMapping(value = "/deleteAwardLevel", method = RequestMethod.DELETE)
    public void deleteAwardLevel(@RequestParam("ids") String ids)throws Exception {
        String deleteIds[] = ids.split(",");
        for (int i = 0; i < deleteIds.length; i++) {
            this.awardLevelService.deleteById(Long.parseLong(deleteIds[i]));
        }
    }
}
