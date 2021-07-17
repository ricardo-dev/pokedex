package com.estudo.api.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import com.estudo.api.model.enuns.TypeEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="acess_tb")
@Inheritance(strategy=InheritanceType.JOINED)
public class Access {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	private String name; 
	
	@NotNull
	@Email
	private String email;
	
	@NotNull
	@JsonIgnore
	private String password;
	
	@Enumerated(EnumType.STRING)
	private TypeEnum role;

	public Access() {}

	public Access(@NotNull String name, @NotNull String email, @NotNull String password, TypeEnum role) {
		this.name = name;
		this.email = email;
		this.password = password;
		this.role = role;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public TypeEnum getRole() {
		return role;
	}

	public void setRole(TypeEnum role) {
		this.role = role;
	}
}
