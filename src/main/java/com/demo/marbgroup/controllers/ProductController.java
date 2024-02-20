package com.demo.marbgroup.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.demo.marbgroup.models.Product;
import com.demo.marbgroup.repositories.ProductRepository;
import com.demo.marbgroup.util.Constants;

@RestController
public class ProductController {

	@Autowired
	ProductRepository productRepository;

	@RequestMapping(method = RequestMethod.POST, value = "/product")
	public ResponseEntity<Product> save(@RequestBody Product product) {
		product.setUuid(UUID.randomUUID().toString());
		return ResponseEntity.ok(productRepository.save(product));
	}

	@RequestMapping(method = RequestMethod.GET, value = "/product/uuid/{uuid}")
	public ResponseEntity<Product> findById(@PathVariable String uuid) {
		Optional<Product> product = productRepository.findByUuid(uuid);
		return ResponseEntity.ok(product.get());
	}

	@RequestMapping(method = RequestMethod.GET, value = "/product/{id}")
	public ResponseEntity<Product> findById(@PathVariable Long id) {
		Optional<Product> product = productRepository.findById(id);
		return ResponseEntity.ok(product.get());
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/product/{id}")
	public ResponseEntity<Product> update(@PathVariable Long id, @RequestBody Product product) {
		Optional<Product> productObject = productRepository.findById(id);
		if (product.getName() != null) {
			productObject.get().setName(product.getName());
		}
		if (product.getUser() != null) {
			productObject.get().setUser(product.getUser());
		}
		if (product.getQuantity() != null) {
			productObject.get().setQuantity(product.getQuantity());
		}
		if (product.getPrice() != null) {
			productObject.get().setPrice(product.getPrice());
		}
		productObject.get().setState(product.isState());
		productObject.get().setStatus(product.getStatus());
		productObject.get().setUpdatedDate(product.getUpdatedDate());
		return ResponseEntity.ok(productRepository.save(productObject.get()));

	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/product/{id}")
	public ResponseEntity<Map<String, Object>> delete(@PathVariable Long id) {
		Map<String,Object> status = new HashMap<String,Object>();
		status.put("deleted", true);
		try {			
			productRepository.deleteById(id);
		} catch(Exception ex) {
			status.put("deleted", false);
		}
		return ResponseEntity.ok(status);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/product-by-name/{name}")
	public ResponseEntity<Object> findProductByName(@PathVariable String name) {
		Product product = null;
		if (productRepository.findByName(name) != null) {
			product = productRepository.findByName(name);
			return ResponseEntity.ok(product);
		}
		return ResponseEntity.ok(HttpStatus.NOT_FOUND);
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/product/state/{id}")
	public ResponseEntity<Product> updateState(@PathVariable Long id, @RequestBody Product product) {
		Optional<Product> productbject = productRepository.findById(id);
		productbject.get().setState(false);
		productbject.get().setUpdatedDate(product.getUpdatedDate());
		return ResponseEntity.ok(productRepository.save(productbject.get()));
	}

	@RequestMapping(method = RequestMethod.GET, value = "/products/{userId}")
	public ResponseEntity<List<Product>> getProducts(@PathVariable Long userId) {
		List<Product> products = productRepository.findAllByStateAndUserId(Constants.INSERTED, userId);
		return ResponseEntity.ok(products);
	}
}
