package com.glmis.controller.attendance;

import com.glmis.domain.attendance.BusinessLeave;
import com.glmis.service.attendance.AttendanceSummaryService;
import com.glmis.service.attendance.BusinessLeaveService;
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
public class BusinessLeaveController {
    @Autowired
    BusinessLeaveService businessLeaveService;
    long summaryId=0;
    @Autowired
    AttendanceSummaryService attendanceSummaryService;
    @RequestMapping(value = "/displayAllBusinessLeave", method = RequestMethod.GET)
    public Map<String, Object> findAllBusinessLeaveSummary(@RequestParam(value = "page") Integer page,
                                                        @RequestParam(value = "rows") Integer size) throws Exception{
        Page<BusinessLeave> list = this.businessLeaveService.findAllT(new PageRequest(page - 1, size));
        Map<String, Object> map = new HashMap<String, Object>();
        int total = this.businessLeaveService.findAllT().size();
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
    @RequestMapping(value = "/displayBusinessLeaveBySummary",method = RequestMethod.GET)
    public Map<String,Object> findByEmployeeId(@RequestParam("id") Long id,
                                               @RequestParam(value = "page")Integer page,
                                               @RequestParam(value = "rows")Integer size ){
        summaryId = id;
        Map<String,Object> map = new HashMap<String,Object>();
        Pageable pageable = new PageRequest(page-1,size);
        Page<BusinessLeave> list = this.businessLeaveService.findByBusinessLeaveSummaryId(id,pageable);
        int total = this.businessLeaveService.findByBusinessLeaveSummaryId(id).size();
        map.put("total",total);
        map.put("rows",list.getContent());
        return map;
    }
    //删除
    @RequestMapping(value = "/deleteBusinessLeave",method = RequestMethod.DELETE)
    public void deleteBusinessLeave(@RequestParam("ids") String ids)throws Exception{
        String deleteIds[] = ids.split(",");
        for (int i = 0; i < deleteIds.length; i++) {
            this.businessLeaveService.deleteById(Long.parseLong(deleteIds[i]));
        }
    }
    //添加
    @RequestMapping(value = "/addBusinessLeave", method = RequestMethod.POST)
    public Map<String, Object> addBusinessLeave(@RequestBody BusinessLeave businessLeave) throws Exception{
        businessLeave.setAttendanceSummary(attendanceSummaryService.findOne(summaryId));
        this.businessLeaveService.add(businessLeave);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("businessLeave", businessLeave);
        return map;
    }
    //修改
    @RequestMapping(value = "/updateBusinessLeave",method = RequestMethod.PUT)
    public Map<String, Object> updateBusinessLeave(@RequestBody BusinessLeave businessLeave){
        businessLeave.setAttendanceSummary(attendanceSummaryService.findOne(summaryId));
        this.businessLeaveService.update(businessLeave);
        Map<String, Object> map = new HashMap<String,Object>();
        map.put("businessLeave",businessLeave);
        return map;
    }
}
