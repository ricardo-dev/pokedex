package com.estudo.api;

import java.util.Optional;
import java.util.concurrent.Executor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.estudo.api.config.property.PokedexApiProperty;
import com.estudo.api.model.Hunter;
import com.estudo.api.model.Pokemon;
import com.estudo.api.model.enuns.PokemonEnum;
import com.estudo.api.model.enuns.TypeEnum;
import com.estudo.api.repository.HunterRepository;
import com.estudo.api.repository.PokemonRepository;
import com.estudo.api.util.PasswordUtils;

@SpringBootApplication
@EnableConfigurationProperties(PokedexApiProperty.class) //property
@EnableCaching //ehcache
@EnableAsync //assincrono
public class PokedexApplication extends SpringBootServletInitializer { // tomcat pom.xml

	public static void main(String[] args) {
		SpringApplication.run(PokedexApplication.class, args);
		//TODO: inserir teste automatizado com junit e mockito
	}
	
	@Bean // async
	public Executor asyncExecutor() {
		ThreadPoolTaskExecutor exec = new ThreadPoolTaskExecutor();
		exec.setCorePoolSize(2);
		exec.setMaxPoolSize(5);
		exec.setQueueCapacity(500);
		exec.initialize();
		return exec;
	}

	@Autowired
	private HunterRepository hunterRepository;
	
	@Autowired
	private PokemonRepository pokemonRepository;
	
	@EventListener
	private void teste(ApplicationReadyEvent event) {
		Optional<Hunter> h = this.hunterRepository.findByEmail("hunter@email.com");
		if(!h.isPresent()) {
			Hunter hunter = new Hunter("Aquiles", 
					"hunter@email.com", 
					PasswordUtils.gerarBCrypt("12345"), 
					TypeEnum.ROLE_ADMIN, false, "Senador Canedo");
			this.hunterRepository.save(hunter);
			this.savePokemons();
		}
	}
	
	private void savePokemons() {
		Pokemon p1 = new Pokemon();
		p1.setCharacteristics("loren ipsun ... ... ... ... ...");
		p1.setName("Pikachu");
		p1.setType(PokemonEnum.ELETRICO);
		
		Pokemon p2 = new Pokemon();
		p2.setCharacteristics("loren ipsun ... ... ... ... ...");
		p2.setName("Bubbasauro");
		p2.setType(PokemonEnum.FOLHA);
		
		Pokemon p3 = new Pokemon();
		p3.setCharacteristics("loren ipsun ... ... ... ... ...");
		p3.setName("Ivysauro");
		p3.setType(PokemonEnum.FOLHA);
		
		Pokemon p4 = new Pokemon();
		p4.setCharacteristics("loren ipsun ... ... ... ... ...");
		p4.setName("Vennosauro");
		p4.setType(PokemonEnum.FOLHA);
		
		Pokemon p5 = new Pokemon();
		p5.setCharacteristics("loren ipsun ... ... ... ... ...");
		p5.setName("Charmander");
		p5.setType(PokemonEnum.FOGO);
		
		Pokemon p6 = new Pokemon();
		p6.setCharacteristics("loren ipsun ... ... ... ... ...");
		p6.setName("Charmilion");
		p6.setType(PokemonEnum.FOGO);
		
		Pokemon p7 = new Pokemon();
		p7.setCharacteristics("loren ipsun ... ... ... ... ...");
		p7.setName("Charizard");
		p7.setType(PokemonEnum.FOGO);
		
		this.pokemonRepository.save(p1);
		this.pokemonRepository.save(p2);
		this.pokemonRepository.save(p3);
		this.pokemonRepository.save(p4);
		this.pokemonRepository.save(p5);
		this.pokemonRepository.save(p6);
		this.pokemonRepository.save(p7);
	}
	
}
