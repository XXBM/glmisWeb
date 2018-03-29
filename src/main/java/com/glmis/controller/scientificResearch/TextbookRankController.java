package com.glmis.controller.scientificResearch;

import com.glmis.domain.scientificResearch.TextbookRank;
import com.glmis.service.scientificResearch.TextbookRankService;
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
public class TextbookRankController {
    @Autowired
    TextbookRankService textbookRankService;
    /**
     * 获取到所有的获奖等级
     */
    @RequestMapping(value = "/findAllTextbookRanks", method = RequestMethod.GET)
    public List<TextbookRank> findAllCitation() {
        List<TextbookRank> textbookRanks = textbookRankService.findAllT();
        return textbookRanks;
    }
    //实现分页
    @RequestMapping(value = "/displayAllTextbookRanks", method = RequestMethod.GET)
    public Map<String, Object> displayAllTextbookRanks(@RequestParam(value = "page") Integer page,
                                                 @RequestParam(value = "rows") Integer size)throws Exception {
        Page<TextbookRank> list = this.textbookRankService.findAllT(new PageRequest(page - 1, size));
        Map<String, java.lang.Object> map = new HashMap<String, Object>();
        int total = this.textbookRankService.findAllT().size();
        map.put("total", total);
        map.put("rows", list.getContent());
        return map;
    }

    //增
    @RequestMapping(value = "/addTextbookRank", method = RequestMethod.POST)
    public Map<String, java.lang.Object> addTextbookRank(@RequestBody TextbookRank TextbookRank) throws Exception{
        this.textbookRankService.add(TextbookRank);
        Map<String, java.lang.Object> map = new HashMap<String, java.lang.Object>();
        map.put("TextbookRank", TextbookRank);
        return map;
    }

    //改
    @RequestMapping(value = "/updateTextbookRank", method = RequestMethod.PUT)
    public Map<String, java.lang.Object> updateTextbookRank(@RequestBody TextbookRank TextbookRank) throws Exception{
        this.textbookRankService.update(TextbookRank);
        Map<String, java.lang.Object> map = new HashMap<String, Object>();
        map.put("TextbookRank", TextbookRank);
        return map;
    }

    //删
    @RequestMapping(value = "/deleteTextbookRank", method = RequestMethod.DELETE)
    public void deleteTextbookRank(@RequestParam("ids") String ids)throws Exception {
        String deleteIds[] = ids.split(",");
        for (int i = 0; i < deleteIds.length; i++) {
            this.textbookRankService.deleteById(Long.parseLong(deleteIds[i]));
        }
    }
}
