package com.glmis.controller.attendance;

import com.glmis.domain.attendance.PrivateLeave;
import com.glmis.service.attendance.AttendanceSummaryService;
import com.glmis.service.attendance.PrivateLeaveService;
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
public class PrivateLeaveController {
    @Autowired
    PrivateLeaveService privateLeaveService;
    long summaryId=0;
    @Autowired
    AttendanceSummaryService attendanceSummaryService;
    @RequestMapping(value = "/displayAllPrivateLeave", method = RequestMethod.GET)
    public Map<String, Object> findAllPrivateLeaveSummary(@RequestParam(value = "page") Integer page,
                                                           @RequestParam(value = "rows") Integer size) throws Exception{
        Page<PrivateLeave> list = this.privateLeaveService.findAllT(new PageRequest(page - 1, size));
        Map<String, Object> map = new HashMap<String, Object>();
        int total = this.privateLeaveService.findAllT().size();
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
    @RequestMapping(value = "/displayPrivateLeaveBySummary",method = RequestMethod.GET)
    public Map<String,Object> findByEmployeeId(@RequestParam("id") Long id,
                                               @RequestParam(value = "page")Integer page,
                                               @RequestParam(value = "rows")Integer size ){
        summaryId = id;
        Map<String,Object> map = new HashMap<String,Object>();
        Pageable pageable = new PageRequest(page-1,size);
        Page<PrivateLeave> list = this.privateLeaveService.findByPrivateLeaveSummaryId(id,pageable);
        int total = this.privateLeaveService.findByPrivateLeaveSummaryId(id).size();
        map.put("total",total);
        map.put("rows",list.getContent());
        return map;
    }
    //删除
    @RequestMapping(value = "/deletePrivateLeave",method = RequestMethod.DELETE)
    public void deletePrivateLeave(@RequestParam("ids") String ids)throws Exception{
        String deleteIds[] = ids.split(",");
        for (int i = 0; i < deleteIds.length; i++) {
            this.privateLeaveService.deleteById(Long.parseLong(deleteIds[i]));
        }
    }
    //添加
    @RequestMapping(value = "/addPrivateLeave", method = RequestMethod.POST)
    public Map<String, Object> addPrivateLeave(@RequestBody PrivateLeave privateLeave) throws Exception{
        privateLeave.setAttendanceSummary(attendanceSummaryService.findOne(summaryId));
        this.privateLeaveService.add(privateLeave);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("privateLeave", privateLeave);
        return map;
    }

    //修改论文信息    完成 改
    @RequestMapping(value = "/updatePrivateLeave",method = RequestMethod.PUT)
    public Map<String, Object> updatePrivateLeave(@RequestBody PrivateLeave privateLeave){
        privateLeave.setAttendanceSummary(attendanceSummaryService.findOne(summaryId));
        this.privateLeaveService.update(privateLeave);
        Map<String, Object> map = new HashMap<String,Object>();
        map.put("privateLeave",privateLeave);
        return map;
    }
}
