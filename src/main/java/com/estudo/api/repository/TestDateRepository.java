package com.estudo.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.estudo.api.model.TesteDate;

@Repository
public interface TestDateRepository extends JpaRepository<TesteDate, Long> {

}
