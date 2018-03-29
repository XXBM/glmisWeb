package com.glmis.controller;

import com.glmis.domain.authority.*;
import com.glmis.service.authority.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * Created by xuling on 2016/11/19.
 */

@RestController
public class CreateMenuController {
    final Logger logger = LoggerFactory.getLogger(CreateMenuController.class);
    @Autowired
    private UserService userService;
    @RequestMapping(value = "/menu", method = RequestMethod.GET)
    public List<Resource> treelist() {
        //通过得到当前的用户名，得到当前用户
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User storedUser = userService.findByUserName(username);
        List<Role> roles = new ArrayList<>();
        List<Integer> ids = new ArrayList<>();
        TreeBuilder treeBuilder = null;
        for (UserRole userRole : storedUser.getUserRoles()) {
            Integer id =userRole.getId();
            if (ids.contains(id)==false){
                roles.add(userRole.getRole());
                logger.debug(userRole.getRole().getDescription().toString());
                ids.add(id);
            }
        }
        treeBuilder = new TreeBuilder(roles);
        List<Resource> tree = treeBuilder.build();
        Collections.sort(tree, new Comparator<Resource>() {
            @Override
            public int compare(Resource o1, Resource o2) {
                //按照学生的年龄进行升序排列
                if (o1.getId() > o2.getId()) {
                    return 1;
                }
                if (o1.getId() == o2.getId()) {
                    return 0;
                }
                return -1;
            }
        });
        return tree;
    }

    //返回用户名和用户类型
    @RequestMapping(value = "/userInfo", method = RequestMethod.GET)
    public User returnUser()throws Exception {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User storedUser = userService.findByUserName(username);
        return storedUser;
    }

}
