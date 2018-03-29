package com.glmis.controller.personnel;

import com.glmis.domain.authority.Role;
import com.glmis.domain.authority.User;
import com.glmis.domain.authority.UserRole;
import com.glmis.domain.personnel.Employee;
import com.glmis.domain.personnel.EmployeeAssPoliticalParty;
import com.glmis.domain.personnel.SpecificationConditions;
import com.glmis.repository.authority.UserRepository;
import com.glmis.service.authority.RoleService;
import com.glmis.service.authority.UserRoleService;
import com.glmis.service.authority.UserService;
import com.glmis.service.personnel.EmployeeAssPoliticalPartyService;
import com.glmis.service.personnel.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2016/11/7.
 */

@RestController
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;
    @Autowired
    UserService userService;
    @Autowired
    RoleService roleService;
    @Autowired
    UserRoleService userRoleService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    EmployeeAssPoliticalPartyService employeeAssPoliticalPartyService;

    /**
     * 定义一个byte[]类型的变量存放照片的二进制数据
     */
    private static byte[] imgByte = null;
    /**
     * 定义一个int类型的变量存放职员的id,用于判断上传图片的状态是编辑还是添加
     * 添加状态设置id为0，编辑状态有id
     */
    private static Integer employeeId = 0;

    private static long employeePoliticalPartyId = 0;
    private static long eId = 0;
    final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    //添加
    @RequestMapping(value = "/addParty", method = RequestMethod.POST)
    public Map<String, Object> addParty(@RequestBody EmployeeAssPoliticalParty employeeAssPoliticalParty) throws Exception {
        logger.debug("-----------------------执行了");
        this.employeeAssPoliticalPartyService.add(employeeAssPoliticalParty);
        Map<String, Object> map = new HashMap<String, Object>();
        employeePoliticalPartyId = employeeAssPoliticalParty.getPid();
        logger.debug("***************{}" , employeePoliticalPartyId);
        map.put("employeeAssPoliticalParty", employeeAssPoliticalParty);
        return map;
    }

    @RequestMapping(value = "/updateParty", method = RequestMethod.PUT)
    public Map<String, Object> updateParty(
            @RequestParam(value = "id") Long id,
            @RequestBody EmployeeAssPoliticalParty employeeAssPoliticalParty) throws Exception {
        eId = id;
        //logger.debug(id+"----------------**************----------------");
        employeeAssPoliticalParty.setEmployee(employeeService.findOne(id));
        this.employeeAssPoliticalPartyService.update(employeeAssPoliticalParty);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("employeeAssPoliticalParty", employeeAssPoliticalParty);
        return map;
    }

    /**
     * 获取到所有的职员
     */
    @RequestMapping(value = "/findAllEmployee", method = RequestMethod.GET)
    public List<Employee> findAllEmployee() throws Exception {
        List<Employee> employees = employeeService.findAllEmployee();
        return employees;
    }

    /**
     * 根据部门查询相应的职员
     */
    @RequestMapping(value = "/displayEmpByDep", method = RequestMethod.GET)
    public Map<String, Object> findByDepartmentId(@RequestParam("id") Long id,
                                                  @RequestParam(value = "page") Integer page,
                                                  @RequestParam(value = "rows") Integer size) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        Pageable pageable = new PageRequest(page - 1, size);
        Page<Employee> list = this.employeeService.findByDepartmentId(id, pageable);
        int total = this.employeeService.findByDepartmentId(id).size();
        map.put("total", total);
        map.put("rows", list.getContent());
        return map;
    }
    /*
     *查询候选人
     */
    @RequestMapping(value = "/displayEmpBySum",method = RequestMethod.GET)
    public Map<String,Object> findByAttendanceSummaryId(@RequestParam("id")Long id,
                                                        @RequestParam(value = "page") Integer page,
                                                        @RequestParam(value = "rows") Integer size) throws Exception{
        Map<String,Object> map =new HashMap<String,Object>();
        Pageable pageable = new PageRequest(page-1,size);
        Page<Employee> candidates = this.employeeService.findByAttendanceSummaryId(id,pageable);
        int total = this.employeeService.findByAttendanceSummaryId(id).size();
        map.put("total", total);
        map.put("rows", candidates.getContent());
        return map;
    }
    /**
     * @param page
     * @param size
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/displayAllEmployeeName", method = RequestMethod.GET)
    public Map<String, Object> findAllDegree(@RequestParam(value = "page") Integer page,
                                             @RequestParam(value = "rows") Integer size) throws Exception {
        Page<Employee> list = this.employeeService.findAllEmployee(new PageRequest(page - 1, size));
        Map<String, Object> map = new HashMap<String, Object>();
        int total = this.employeeService.findAllEmployee().size();
        map.put("total", total);
        map.put("rows", list.getContent());
        return map;
    }

    /**
     * 获取到从前台上传的照片
     * 只有前台改变上传框才会触动该事件
     */
    @RequestMapping(value = "/test1111", method = RequestMethod.POST)
    public void uploadImg(@RequestParam("empId") Integer empId,
                          HttpServletRequest request) throws IOException {
        logger.debug("获取前台上传的图片---");
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
//      根据参数名获取到文件
        MultipartFile multipartFile = multipartRequest.getFile("imge");
        imgByte = multipartFile.getBytes();//将文件转化为二进制的格式,将值保存在byte类型的变量里。
        logger.debug("222333444---{}" , multipartFile.getSize() / 1024);
        logger.debug(""+empId);
        employeeId = empId;
    }

    /**
     * 添加一个新的职员  完成 增
     */
    @RequestMapping(value = "/addEmployee", method = RequestMethod.POST)
    public Map<String, Object> addEmployee(@RequestBody Employee employee) throws Exception {
        if (employeeId == 0) {
            logger.debug("得到照片为 {}" , employee.getPhoto());
            employee.setPhoto(imgByte);
            logger.debug("添加职员图片{}" , imgByte);
        }
        logger.debug("职员职工号为{}" , employee.getNo());
        this.employeeService.add(employee);//保存职员信息
        setUserAndRole(employee, employee.getNo());//自动分配账号，账号密码均为职工号
        employeeService.update(employee);//必须先保存职员信息，在保存User
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("employee", employee);
        logger.debug("******************{}" , employee.getId());
        if (employeePoliticalPartyId != 0) {
            employeeAssPoliticalPartyService.findOne(employeePoliticalPartyId).setEmployee(employeeService.findOne(employee.getId()));
            logger.debug("*****************---------------------" + employeeAssPoliticalPartyService.findOne(employeePoliticalPartyId).getEmployee().getId());
            employeeAssPoliticalPartyService.update(employeeAssPoliticalPartyService.findOne(employeePoliticalPartyId));
        }
        return map;
    }

    /**
     * 修改职员信息    完成 改
     */
    @RequestMapping(value = "/updateEmployee", method = RequestMethod.PUT)
    public Map<String, Object> updateDegree(@RequestBody Employee employee) throws Exception {
        /**当前id和从前台获取图片时的对应id为同一人时，再将图片存进该用户。
         * */
        logger.debug(""+employee.getId());
        if ((employee.getId() - employeeId) == 0) {
            employee.setPhoto(imgByte);
            logger.debug("她们是同一个人");
        }
        logger.debug("imageByte----{}" , imgByte);
        logger.debug("photo---{}" , employee.getPhoto());
        logger.debug("职员id---{}" , employee.getId());
        logger.debug("从图片得到的id---{}" , employeeId);
        logger.debug("职员职工号为{}" , employee.getNo());
        logger.debug("职员职工的 User 为{}" , employee.getUser());
        updateUserAndRole(employee, employee.getNo());//更新时重新分配账号，账号密码仍均为职工号
        employeeService.update(employee);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("employee", employee);
        return map;
    }

    /**
     * 删除一个职员   完成 删
     */
    @RequestMapping(value = "/deleteEmployee", method = RequestMethod.DELETE)
    public void deleteEmployee(@RequestParam("id") Long id) throws Exception {
        this.employeeService.deleteById(id);
    }

    /**
     * 从数据库回显图片到前台页面
     */
    @RequestMapping(value = "/toLookImge", method = RequestMethod.GET)
    public void displayImg(@RequestParam("imgId") Long imgId, HttpServletResponse response) throws Exception {
        logger.debug("回显图片测试");
        Employee employee = employeeService.findOne(imgId);//获取到员工
        byte[] imgByte = employee.getPhoto();//获取到图片
        response.setContentType("img/jpeg");
        response.setCharacterEncoding("utf-8");
//      如果当前图片不为空
        if (imgByte != null) {
            try {
                OutputStream out = response.getOutputStream();
                InputStream in = new ByteArrayInputStream(imgByte);
                int len = 0;
                byte[] buf = new byte[1024];
                while ((len = in.read(buf, 0, 1024)) != -1) {
                    out.write(buf, 0, len);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * 设置账号
     */
    public void setUserAndRole(Employee employee, String num) throws Exception {
        if ("1".equals(num)) {
            User user = userService.findOne(1);
            employee.setUser(user);
        } else {
            Role role = roleService.findOne(2);
            List<UserRole> list = new ArrayList<>();
            UserRole userRole = new UserRole();
            String name = num;
            String password = name;
            //password = Utils.makeMD5(password);
            logger.debug("当前用户的账号为{}密码为{}" , name ,password);
            User user = new User();
            user.setUserName(name);
            user.setPassword(password);
            user.setActor(employee);
            userService.add(user);
            userRole.setUser(user);
            userRole.setRole(role);
            userRoleService.add(userRole);
            list.add(userRole);
            user.setUserRoles(list);
            role.setUserRoles(list);
            employee.setUser(user);
        }
    }

    /**
     * 更新账号
     */
    public void updateUserAndRole(Employee employee, String num) throws NullPointerException {
        String name = num;
        String password = name;
        //password = Utils.makeMD5(password);
        logger.debug("当前用户的账号为{}密码为{}" , name ,password);
        User user = employee.getUser();
        logger.debug("查到了{}" , user);
        user.setUserName(name);
        user.setPassword(password);
        user.setActor(employee);
        userService.update(user);
    }

    @PostMapping("/searchEmployeeBySpecification")
    public List<Employee> searchEmployeeBySpecification(
            @RequestBody SpecificationConditions specificationConditions) {

        Specification<Employee> specification = this.employeeService.searchEmployeeByAcademicDegree(specificationConditions);
        List<Employee> list = this.employeeService.findBySepc(specification);
        return list;
    }

    @PostMapping("/searchEmployee")
    public Map<String,Object> searchEmployee(
            @RequestBody SpecificationConditions specificationConditions,
            @RequestParam(value = "page") Integer page,
            @RequestParam(value = "rows") Integer size){
        Pageable pageable = new PageRequest(page-1,size);
        Specification<Employee> specification = this.employeeService.searchEmployeeByAcademicDegree(specificationConditions);
        Page<Employee> list = this.employeeService.findBySepc(specification,pageable);
        Map<String, Object> map = new HashMap<String, Object>();
        int total = this.employeeService.findBySepc(specification).size();
        map.put("total", total);
        map.put("rows", list.getContent());
        return map;
    }



    /*
    *
    * 多条件查询
    *
    */
    //根据查询条件显示选中员工的论文信息
//    @RequestMapping(value = "/searchEmployeeByAcademicDegree", method = RequestMethod.GET)
//    public Map<String, Object> searchEmployeeByAcademicDegree(
//            @RequestParam("academicDegrees") String academicDegreeIds,
//            @RequestParam(";overseasChineseOrNot") String overseasChineseOrNotIds,
//            @RequestParam(";seXes") String sexIds,
//            @RequestParam(";practisingCertifications") String practisingCertificationIds,
//            @RequestParam(";startBirth") String startBirth,
//            @RequestParam(";endBirth") String endBirth,
//            @RequestParam(";endDate") String endDate,
//            @RequestParam(";professionalTitle") String professionalTitle,
//            @RequestParam(";departments") String departmentIds,
//            @RequestParam(";domesticOrNot") String domesticOrNotIds,
//            @RequestParam(";politicalParties") String politicalPartyIds,
//            @RequestParam(";academicConferences") String academicConferenceIds,
//            @RequestParam(";nation") String nationIds,
//            @RequestParam(";sponsor") String sponsorIds,
//            @RequestParam(value = "page") Integer page,
//            @RequestParam(value = "rows") Integer size) throws Exception {
//        Map<String, Object> map = new HashMap<>();
//        Pageable pageable = new PageRequest(page - 1, size);
//        //Arrays.asList将数组转换成list型，以“，”为分隔符，分割字符串
//        //条件一：员工学位
//        List<String> academicDegrees = Arrays.asList(academicDegreeIds.split(","));
//        List<Long> academicDegreeList = new ArrayList<Long>();
//        for (String ids : academicDegrees) {
//            academicDegreeList.add(Long.parseLong(ids));
//        }
//        //条件二：是否华侨
//        List<String> overseasChineseOrNots = Arrays.asList(overseasChineseOrNotIds.split(","));
//        List<Long> overseasChineseOrNotList = new ArrayList<Long>();
//        for (String ids : overseasChineseOrNots) {
//            overseasChineseOrNotList.add(Long.parseLong(ids));
//        }
//        //条件五：职工性别
//        List<String> sexes = Arrays.asList(sexIds.split(","));
//        List<Long> sexList = new ArrayList<Long>();
//        for (String ids : sexes) {
//            sexList.add(Long.parseLong(ids));
//        }
//        //条件六：执业资格
//        List<String> practisingCertifications = Arrays.asList(practisingCertificationIds.split(","));
//        List<Long> practisingCertificationList = new ArrayList<Long>();
//        for (String ids : practisingCertifications) {
//            practisingCertificationList.add(Long.parseLong(ids));
//        }
//        //条件四：职称
//        List<String> professionalTitles = Arrays.asList(professionalTitle.split(","));
//        List<Long> professionalTitleList = new ArrayList<>();
//        for (String id : professionalTitles) {
//            professionalTitleList.add(Long.parseLong(id));
//        }
//        //条件：部门
//        List<String> departments = Arrays.asList(departmentIds.split(","));
//        List<Long> departmentList = new ArrayList<Long>();
//        for (String ids : departments) {
//            departmentList.add(Long.parseLong(ids));
//        }
//        //条件：是否国内访学
//        List<String> domesticOrNots = Arrays.asList(domesticOrNotIds.split(","));
//        List<Long> domesticOrNotList = new ArrayList<Long>();
//        for (String ids : domesticOrNots) {
//            domesticOrNotList.add(Long.parseLong(ids));
//        }
//        //条件：政治面貌
//        List<String> politicalParties = Arrays.asList(politicalPartyIds.split(","));
//        List<Long> politicalPartyList = new ArrayList<Long>();
//        for (String ids : politicalParties) {
//            politicalPartyList.add(Long.parseLong(ids));
//        }
//        //条件三：统计截止日期时间
//        //logger.debug(endTime);
//        //条件四：学术会议
//        List<String> academicConferences = Arrays.asList(academicConferenceIds.split(","));
//        List<Long> academicConferenceList = new ArrayList<Long>();
//        for (String ids : academicConferences) {
//            academicConferenceList.add(Long.parseLong(ids));
//        }
//        //民族
//        List<String> nation = Arrays.asList(nationIds.split(","));
//        List<Long> nationList = new ArrayList<Long>();
//        for (String ids : nation) {
//            nationList.add(Long.parseLong(ids));
//        }
//        //访问学者资助
//        List<String> sponsor = Arrays.asList(sponsorIds.split(","));
//        List<Long> sponsorList = new ArrayList<Long>();
//        for (String ids : sponsor) {
//            sponsorList.add(Long.parseLong(ids));
//        }
//        Specification<Employee> specification = this.employeeService.searchEmployeeByAcademicDegree(academicDegreeList, overseasChineseOrNotList, sexList, practisingCertificationList, professionalTitleList, departmentList, domesticOrNotList, politicalPartyList, nationList, sponsorList, endDate, startBirth, endBirth, academicConferenceList);
//        Page<Employee> list = this.employeeService.findBySepc(specification, pageable);
//        int total = this.employeeService.findBySepc(specification).size();
//        map.put("total", total);
//        map.put("rows", list.getContent());
//        return map;
//    }

}

