package com.glmis.controller.scientificResearch;

import com.glmis.domain.authority.User;
import com.glmis.domain.personnel.Employee;
import com.glmis.domain.scientificResearch.ScienReasCheckingStatus;
import com.glmis.domain.scientificResearch.Thesis;
import com.glmis.service.authority.UserService;
import com.glmis.service.scientificResearch.ThesisService;
import com.glmis.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Created by dell on 2016/11/7.
 */

@RestController
public class ThesisController {

    @Autowired
    ThesisService thesisService;
    @Autowired
    UserService userService;

    long empId = 0;

    //获取当前Actor
    public Employee getActor(javax.servlet.http.HttpServletRequest request){
        String userName = Utils.getCurrentUserName(request);//得到当前User的username
        User user = userService.findByUserName(userName);//得到当前User
        Employee employee = (Employee) user.getActor();
        return employee;
    }

    // 根据选中的职员，显示其论文信息
    @RequestMapping(value = "/displayScienThesisByEmp",method = RequestMethod.GET)
    public Map<String,Object> findByEmployeeId(@RequestParam("id") Long id,
                                               @RequestParam(value = "page")Integer page,
                                               @RequestParam(value = "rows")Integer size ){
        empId = id;
        Map<String,Object> map = new HashMap<String,Object>();
        Pageable pageable = new PageRequest(page-1,size);
        Page<Thesis> list = this.thesisService.findByEmployeeId(id,pageable);
        int total = this.thesisService.findByEmployeeId(id).size();
        map.put("total",total);
        map.put("rows",list.getContent());
        return map;
    }

    //根据查询条件显示选中员工的论文信息
    @RequestMapping(value = "/dispThesisQuerry",method = RequestMethod.GET)
    public Map<String,Object> dispThesisQuerry(@RequestParam("journalRankIds")String journalRankIds,
                                               @RequestParam(";citationIds")String citationIds,
                                               @RequestParam(";beginYear")String beginYear,
                                               @RequestParam(";endYear")String endYear,
                                               @RequestParam(value = "page")Integer page,
                                               @RequestParam(value = "rows")Integer size ){
        Map<String,Object> map = new HashMap<>();
        Pageable pageable = new PageRequest(page-1,size);
        //Arrays.asList将数组转换成list型，以“，”为分隔符，分割字符串
        List<String> journalRankIdList = Arrays.asList(journalRankIds.split(","));
        List<Long> journalRankIdInteger= new ArrayList<Long>();;
        for(String ids : journalRankIdList) {
            if(journalRankIdList.size()!=0 && journalRankIdList.get(0)!=""){
                journalRankIdInteger.add(Long.parseLong(ids));
            }
        }
        List<String> citationIdList = Arrays.asList(citationIds.split(","));
        List<Long> citationIdInteger= new ArrayList<Long>();;
        for(String ids : citationIdList) {
            if(citationIdList.size()!=0 && citationIdList.get(0)!=""){
                citationIdInteger.add(Long.parseLong(ids));
            }
        }
        Specification<Thesis> specification = this.thesisService.thesisQuery(empId,"citation",citationIdInteger,"journalRank",journalRankIdInteger,"year",beginYear,endYear);
        Page<Thesis> list = this.thesisService.findBySepc(specification,pageable);
        int total = this.thesisService.findBySepc(specification).size();
        map.put("total",total);
        map.put("rows",list.getContent());
        return map;
    }

    //根据当前用户显示论文信息
    @RequestMapping(value = "/displayOwnThesis",method = RequestMethod.GET)
    public Map<String,Object> findOwnThesis(javax.servlet.http.HttpServletRequest request,
                                            @RequestParam(value = "page")Integer page,
                                            @RequestParam(value = "rows")Integer size,
                                            Sort sort){
        Long id = getActor(request).getId();
        Map<String,Object> map = new HashMap<String,Object>();
        Pageable pageable = Utils.getPageable(request,page,size,sort);//得到一个Pageable对象
        Page<Thesis> list = this.thesisService.findByEmployeeId(id,pageable);
        int total = this.thesisService.findByEmployeeId(id).size();
        map.put("total",total);
        map.put("rows",list.getContent());
        return map;
    }

    //添加新的论文 完成 增
    @RequestMapping(value = "/addThesis",method = RequestMethod.POST)
    public Map<String, Object> addThesis(javax.servlet.http.HttpServletRequest request,@RequestBody Thesis thesis){
        thesis.setEmployee(getActor(request));
        thesis.setCheckingStatus(new ScienReasCheckingStatus((long)1));
        this.thesisService.add(thesis);
        Map<String, Object> map = new HashMap<String,Object>();
        map.put("thesis",thesis);
        return map;
    }

    //修改论文信息    完成 改
    @RequestMapping(value = "/updateThesis",method = RequestMethod.PUT)
    public Map<String, Object> updateThesis(@RequestBody Thesis thesis){
        this.thesisService.update(thesis);
        Map<String, Object> map = new HashMap<String,Object>();
        map.put("thesis",thesis);
        return map;
    }

    //删除论文   完成 删
    @RequestMapping(value = "/deleteThesis",method = RequestMethod.DELETE)
    public void deleteThesis(@RequestParam("ids") String ids){
        String deleteIds[] = ids.split(",");
        for(int i = 0; i<deleteIds.length; i++){
            this.thesisService.deleteById(Long.parseLong(deleteIds[i]));
        }
    }
}
