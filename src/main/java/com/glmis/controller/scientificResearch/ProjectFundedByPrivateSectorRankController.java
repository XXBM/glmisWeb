package com.glmis.controller.scientificResearch;

import com.glmis.domain.scientificResearch.ProjectFundedByPrivateSectorRank;
import com.glmis.service.scientificResearch.ProjectFundedByPrivateSectorRankService;
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
public class ProjectFundedByPrivateSectorRankController {
    @Autowired
    ProjectFundedByPrivateSectorRankService projectFundedByPrivateSectorRankService;
    /**
     * 获取到所有的获奖等级
     */
    @RequestMapping(value = "/findAllProjectFundedByPrivateSectorRanks", method = RequestMethod.GET)
    public List<ProjectFundedByPrivateSectorRank> findAllCitation() {
        List<ProjectFundedByPrivateSectorRank> projectFundedByPrivateSectorRanks = projectFundedByPrivateSectorRankService.findAllT();
        return projectFundedByPrivateSectorRanks;
    }
    //实现分页
    @RequestMapping(value = "/displayAllProjectFundedByPrivateSectorRanks", method = RequestMethod.GET)
    public Map<String, Object> displayAllProjectFundedByPrivateSectorRanks(@RequestParam(value = "page") Integer page,
                                                 @RequestParam(value = "rows") Integer size)throws Exception {
        Page<ProjectFundedByPrivateSectorRank> list = this.projectFundedByPrivateSectorRankService.findAllT(new PageRequest(page - 1, size));
        Map<String, java.lang.Object> map = new HashMap<String, Object>();
        int total = this.projectFundedByPrivateSectorRankService.findAllT().size();
        map.put("total", total);
        map.put("rows", list.getContent());
        return map;
    }

    //增
    @RequestMapping(value = "/addProjectFundedByPrivateSectorRank", method = RequestMethod.POST)
    public Map<String, java.lang.Object> addProjectFundedByPrivateSectorRank(@RequestBody ProjectFundedByPrivateSectorRank ProjectFundedByPrivateSectorRank) throws Exception{
        this.projectFundedByPrivateSectorRankService.add(ProjectFundedByPrivateSectorRank);
        Map<String, java.lang.Object> map = new HashMap<String, java.lang.Object>();
        map.put("ProjectFundedByPrivateSectorRank", ProjectFundedByPrivateSectorRank);
        return map;
    }

    //改
    @RequestMapping(value = "/updateProjectFundedByPrivateSectorRank", method = RequestMethod.PUT)
    public Map<String, java.lang.Object> updateProjectFundedByPrivateSectorRank(@RequestBody ProjectFundedByPrivateSectorRank ProjectFundedByPrivateSectorRank) throws Exception{
        this.projectFundedByPrivateSectorRankService.update(ProjectFundedByPrivateSectorRank);
        Map<String, java.lang.Object> map = new HashMap<String, Object>();
        map.put("ProjectFundedByPrivateSectorRank", ProjectFundedByPrivateSectorRank);
        return map;
    }

    //删
    @RequestMapping(value = "/deleteProjectFundedByPrivateSectorRank", method = RequestMethod.DELETE)
    public void deleteProjectFundedByPrivateSectorRank(@RequestParam("ids") String ids)throws Exception {
        String deleteIds[] = ids.split(",");
        for (int i = 0; i < deleteIds.length; i++) {
            this.projectFundedByPrivateSectorRankService.deleteById(Long.parseLong(deleteIds[i]));
        }
    }
}
