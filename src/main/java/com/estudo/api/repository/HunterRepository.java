package com.estudo.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.estudo.api.model.Hunter;

@Repository
public interface HunterRepository extends JpaRepository<Hunter, Long> {
	Optional<Hunter> findByEmail(String email);
	
	List<Hunter> findByNameContainingAndBloquedIsTrue(String name);
	Page<Hunter> findByNameContainingAndBloquedIsTrue(String name, Pageable pageable);
	
	List<Hunter> findByNameContainingAndBloquedIsFalse(String name);
	Page<Hunter> findByNameContainingAndBloquedIsFalse(String name, Pageable pageable);
	
	//List<Lancamento> findByDataVencimentoLessThanEqualAndDataPagamentoIsNull(LocalDate data);
}
