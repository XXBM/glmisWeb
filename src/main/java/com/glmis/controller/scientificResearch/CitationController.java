package com.glmis.controller.scientificResearch;

import com.glmis.domain.scientificResearch.Citation;
import com.glmis.service.scientificResearch.CitationService;
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
public class CitationController {
    @Autowired
    CitationService citationService;
    /**
     * 获取到所有的获奖等级
     */
    @RequestMapping(value = "/findAllCitation", method = RequestMethod.GET)
    public List<Citation> findAllCitation() {
        List<Citation> citations = citationService.findAllT();
        return citations;
    }
    //实现分页
    @RequestMapping(value = "/displayAllCitations", method = RequestMethod.GET)
    public Map<String, Object> displayAllCitations(@RequestParam(value = "page") Integer page,
                                                 @RequestParam(value = "rows") Integer size)throws Exception {
        Page<Citation> list = this.citationService.findAllT(new PageRequest(page - 1, size));
        Map<String, java.lang.Object> map = new HashMap<String, Object>();
        int total = this.citationService.findAllT().size();
        map.put("total", total);
        map.put("rows", list.getContent());
        return map;
    }

    //增
    @RequestMapping(value = "/addCitation", method = RequestMethod.POST)
    public Map<String, java.lang.Object> addCitation(@RequestBody Citation Citation) throws Exception{
        this.citationService.add(Citation);
        Map<String, java.lang.Object> map = new HashMap<String, java.lang.Object>();
        map.put("Citation", Citation);
        return map;
    }

    //改
    @RequestMapping(value = "/updateCitation", method = RequestMethod.PUT)
    public Map<String, java.lang.Object> updateCitation(@RequestBody Citation Citation) throws Exception{
        this.citationService.update(Citation);
        Map<String, java.lang.Object> map = new HashMap<String, Object>();
        map.put("Citation", Citation);
        return map;
    }

    //删
    @RequestMapping(value = "/deleteCitation", method = RequestMethod.DELETE)
    public void deleteCitation(@RequestParam("ids") String ids)throws Exception {
        String deleteIds[] = ids.split(",");
        for (int i = 0; i < deleteIds.length; i++) {
            this.citationService.deleteById(Long.parseLong(deleteIds[i]));
        }
    }
}
