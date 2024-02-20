package com.demo.marbgroup.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.demo.marbgroup.models.Role;
import com.demo.marbgroup.models.User;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

	@Autowired
	private UserService userService;

	@Override
	public UserDetails loadUserByUsername(String phoneOrEmail) {
		User user = userService.findByPhoneOrEmail(phoneOrEmail);
		if (user != null) {
			List<GrantedAuthority> authorities = getUserAuthority(user.getRole());
			return buildUserForAuthentication(user, authorities);
		}
		return null;
	}

	private List<GrantedAuthority> getUserAuthority(Role role) {
		Set<GrantedAuthority> roles = new HashSet<>();
		// userRoles.forEach((role) -> {
		roles.add(new SimpleGrantedAuthority(role.getName()));
		// });
		List<GrantedAuthority> grantedAuthorities = new ArrayList<>(roles);
		return grantedAuthorities;
	}

	private UserDetails buildUserForAuthentication(User user, List<GrantedAuthority> authorities) {
		return new org.springframework.security.core.userdetails.User(user.getPhone(), user.getPassword(), authorities);
	}
}
