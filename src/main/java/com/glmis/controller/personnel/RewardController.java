package com.glmis.controller.personnel;

import com.glmis.domain.personnel.Reward;
import com.glmis.service.personnel.EmployeeService;
import com.glmis.service.personnel.RewardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dell on 2016/11/7.
 */

@RestController
public class RewardController {
    @Autowired
    RewardService rewardService;
    @Autowired
    EmployeeService employeeService;
    long empId = 0;

    // 根据职员id查询
    @RequestMapping(value = "/displayRewByEmp", method = RequestMethod.GET)
    public Map<String, Object> findByEmployeeId(@RequestParam("id") Long id,
                                                @RequestParam(value = "page") Integer page,
                                                @RequestParam(value = "rows") Integer size)throws Exception {
        empId = id;
        Map<String, Object> map = new HashMap<String, Object>();
        Pageable pageable = new PageRequest(page - 1, size);
        Page<Reward> list = this.rewardService.findByEmployeeId(id, pageable);
        int total = this.rewardService.findByEmployeeId(id).size();
        map.put("total", total);
        map.put("rows", list.getContent());
        return map;
    }

    //添加
    @RequestMapping(value = "/addReward", method = RequestMethod.POST)
    public Map<String, Object> addReward(@RequestBody Reward reward)throws Exception {
        reward.setEmployee(employeeService.findOne(empId));
        this.rewardService.add(reward);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("reward", reward);
        return map;
    }

    //修改
    @RequestMapping(value = "/updateReward", method = RequestMethod.PUT)
    public Map<String, Object> updateReward(@RequestBody Reward reward)throws Exception {
        reward.setEmployee(employeeService.findOne(empId));
        this.rewardService.update(reward);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("reward", reward);
        return map;
    }

    //删除
    @RequestMapping(value = "/deleteReward", method = RequestMethod.DELETE)
    public void deleteReward(@RequestParam("id") Long id)throws Exception {
        this.rewardService.deleteById(id);
    }
}
