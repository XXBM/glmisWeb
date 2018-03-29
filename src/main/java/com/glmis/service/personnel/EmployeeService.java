package com.glmis.service.personnel;

import com.glmis.domain.attendance.Leave;
import com.glmis.domain.personnel.*;
import com.glmis.repository.personnel.EmployeeRepository;
import com.glmis.repository.personnel.EmploymentCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ibs on 16/11/6.
 */

@Service
public class EmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;

    public List<Employee> employeeList;

    public Employee findByUserId(Integer id) {
        return this.employeeRepository.findByUserId(id);
    }
    /*通过id获取所有的职员*/
    public List<Employee> findAllEmployee() {
        employeeList = this.employeeRepository.findAll();
        return employeeList;
    }

    /*通过id得到一个职员*/
    public Employee findOne(Long id) {
        return employeeRepository.findOne(id);
    }

    /*保存职员信息*/
    public void save(Employee employee) {
        employeeRepository.save(employee);
    }

    //通过部门id获取所有的职员
    public List<Employee> findByEmployeeId(Long id) {
        return this.employeeRepository.findByDepartmentId(id);
    }

    //分页显示
    public Page<Employee> findAllEmployee(Pageable pageable) {
        return this.employeeRepository.findAll(pageable);
    }

    public Page<Employee> findByDepartmentId(Long id, Pageable pageable) {
        return this.employeeRepository.findByDepartmentId(id, pageable);
    }
    public List<Employee> findByDepartmentId(Long id) {
        return this.employeeRepository.findByDepartmentId(id);
    }
    public Page<Employee> findByAttendanceSummaryId(Long id,Pageable pageable){
        return this.employeeRepository.findByAttendanceSummariesId(id,pageable);
    }
    public List<Employee> findByAttendanceSummaryId(Long id){
        return this.employeeRepository.findByAttendanceSummariesId(id);
    }

    public Employee findByName(String name) {
        return this.employeeRepository.findByName(name);
    }

    //添加职员信息
    public void add(Employee employee) {
        this.employeeRepository.save(employee);
    }

    //更新职员信息
    public void update(Employee employee) {
        this.employeeRepository.saveAndFlush(employee);
    }

    //删除职员信息
    public void deleteById(Long id) {
        this.employeeRepository.delete(id);
    }

    /**
     * 多条件查询
     */
    public Page<Employee> findBySepc(Specification<Employee> specification, Pageable pageable) {
        return this.employeeRepository.findAll(specification, pageable);
    }

    /**
     * 多条件查询
     */
    public List<Employee> findBySepc(Specification<Employee> specification) {
        employeeList = this.employeeRepository.findAll(specification);
        return employeeList;
    }
    public List<Employee> findBySepc(Specification<Employee> specification,Sort sort) {
        employeeList = this.employeeRepository.findAll(specification,sort);
        return employeeList;
    }

    /*
    *
    * 封装查询条件
    *
    */

    public Specification<Employee> searchEmployeeByAcademicDegree(SpecificationConditions specificationConditions) {
        return new Specification<Employee>() {
            @Override
            public Predicate toPredicate(Root<Employee> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicate = new ArrayList<>();
                //条件一：员工学位
                Join<Employee, EmployeeAssAcademicDegree> employeeAssAcademicDegreeJoins = null;
                if(specificationConditions.getAcademicDegree().size() != 0) {
                    employeeAssAcademicDegreeJoins = root.join("employeeAssAcademicDegrees");
                    predicate.add(cb.in(employeeAssAcademicDegreeJoins.get("academicDegree").get("id")).value(specificationConditions.getAcademicDegree()));
                }
                //条件二：是否华侨
                if(specificationConditions.getOverseasChineseOrNot().size() != 0) {
                    predicate.add(cb.in(root.get("overseasChineseOrNot").get("id")).value(specificationConditions.getOverseasChineseOrNot()));
                }
                //条件三：出生日期
                if(!"".equals(specificationConditions.getStartBirth()) && !"".equals(specificationConditions.getEndBirth())) {
                    predicate.add(cb.between(root.get("birth").as(String.class), specificationConditions.getStartBirth(), specificationConditions.getEndBirth()));
                }
                //条件四：职称
                Join<Employee, EmployeeAssProfessionalTitle> employeeAssProfessionalTitleJoins = null;
                if(specificationConditions.getProfessionalTitle().size() != 0) {
                    employeeAssProfessionalTitleJoins = root.join("employeeAssProfessionalTitles");
                    predicate.add(cb.in(employeeAssProfessionalTitleJoins.get("professionalTitle").get("id")).value(specificationConditions.getProfessionalTitle()));
                }
                //条件五：职工性别
                if(specificationConditions.getSex().size() != 0) {
                    predicate.add(cb.in(root.get("sex").get("id")).value(specificationConditions.getSex()));
                }
                //条件六：执业资格
                Join<Employee, EmployeeAssPractisingCertification> employeeAssPractisingCertificationJoins = null;
                if(specificationConditions.getPractisingCertification().size() != 0) {
                    employeeAssPractisingCertificationJoins = root.join("employeeAssPractisingCertifications");
                    predicate.add(cb.in(employeeAssPractisingCertificationJoins.get("practisingCertification").get("id")).value(specificationConditions.getPractisingCertification()));
                }
                //条件七：部门
                if(specificationConditions.getDepartment().size() != 0) {
                    predicate.add(cb.in(root.join("department").get("id")).value(specificationConditions.getDepartment()));
                }
                //条件八：是否国内访学
                Join<Employee, VisitingAcademic> visitingAcademicJoins = null;
                if(specificationConditions.getDomesticOrNot().size() != 0) {
                    visitingAcademicJoins = root.join("visitingAcademics");
                    predicate.add(cb.in(visitingAcademicJoins.get("domesticOrNot").get("id")).value(specificationConditions.getDomesticOrNot()));
                }
                //条件九：政治面貌
                Join<Employee, EmployeeAssPoliticalParty> employeeAssPoliticalPartyJoins = null;
                if(specificationConditions.getPoliticalParty().size() != 0) {
                    employeeAssPoliticalPartyJoins = root.join("employeeAssPoliticalParties");
                    predicate.add(cb.in(employeeAssPoliticalPartyJoins.join("politicalParty").get("id")).value(specificationConditions.getPoliticalParty()));
                }
                //民族
                if(specificationConditions.getNation().size() != 0) {
                    predicate.add(cb.in(root.join("nation").get("id")).value(specificationConditions.getNation()));
                }
                //访问学者资
                if(specificationConditions.getSponsor().size() != 0) {
                    predicate.add(cb.in(visitingAcademicJoins.join("sponsor").get("id")).value(specificationConditions.getSponsor()));
                }
                //条件十：统计截止日期时间
                //统计截止日期含义：如果有此日期，则入党时间需早于它，获得学位时间早于它
                //职称聘任时间早于它，执业资格获得时间早于它，访问学者结束时间早于它，学术会议结束时间早于它
                Join<Employee, AcademicConference> academicConferenceJoins = null;
                if(!"".equals(specificationConditions.getEndDate())) {
                    academicConferenceJoins = root.join("academicConferences");
                    String endDate = specificationConditions.getEndDate();
                    predicate.add(cb.lessThanOrEqualTo(employeeAssPoliticalPartyJoins.get("membershipFrom").as(String.class), endDate));
                    predicate.add(cb.lessThanOrEqualTo(employeeAssAcademicDegreeJoins.get("grantedDate").as(String.class), endDate));
                    predicate.add(cb.lessThanOrEqualTo(employeeAssProfessionalTitleJoins.get("appointedDate").as(String.class), endDate));
                    predicate.add(cb.lessThanOrEqualTo(employeeAssPractisingCertificationJoins.get("issuedDate").as(String.class), endDate));
                    predicate.add(cb.lessThanOrEqualTo(visitingAcademicJoins.get("endTime").as(String.class), endDate));
                    predicate.add(cb.lessThanOrEqualTo(academicConferenceJoins.get("endTime").as(String.class), endDate));
                }
                //条件四：学术会议是否汇报
                //获取当前时间
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
                String nowDate = df.format(new Date());// new Date()为获取当前系统时间
                if(specificationConditions.getAcademicConference().size()==1){
                    if(specificationConditions.getAcademicConference().get(0)==1){
                        predicate.add(cb.lessThanOrEqualTo(academicConferenceJoins.get("reportTime").as(String.class), nowDate));
                    }else{
                        predicate.add(cb.greaterThanOrEqualTo(academicConferenceJoins.get("reportTime").as(String.class), nowDate));
                    }
                }
                //返回结果
                Predicate[] pre = new Predicate[predicate.size()];
                query.distinct(true);
                return query.where(predicate.toArray(pre)).getRestriction();
            }
        };
    }

//    public Specification<Employee> attendanceExportExcel(
//            String attendanceStartTime,
//            String attendanceEndTime
//           ){
//        return new Specification<Employee>() {
//            @Override
//            public Predicate toPredicate(Root<Employee> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
//                List<Predicate> predicate = new ArrayList<>();
//                //Join<Dept, Employee> employeeJoin = deptRoot.join(Dept_.employeeCollection);
//                //cqDept.where(criteriaBuilder.equal(employeeJoin.get(Employee_.deptId).get(Dept_.id), 1));
//                Root<UniversityAbsence> attendanceRoot = query.from(UniversityAbsence.class);
//                Join<Employee, UniversityAbsence> employeeJoin = root.join("attendances");
//                //条件一：时间范围
//                //Join<Employee, Presence> employeeAssAttenanceJoins = root.join("attendances");
//                //predicate.add(cb.greaterThanOrEqualTo(employeeAssAttenanceJoins.get("attendanceSummary").get("attendanceTime").as(String.class),attendanceStartTime));
//                //predicate.add(cb.lessThanOrEqualTo(employeeAssAttenanceJoins.get("attendanceSummary").get("attendanceTime").as(String.class),attendanceEndTime));
//                //predicate.add(cb.in(employeeAssAttenanceJoins.get("presenceDescription").get("id")).value(ids));
//                //predicate.add(cb.equal(cb.equal(employeeJoin.get(attendanceRoot.get(""))),3));
//                //predicate.add(cb.equal(employeeJoin.get("universityAbsenceDescription").get("id"), 3));
//                List<Long> ids = new ArrayList<>();
//                ids.add((long)2);
//                ids.add((long)3);
//                ids.add((long)4);
//                ids.add((long)5);
//                ids.add((long)6);
//                Predicate[] pre = new Predicate[predicate.size()];
//                query.distinct(true);
//                return query.where(predicate.toArray(pre)).getRestriction();
//            }
//        };
//    }


    public Specification<Employee> findCandidates(){
        return new Specification<Employee>() {
            @Override
            public Predicate toPredicate(Root<Employee> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicate = new ArrayList<>();
                List<Long> employmentCategoryList = new ArrayList<>();
//                employmentCategoryList.add(employmentCategoryRepository.findByDescription("在职").getId());
//                employmentCategoryList.add(employmentCategoryRepository.findByDescription("人事代理").getId());
//                employmentCategoryList.add(employmentCategoryRepository.findByDescription("合同").getId());
                employmentCategoryList.add((long)38);
                employmentCategoryList.add((long)39);
                employmentCategoryList.add((long)40);
                //条件一：查询在岗人员
                predicate.add(cb.in(root.join("employmentCategory").get("id")).value(employmentCategoryList));
                Predicate[] pre = new Predicate[predicate.size()];
                query.distinct(true);
                return query.where(predicate.toArray(pre)).getRestriction();
            }
        };
    }

    @Autowired
    EmploymentCategoryRepository employmentCategoryRepository;


    public Specification<Employee> findUniversityAbsences(){
        return new Specification<Employee>() {
            @Override
            public Predicate toPredicate(Root<Employee> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicate = new ArrayList<>();
                Join<Employee, Leave> employeeAssLeaveJoins =  root.join("leaves");
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
                String nowDate = df.format(new Date());// new Date()为获取当前系统时间
                predicate.add(cb.lessThanOrEqualTo(employeeAssLeaveJoins.get("startTime").as(String.class), nowDate));
                predicate.add(cb.greaterThanOrEqualTo(employeeAssLeaveJoins.get("endTime").as(String.class), nowDate));
                predicate.add(cb.greaterThanOrEqualTo(employeeAssLeaveJoins.get("realTime").as(String.class), nowDate));
                Predicate[] pre = new Predicate[predicate.size()];
                query.distinct(true);
                return query.where(predicate.toArray(pre)).getRestriction();
            }
        };
    }

}
