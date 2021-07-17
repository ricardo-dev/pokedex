package com.estudo.api.resources;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.estudo.api.dto.HunterBloqued;
import com.estudo.api.dto.HunterDto;
import com.estudo.api.dto.HunterPassword;
import com.estudo.api.event.RecursoCriadoEvent;
import com.estudo.api.exceptionhandler.PokedexApiExceptionHandler.Erro;
import com.estudo.api.model.Hunter;
import com.estudo.api.model.TesteDate;
import com.estudo.api.repository.HunterRepository;
import com.estudo.api.repository.TestDateRepository;
import com.estudo.api.service.ApiExternaService;
import com.estudo.api.service.HunterService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value="/hunter")
@EnableTransactionManagement // @Transactional na camada de services
public class HunterResource {
	
	@Autowired
	private HunterRepository hunterRepository;
	
	@Autowired
	private HunterService hunterService;
	
	@Autowired
	private ApiExternaService apiExternaService;
	
	@Autowired
    private ApplicationEventPublisher publisher;
	
	@RequestMapping(method=RequestMethod.GET, value="/externo")
	public ResponseEntity<?> getApiExterna(@RequestParam(name="hunter", required=false, defaultValue="8dcc6777-5628-4b24-a6d4-da47623f7519") String hunter){
		return ResponseEntity.ok(this.apiExternaService.teste(hunter));
	}
	
	@ApiOperation(value="Salva um novo caçador")
	@ApiResponses(value= {
			@ApiResponse(code=201, message="Caçador criado com sucesso", response=Hunter.class),
			@ApiResponse(code=400, message="Email já cadastrado na base de dados || Operação não permitida", response=Erro[].class),
			@ApiResponse(code=500, message="Erro inesperado")
	})
	@RequestMapping(method=RequestMethod.POST)
	// @PreAuthorize("hasAuthority('ROLE_CADASTRAR_PESSOA') and #oauth2.hasScope('write')")
    // @PreAuthorize("hasAnyAuthority('ROLE_CADASTRAR_PESSOA', 'ROLE_CADASTRAR_LANCAMENTO') and #oauth2.hasScope('write')")
	public ResponseEntity<Hunter> saveHunter(@RequestBody @Valid HunterDto hunterDto, HttpServletResponse response){
		Hunter hunter = this.hunterService.save(hunterDto);
		
		publisher.publishEvent(new RecursoCriadoEvent(this, response, hunter.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(hunter);
	}
	
	@RequestMapping(method=RequestMethod.PUT, value="/{id}")
	public ResponseEntity<Hunter> update(@PathVariable("id") Long id, @RequestBody @Valid HunterDto hunterDto){
		Hunter hunter = this.hunterService.update(id, hunterDto);
		return ResponseEntity.ok(hunter);
	}
	
	@RequestMapping(method=RequestMethod.PATCH, value="/{id}/bloqued")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void updateBloqued(@PathVariable("id") Long id, @RequestBody @Valid HunterBloqued dto) {
		this.hunterService.updateBloqued(id, dto);
	}
	
	@RequestMapping(method=RequestMethod.PATCH, value="/{id}/password")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void updatePassword(@PathVariable("id") Long id, @RequestBody @Valid HunterPassword dto) {
		this.hunterService.updatePassword(id, dto);
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/{id}")
	public ResponseEntity<Hunter> getHunter(@PathVariable("id") Long id){
		Optional<Hunter> hunter = this.hunterRepository.findById(id);
		return hunter.isPresent() ? ResponseEntity.ok(hunter.get()) : ResponseEntity.notFound().build();
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<Hunter>> findHunterNotBloqued(@RequestParam(required=false, defaultValue="") String name){
		return ResponseEntity.ok(this.hunterRepository.findByNameContainingAndBloquedIsFalse(name));
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/page") // ,params="parametro")
	public ResponseEntity<Page<Hunter>> findHunterNotBloqued(@RequestParam(required=false, defaultValue="") String name, Pageable pageable){
		return ResponseEntity.ok(this.hunterRepository.findByNameContainingAndBloquedIsFalse(name, pageable));
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/bloqued")
	public ResponseEntity<List<Hunter>> findHunterBloqued(@RequestParam(required=false, defaultValue="") String name){
		return ResponseEntity.ok(this.hunterRepository.findByNameContainingAndBloquedIsTrue(name));
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/bloqued/page")//, params="page")
	public ResponseEntity<Page<Hunter>> findHunterBloqued(@RequestParam(required=false, defaultValue="") String name, Pageable pageable){
		return ResponseEntity.ok(this.hunterRepository.findByNameContainingAndBloquedIsTrue(name, pageable));
	}
	
	@Autowired
	private TestDateRepository testeRepo;
	
	@RequestMapping(method=RequestMethod.POST, value="/teste-date")
	public void saveTestDate(@RequestBody @Valid TesteDate teste) {
		this.testeRepo.save(teste);
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/teste-date")
	public ResponseEntity<List<TesteDate>> getTestDate() {
		return ResponseEntity.ok(this.testeRepo.findAll());
	}
}
