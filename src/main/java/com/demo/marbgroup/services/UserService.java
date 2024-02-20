package com.demo.marbgroup.services;

import java.util.Date;
import java.util.UUID;

import com.demo.marbgroup.dtos.RegisterDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.demo.marbgroup.enums.RoleType;
import com.demo.marbgroup.enums.Status;
import com.demo.marbgroup.models.Role;
import com.demo.marbgroup.models.User;
import com.demo.marbgroup.repositories.RoleRepository;
import com.demo.marbgroup.repositories.UserRepository;
import com.demo.marbgroup.util.Constants;

@Service
public class UserService {

	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder bCryptPasswordEncoder;

	public User saveUser(User user) {
		user.setUuid(UUID.randomUUID().toString());
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setState(Constants.INSERTED);
		user.setStatus(Status.Active.toString());
		user.setCreatedDate(new Date());
		user.setUpdatedDate(new Date());
		Role userRole = roleRepository.findByName(RoleType.Admin.toString());
		user.setRole(userRole);
		return userRepository.save(user);
	}

	public User findByPhone(String phone) {
		return userRepository.findByPhoneAndStateAndStatus(phone, Constants.INSERTED, Status.Active.toString());
	}

	public User findByPhoneOrEmail(String phoneOrEmail) {
		User user = null;
		user = userRepository.findByPhoneAndStateAndStatus(phoneOrEmail, Constants.INSERTED,
				Status.Active.toString());
		if (user == null) {
			user = userRepository.findByEmailAndStateAndStatus(phoneOrEmail, Constants.INSERTED,
					Status.Active.toString());
		}

		return user;
	}

	public boolean veifyPassword(String rawPassword, String encodedPassword) {
		return bCryptPasswordEncoder.matches(rawPassword, encodedPassword);
	}

}
