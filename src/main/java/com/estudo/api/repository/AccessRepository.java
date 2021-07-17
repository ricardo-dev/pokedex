package com.estudo.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.estudo.api.model.Access;

@Repository
public interface AccessRepository extends JpaRepository<Access, Long> {
	Optional<Access> findByEmail(String email);
}
