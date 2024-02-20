package com.demo.marbgroup.models;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.demo.marbgroup.enums.Status;
import com.demo.marbgroup.util.Constants;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String uuid = UUID.randomUUID().toString();
	private String selectLabel;
	private String fullName;
	private String phone;
	private String email;
	private String password;
	private boolean agreed;
	private boolean state = Constants.INSERTED;
	private String status = Status.Active.toString();
	private Date createdDate = new Date();
	private Date updatedDate = new Date();

	@JsonIgnoreProperties({ "state", "status", "createdDate", "updatedDate" })
	@ManyToOne
	@JoinColumn(name = "role_id")
	private Role role;

}