package com.glmis.security;
import com.glmis.domain.authority.User;
import com.glmis.domain.authority.UserRole;
import com.glmis.repository.authority.UserRepository;
import com.glmis.repository.authority.UserRolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService{
	private final UserRepository userRepository;
	private final UserRolesRepository userRolesRepository;
	final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);


	@Autowired
    public CustomUserDetailsService(UserRepository userRepository,UserRolesRepository userRolesRepository) {
        this.userRepository = userRepository;
        this.userRolesRepository=userRolesRepository;
    }


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user=userRepository.findByUserName(username);
		if(null == user){
			throw new UsernameNotFoundException("No user present with username: "+username);
		}else{
			List<String> userRoles = new ArrayList<>();
			try{
				for (UserRole userRole : user.getUserRoles()) {
					String roleName = userRole.getRole().getRoleName();
					logger.debug("user roleName======{}",roleName);
					userRoles.add(roleName);
				}
			} catch(Exception e){
				e.printStackTrace();
			}
			return new CustomUserDetails(user,userRoles);
		}
	}

}
