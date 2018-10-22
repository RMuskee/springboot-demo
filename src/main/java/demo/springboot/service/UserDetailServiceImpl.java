package demo.springboot.service;

import demo.springboot.model.Role;
import demo.springboot.repository.UserRepository;
import demo.springboot.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
	private final UserRepository userRepository;

	@Autowired
	public UserDetailServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

    @Override
    public UserDetails loadUserByUsername(String username)
    {   
    	User foundUser = userRepository.findByUsername(username);

    	if (foundUser != null) {
            String[] roles = foundUser.getRoles()
                    .stream()
                    .map(Role::getRole)
                    .collect(Collectors.toSet())
                    .toArray(new String[foundUser.getRoles().size()]);

            return new org.springframework.security.core.userdetails.User(
                    username,
                    foundUser.getPasswordHash(),
                    true,
                    true,
                    true,
                    true,
                    AuthorityUtils.createAuthorityList(roles));
        } else {
    	    throw new UsernameNotFoundException(username);
        }
    }    
}
