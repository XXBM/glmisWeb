package com.glmis.controller.personnel;

import com.glmis.domain.personnel.PositionRank;
import com.glmis.service.personnel.PositionRankService;
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
public class PositionRankController {
    @Autowired
    PositionRankService positionRankService;

    /**
     * 获取到所有的等级
     */
    @RequestMapping(value = "/findAllRank", method = RequestMethod.GET)
    public List<PositionRank> findAllRank()throws Exception {
        List<PositionRank> positionRanks = positionRankService.findAllT();
        return positionRanks;
    }
    //实现分页
    @RequestMapping(value = "/displayAllPositionRanks", method = RequestMethod.GET)
    public Map<String, Object> displayAllPositionRanks(@RequestParam(value = "page") Integer page,
                                                 @RequestParam(value = "rows") Integer size)throws Exception {
        Page<PositionRank> list = this.positionRankService.findAllT(new PageRequest(page - 1, size));
        Map<String, java.lang.Object> map = new HashMap<String, Object>();
        int total = this.positionRankService.findAllT().size();
        map.put("total", total);
        map.put("rows", list.getContent());
        return map;
    }

    //增
    @RequestMapping(value = "/addPositionRank", method = RequestMethod.POST)
    public Map<String, java.lang.Object> addDepartment(@RequestBody PositionRank PositionRank) throws Exception{
        this.positionRankService.add(PositionRank);
        Map<String, java.lang.Object> map = new HashMap<String, java.lang.Object>();
        map.put("PositionRank", PositionRank);
        return map;
    }

    //改
    @RequestMapping(value = "/updatePositionRank", method = RequestMethod.PUT)
    public Map<String, java.lang.Object> updatePositionRank(@RequestBody PositionRank PositionRank) throws Exception{
        this.positionRankService.update(PositionRank);
        Map<String, java.lang.Object> map = new HashMap<String, Object>();
        map.put("PositionRank", PositionRank);
        return map;
    }

    //删
    @RequestMapping(value = "/deletePositionRank", method = RequestMethod.DELETE)
    public void deletePositionRank(@RequestParam("ids") String ids)throws Exception {
        String deleteIds[] = ids.split(",");
        for (int i = 0; i < deleteIds.length; i++) {
            this.positionRankService.deleteById(Long.parseLong(deleteIds[i]));
        }
    }
}
