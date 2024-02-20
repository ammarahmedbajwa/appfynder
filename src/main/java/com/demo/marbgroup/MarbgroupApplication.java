package com.demo.marbgroup;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.demo.marbgroup.enums.RoleType;
import com.demo.marbgroup.enums.Status;
import com.demo.marbgroup.models.Role;
import com.demo.marbgroup.models.User;
import com.demo.marbgroup.repositories.RoleRepository;
import com.demo.marbgroup.repositories.UserRepository;
import com.demo.marbgroup.util.Constants;

@SpringBootApplication
public class MarbgroupApplication {

	@Autowired
	private PasswordEncoder bCryptPasswordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(MarbgroupApplication.class, args);
	}

	@Bean
	CommandLineRunner init(RoleRepository roleRepository, UserRepository userRepository) {

		return args -> {

			Role adminRole = roleRepository.findByName(RoleType.Admin.toString());
			if (adminRole == null) {
				Role newAdminRole = new Role();
				newAdminRole.setName(RoleType.Admin.toString());
				newAdminRole.setState(Constants.INSERTED);
				newAdminRole.setStatus(Status.Active.toString());
				newAdminRole.setCreatedDate(new Date());
				newAdminRole.setUpdatedDate(new Date());
				roleRepository.save(newAdminRole);
			}

			User adminUser = userRepository.findByEmailAndStateAndStatus(Constants.ADMIN_EMAIL,
					Constants.INSERTED, Status.Active.toString());
			if (adminUser == null) {
				User newAdminUser = new User();
				newAdminUser.setFullName("Mazhar");
				newAdminUser.setEmail(Constants.ADMIN_EMAIL);
				newAdminUser.setPhone("03092396502");
				newAdminUser.setSelectLabel(newAdminUser.getFullName() + " : "
						+ newAdminUser.getPhone() + " : " + newAdminUser.getEmail());
				newAdminUser.setPassword(bCryptPasswordEncoder.encode(Constants.DEFAULT_PASSWORD));
				newAdminUser.setAgreed(true);
				newAdminUser.setCreatedDate(new Date());
				newAdminUser.setUpdatedDate(new Date());
				newAdminUser.setRole(roleRepository.findByName(RoleType.Admin.toString()));
				newAdminUser.setState(Constants.INSERTED);
				newAdminUser.setStatus(Status.Active.toString());
				userRepository.save(newAdminUser);
			}

		};
	}
}
