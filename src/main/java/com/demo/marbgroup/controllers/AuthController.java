package com.demo.marbgroup.controllers;

import static org.springframework.http.ResponseEntity.ok;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import com.demo.marbgroup.dtos.RegisterDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.demo.marbgroup.configs.JwtTokenProvider;
import com.demo.marbgroup.dtos.LoginDto;
import com.demo.marbgroup.enums.Status;
import com.demo.marbgroup.models.User;
import com.demo.marbgroup.services.UserService;

@RestController
public class AuthController {

	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	JwtTokenProvider jwtTokenProvider;

	@Autowired
	UserService userService;

	@RequestMapping(method = RequestMethod.GET, value = { "", "/" })
	public ResponseEntity<Map<Object, Object>> defaultRoute() {
		Map<Object, Object> model = new HashMap<>();
		model.put("signal-api","status:up");
		model.put("status", true);
		model.put("timeZone", TimeZone.getDefault());
		model.put("date", new Date().toString());
		model.put("message", "Server app is running fine :)");
		return ok(model);
	}

	@PostMapping("/login")
	public ResponseEntity<Map<Object, Object>> login(@RequestBody LoginDto loginDto) {
		logger.info("login : start");
		Map<Object, Object> model = new HashMap<>();
		User user = null;
		String jwt = null;
		try {
			String phone = loginDto.getPhone();
			String password = loginDto.getPassword();

			if (phone == null || password == null) {
				model.put("jwt", jwt);
				model.put("status", "FAIL");
				model.put("message", "Invalid or Bad credentials");
				return ok(model);
			}

			user = userService.findByPhoneOrEmail(phone);

			if (user == null) {
				model.put("jwt", jwt);
				model.put("status", "FAIL");
				model.put("message", "User with this phone is not available.");
				return ok(model);
			}
			if (user != null && user.getStatus().equals(Status.Inactive.toString())) {
				model.put("jwt", jwt);
				model.put("status", "FAIL");
				model.put("message", "Your Account is not active. Kindly contact support.");
				return ok(model);
			}
			if (user != null && user.getStatus().equals(Status.Active.toString())
					&& !userService.veifyPassword(password, user.getPassword())) {
				model.put("jwt", jwt);
				model.put("status", "FAIL");
				model.put("message", "Incorrect password.");
				return ok(model);
			}
			// authenticationManager.authenticate(new
			// UsernamePasswordAuthenticationToken(phone, loginDto.getPassword()));
			jwt = jwtTokenProvider.createToken(phone);
			model.put("jwt", jwt);
			model.put("status", "SUCCESS");
			model.put("message", "Access token generated successfully.");
			return ok(model);
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			model.put("jwt", jwt);
			model.put("status", "FAIL");
			model.put("message", "Something went wrong.");
			return ok(model);
		}
	}

	@PostMapping("/register")
	public ResponseEntity<Map<Object, Object>> register(@RequestBody User user) {
		String jwt = null;
		User savedUser = null;
		User userExists = userService.findByPhoneOrEmail(user.getPhone());
		Map<Object, Object> model = new HashMap<>();
		String message = "Account created.";
		if (userExists != null) {
			message = "User with phone: " + user.getPhone() + " already exists";
		} else {
			savedUser = userService.saveUser(user);
		}
		jwt = jwtTokenProvider.createToken(savedUser.getPhone());
		model.put("jwt", jwt);
		model.put("status", "SUCCESS");
		model.put("message", message);
		return ok(model);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/is-token-expired/{token}")
	public User isTokenExpired(@PathVariable String token) {
		User user = null;
		try {
			if (!jwtTokenProvider.isTokenExpired(token)) {
				String phoneOrEmail = jwtTokenProvider.getPhone(token);
				user = userService.findByPhoneOrEmail(phoneOrEmail);
				return user;
			}
			return user;
		} catch (Exception ex) {
			return user;
		}
	}

}
