package com.glmis.service.attendance;

import com.glmis.domain.attendance.Attendance;
import com.glmis.repository.attendance.AttendanceRepository;
import com.glmis.service.BasicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ibs on 16/11/6.
 */

@Service
public class AttendanceService extends BasicService<Attendance,Long>{

    @Autowired
    AttendanceRepository attendanceRepository;
    /**通过选中的下拉框实体id获取所有的自己的所有属性*/
    public List<Attendance> findByAttendanceSummaryId(Long id){
        return this.attendanceRepository.findByAttendanceSummaryId(id);
    }
    public Attendance findByEmployeeId(Long id){
        return this.attendanceRepository.findByEmployeeId(id);
    }
    /**通过选中的下拉框实体id获取所有的自己的所有属性   +  分页显示*/
    public Page<Attendance> findByAttendanceSummaryId(Long id, Pageable pageable){
        return this.attendanceRepository.findByAttendanceSummaryId(id,pageable);
    }


    /**
     * 多条件查询
     */
    public Page<Attendance> findBySepc(Specification<Attendance> specification, Pageable pageable) {
        return this.attendanceRepository.findAll(specification, pageable);
    }

    /**
     * 多条件查询
     */
    public List<Attendance> findBySepc(Specification<Attendance> specification) {
        return this.attendanceRepository.findAll(specification);
    }

    public Specification<Attendance> findAttendanceSpec(
            Long employeeId,
            Long attendanceSummaryId
    ){
        return new Specification<Attendance>() {
            @Override
            public Predicate toPredicate(Root<Attendance> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicate = new ArrayList<>();
                //条件一：员工
                predicate.add(cb.equal(root.get("employee"), employeeId));
                //条件二：考勤纸
                predicate.add(cb.equal(root.get("attendanceSummary"), attendanceSummaryId));
                Predicate[] pre = new Predicate[predicate.size()];
                query.distinct(true);
                return query.where(predicate.toArray(pre)).getRestriction();
            }
        };
    }



}
