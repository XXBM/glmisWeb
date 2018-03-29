package com.glmis.controller.scientificResearch;

import com.glmis.domain.scientificResearch.ProjectFundedByGovernmentRank;
import com.glmis.service.scientificResearch.ProjectFundedByGovernmentRankService;
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
public class ProjectFundedByGovernmentRankController {
    @Autowired
    ProjectFundedByGovernmentRankService projectFundedByGovernmentRankService;
    /**
     * 获取到所有的获奖等级
     */
    @RequestMapping(value = "/findAllProjectFundedByGovernmentRanks", method = RequestMethod.GET)
    public List<ProjectFundedByGovernmentRank> findAllCitation() {
        List<ProjectFundedByGovernmentRank> projectFundedByGovernmentRanks = projectFundedByGovernmentRankService.findAllT();
        return projectFundedByGovernmentRanks;
    }
    //实现分页
    @RequestMapping(value = "/displayAllProjectFundedByGovernmentRanks", method = RequestMethod.GET)
    public Map<String, Object> displayAllProjectFundedByGovernmentRanks(@RequestParam(value = "page") Integer page,
                                                 @RequestParam(value = "rows") Integer size)throws Exception {
        Page<ProjectFundedByGovernmentRank> list = this.projectFundedByGovernmentRankService.findAllT(new PageRequest(page - 1, size));
        Map<String, java.lang.Object> map = new HashMap<String, Object>();
        int total = this.projectFundedByGovernmentRankService.findAllT().size();
        map.put("total", total);
        map.put("rows", list.getContent());
        return map;
    }

    //增
    @RequestMapping(value = "/addProjectFundedByGovernmentRank", method = RequestMethod.POST)
    public Map<String, java.lang.Object> addProjectFundedByGovernmentRank(@RequestBody ProjectFundedByGovernmentRank ProjectFundedByGovernmentRank) throws Exception{
        this.projectFundedByGovernmentRankService.add(ProjectFundedByGovernmentRank);
        Map<String, java.lang.Object> map = new HashMap<String, java.lang.Object>();
        map.put("ProjectFundedByGovernmentRank", ProjectFundedByGovernmentRank);
        return map;
    }

    //改
    @RequestMapping(value = "/updateProjectFundedByGovernmentRank", method = RequestMethod.PUT)
    public Map<String, java.lang.Object> updateProjectFundedByGovernmentRank(@RequestBody ProjectFundedByGovernmentRank ProjectFundedByGovernmentRank) throws Exception{
        this.projectFundedByGovernmentRankService.update(ProjectFundedByGovernmentRank);
        Map<String, java.lang.Object> map = new HashMap<String, Object>();
        map.put("ProjectFundedByGovernmentRank", ProjectFundedByGovernmentRank);
        return map;
    }

    //删
    @RequestMapping(value = "/deleteProjectFundedByGovernmentRank", method = RequestMethod.DELETE)
    public void deleteProjectFundedByGovernmentRank(@RequestParam("ids") String ids)throws Exception {
        String deleteIds[] = ids.split(",");
        for (int i = 0; i < deleteIds.length; i++) {
            this.projectFundedByGovernmentRankService.deleteById(Long.parseLong(deleteIds[i]));
        }
    }
}
