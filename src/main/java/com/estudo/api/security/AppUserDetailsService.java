package com.estudo.api.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.estudo.api.model.Access;
import com.estudo.api.repository.AccessRepository;

@Service
public class AppUserDetailsService implements UserDetailsService{
	
	@Autowired
	private AccessRepository accessRpository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Access> usuarioOpt = accessRpository.findByEmail(username);
		Access usuario = usuarioOpt.orElseThrow(()-> new UsernameNotFoundException("Usuário e/ou Senha inexistentes ou incorreto"));
		// Usuario usuario = usuarioOpt.orElseThrow(()-> new InvalidGrantException("Usuario e/ou Senha inexistentes"));
		// if(algum campo invalido) throw new InternalAuthenticationServiceException("Usuario Bloqueado ou E-mail nao confirmado");
		return new UsuarioSistema(usuario, getPermissoes(usuario));
	}

	private Collection<? extends GrantedAuthority> getPermissoes(Access usuario) {
		Set<SimpleGrantedAuthority> authorities = new HashSet<>();
		authorities.add(new SimpleGrantedAuthority(usuario.getRole().toString().toUpperCase()));
		//usuario.getPermissoes().forEach(p -> authorities.add(new SimpleGrantedAuthority(p.getDescricao().toUpperCase())));
		return authorities;
	}
}
