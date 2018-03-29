package com.glmis.controller.attendance;

import com.glmis.domain.attendance.UniversityAbsence;
import com.glmis.service.attendance.AttendanceSummaryService;
import com.glmis.service.attendance.UniversityAbsenceService;
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
public class UniversityAbsenceController {
    @Autowired
    UniversityAbsenceService universityAbsenceService;
    long summaryId=0;
    @Autowired
    AttendanceSummaryService attendanceSummaryService;
    @RequestMapping(value = "/displayAllUniversityAbsence", method = RequestMethod.GET)
    public Map<String, Object> findAllUniversityAbsenceSummary(@RequestParam(value = "page") Integer page,
                                                           @RequestParam(value = "rows") Integer size) throws Exception{
        Page<UniversityAbsence> list = this.universityAbsenceService.findAllT(new PageRequest(page - 1, size));
        Map<String, Object> map = new HashMap<String, Object>();
        int total = this.universityAbsenceService.findAllT().size();
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
    @RequestMapping(value = "/displayUniversityAbsenceBySummary",method = RequestMethod.GET)
    public Map<String,Object> findByEmployeeId(@RequestParam("id") Long id,
                                               @RequestParam(value = "page")Integer page,
                                               @RequestParam(value = "rows")Integer size ){
        summaryId = id;
        Map<String,Object> map = new HashMap<String,Object>();
        Pageable pageable = new PageRequest(page-1,size);
        Page<UniversityAbsence> list = this.universityAbsenceService.findByUniversityAbsenceSummaryId(id,pageable);
        int total = this.universityAbsenceService.findByUniversityAbsenceSummaryId(id).size();
        map.put("total",total);
        map.put("rows",list.getContent());
        return map;
    }
    //删除
    @RequestMapping(value = "/deleteUniversityAbsence",method = RequestMethod.DELETE)
    public void deleteUniversityAbsence(@RequestParam("ids") String ids)throws Exception{
        String deleteIds[] = ids.split(",");
        for (int i = 0; i < deleteIds.length; i++) {
            this.universityAbsenceService.deleteById(Long.parseLong(deleteIds[i]));
        }
    }
    //添加
    @RequestMapping(value = "/addUniversityAbsence", method = RequestMethod.POST)
    public Map<String, Object> addUniversityAbsence(@RequestBody UniversityAbsence universityAbsence) throws Exception{
        universityAbsence.setAttendanceSummary(attendanceSummaryService.findOne(summaryId));
        this.universityAbsenceService.add(universityAbsence);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("universityAbsence", universityAbsence);
        return map;
    }
    //修改
    @RequestMapping(value = "/updateUniversityAbsence",method = RequestMethod.PUT)
    public Map<String, Object> updateUniversityAbsence(@RequestBody UniversityAbsence universityAbsence){
        universityAbsence.setAttendanceSummary(attendanceSummaryService.findOne(summaryId));
        this.universityAbsenceService.update(universityAbsence);
        Map<String, Object> map = new HashMap<String,Object>();
        map.put("universityAbsence",universityAbsence);
        return map;
    }
}
