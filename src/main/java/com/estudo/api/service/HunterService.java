package com.estudo.api.service;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.TimeZone;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.estudo.api.dto.HunterBloqued;
import com.estudo.api.dto.HunterDto;
import com.estudo.api.dto.HunterPassword;
import com.estudo.api.model.Hunter;
import com.estudo.api.model.enuns.TypeEnum;
import com.estudo.api.repository.HunterRepository;
import com.estudo.api.service.exception.EmailJaCadastradoException;
import com.estudo.api.service.exception.HunterPasswordNotAcceptException;
import com.estudo.api.util.PasswordUtils;


@Service
public class HunterService {
	
	@Autowired
	private HunterRepository hunterRepository;
	
	public Hunter save(HunterDto hunterDto) {
		this.verifyEmail(hunterDto.getEmail());
		Hunter hunter = this.convertDtoToHunter(hunterDto);
		return this.hunterRepository.save(hunter);
	}

	public Hunter update(Long id, HunterDto hunterDto) {
		Hunter hunterSave = this.findById(id);
		Hunter hunter = this.convertDtoToHunter(hunterDto);
		if(!hunter.getEmail().equals(hunterSave.getEmail())) {
			this.verifyEmail(hunter.getEmail());
		}
		BeanUtils.copyProperties(hunter, hunterSave, "id", "role", "password");
		return this.hunterRepository.save(hunterSave);
	}
	
	public void updateBloqued(Long id, HunterBloqued dto) {
		Hunter hunterSave = this.findById(id);
		hunterSave.setBloqued(dto.isBloqued());
		this.hunterRepository.save(hunterSave);
	}
	
	public void updatePassword(Long id, HunterPassword dto) {
		Hunter hunterSave = this.findById(id);
		if(PasswordUtils.compare(dto.getPassword(), hunterSave.getPassword())) {
			hunterSave.setPassword(PasswordUtils.gerarBCrypt(dto.getNewPassword()));
			this.hunterRepository.save(hunterSave);
		} else {
			throw new HunterPasswordNotAcceptException();
		}
	}
	
	private void verifyEmail(String email) {
		Optional<Hunter> hunter = this.hunterRepository.findByEmail(email);
		if(hunter.isPresent()) {
			throw new EmailJaCadastradoException();
		}
		
	}
	
	private Hunter findById(Long id) {
		Hunter hunter = this.hunterRepository.findById(id)
				.orElseThrow(()->new EmptyResultDataAccessException(1));
		return hunter;
	}

	private Hunter convertDtoToHunter(HunterDto dto) {
			Hunter hunter = new Hunter(dto.getName(), dto.getEmail(), PasswordUtils.gerarBCrypt(dto.getPassword()),
					TypeEnum.ROLE_USER, dto.isBloqued(), dto.getCity());
			return hunter;
	}
}

/*
 * Utilizar Set para remover itens duplicados
 * public List<Camera> removeDuplicate(List<Camera> list){
		System.out.println("Antes de remover");
		list.forEach(l -> {
			System.out.println("## -> "+l.getId()+" principal "+l.getPrincipal());
		});
		Set<Camera> cam = new HashSet<>();
		list.forEach(l -> {
			cam.add(l);
		});
		List<Camera> camReturn = new ArrayList<>();
		System.out.println("Após remover");
		cam.forEach(c -> {
			System.out.println("### -> "+c.getId());
			camReturn.add(c);
		});
		Collections.sort(camReturn);
		return camReturn;
	}
 */

/*
 * Serializer serializer = new Persister();
		File diretory = new File(diretorioUpload+"\\"+socialReason);
		if(!diretory.exists()) {
			diretory.mkdirs();
		}
		File result = new File(diretory, "olx-xml.xml");
 */

/*
 * @PersistenceContext
	public EntityManager manager;
	
 * @Override
	public List<Camera> filterKM(Camera c1) {
		String sql = "SELECT *, (6371 * acos(cos(radians(?)) * cos(radians(latitude)) * cos(radians(?) - radians(longitude)) + sin(radians(?)) * sin(radians(latitude)) )) AS distance FROM camera_tb HAVING distance <= ?";
		Query query = manager.createNativeQuery(sql, Camera.class);
		query.setParameter(1, c1.getLatitude());
		query.setParameter(2, c1.getLongitude());
		query.setParameter(3, c1.getLatitude());
		query.setParameter(4, 100.00);
		List<Camera> cameras = (List<Camera>) query.getResultList();
		return cameras;
	}
 */

/*
 * List<?> list = repo.findAll();
 * Collections.reverse(list);
 * return list
 * */

/*
 * if(logger.isDebugEnabled()) {
		logger.debug("Preparando envio de e-mails sobre lancamentos");
	}
	
	logger.info("Sem lancamentos vencidos para aviso");
	
	logger.warn("Existem lançamentos vencidos, mas não tem destinatários");
 */

/*  Json -> object
 * 	Gson gson = new Gson();
	UserModelDTO dto = gson.fromJson(json, UserModelDTO.class);
 */

/* Json -> list of objects
 * DataPriceDto[] yearData = gson.fromJson(jsonYearId, DataPriceDto[].class);
 */

/* Object | List -> json
 * GsonBuilder builder = new GsonBuilder();
   Gson gsonReturn = builder.create();
   return gsonReturn.toJson(object).toString();
 */

/* @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
 * private LocalDate data;
 * 
 * converter localdate e localtime para string
 * 
 * public String formatarData(LocalDate date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String data = date.format(formatter);
		return data;
	}
	
	public String formatarHora(LocalTime time) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
		String data = time.format(formatter);
		return data;
	}
	
	private String teste() {
		Date date = new Date();
        SimpleDateFormat DateFor = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        String stringDate= DateFor.format(date);
        System.out.println(stringDate);
	}
	
	public void formatUTCTZ(Date d) {
		TimeZone.setDefault(TimeZone.getTimeZone("America/Sao_Paulo"));
		System.out.println(d.getTime() + "=" + d);
		TimeZone.setDefault(TimeZone.getTimeZone("America/Los_Angeles"));
		System.out.println(d.getTime() + "=" + d);
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Tokyo"));
		System.out.println(d.getTime() + "=" + d);
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		System.out.println(d.getTime() + "=" + d);
	}
	
	public static final long HOUR = 3600*1000; // in milli-seconds.
	pedido.setEntrega( new Date(pedido.getEntrega().getTime() + 3 * HOUR));
 */

/*
Alterando o retorno paginado
Page<Contact> contactPage = this.contactRepository.findAll(pageable);
final Page<ContactDto> contactDtoPage = contactPage.map(this::convertToContactDto);

private ContactDto convertToContactDto(final Contact contact) {
    final ContactDto contactDto = new ContactDto();
    //get values from contact entity and set them in contactDto
    //e.g. contactDto.setContactId(contact.getContactId());
    return contactDto;
}
*/

/*	Pegar usuario pelo token - OAUTH2
 *  String principal = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
 *  Optional<Usuario> logg = this.repository.findByEmail(principal);
*/

/*	Pegar usuario pelo token - basic JJWT
 *  JwtUser principal = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
 *  Optional<Usuario> logg = this.repository.findByEmail(principal.getUsername());
*/

/* List To Page
 * public Page<AllUserDto> toPage(List<AllUserDto> list, Pageable pageable) {
	if (pageable.getOffset() >= list.size()) {
		return Page.empty();
	}
	int startIndex = (int)pageable.getOffset();
	int endIndex = (int) ((pageable.getOffset() + pageable.getPageSize()) > list.size() ?
	list.size() :
	pageable.getOffset() + pageable.getPageSize());
	List<AllUserDto> subList = list.subList(startIndex, endIndex);
	return new PageImpl<AllUserDto>(subList, pageable, list.size());
}
 */

/* Ordenar paginacao
 * @RequestMapping(method=RequestMethod.GET)
@PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_PARCEIRO') and #oauth2.hasScope('read')")
public ResponseEntity<Page<Contact>> getAllPaginator(@RequestParam(required=false, defaultValue="") String name, @RequestParam("page") int page, @RequestParam("size") int size){
	Page<Contact> contactsPage = this.contactRepository.findByNameContaining(name, PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id")));
	return ResponseEntity.ok(contactsPage);
}*/

/* passar uma lista como query - 'In'
 * Page<Camera> findByNameContainingAndTagsTagIn(String name, List<String> tags,Pageable pageable);
 * Page<Camera> findDistinctByNameContainingAndTagsTagIn(String name, List<String> tags,Pageable pageable); - nao vem dados repetidos (lembrar de colocar hashCode e equals)
 */

/*
 * Transactional
 * main class @EnableTransactionManagement
 * camada de serviço @Transacional - operações salvar, atualizar, remover
 */

/*
 * public String obterNovaSenha() {
		Random random = new Random();
		StringBuilder saida = new StringBuilder();
		int tamanho = 6;
		String letras = "abcdefghijklmnopqrstuvwxyz0123456789";
		for(int i=0; i<tamanho; i++) {
			saida.append(letras.charAt(random.nextInt(letras.length())));
		}
		return saida.toString();
	}
 * */

/*
 * @Query("SELECT sum(u.hh) FROM HHUsers u WHERE u.users.id=:usersId")
	double countHh(@Param("usersId") Long users);
	*/
