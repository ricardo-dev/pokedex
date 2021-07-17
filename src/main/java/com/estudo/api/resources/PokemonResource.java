package com.estudo.api.resources;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.estudo.api.model.Pokemon;
import com.estudo.api.service.PokemonService;

@RestController
@RequestMapping(value="/pokemon")
public class PokemonResource {
	
	@Autowired
	private PokemonService pokemonService;
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<Pokemon>> getList(@RequestParam(required=false, defaultValue="") String name){
		return ResponseEntity.ok(this.pokemonService.getListPokemons(name));
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/page")
	public ResponseEntity<Page<Pokemon>> getPage(@RequestParam(required=false, defaultValue="") String name,
			@RequestParam("size") int size, @RequestParam("page") int page){
		return ResponseEntity.ok(this.pokemonService.getPagePokemons(name, PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"))));
	}
	
	@RequestMapping(value="/relatorio", method=RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public void gerarCsv(HttpServletResponse response) throws IOException {
		this.pokemonService.generateCSVResponse(response);
	}

}
