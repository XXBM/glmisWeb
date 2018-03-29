package com.glmis.controller.personnel;

import com.glmis.domain.personnel.Issuer;
import com.glmis.service.personnel.IssuerService;
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
public class IssuerController {
    @Autowired
    IssuerService issuerService;

    /**
     * 获取到所有的发证机构名字
     */
    @RequestMapping(value = "/findAllCertificateOrganization", method = RequestMethod.GET)
    public List<Issuer> findAllCertificateOrganization()throws Exception {
        List<Issuer> issuers = issuerService.findAllT();
        return issuers;
    }
    //实现分页
    @RequestMapping(value = "/displayAllIssuers", method = RequestMethod.GET)
    public Map<String, Object> displayAllIssuers(@RequestParam(value = "page") Integer page,
                                                             @RequestParam(value = "rows") Integer size)throws Exception {
        Page<Issuer> list = this.issuerService.findAllT(new PageRequest(page - 1, size));
        Map<String, Object> map = new HashMap<String, Object>();
        int total = this.issuerService.findAllT().size();
        map.put("total", total);
        map.put("rows", list.getContent());
        return map;
    }

    //添加一个新的发证机构  完成 增
    @RequestMapping(value = "/addIssuer", method = RequestMethod.POST)
    public Map<String, Object> addIssuer(@RequestBody Issuer issuer) throws Exception{
        this.issuerService.add(issuer);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("issuer", issuer);
        return map;
    }

    //修改发证机构信息    完成 改
    @RequestMapping(value = "/updateIssuer", method = RequestMethod.PUT)
    public Map<String, Object> updateIssuer(@RequestBody Issuer issuer)throws Exception {
        this.issuerService.update(issuer);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("issuer", issuer);
        return map;
    }

    //删除一个发证机构   完成 删
    @RequestMapping(value = "/deleteIssuer", method = RequestMethod.DELETE)
    public void deleteIssuer(@RequestParam("ids") String ids)throws Exception {
        String deleteIds[] = ids.split(",");
        for (int i = 0; i < deleteIds.length; i++) {
            this.issuerService.deleteById(Long.parseLong(deleteIds[i]));
        }
    }
}
