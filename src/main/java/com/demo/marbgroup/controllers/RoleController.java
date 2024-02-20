package com.demo.marbgroup.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.demo.marbgroup.enums.RoleType;
import com.demo.marbgroup.models.Role;
import com.demo.marbgroup.repositories.RoleRepository;
import com.demo.marbgroup.util.Constants;

@RestController
public class RoleController {

	@Autowired
	RoleRepository roleRepository;	

	@RequestMapping(method = RequestMethod.GET, value = "/roles/{roleName}/{userId}")
	public ResponseEntity<List<Role>> findAll(@PathVariable String roleName, @PathVariable String userId) {
		List<Role> roles = null;

		if (roleName.equals(RoleType.Admin.toString())) {
			roles = roleRepository.findAll();
		} else if (roleName.equals(RoleType.Operator.toString())) {
			roles = roleRepository.findAllByState(Constants.INSERTED);
		} else {
			List<String> names = new ArrayList<String>();
			names.add(RoleType.Owner.toString());
			names.add(RoleType.Staff.toString());
			roles = roleRepository.findAllByStateAndNameIn(Constants.INSERTED, names);
		}

		return ResponseEntity.ok(roles);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/role")
	public ResponseEntity<Role> save(@RequestBody Role role) {
		role.setUuid(UUID.randomUUID().toString());
		return ResponseEntity.ok(roleRepository.save(role));
	}

	@RequestMapping(method = RequestMethod.GET, value = "/role/uuid/{uuid}")
	public ResponseEntity<Role> findById(@PathVariable String uuid) {
		Optional<Role> role = roleRepository.findByUuid(uuid);
		return ResponseEntity.ok(role.get());
	}

	@RequestMapping(method = RequestMethod.GET, value = "/role/{id}")
	public ResponseEntity<Role> findById(@PathVariable Long id) {
		Optional<Role> role = roleRepository.findById(id);
		return ResponseEntity.ok(role.get());
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/role/{id}")
	public ResponseEntity<Role> update(@PathVariable Long id, @RequestBody Role role) {
		Optional<Role> roleObject = roleRepository.findById(id);
		if (role.getName() != null) {
			roleObject.get().setName(role.getName());
		}
		roleObject.get().setState(role.isState());
		roleObject.get().setStatus(role.getStatus());
		roleObject.get().setUpdatedDate(role.getUpdatedDate());
		return ResponseEntity.ok(roleRepository.save(roleObject.get()));

	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/role/{id}")
	public void delete(@PathVariable Long id) {
		Optional<Role> role = roleRepository.findById(id);
		roleRepository.delete(role.get());
	}

	@RequestMapping(method = RequestMethod.GET, value = "/role-by-name/{name}")
	public ResponseEntity<Object> findRoleByName(@PathVariable String name) {
		Role role = null;
		if (roleRepository.findByName(name) != null) {
			role = roleRepository.findByName(name);
			return ResponseEntity.ok(role);
		}
		return ResponseEntity.ok(HttpStatus.NOT_FOUND);
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/role/state/{id}")
	public ResponseEntity<Role> updateState(@PathVariable Long id, @RequestBody Role role) {
		Optional<Role> roleObject = roleRepository.findById(id);
		roleObject.get().setState(false);
		roleObject.get().setUpdatedDate(role.getUpdatedDate());
		return ResponseEntity.ok(roleRepository.save(roleObject.get()));
	}
}
