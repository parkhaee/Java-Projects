package ca.sheridancollege.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ca.sheridancollege.database.DatabaseAccess;
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	@Lazy
	private DatabaseAccess da;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//find the user based on the username
		ca.sheridancollege.beans.User user = da.findUserAccount(username); //not importing the User bean
		
		//if the user doesn't exist throw an exception...handled by Springboot
		if (user==null) {
			System.out.println("User not found:" + username);
			throw new UsernameNotFoundException("User " + username + "was not found in the database.");
		}
		
		//get a list of roles
		List<String> roleNames = da.getRolesById(user.getUserId());
		//change the list of roles into a list of GrantedAuthority
		List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
		if (roleNames != null) {
			for(String role: roleNames) {
				grantList.add(new SimpleGrantedAuthority(role));
			}
		}
		UserDetails userDetails = (UserDetails)new User(user.getUserName(), user.getEncryptedPassword(), grantList);
		return userDetails;
	}

}
