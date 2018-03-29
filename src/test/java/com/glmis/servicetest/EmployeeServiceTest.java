//package com.glmis.servicetest;
//
//import com.glmis.domain.personnel.Employee;
//import com.glmis.repository.authority.UserRepository;
//import com.glmis.service.personnel.EmployeeService;
//import com.glmis.service.scientificResearch.ThesisService;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.jpa.domain.Specification;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.web.WebAppConfiguration;
//
//import java.util.Arrays;
//import java.util.List;
//
///**
// * Created by ibs on 16/11/10.
// */
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest
//@WebAppConfiguration
//public class EmployeeServiceTest {
//    @Autowired
//    public EmployeeService employeeService;
//    @Autowired
//    UserRepository userRepository;
//    @Autowired
//    ThesisService thesisService;
//
//
//    @Test
//    public void testSpec(){
//        String str = "1";
//        String str11 = "1";
//        String[] str2 = str.split(",");
//        String[] str22 = str11.split(",");
//        List list = Arrays.asList(str2);
//        List list1 = Arrays.asList(str22);
//        Specification<Thesis> specification = thesisService.inAndQuery(3080192,"collectionStuiation",list,"rank",list1,"year","2012","2090");
//        List<Thesis> thesises = thesisService.findBySepc(specification);
//        System.out.println(thesises);
//    }
//
//    @Test
//    public void testFindAllEmployee(){
//        List<Employee> employeeList = employeeService.findAllEmployee();
//        System.out.println(employeeList);
//    }
//
//}
