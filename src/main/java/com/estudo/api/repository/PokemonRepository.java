package com.estudo.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.estudo.api.model.Pokemon;
import com.estudo.api.model.enuns.PokemonEnum;

@Repository
public interface PokemonRepository extends JpaRepository<Pokemon, Long>{
	
	List<Pokemon> findByNameContainingAndType(String name, PokemonEnum type);
	Page<Pokemon> findByNameContainingAndType(String name, PokemonEnum type, Pageable pageable);
	
	List<Pokemon> findByType(PokemonEnum type);
	List<Pokemon> findByNameContaining(String name);
	Page<Pokemon> findByNameContaining(String name, Pageable pageable);
}
