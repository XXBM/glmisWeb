package com.glmis.controller.scientificResearch;

import com.glmis.domain.scientificResearch.MonographRank;
import com.glmis.service.scientificResearch.MonographRankService;
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
public class MonographRankController {
    @Autowired
    MonographRankService monographRankService;
    /**
     * 获取到所有的获奖等级
     */
    @RequestMapping(value = "/findAllMonographRanks", method = RequestMethod.GET)
    public List<MonographRank> findAllCitation() {
        List<MonographRank> monographRanks = monographRankService.findAllT();
        return monographRanks;
    }
    //实现分页
    @RequestMapping(value = "/displayAllMonographRanks", method = RequestMethod.GET)
    public Map<String, Object> displayAllMonographRanks(@RequestParam(value = "page") Integer page,
                                                 @RequestParam(value = "rows") Integer size)throws Exception {
        Page<MonographRank> list = this.monographRankService.findAllT(new PageRequest(page - 1, size));
        Map<String, java.lang.Object> map = new HashMap<String, Object>();
        int total = this.monographRankService.findAllT().size();
        map.put("total", total);
        map.put("rows", list.getContent());
        return map;
    }

    //增
    @RequestMapping(value = "/addMonographRank", method = RequestMethod.POST)
    public Map<String, java.lang.Object> addMonographRank(@RequestBody MonographRank MonographRank) throws Exception{
        this.monographRankService.add(MonographRank);
        Map<String, java.lang.Object> map = new HashMap<String, java.lang.Object>();
        map.put("MonographRank", MonographRank);
        return map;
    }

    //改
    @RequestMapping(value = "/updateMonographRank", method = RequestMethod.PUT)
    public Map<String, java.lang.Object> updateMonographRank(@RequestBody MonographRank MonographRank) throws Exception{
        this.monographRankService.update(MonographRank);
        Map<String, java.lang.Object> map = new HashMap<String, Object>();
        map.put("MonographRank", MonographRank);
        return map;
    }

    //删
    @RequestMapping(value = "/deleteMonographRank", method = RequestMethod.DELETE)
    public void deleteMonographRank(@RequestParam("ids") String ids)throws Exception {
        String deleteIds[] = ids.split(",");
        for (int i = 0; i < deleteIds.length; i++) {
            this.monographRankService.deleteById(Long.parseLong(deleteIds[i]));
        }
    }
}
