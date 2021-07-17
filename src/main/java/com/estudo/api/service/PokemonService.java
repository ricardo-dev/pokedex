package com.estudo.api.service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.estudo.api.mail.Mailer;
import com.estudo.api.model.Pokemon;
import com.estudo.api.repository.PokemonRepository;
import com.estudo.api.service.exception.PokemonNotFoundException;

@Service
public class PokemonService {
	
	@Autowired
	private PokemonRepository pokemonRepository;
	
	private static final Logger logger = LoggerFactory.getLogger(PokemonService.class);
	
	@Autowired
	private Mailer mailer;
	
	//config/WebConfig
	//fixedDelay -> tempo fixo sempre quando o procedimento anterior e finalizado
	//cron -> segundo minuto hora dia mes semana
	@Scheduled(cron = "0 14 13 * * *")
	public void avisarSobreLancamentoVencido() {
		List<Pokemon> list = pokemonRepository.findAll();
		String email = "ricardoengdepc@gmail.com";
		List<String> destinatarios = new ArrayList();
		this.mailer.emailAsync(Arrays.asList(email), list);
		logger.info("Envio de e-mail de aviso concluido");
	}
	
	@Cacheable("cache_consulta_1")
	public List<Pokemon> getListPokemons(String name){
		return this.pokemonRepository.findByNameContaining(name);
	}
	
	@Cacheable("cache_consulta_1")
	public Page<Pokemon> getPagePokemons(String name, Pageable pageable){
		return this.pokemonRepository.findByNameContaining(name, pageable);
	}
	
	private Pokemon findPokemon(Long id) {
		Pokemon p = this.pokemonRepository.findById(id)
				.orElseThrow(()-> new EmptyResultDataAccessException(1));
		return p;
	}
	
	public void generateCSVResponse(HttpServletResponse response) throws IOException {
		
		String filename = "pokemons.csv";
		List<Pokemon> pokemons = this.pokemonRepository.findAll();
		
		try {
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			
			response.setContentType("text/csv");
			response.setHeader(HttpHeaders.CONTENT_DISPOSITION, 
					"attachment; filename=\""+filename+"\"");
			CSVPrinter csvPrinter = new CSVPrinter(response.getWriter(),
					CSVFormat.DEFAULT.withHeader("Nome", "Tipo", "Caracteristicas", "Data"));
			
			for(Pokemon p: pokemons) {
				csvPrinter.printRecord(Arrays.asList(p.getName(), p.getType().toString(), p.getCharacteristics(), this.formatarData(p.getCreationDate())));
			}
			
			csvPrinter.close();
			
		} finally {
			/*if(csvPrinter != null) {
				
			}*/
		}
	}
	
	public String formatarData(LocalDate date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String data = date.format(formatter);
		return data;
	}
	
	private void validPokemon(Long id) {
		Optional<Pokemon> p = this.pokemonRepository.findById(id);
		if(!p.isPresent()) {
			throw new PokemonNotFoundException();
		}
	}
}
