//package com.glmis.servicetest;
//
//import com.glmis.domain.personnel.Employee;
//import com.glmis.domain.scientificResearch.Project;
//import com.glmis.repository.personnel.EmployeeRepository;
//import com.glmis.repository.scientificResearch.ProjectFundedByGovernmentRepository;
//import com.glmis.service.personnel.DepartmentService;
//import jpa.test.TaskSpec;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.web.WebAppConfiguration;
//import java.util.List;
//
///**
// * Created by ibs on 16/10/28.
// */
//// SpringJUnit支持，由此引入Spring-Test框架支持！
//@RunWith(SpringJUnit4ClassRunner.class)
//// 指定我们SpringBoot工程的Application启动类
//@SpringBootTest
//// 由于是Web项目，Junit需要模拟ServletContext，
//// 因此我们需要给我们的测试类加上@WebAppConfiguration。
//@WebAppConfiguration
//public class DepartmentServiceTest {
//    @Autowired
//    DepartmentService departmentService;
//    @Autowired
//    ProjectFundedByGovernmentRepository projectFundedByGovernmentRepository;
//    @Autowired
//    EmployeeRepository employeeRepository;
//
//    @Test
//    public void testfindAllDepartment(){
//        List<Department> departments = departmentService.findAllDepartment();
//        System.out.println(departments);
//    }
//    @Test
//    public void testScientificResearchSpec() {
//        System.out.println("b");
//
//        System.out.println(this.projectFundedByGovernmentRepository.count(TaskSpec.method1()));
//        //System.out.println(this.projectRepository.findOne(TaskSpec.method1()).toString());
//        List<Project> projects = this.projectFundedByGovernmentRepository.findAll(TaskSpec.method1());
//        for(Project project : projects){
//            System.out.println("再一次打印");
//            //System.out.println("NAME:"+project.getName());
//            //System.out.println("NO:"+project.getNo());
//            System.out.println(project);
//        }
//        System.out.println("c");
//    }
//
//    @Test
//    public void testEmploySpec() {
//        System.out.println("b");
//
//        System.out.println(this.employeeRepository.count(TaskSpec.method2()));
//        //System.out.println(this.projectRepository.findOne(TaskSpec.method1()).toString());
//        List<Employee> employees = this.employeeRepository.findAll(TaskSpec.method2());
//        for(Employee employee : employees){
//            System.out.println("再一次打印");
//            //System.out.println("NAME:"+project.getName());
//            //System.out.println("NO:"+project.getNo());
//            System.out.println(employee.getName());
//        }
//        System.out.println("c");
//    }
//
//}
