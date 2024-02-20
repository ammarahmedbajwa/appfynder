package com.demo.marbgroup.models;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;

import com.demo.marbgroup.enums.Status;
import com.demo.marbgroup.util.Constants;

import lombok.Data;

@Data
@Entity
@Table(name = "roles")
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String uuid = UUID.randomUUID().toString();
	private String name;
	private boolean state = Constants.INSERTED;
	private String status = Status.Active.toString();
	private Date createdDate = new Date();
	private Date updatedDate = new Date();

}
