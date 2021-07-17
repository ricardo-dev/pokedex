package com.estudo.api.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.estudo.api.model.Access;

public class UsuarioSistema extends User{
	
	private static final long serialVersionUID = 1l;
	
	private Access usuario;
	
	public UsuarioSistema(Access usuario, Collection<? extends GrantedAuthority> authorities) {
		super(usuario.getEmail(), usuario.getPassword(), authorities);
		this.usuario = usuario;
	}

	public Access getUsuario() {
		return usuario;
	}
}
