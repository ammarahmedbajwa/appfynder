package com.demo.marbgroup.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.marbgroup.models.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

	Role findByName(String name);

	List<Role> findAllByState(boolean state);

	Optional<Role> findByUuid(String uuid);

	List<Role> findAllByStateAndNameIn(boolean state, List<String> names);
}
