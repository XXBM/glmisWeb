package com.glmis.controller.attendance;

import com.glmis.domain.attendance.Presence;
import com.glmis.service.attendance.AttendanceSummaryService;
import com.glmis.service.attendance.PresenceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class PresenceController {
    final Logger logger = LoggerFactory.getLogger(PresenceController.class);
    @Autowired
    PresenceService presenceService;
    long summaryId=0;
    @Autowired
    AttendanceSummaryService attendanceSummaryService;
    @RequestMapping(value = "/displayAllPresence", method = RequestMethod.GET)
    public Map<String, Object> findAllPresenceSummary(@RequestParam(value = "page") Integer page,
                                                           @RequestParam(value = "rows") Integer size) throws Exception{
        Page<Presence> list = this.presenceService.findAllT(new PageRequest(page - 1, size));
        Map<String, Object> map = new HashMap<String, Object>();
        int total = this.presenceService.findAllT().size();
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
    @RequestMapping(value = "/displayPresenceBySummary",method = RequestMethod.GET)
    public Map<String,Object> findByEmployeeId(@RequestParam("id") Long id,
                                               @RequestParam(value = "page")Integer page,
                                               @RequestParam(value = "rows")Integer size ){
        summaryId = id;
        Map<String,Object> map = new HashMap<String,Object>();
        Pageable pageable = new PageRequest(page-1,size);
        Page<Presence> list = this.presenceService.findByPresenceSummaryId(id,pageable);
        int total = this.presenceService.findByPresenceSummaryId(id).size();
        map.put("total",total);
        map.put("rows",list.getContent());
        return map;
    }
    //删除
    @RequestMapping(value = "/deletePresence",method = RequestMethod.DELETE)
    public void deletePresence(@RequestParam("ids") String ids)throws Exception{
        String deleteIds[] = ids.split(",");
        for (int i = 0; i < deleteIds.length; i++) {
            this.presenceService.deleteById(Long.parseLong(deleteIds[i]));
        }
    }
    //添加
    @RequestMapping(value = "/addPresence", method = RequestMethod.POST)
    public Map<String, Object> addPresence(@RequestBody Presence presence) throws Exception{
        logger.debug(""+summaryId);
        presence.setAttendanceSummary(attendanceSummaryService.findOne(summaryId));
        this.presenceService.add(presence);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("presence", presence);
        return map;
    }

    //修改论文信息    完成 改
    @RequestMapping(value = "/updatePresence",method = RequestMethod.PUT)
    public Map<String, Object> updatePresence(@RequestBody Presence presence){
        presence.setAttendanceSummary(attendanceSummaryService.findOne(summaryId));
        this.presenceService.update(presence);
        Map<String, Object> map = new HashMap<String,Object>();
        map.put("presence",presence);
        return map;
    }
}
