package com.demo.marbgroup.controllers;

import static org.springframework.http.ResponseEntity.ok;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.demo.marbgroup.configs.JwtTokenProvider;
import com.demo.marbgroup.enums.RoleType;
import com.demo.marbgroup.enums.Status;
import com.demo.marbgroup.models.User;
import com.demo.marbgroup.repositories.RoleRepository;
import com.demo.marbgroup.repositories.UserRepository;
import com.demo.marbgroup.services.UserService;
import com.demo.marbgroup.util.Constants;

@RestController
//@Api(tags = "Users")	
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private UserService userService;

	@Autowired
	JwtTokenProvider jwtTokenProvider;

	@RequestMapping(method = RequestMethod.GET, value = "/users")
	public ResponseEntity<List<User>> findAllUsers() {
		return ResponseEntity.ok(userRepository.findAllByStateAndStatus(Constants.INSERTED, Status.Active.toString()));
	}

	@RequestMapping(method = RequestMethod.GET, value = "/users/{roleName}")
	public ResponseEntity<List<User>> findAll(@PathVariable String roleName) {
		List<User> users = null;
		if (roleName.equals(RoleType.Admin.toString())) {
			users = userRepository.findAll();
		} else if (roleName.equals(RoleType.Operator.toString())) {
			users = userRepository.findAllByState(Constants.INSERTED);
		} else {
			List<String> roles = new ArrayList<String>();
			roles.add(RoleType.Owner.toString());
			roles.add(RoleType.Staff.toString());
			users = userRepository.findAllByStateAndRoleNameIn(Constants.INSERTED, roles);
		}
		return ResponseEntity.ok(users);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/user")
	public ResponseEntity<User> save(@RequestBody User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setUuid(UUID.randomUUID().toString());
		return ResponseEntity.ok(userRepository.save(user));
	}

	@RequestMapping(method = RequestMethod.GET, value = "/user/uuid/{uuid}")
	public ResponseEntity<User> findById(@PathVariable String uuid) {
		Optional<User> user = userRepository.findByUuid(uuid);
		return ResponseEntity.ok(user.get());
	}

	@RequestMapping(method = RequestMethod.GET, value = "/user/{id}")
	public ResponseEntity<User> show(@PathVariable Long id) {
		logger.info("show : start");
		Optional<User> user = userRepository.findById(id);
		return ResponseEntity.ok(user.get());
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/user/{id}")
	public ResponseEntity<User> update(@PathVariable Long id, @RequestBody User user) {
		Optional<User> userObject = userRepository.findById(id);
		if (user.getFullName() != null) {
			userObject.get().setFullName(user.getFullName());
		}
		if (user.getEmail() != null) {
			userObject.get().setEmail(user.getEmail());
		}
		if (user.getPhone() != null) {
			userObject.get().setPhone(user.getPhone());
		}
		if (user.getSelectLabel() != null) {
			userObject.get().setSelectLabel(user.getSelectLabel());
		}
		userObject.get().setAgreed(user.isAgreed());
		userObject.get().setState(user.isState());
		userObject.get().setStatus(user.getStatus());
		userObject.get().setUpdatedDate(user.getUpdatedDate());
		return ResponseEntity.ok(userRepository.save(userObject.get()));
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/user/{id}")
	public void delete(@PathVariable Long id) {
		Optional<User> user = userRepository.findById(id);
		userRepository.delete(user.get());
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/change-password/{id}")
	public ResponseEntity<User> chanePasswordPost(@PathVariable Long id, @RequestBody User user) {
		Optional<User> userObject = userRepository.findById(id);
		if (user.getPassword() != null) {
			userObject.get().setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		}

		userObject.get().setUpdatedDate(user.getUpdatedDate());
		return ResponseEntity.ok(userRepository.save(userObject.get()));
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/reset-password/{id}")
	public ResponseEntity<User> resetPasswordPost(@PathVariable Long id, @RequestBody User user) {
		Optional<User> userObject = userRepository.findById(id);
		if (user.getPassword() != null) {
			userObject.get().setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		}

		userObject.get().setUpdatedDate(user.getUpdatedDate());
		return ResponseEntity.ok(userRepository.save(userObject.get()));
	}

	@RequestMapping(method = RequestMethod.GET, value = "/check-phone-or-email/{phoneOrEmail}")
	public ResponseEntity<User> getUserByPhoneOrEmail(@PathVariable String phoneOrEmail) {
		User user = null;
		try {
			user = userService.findByPhoneOrEmail(phoneOrEmail);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			return ResponseEntity.ok(user);
		}
		return ResponseEntity.ok(user);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/check-phone/{phone}")
	public boolean getUserByPhone(@PathVariable String phone) {
		User user = null;
		try {
			user = userRepository.findByPhone(phone);
			if (user != null) {
				return true;
			} else {
				return false;
			}
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			return false;
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/check-email/{email}")
	public boolean getUserByEmail(@PathVariable String email) {
		User user = null;
		try {
			user = userRepository.findByEmail(email);
			if (user != null) {
				return true;
			} else {
				return false;
			}
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			return false;
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/get-reset-password-token/{phoneOrEmail}")
	public ResponseEntity<Map<Object, Object>> getResetPasswordToken(@PathVariable String phoneOrEmail) {
		logger.info("getResetPasswordToken() : " + phoneOrEmail);
		String resetPasswordToken = null;
		Map<Object, Object> model = new HashMap<Object, Object>();
		User user = userService.findByPhoneOrEmail(phoneOrEmail);
		resetPasswordToken = jwtTokenProvider.createResetPasswordToken(user.getPhone(), 600000);
		model.put("resetPasswordToken", resetPasswordToken);
		return ok(model);
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/user/state/{id}")
	public ResponseEntity<User> updateStatePost(@PathVariable Long id, @RequestBody User user) {
		Optional<User> userObject = userRepository.findById(id);
		userObject.get().setState(false);
		userObject.get().setUpdatedDate(user.getUpdatedDate());
		return ResponseEntity.ok(userRepository.save(userObject.get()));
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/user/status/{id}")
	public ResponseEntity<User> updateStatus(@PathVariable Long id, @RequestBody User user) {
		Optional<User> userObject = userRepository.findById(id);
		userObject.get().setState(true);
		userObject.get().setStatus(user.getStatus());
		userObject.get().setUpdatedDate(user.getUpdatedDate());
		return ResponseEntity.ok(userRepository.save(userObject.get()));
	}

}