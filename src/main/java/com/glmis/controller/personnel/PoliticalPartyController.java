package com.glmis.controller.personnel;

import com.glmis.domain.personnel.PoliticalParty;
import com.glmis.service.personnel.PoliticalPartyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2016/11/17.
 */

@RestController
public class PoliticalPartyController {
    @Autowired
    PoliticalPartyService politicalPartyService;

    /**
     * 获取到所有的党派
     */
    @RequestMapping(value = "/findAllPoliticalParties", method = RequestMethod.GET)
    public List<PoliticalParty> findAllPartyGroup() throws Exception{
        List<PoliticalParty> politicalParties = politicalPartyService.findAllT();
        return politicalParties;
    }

    @RequestMapping(value = "/findAllParties", method = RequestMethod.GET)
    public List<Object> findAllParty()throws Exception {
        Map<String, String> userName = new HashMap<String, String>();
        Map<String, String> userValue = new HashMap<String, String>();
        userName.put("name", "名称");
        userValue.put("name", "编号");
        ArrayList<Object> result = new ArrayList<Object>();
        List<PoliticalParty> politicalParties = politicalPartyService.findAllT();
        for (PoliticalParty politicalParty : politicalParties) {
            userName.put("value", politicalParty.getDescription());
            userValue.put("value", politicalParty.getNo());
        }
        result.add(userName);
        result.add(userValue);
        return result;
    }
    //实现分页
    @RequestMapping(value = "/displayAllPoliticalParties", method = RequestMethod.GET)
    public Map<String, Object> displayAllPoliticalParties(@RequestParam(value = "page") Integer page,
                                                 @RequestParam(value = "rows") Integer size)throws Exception {
        Page<PoliticalParty> list = this.politicalPartyService.findAllT(new PageRequest(page - 1, size));
        Map<String, java.lang.Object> map = new HashMap<String, Object>();
        int total = this.politicalPartyService.findAllT().size();
        map.put("total", total);
        map.put("rows", list.getContent());
        return map;
    }

    //增
    @RequestMapping(value = "/addPoliticalParty", method = RequestMethod.POST)
    public Map<String, java.lang.Object> addPoliticalParty(@RequestBody PoliticalParty PoliticalParty) throws Exception{
        this.politicalPartyService.add(PoliticalParty);
        Map<String, java.lang.Object> map = new HashMap<String, java.lang.Object>();
        map.put("PoliticalParty", PoliticalParty);
        return map;
    }

    //改
    @RequestMapping(value = "/updatePoliticalParty", method = RequestMethod.PUT)
    public Map<String, java.lang.Object> updatePoliticalParty(@RequestBody PoliticalParty PoliticalParty) throws Exception{
        this.politicalPartyService.update(PoliticalParty);
        Map<String, java.lang.Object> map = new HashMap<String, Object>();
        map.put("PoliticalParty", PoliticalParty);
        return map;
    }

    //删
    @RequestMapping(value = "/deletePoliticalParty", method = RequestMethod.DELETE)
    public void deletePoliticalParty(@RequestParam("ids") String ids)throws Exception {
        String deleteIds[] = ids.split(",");
        for (int i = 0; i < deleteIds.length; i++) {
            this.politicalPartyService.deleteById(Long.parseLong(deleteIds[i]));
        }
    }
}
