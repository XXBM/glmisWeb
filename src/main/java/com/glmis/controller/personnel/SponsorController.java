package com.glmis.controller.personnel;

import com.glmis.domain.personnel.Sponsor;
import com.glmis.service.personnel.SponsorService;
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
public class SponsorController {
    @Autowired
    SponsorService sponsorService;

    /**
     * 获取到所有的资助机构名称
     */
    @RequestMapping(value = "/findAllFundOrganization", method = RequestMethod.GET)
    public List<Sponsor> findAllFundOrganization() throws Exception{
        List<Sponsor> sponsors = sponsorService.findAllT();
        return sponsors;
    }
    //实现分页
    @RequestMapping(value = "/displayAllSponsors", method = RequestMethod.GET)
    public Map<String, Object> displayAllSponsors(@RequestParam(value = "page") Integer page,
                                                             @RequestParam(value = "rows") Integer size) throws Exception{
        Page<Sponsor> list = this.sponsorService.findAllT(new PageRequest(page - 1, size));
        Map<String, Object> map = new HashMap<String, Object>();
        int total = this.sponsorService.findAllT().size();
        map.put("total", total);
        map.put("rows", list.getContent());
        return map;
    }

    //添加一个新的资助机构  完成 增
    @RequestMapping(value = "/addSponsor", method = RequestMethod.POST)
    public Map<String, Object> addSponsor(@RequestBody Sponsor sponsor)throws Exception {
        this.sponsorService.add(sponsor);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sponsor", sponsor);
        return map;
    }

    //修改资助机构信息    完成 改
    @RequestMapping(value = "/updateSponsor", method = RequestMethod.PUT)
    public Map<String, Object> updateSponsor(@RequestBody Sponsor sponsor)throws Exception {
        this.sponsorService.update(sponsor);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sponsor", sponsor);
        return map;
    }

    //删除一个资助机构   完成 删
    @RequestMapping(value = "/deleteSponsor", method = RequestMethod.DELETE)
    public void deleteSponsor(@RequestParam("ids") String ids)throws Exception {
        String deleteIds[] = ids.split(",");
        for (int i = 0; i < deleteIds.length; i++) {
            this.sponsorService.deleteById(Long.parseLong(deleteIds[i]));
        }
    }
}
