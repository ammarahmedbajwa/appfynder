package com.demo.marbgroup.models;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.demo.marbgroup.enums.Status;
import com.demo.marbgroup.util.Constants;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@Entity
@Table(name = "products")
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String uuid = UUID.randomUUID().toString();
	private String name;
	private Integer quantity;
	private Integer price;
	private boolean state = Constants.INSERTED;
	private String status = Status.Active.toString();
	private Date createdDate = new Date();
	private Date updatedDate = new Date();

	@JsonIgnoreProperties({"state", "status", "createdDate", "updatedDate" })
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "user_id")
	private User user;


}
