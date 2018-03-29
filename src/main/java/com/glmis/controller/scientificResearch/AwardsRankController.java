package com.glmis.controller.scientificResearch;

import com.glmis.domain.scientificResearch.AwardsRank;
import com.glmis.service.scientificResearch.AwardsRankService;
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
public class AwardsRankController {
    @Autowired
    AwardsRankService awardsRankService;
    /**
     * 获取到所有的获奖等级
     */
    @RequestMapping(value = "/findAllAwardsRanks", method = RequestMethod.GET)
    public List<AwardsRank> findAllCitation() {
        List<AwardsRank> awardsRanks = awardsRankService.findAllT();
        return awardsRanks;
    }
    //实现分页
    @RequestMapping(value = "/displayAllAwardsRanks", method = RequestMethod.GET)
    public Map<String, Object> displayAllAwardsRanks(@RequestParam(value = "page") Integer page,
                                                 @RequestParam(value = "rows") Integer size)throws Exception {
        Page<AwardsRank> list = this.awardsRankService.findAllT(new PageRequest(page - 1, size));
        Map<String, java.lang.Object> map = new HashMap<String, Object>();
        int total = this.awardsRankService.findAllT().size();
        map.put("total", total);
        map.put("rows", list.getContent());
        return map;
    }

    //增
    @RequestMapping(value = "/addAwardsRank", method = RequestMethod.POST)
    public Map<String, java.lang.Object> addAwardsRank(@RequestBody AwardsRank AwardsRank) throws Exception{
        this.awardsRankService.add(AwardsRank);
        Map<String, java.lang.Object> map = new HashMap<String, java.lang.Object>();
        map.put("AwardsRank", AwardsRank);
        return map;
    }

    //改
    @RequestMapping(value = "/updateAwardsRank", method = RequestMethod.PUT)
    public Map<String, java.lang.Object> updateAwardsRank(@RequestBody AwardsRank AwardsRank) throws Exception{
        this.awardsRankService.update(AwardsRank);
        Map<String, java.lang.Object> map = new HashMap<String, Object>();
        map.put("AwardsRank", AwardsRank);
        return map;
    }

    //删
    @RequestMapping(value = "/deleteAwardsRank", method = RequestMethod.DELETE)
    public void deleteAwardsRank(@RequestParam("ids") String ids)throws Exception {
        String deleteIds[] = ids.split(",");
        for (int i = 0; i < deleteIds.length; i++) {
            this.awardsRankService.deleteById(Long.parseLong(deleteIds[i]));
        }
    }
}
