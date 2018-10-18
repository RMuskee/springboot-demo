package demo.springboot.service;

import demo.springboot.repository.UserRepository;
import demo.springboot.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;


/**
 * This class is used by spring controller to authenticate and authorize user
 **/
@Service
public class UserDetailServiceImpl implements UserDetailsService {
	private final UserRepository userRepository;

	@Autowired
	public UserDetailServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {   
    	User curruser = userRepository.findByUsername(username);

        String[] roles = curruser.getRoles()
                .stream()
                .map(role -> role.getRole())
                .collect(Collectors.toSet())
                .toArray(new String[curruser.getRoles().size()]);
        UserDetails user = new org.springframework.security.core.userdetails.User(username, curruser.getPasswordHash(), true,
                true, true, true, AuthorityUtils.createAuthorityList(roles));
        return user;
    }
    
}
