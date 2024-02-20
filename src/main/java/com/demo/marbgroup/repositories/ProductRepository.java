package com.demo.marbgroup.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.marbgroup.models.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

	Product findByName(String name);

	List<Product> findAllByState(boolean state);

	Optional<Product> findByUuid(String uuid);

	List<Product> findAllByStateAndNameIn(boolean state, List<String> names);

	List<Product> findAllByStateAndUserId(boolean state, Long userId);
}
