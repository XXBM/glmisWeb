package com.glmis.controller.scientificResearch;

import com.glmis.domain.scientificResearch.JournalRank;
import com.glmis.service.scientificResearch.JournalRankService;
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
public class JournalRankController {
    @Autowired
    JournalRankService journalRankService;
    /**
     * 获取到所有的获奖等级
     */
    @RequestMapping(value = "/findAllJournalRanks", method = RequestMethod.GET)
    public List<JournalRank> findAllCitation() {
        System.out.println("come");
        List<JournalRank> journalRanks = journalRankService.findAllT();
        return journalRanks;
    }
    //实现分页
    @RequestMapping(value = "/displayAllJournalRanks", method = RequestMethod.GET)
    public Map<String, Object> displayAllJournalRanks(@RequestParam(value = "page") Integer page,
                                                 @RequestParam(value = "rows") Integer size)throws Exception {
        Page<JournalRank> list = this.journalRankService.findAllT(new PageRequest(page - 1, size));
        Map<String, java.lang.Object> map = new HashMap<String, Object>();
        int total = this.journalRankService.findAllT().size();
        map.put("total", total);
        map.put("rows", list.getContent());
        return map;
    }

    //增
    @RequestMapping(value = "/addJournalRank", method = RequestMethod.POST)
    public Map<String, java.lang.Object> addJournalRank(@RequestBody JournalRank JournalRank) throws Exception{
        this.journalRankService.add(JournalRank);
        Map<String, java.lang.Object> map = new HashMap<String, java.lang.Object>();
        map.put("JournalRank", JournalRank);
        return map;
    }

    //改
    @RequestMapping(value = "/updateJournalRank", method = RequestMethod.PUT)
    public Map<String, java.lang.Object> updateJournalRank(@RequestBody JournalRank JournalRank) throws Exception{
        this.journalRankService.update(JournalRank);
        Map<String, java.lang.Object> map = new HashMap<String, Object>();
        map.put("JournalRank", JournalRank);
        return map;
    }

    //删
    @RequestMapping(value = "/deleteJournalRank", method = RequestMethod.DELETE)
    public void deleteJournalRank(@RequestParam("ids") String ids)throws Exception {
        String deleteIds[] = ids.split(",");
        for (int i = 0; i < deleteIds.length; i++) {
            this.journalRankService.deleteById(Long.parseLong(deleteIds[i]));
        }
    }
}
