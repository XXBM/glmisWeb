package com.glmis.controller.personnel;

import com.glmis.domain.personnel.Host;
import com.glmis.service.personnel.HostService;
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
public class HostController {
    @Autowired
    HostService hostService;

    /**
     * 获取到所有的主办机构名称
     */
    @RequestMapping(value = "/findAllHostOrganization", method = RequestMethod.GET)
    public List<Host> findAllHostOrganization()throws Exception {
        List<Host> hosts = hostService.findAllT();
        return hosts;
    }
    //实现分页
    @RequestMapping(value = "/displayAllHosts", method = RequestMethod.GET)
    public Map<String, Object> displayAllHosts(@RequestParam(value = "page") Integer page,
                                               @RequestParam(value = "rows") Integer size) {
        Page<Host> list = this.hostService.findAllT(new PageRequest(page - 1, size));
        Map<String, Object> map = new HashMap<String, Object>();
        int total = this.hostService.findAllT().size();
        map.put("total", total);
        map.put("rows", list.getContent());
        return map;
    }

    //添加一个新的主办机构  完成 增
    @RequestMapping(value = "/addHost", method = RequestMethod.POST)
    public Map<String, Object> addHost(@RequestBody Host host) {
        this.hostService.add(host);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("host", host);
        return map;
    }

    //修改主办机构信息    完成 改
    @RequestMapping(value = "/updateHost", method = RequestMethod.PUT)
    public Map<String, Object> updateHost(@RequestBody Host host) {
        this.hostService.update(host);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("host", host);
        return map;
    }

    //删除一个主办机构   完成 删
    @RequestMapping(value = "/deleteHost", method = RequestMethod.DELETE)
    public void deleteHost(@RequestParam("ids") String ids) {
        String deleteIds[] = ids.split(",");
        for (int i = 0; i < deleteIds.length; i++) {
            this.hostService.deleteById(Long.parseLong(deleteIds[i]));
        }
    }
}
