package com.glmis.service.attendance;

import com.glmis.domain.attendance.UniversityAbsence;
import com.glmis.repository.attendance.UniversityAbsenceRepository;
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
public class UniversityAbsenceService extends BasicService<UniversityAbsence,Long>{
    @Autowired
    UniversityAbsenceRepository universityAbsenceRepository;
    /**通过选中的下拉框实体id获取所有的自己的所有属性*/
    public List<UniversityAbsence> findByUniversityAbsenceSummaryId(Long id){
        return this.universityAbsenceRepository.findByAttendanceSummaryId(id);
    }
    /**通过选中的下拉框实体id获取所有的自己的所有属性   +  分页显示*/
    public Page<UniversityAbsence> findByUniversityAbsenceSummaryId(Long id, Pageable pageable){
        return this.universityAbsenceRepository.findByAttendanceSummaryId(id,pageable);
    }

    /**
     * 多条件查询
     */
    public Page<UniversityAbsence> findBySepc(Specification<UniversityAbsence> specification, Pageable pageable) {
        return this.universityAbsenceRepository.findAll(specification, pageable);
    }

    /**
     * 多条件查询
     */
    public List<UniversityAbsence> findBySepc(Specification<UniversityAbsence> specification) {
        return this.universityAbsenceRepository.findAll(specification);
    }

    public Specification<UniversityAbsence> attendanceExportExcel(
            String attendanceStartTime,
            String attendanceEndTime){
        return new Specification<UniversityAbsence>() {
            @Override
            public Predicate toPredicate(Root<UniversityAbsence> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicate = new ArrayList<>();
                //条件一：时间范围
                predicate.add(cb.greaterThanOrEqualTo(root.get("attendanceSummary").get("attendanceTime").as(String.class),attendanceStartTime));
                predicate.add(cb.lessThanOrEqualTo(root.get("attendanceSummary").get("attendanceTime").as(String.class),attendanceEndTime));
                Predicate[] pre = new Predicate[predicate.size()];
                query.distinct(true);
                return query.where(predicate.toArray(pre)).getRestriction();
            }
        };
    }

    public Specification<UniversityAbsence> findUniversityAbsenceBySpec(
            Long id,
            String attendanceStartTime,
            String attendanceEndTime){
        return new Specification<UniversityAbsence>() {
            @Override
            public Predicate toPredicate(Root<UniversityAbsence> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicate = new ArrayList<>();
                //条件一：员工的id
                predicate.add(cb.equal(root.get("employee").get("id"),id));
                //条件二：时间范围
                predicate.add(cb.greaterThanOrEqualTo(root.get("attendanceSummary").get("attendanceTime").as(String.class),attendanceStartTime));
                predicate.add(cb.lessThanOrEqualTo(root.get("attendanceSummary").get("attendanceTime").as(String.class),attendanceEndTime));
                Predicate[] pre = new Predicate[predicate.size()];
                query.distinct(true);
                return query.where(predicate.toArray(pre)).getRestriction();
            }
        };
    }
}
