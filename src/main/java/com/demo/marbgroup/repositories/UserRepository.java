package com.demo.marbgroup.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.marbgroup.models.Role;
import com.demo.marbgroup.models.User;

public interface UserRepository extends JpaRepository<User, Long> {

	List<User> findAllByRole(Role role);

	Optional<User> findByUuid(String uuid);

	User findByPhoneAndStateAndStatus(String phone, boolean state, String status);

	User findByEmailAndStateAndStatus(String email, boolean state, String status);

	User findByPhone(String phone);

	User findByEmail(String email);

	List<User> findAllByStateAndRole(boolean state, Role role);

	List<User> findAllByState(boolean state);

	List<User> findAllByStateAndStatus(boolean state, String status);

	List<User> findAllByStateAndRoleNameIn(boolean stateAvailable, List<String> roles);

}