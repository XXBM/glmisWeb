package com.glmis.controller.personnel;

import com.glmis.domain.personnel.Nation;
import com.glmis.service.personnel.NationService;
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
public class NationController {
    @Autowired
    NationService nationService;

    /**
     * 获取到所有的民族
     */
    @RequestMapping(value = "/findAllNation", method = RequestMethod.GET)
    public List<Nation> findAllNation()throws Exception {
        List<Nation> nations = nationService.findAllT();
        return nations;
    }
    //实现分页
    @RequestMapping(value = "/displayAllNations", method = RequestMethod.GET)
    public Map<String, Object> displayAllNations(@RequestParam(value = "page") Integer page,
                                                         @RequestParam(value = "rows") Integer size)throws Exception {
        Page<Nation> list = this.nationService.findAllT(new PageRequest(page - 1, size));
        Map<String, java.lang.Object> map = new HashMap<String, Object>();
        int total = this.nationService.findAllT().size();
        map.put("total", total);
        map.put("rows", list.getContent());
        return map;
    }

    //增
    @RequestMapping(value = "/addNation", method = RequestMethod.POST)
    public Map<String, java.lang.Object> addDepartment(@RequestBody Nation Nation) throws Exception{
        this.nationService.add(Nation);
        Map<String, java.lang.Object> map = new HashMap<String, java.lang.Object>();
        map.put("Nation", Nation);
        return map;
    }

    //改
    @RequestMapping(value = "/updateNation", method = RequestMethod.PUT)
    public Map<String, java.lang.Object> updateNation(@RequestBody Nation Nation) throws Exception{
        this.nationService.update(Nation);
        Map<String, java.lang.Object> map = new HashMap<String, Object>();
        map.put("Nation", Nation);
        return map;
    }

    //删
    @RequestMapping(value = "/deleteNation", method = RequestMethod.DELETE)
    public void deleteNation(@RequestParam("ids") String ids)throws Exception {
        String deleteIds[] = ids.split(",");
        for (int i = 0; i < deleteIds.length; i++) {
            this.nationService.deleteById(Long.parseLong(deleteIds[i]));
        }
    }
}
