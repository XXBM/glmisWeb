package com.glmis.controller.scientificResearch;

import com.glmis.domain.authority.User;
import com.glmis.domain.personnel.Employee;
import com.glmis.domain.scientificResearch.Awards;
import com.glmis.domain.scientificResearch.ScienReasCheckingStatus;
import com.glmis.service.authority.UserService;
import com.glmis.service.scientificResearch.AwardsService;
import com.glmis.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by dell on 2016/11/7.
 */

@RestController
public class AwardsController {

    @Autowired
    AwardsService awardsService;
    @Autowired
    UserService userService;

    long empId = 0;

    /**
     * 获取当前Actor
     */
    public Employee getActor(javax.servlet.http.HttpServletRequest request) {
        String userName = Utils.getCurrentUserName(request);//得到当前User的username
        User user = userService.findByUserName(userName);//得到当前User
        Employee employee = (Employee) user.getActor();
        return employee;
    }
    final Logger logger = LoggerFactory.getLogger(AwardsController.class);
    // 根据选中的职员，显示其科研获奖信息
    @RequestMapping(value = "/displayScienAwardsByEmp", method = RequestMethod.GET)
    public Map<String, Object> findByEmployeeId(@RequestParam("id") Long id,
                                                @RequestParam(value = "page") Integer page,
                                                @RequestParam(value = "rows") Integer size) {
        empId = id;
        Map<String, Object> map = new HashMap<String, Object>();
        Pageable pageable = new PageRequest(page - 1, size);
        Page<Awards> list = this.awardsService.findByEmployeeId(id, pageable);
        int total = this.awardsService.findByEmployeeId(id).size();
        map.put("total", total);
        map.put("rows", list.getContent());
        return map;
    }

    //根据当前用户显示科研获奖信息
    @RequestMapping(value = "/displayOwnAwards", method = RequestMethod.GET)
    public Map<String, Object> findOwnAwards(javax.servlet.http.HttpServletRequest request,
                                             @RequestParam(value = "page") Integer page,
                                             @RequestParam(value = "rows") Integer size,
                                             Sort sort) {
        Long id = getActor(request).getId();
        logger.debug("-------------"+id);
        Map<String, Object> map = new HashMap<String, Object>();
        Pageable pageable = Utils.getPageable(request, page, size, sort);//得到一个Pageable对象
        Page<Awards> list = this.awardsService.findByEmployeeId(id, pageable);
        int total = this.awardsService.findByEmployeeId(id).size();
        map.put("total", total);
        map.put("rows", list.getContent());
        return map;
    }

    //添加新的获奖信息 完成 增
    @RequestMapping(value = "/addAwards", method = RequestMethod.POST)
    public Map<String, Object> addAwards(HttpServletRequest request, @RequestBody Awards awards) {
        awards.setEmployee(getActor(request));
        awards.setCheckingStatus(new ScienReasCheckingStatus((long)1));
        this.awardsService.add(awards);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("awards", awards);
        return map;
    }

    //修改获奖信息    完成 改
    @RequestMapping(value = "/updateAwards", method = RequestMethod.PUT)
    public Map<String, Object> updateAwards(@RequestBody Awards awards) {
        logger.debug("",awards.getAwardLevel()==null);
        this.awardsService.update(awards);
        logger.debug("",awards.getAwardLevel()==null);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("awards", awards);
        return map;
    }

    //删除获奖信息   完成 删
    @RequestMapping(value = "/deleteAwards", method = RequestMethod.DELETE)
    public void deleteAwards(@RequestParam("ids") String ids) {
        String deleteIds[] = ids.split(",");
        for (int i = 0; i < deleteIds.length; i++) {
            this.awardsService.deleteById(Long.parseLong(deleteIds[i]));
        }
    }

    //根据查询条件显示选中员工的获奖信息
    //级别，等级，申报人，时间(晚于设定开始时间，早于设定结束时间)，位次，状态
    @RequestMapping(value = "/dispAwardsSpecification", method = RequestMethod.GET)
    public Map<String, Object> dispAwardsSpecification(@RequestParam("awardsRankIds") String awardsRankIds,
                                                       @RequestParam(";startTime") String startTime,
                                                       @RequestParam(";endTime") String endTime,
                                                       @RequestParam(";seating") String seating,
                                                       @RequestParam(";checkingStatusId") String checkingStatusIds,
                                                       @RequestParam(value = "page") Integer page,
                                                       @RequestParam(value = "rows") Integer size) {
        Map<String, Object> map = new HashMap<>();
        Pageable pageable = new PageRequest(page - 1, size);
        //Arrays.asList将数组转换成list型，以“，”为分隔符，分割字符串
        List<String> projectRankIdList = Arrays.asList(awardsRankIds.split(","));
        List<Long>  projectRankIdInteger = new ArrayList<Long>();
        for (String ids : projectRankIdList) {
            if(projectRankIdList.size()!=0 && projectRankIdList.get(0)!=""){
                projectRankIdInteger.add(Long.parseLong(ids));
            }

        }
        List<String> checkingStatusIdList = Arrays.asList(checkingStatusIds.split(","));
        List<Long>  checkingStatusIdInteger = new ArrayList<Long>();
        for (String ids : checkingStatusIdList) {
            if(checkingStatusIdList.size()!=0 && checkingStatusIdList.get(0)!=""){
                checkingStatusIdInteger.add(Long.parseLong(ids));
            }
        }
        Specification<Awards> specification = this.awardsService.awardsSpecification(
                empId, "awardsRank", projectRankIdInteger, "dateOfAward", startTime,
                "dateOfAward", endTime, "seating", seating, "checkingStatus", checkingStatusIdInteger);
        Page<Awards> list = this.awardsService.findBySepc(specification, pageable);
        int total = this.awardsService.findBySepc(specification).size();
        map.put("total", total);
        map.put("rows", list.getContent());
        return map;
    }
}
