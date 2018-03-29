package com.glmis.controller.scientificResearch;

import com.glmis.domain.authority.User;
import com.glmis.domain.personnel.Employee;
import com.glmis.domain.scientificResearch.ProjectFundedByPrivateSector;
import com.glmis.domain.scientificResearch.ScienReasCheckingStatus;
import com.glmis.service.authority.UserService;
import com.glmis.service.scientificResearch.ProjectFundedByPrivateSectorService;
import com.glmis.utils.Utils;
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
public class ProjectFundedByPrivateSectorController {

    @Autowired
    ProjectFundedByPrivateSectorService projectFundedByPrivateSectorService;
    @Autowired
    UserService userService;

    long empId = 0;

    /**获取当前Actor
     * 不能提到Util类中（如果把Service设为static，他的方法就不起作用了）*/
    public Employee getActor(HttpServletRequest request){
        String userName = Utils.getCurrentUserName(request);//得到当前User的username
        User user = userService.findByUserName(userName);//得到当前User
        Employee employee = (Employee) user.getActor();
        return employee;
    }

    // 根据选中的职员，显示其项目信息
    @RequestMapping(value = "/displayProjectFundedByPrivateSectorByEmp",method = RequestMethod.GET)
    public Map<String,Object> findByEmployeeId(@RequestParam("id") Long id,
                                                         @RequestParam(value = "page")Integer page,
                                                         @RequestParam(value = "rows")Integer size){
        empId = id;
        Map<String,Object> map = new HashMap<String,Object>();
        Pageable pageable = new PageRequest(page-1,size);
        Page<ProjectFundedByPrivateSector> list = this.projectFundedByPrivateSectorService.findByEmployeeId(id,pageable);
        int total = this.projectFundedByPrivateSectorService.findByEmployeeId(id).size();
        map.put("total",total);
        map.put("rows",list.getContent());
        return map;
    }

    //根据当前用户显示项目信息
    @RequestMapping(value = "/displayOwnProjectFundedByPrivateSector",method = RequestMethod.GET)
    public Map<String,Object> findOwnPro(HttpServletRequest request,
                                         @RequestParam(value = "page")Integer page,
                                         @RequestParam(value = "rows")Integer size,
                                         Sort sort){
        Long id = getActor(request).getId();
        Map<String,Object> map = new HashMap<String,Object>();
        Pageable pageable = Utils.getPageable(request,page,size,sort);//得到一个Pageable对象
        Page<ProjectFundedByPrivateSector> list = this.projectFundedByPrivateSectorService.findByEmployeeId(id,pageable);
        int total = this.projectFundedByPrivateSectorService.findByEmployeeId(id).size();
        map.put("total",total);
        map.put("rows",list.getContent());
        return map;
    }

    //添加新的项目  完成 增
    @RequestMapping(value = "/addProjectFundedByPrivateSector",method = RequestMethod.POST)
    public Map<String, Object> addProject(HttpServletRequest request,@RequestBody ProjectFundedByPrivateSector project){
        project.setEmployee(getActor(request));
        project.setCheckingStatus(new ScienReasCheckingStatus((long) 1));
        this.projectFundedByPrivateSectorService.add(project);
        Map<String, Object> map = new HashMap<String,Object>();
        map.put("project",project);
        return map;
    }

    //修改项目信息    完成 改
    @RequestMapping(value = "/updateProjectFundedByPrivateSector",method = RequestMethod.PUT)
    public Map<String, Object> updateProject(@RequestBody ProjectFundedByPrivateSector project){
        this.projectFundedByPrivateSectorService.update(project);
        Map<String, Object> map = new HashMap<String,Object>();
        map.put("project",project);
        return map;
    }

    //删除项目   完成 删
    @RequestMapping(value = "/deleteProjectFundedByPrivateSector",method = RequestMethod.DELETE)
    public void deleteProject(@RequestParam("ids") String ids){
        String deleteIds[] = ids.split(",");
        for(int i = 0; i<deleteIds.length; i++){
            this.projectFundedByPrivateSectorService.deleteById(Long.parseLong(deleteIds[i]));
        }
    }

    //根据查询条件显示选中员工的项目信息
    //级别，申报人，立项时间(晚于设定开始时间，早于设定结束时间)，经费(大于x万)，横纵向，状态
    @RequestMapping(value = "/dispProjectFundedByPrivateSectorSpecification",method = RequestMethod.GET)
    public Map<String,Object> dispProjectSpecification(
            @RequestParam("expenditure")String expenditure,
            @RequestParam(";startTime")String startTime,
            @RequestParam(";endTime")String endTime,
            @RequestParam(";projectRankIds")String projectRankIds,
            @RequestParam(";checkingStatusIds")String checkingStatusIds,
            @RequestParam(value = "page")Integer page,
            @RequestParam(value = "rows")Integer size ){
        Map<String,Object> map = new HashMap<>();
        Pageable pageable = new PageRequest(page-1,size);
        //Arrays.asList将数组转换成list型，以“，”为分隔符，分割字符串
        List<String> projectRankIdList = Arrays.asList(projectRankIds.split(","));
        List<Long> projectRankIdInteger= new ArrayList<Long>();;
        for(String ids : projectRankIdList) {
            if(projectRankIdList.size()!=0 && projectRankIdList.get(0)!=""){
                projectRankIdInteger.add(Long.parseLong(ids));
            }
        }
        List<String> checkingStatusIdList = Arrays.asList(checkingStatusIds.split(","));
        List<Long> checkingStatusIdInteger= new ArrayList<Long>();;
        for(String ids : checkingStatusIdList) {
            if(checkingStatusIdList.size()!=0 && checkingStatusIdList.get(0)!=""){
                checkingStatusIdInteger.add(Long.parseLong(ids));
            }
        }
        Specification<ProjectFundedByPrivateSector> specification = this.projectFundedByPrivateSectorService.projectSpecification(
                empId,"projectFundedByPrivateSectorRank",projectRankIdInteger,"startTime",startTime,"startTime",endTime,
                "expenditure",expenditure,"checkingStatus",checkingStatusIdInteger);
        Page<ProjectFundedByPrivateSector> list = this.projectFundedByPrivateSectorService.findBySepc(specification,pageable);
        int total = this.projectFundedByPrivateSectorService.findBySepc(specification).size();
        map.put("total",total);
        map.put("rows",list.getContent());
        return map;
    }
}
