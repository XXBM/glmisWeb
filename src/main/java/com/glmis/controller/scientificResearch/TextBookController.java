package com.glmis.controller.scientificResearch;

import com.glmis.domain.authority.User;
import com.glmis.domain.personnel.Employee;
import com.glmis.domain.scientificResearch.ScienReasCheckingStatus;
import com.glmis.domain.scientificResearch.TextBook;
import com.glmis.service.authority.UserService;
import com.glmis.service.scientificResearch.TextBookService;
import com.glmis.utils.Utils;
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
public class TextBookController {

    @Autowired
    UserService userService;

    @Autowired
    TextBookService textBookService;

    long empId = 0;

    /**获取当前Actor*/
    public Employee getActor(javax.servlet.http.HttpServletRequest request){
        String userName = Utils.getCurrentUserName(request);//得到当前User的username
        User user = userService.findByUserName(userName);//得到当前User
        Employee employee = (Employee) user.getActor();
        return employee;
    }
    // 根据选中的职员，显示我的专著/教材信息
    @RequestMapping(value = "/displayTextbookByEmp",method = RequestMethod.GET)
    public Map<String,Object> findByEmployeeId(@RequestParam("id") Long id,
                                               @RequestParam(value = "page")Integer page,
                                               @RequestParam(value = "rows")Integer size ){
        empId = id;
        Map<String,Object> map = new HashMap<String,Object>();
        Pageable pageable = new PageRequest(page-1,size);
        Page<TextBook> list = this.textBookService.findByEmployeeId(id,pageable);
        int total = this.textBookService.findByEmployeeId(id).size();
        map.put("total",total);
        map.put("rows",list.getContent());
        return map;
    }

    //根据当前用户显示我的教材信息
    @RequestMapping(value = "/displayTextbook",method = RequestMethod.GET)
    public Map<String,Object> findOwnBooks(javax.servlet.http.HttpServletRequest request,
                                            @RequestParam(value = "page")Integer page,
                                            @RequestParam(value = "rows")Integer size){
        Long id = getActor(request).getId();
        Map<String,Object> map = new HashMap<String,Object>();
        //第几页，一页有几条数据
        Pageable pageable = new PageRequest(page-1,size);
        //Pageable pageable = Utils.getPageable(request,page,size,description);//得到一个Pageable对象
        Page<TextBook> list = this.textBookService.findByEmployeeId(id,pageable);
        int total = this.textBookService.findByEmployeeId(id).size();
        //数据量
        map.put("total",total);
        //数据内容
        map.put("rows",list.getContent());
        return map;
    }

//    //根据当前用户显示我的教材信息
//    @RequestMapping(value = "/displayTextbook",method = RequestMethod.GET)
//    public List<TextbookOrTextBook> findOwnAwards(javax.servlet.http.HttpServletRequest request){
//        Integer id = getActor(request).getId();
//        List<TextbookOrTextBook> lists = this.textbookOrTextBookService.findByEmployeeId(id);
//        return lists;
//    }

    //添加新的教材信息 完成 增
    @RequestMapping(value = "/addTextbook",method = RequestMethod.POST)
    public Map<String, Object> addBooks(javax.servlet.http.HttpServletRequest request, @RequestBody TextBook textBook){
        textBook.setEmployee(getActor(request));
        textBook.setCheckingStatus(new ScienReasCheckingStatus((long)1));
        this.textBookService.add(textBook);
        Map<String, Object> map = new HashMap<String,Object>();
        map.put("textBook",textBook);
        return map;
    }

    //修改教材信息    完成 改
    @RequestMapping(value = "/updateTextbook",method = RequestMethod.PUT)
    public Map<String, Object> updateBooks(@RequestBody  TextBook textBook){
        this.textBookService.update(textBook);
        Map<String, Object> map = new HashMap<String,Object>();
        map.put("textBook",textBook);
        return map;
    }

    //删除教材信息   完成 删
    @RequestMapping(value = "/deleteTextbook",method = RequestMethod.DELETE)
    public void deleteBooks(@RequestParam("ids") String ids){
        String deleteIds[] = ids.split(",");
        for(int i = 0; i<deleteIds.length; i++){
            this.textBookService.deleteById(Long.parseLong(deleteIds[i]));
        }
    }
    //根据查询条件显示选中员工的教材/专著信息
    //查询：级别，申报人，出版时间(晚于设定开始时间，早于设定结束时间)，位次，专著/教材，状态
    @RequestMapping(value = "/dispBooksSpecification",method = RequestMethod.GET)
    public Map<String,Object> dispBooksSpecification(
            @RequestParam("seating")String seating,
            @RequestParam(";publishingStart")String publishingStart,
            @RequestParam(";publishingEnd")String publishingEnd,
            @RequestParam(";textbookRankIds")String textbookRankIds,
            @RequestParam(";checkingStatusIds")String checkingStatusIds,
            @RequestParam(value = "page")Integer page,
            @RequestParam(value = "rows")Integer size ){
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
        List<String> textbookRankIdList = Arrays.asList(textbookRankIds.split(","));
        List<Long> textbookRankIdInteger= new ArrayList<Long>();;
        for(String ids : textbookRankIdList) {
            System.out.println(textbookRankIdList.isEmpty()+"----------------"+textbookRankIdList.get(0)=="");
            if(textbookRankIdList.size()!=0 && textbookRankIdList.get(0)!=""){
                textbookRankIdInteger.add(Long.parseLong(ids));
            }
        }
        Specification<TextBook> specification = this.textBookService.projectSpecification(
                empId,"textbookRank",textbookRankIdInteger,"publicationTime",publishingStart,"publicationTime",publishingEnd,
                "seating",seating,"checkingStatus",checkingStatusIdInteger);
        Page<TextBook> list = this.textBookService.findBySepc(specification,pageable);
        int total = this.textBookService.findBySepc(specification).size();
        map.put("total",total);
        map.put("rows",list.getContent());
        return map;
    }
}
