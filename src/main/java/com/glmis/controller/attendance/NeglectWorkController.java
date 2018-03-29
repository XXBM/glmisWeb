package com.glmis.controller.attendance;

import com.glmis.domain.attendance.NeglectWork;
import com.glmis.service.attendance.AttendanceSummaryService;
import com.glmis.service.attendance.NeglectWorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by inkskyu428 on 17-6-3.
 */
@RestController
public class NeglectWorkController {
    @Autowired
    NeglectWorkService neglectWorkService;
    long summaryId=0;
    @Autowired
    AttendanceSummaryService attendanceSummaryService;
    @RequestMapping(value = "/displayAllNeglectWork", method = RequestMethod.GET)
    public Map<String, Object> findAllNeglectWorkSummary(@RequestParam(value = "page") Integer page,
                                                           @RequestParam(value = "rows") Integer size) throws Exception{
        Page<NeglectWork> list = this.neglectWorkService.findAllT(new PageRequest(page - 1, size));
        Map<String, Object> map = new HashMap<String, Object>();
        int total = this.neglectWorkService.findAllT().size();
        map.put("total", total);
        map.put("rows", list.getContent());
        return map;
    }
    /**
     *
     * @param id
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "/displayNeglectWorkBySummary",method = RequestMethod.GET)
    public Map<String,Object> findByEmployeeId(@RequestParam("id") Long id,
                                               @RequestParam(value = "page")Integer page,
                                               @RequestParam(value = "rows")Integer size ){
        summaryId = id;
        Map<String,Object> map = new HashMap<String,Object>();
        Pageable pageable = new PageRequest(page-1,size);
        Page<NeglectWork> list = this.neglectWorkService.findByNeglectWorkSummaryId(id,pageable);
        int total = this.neglectWorkService.findByNeglectWorkSummaryId(id).size();
        map.put("total",total);
        map.put("rows",list.getContent());
        return map;
    }
    //删除
    @RequestMapping(value = "/deleteNeglectWork",method = RequestMethod.DELETE)
    public void deleteNeglectWork(@RequestParam("ids") String ids)throws Exception{
        String deleteIds[] = ids.split(",");
        for (int i = 0; i < deleteIds.length; i++) {
            this.neglectWorkService.deleteById(Long.parseLong(deleteIds[i]));
        }
    }
    //添加
    @RequestMapping(value = "/addNeglectWork", method = RequestMethod.POST)
    public Map<String, Object> addNeglectWork(@RequestBody NeglectWork neglectWork) throws Exception{
        neglectWork.setAttendanceSummary(attendanceSummaryService.findOne(summaryId));
        this.neglectWorkService.add(neglectWork);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("neglectWork", neglectWork);
        return map;
    }
    //修改
    @RequestMapping(value = "/updateNeglectWork",method = RequestMethod.PUT)
    public Map<String, Object> updateNeglectWork(@RequestBody NeglectWork neglectWork){
        neglectWork.setAttendanceSummary(attendanceSummaryService.findOne(summaryId));
        this.neglectWorkService.update(neglectWork);
        Map<String, Object> map = new HashMap<String,Object>();
        map.put("neglectWork",neglectWork);
        return map;
    }
}
