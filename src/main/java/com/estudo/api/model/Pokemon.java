package com.estudo.api.model;

import java.time.LocalDate;
import java.time.ZoneId;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import com.estudo.api.model.enuns.PokemonEnum;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name="pokemon_tb")
public class Pokemon {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
	@Enumerated(EnumType.STRING)
	private PokemonEnum type;
	
	private String characteristics;
	
	//@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "hh:mm") time
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") 
	private LocalDate creationDate;

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

	public PokemonEnum getType() {
		return type;
	}

	public void setType(PokemonEnum type) {
		this.type = type;
	}

	public String getCharacteristics() {
		return characteristics;
	}

	public void setCharacteristics(String characteristics) {
		this.characteristics = characteristics;
	}

	public LocalDate getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDate creationDate) {
		this.creationDate = creationDate;
	}
	
	@PrePersist
	private void prePersist() {
		ZoneId zone = ZoneId.of("America/Sao_Paulo");
		this.creationDate = LocalDate.now(zone);
	}
}
