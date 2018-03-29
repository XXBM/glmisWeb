package com.glmis.controller.scientificResearch;

import com.glmis.domain.authority.User;
import com.glmis.domain.personnel.Employee;
import com.glmis.domain.scientificResearch.Monograph;
import com.glmis.domain.scientificResearch.ScienReasCheckingStatus;
import com.glmis.service.authority.UserService;
import com.glmis.service.scientificResearch.MonographService;
import com.glmis.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Created by apple on 2017/3/4.
 */

@RestController
public class MonographController {

    @Autowired
    UserService userService;

    @Autowired
    MonographService monographService;

    long empId = 0;
    final Logger logger = LoggerFactory.getLogger(MonographController.class);

    /**获取当前Actor*/
    public Employee getActor(javax.servlet.http.HttpServletRequest request){
        String userName = Utils.getCurrentUserName(request);//得到当前User的username
        User user = userService.findByUserName(userName);//得到当前User
        Employee employee = (Employee) user.getActor();
        return employee;
    }
    // 根据选中的职员，显示我的专著/教材信息
    @RequestMapping(value = "/displayMonographByEmp",method = RequestMethod.GET)
    public Map<String,Object> findByEmployeeId(@RequestParam("id") Long id,
                                               @RequestParam(value = "page")Integer page,
                                               @RequestParam(value = "rows")Integer size ){
        empId = id;
        Map<String,Object> map = new HashMap<String,Object>();
        Pageable pageable = new PageRequest(page-1,size);
        Page<Monograph> list = this.monographService.findByEmployeeId(id,pageable);
        int total = this.monographService.findByEmployeeId(id).size();
        map.put("total",total);
        map.put("rows",list.getContent());
        return map;
    }

    //根据当前用户显示我的专著/教材信息
    @RequestMapping(value = "/displayMonograph",method = RequestMethod.GET)
    public Map<String,Object> findOwnBooks(javax.servlet.http.HttpServletRequest request,
                                            @RequestParam(value = "page")Integer page,
                                            @RequestParam(value = "rows")Integer size){
        Long id = getActor(request).getId();
        Map<String,Object> map = new HashMap<String,Object>();
        //第几页，一页有几条数据
        Pageable pageable = new PageRequest(page-1,size);
        //Pageable pageable = Utils.getPageable(request,page,size,description);//得到一个Pageable对象
        Page<Monograph> list = this.monographService.findByEmployeeId(id,pageable);
        int total = this.monographService.findByEmployeeId(id).size();
        //数据量
        map.put("total",total);
        //数据内容
        map.put("rows",list.getContent());
        return map;
    }

//    //根据当前用户显示我的专著/教材信息
//    @RequestMapping(value = "/displayTextbook",method = RequestMethod.GET)
//    public List<TextbookOrMonograph> findOwnAwards(javax.servlet.http.HttpServletRequest request){
//        Integer id = getActor(request).getId();
//        List<TextbookOrMonograph> lists = this.textbookOrMonographService.findByEmployeeId(id);
//        return lists;
//    }

    //添加新的专著/教材信息 完成 增
    @RequestMapping(value = "/addMonograph",method = RequestMethod.POST)
    public Map<String, Object> addBooks(javax.servlet.http.HttpServletRequest request, @RequestBody Monograph monograph){
        monograph.setEmployee(getActor(request));
        monograph.setCheckingStatus(new ScienReasCheckingStatus((long)1));
        this.monographService.add(monograph);
        Map<String, Object> map = new HashMap<String,Object>();
        map.put("monograph",monograph);
        return map;
    }

    //修改专著/教材信息    完成 改
    @RequestMapping(value = "/updateMonograph",method = RequestMethod.PUT)
    public Map<String, Object> updateBooks(@RequestBody  Monograph monograph){
        this.monographService.update(monograph);
        Map<String, Object> map = new HashMap<String,Object>();
        map.put("monograph",monograph);
        return map;
    }

    //删除专著/教材信息   完成 删
    @RequestMapping(value = "/deleteMonograph",method = RequestMethod.DELETE)
    public void deleteBooks(@RequestParam("ids") String ids){
        String deleteIds[] = ids.split(",");
        for(int i = 0; i<deleteIds.length; i++){
            this.monographService.deleteById(Long.parseLong(deleteIds[i]));
        }
    }
    //根据查询条件显示选中员工的教材/专著信息
    //查询：级别，申报人，出版时间(晚于设定开始时间，早于设定结束时间)，位次，专著/教材，状态
    @RequestMapping(value = "/dispMonographSpecification",method = RequestMethod.GET)
    public Map<String,Object> dispBooksSpecification(
            @RequestParam("seating") String seating,
            @RequestParam(";publishingStart")String publishingStart,
            @RequestParam(";publishingEnd")String publishingEnd,
            @RequestParam(";monographRankIds")String monographRankIds,
            @RequestParam(";checkingStatusIds")String checkingStatusIds,
            @RequestParam(value = "page")Integer page,
            @RequestParam(value = "rows")Integer size ){
        logger.debug(publishingEnd);
        Map<String,Object> map = new HashMap<>();
        Pageable pageable = new PageRequest(page-1,size);
        //Arrays.asList将数组转换成list型，以“，”为分隔符，分割字符串
        List<String> checkingStatusIdList = Arrays.asList(checkingStatusIds.split(","));
        List<Long> checkingStatusIdInteger= new ArrayList<Long>();;
        for(String ids : checkingStatusIdList) {
            if(checkingStatusIdList.size()!=0 && checkingStatusIdList.get(0)!=""){
                checkingStatusIdInteger.add(Long.parseLong(ids));
            }
        }
        List<String> monographRankIdList = Arrays.asList(monographRankIds.split(","));
        List<Long> monographRankIdInteger= new ArrayList<Long>();;
        for(String ids : monographRankIdList) {
            System.out.println(monographRankIdList.isEmpty()+"++++++++++++++++"+monographRankIdList.get(0)=="");
            if(monographRankIdList.size()!=0 && !"".equals(monographRankIdList.get(0))){
                monographRankIdInteger.add(Long.parseLong(ids));
            }
        }
        Specification<Monograph> specification = this.monographService.projectSpecification(
                empId,"monographRank",monographRankIdInteger,"publicationTime",publishingStart,"publicationTime",publishingEnd,
                "seating",seating,"checkingStatus",checkingStatusIdInteger);
        Page<Monograph> list = this.monographService.findBySepc(specification,pageable);
        logger.debug(publishingEnd);
        int total = this.monographService.findBySepc(specification).size();
        map.put("total",total);
        map.put("rows",list.getContent());
        return map;
    }
}
