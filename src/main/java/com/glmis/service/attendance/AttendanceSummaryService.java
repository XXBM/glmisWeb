package com.glmis.service.attendance;

import com.glmis.domain.attendance.AttendanceSummary;
import com.glmis.repository.attendance.AttendanceSummaryRepository;
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
public class AttendanceSummaryService extends BasicService<AttendanceSummary,Long>{
    @Autowired
    AttendanceSummaryRepository attendanceSummaryRepository;
    //分页显示
    public Page<AttendanceSummary> findAllAttendanceSummary(Pageable pageable) {
        return this.attendanceSummaryRepository.findAll(pageable);
    }

    public AttendanceSummary findByAttendanceName(String attendanceName){
        return attendanceSummaryRepository.findByAttendanceName(attendanceName);
    }
    /*通过id得到一张纸*/
    public AttendanceSummary findOne(Long id) {
        return attendanceSummaryRepository.findOne(id);
    }
    /**
     * 多条件查询
     */
    public Page<AttendanceSummary> findBySepc(Specification<AttendanceSummary> specification, Pageable pageable) {
        return this.attendanceSummaryRepository.findAll(specification, pageable);
    }

    /**
     * 多条件查询
     */
    public List<AttendanceSummary> findBySepc(Specification<AttendanceSummary> specification) {
        return this.attendanceSummaryRepository.findAll(specification);
    }

    /**
     * in and 多条件查询
     */
    public Specification<AttendanceSummary> summaryQuery(
                                             String startTime,
                                             String endTime) {
        return new Specification<AttendanceSummary>() {
            @Override
            public Predicate toPredicate(Root<AttendanceSummary> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                //动态查询
                List<Predicate> predicate = new ArrayList<>();
                if(startTime!=""){
                    predicate.add(cb.greaterThanOrEqualTo(root.get("attendanceTime").as(String.class), startTime));
                }
                if(endTime!=""){
                    predicate.add(cb.lessThanOrEqualTo(root.get("attendanceTime").as(String.class), endTime));
                }
                Predicate[] pre = new Predicate[predicate.size()];
                return query.where(predicate.toArray(pre)).getRestriction();
            }
        };
    }
}
