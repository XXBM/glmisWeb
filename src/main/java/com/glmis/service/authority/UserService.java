package com.glmis.service.authority;

import com.glmis.domain.authority.User;
import com.glmis.repository.authority.UserRepository;
import com.glmis.service.BasicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by xuling on 2016/10/11.
 */

@Service
public class UserService extends BasicService<User,Integer>{
    @Autowired
    UserRepository userRepository;

    public User findByUserName(String userName){
        return this.userRepository.findByUserName(userName);
    }
    public User findById(Integer id){
        return userRepository.findById(id);
    }

    public  User addUser(User user){
        return this.userRepository.save(user);
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}