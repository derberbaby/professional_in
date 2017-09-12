package com.debbie.beltexam.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.debbie.beltexam.models.Role;
import com.debbie.beltexam.models.User;
import com.debbie.beltexam.repositories.UserRepo;

@Service
public class UserDetailsServiceImplementation implements UserDetailsService {
	private UserRepo userRepo;
	
	public UserDetailsServiceImplementation(UserRepo userRepo) {
		this.userRepo = userRepo;
	}
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		// login route automatically hits here
		User user = userRepo.findByEmail(email);
		
		if(user == null) {
			throw new UsernameNotFoundException("User not found");
		}
		
		// puts user in session
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), getAuthorities(user));
	}
	
	private List<GrantedAuthority> getAuthorities(User user) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		for(Role role : user.getRoles()) {
			GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role.getName());
			authorities.add(grantedAuthority);;
		}
		return authorities;
	}
}