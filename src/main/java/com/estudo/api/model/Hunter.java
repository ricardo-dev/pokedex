package com.estudo.api.model;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.estudo.api.model.enuns.TypeEnum;

@Entity
@Table(name="hunter")
@PrimaryKeyJoinColumn(name="id_usuario")
public class Hunter extends Access {
	
	private boolean bloqued;
	
	private String city;

	public Hunter() {
		super();
	}

	public Hunter(@NotNull String name, @NotNull String email, @NotNull String password, TypeEnum role, boolean bloqued, String city) {
		super(name, email, password, role);
		this.bloqued = bloqued;
		this.city = city;
	}

	public boolean isBloqued() {
		return bloqued;
	}

	public void setBloqued(boolean bloqued) {
		this.bloqued = bloqued;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
}
