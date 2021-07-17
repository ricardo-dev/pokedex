package com.estudo.api.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.estudo.api.model.Contato;
import com.estudo.api.model.Pessoa;
import com.estudo.api.service.exception.ApiExternaNotFoundException;
import com.google.gson.Gson;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

@Service
public class ApiExternaService {
	
	
	private static final Logger LOG = LoggerFactory.getLogger(ApiExternaService.class);
	
	public String teste(String tags) {
		String response = this.getTwitters(tags);
		LOG.info("####### "+response);
		return response;
	}
	
	/*@EventListener
	private void teste(ApplicationReadyEvent event) {
		Contato contato = new Contato();
		contato.setEmail("pokedex@email.com");
		contato.setNome("Email");
		contato.setTelefone("32033136");
		
		
		Pessoa pessoa = new Pessoa();
		pessoa.setNome("Teste pokedex");
		pessoa.setAtivo(true);
		pessoa.setEndereco(null);
		pessoa.setContatos(Arrays.asList(contato));
		this.setAlgaworks(pessoa);
		LOG.info("Fim de post! - inicio GET");
		this.getAlgaworks();
		LOG.info("Fim do GET");
		
	}*/
	
	private String setAlgaworks(Pessoa pessoa) {
		// TODO retornar response da request e retornar response code ou resposta
		try {
			Gson gson = new Gson();
			String pessoaString = gson.toJson(pessoa);
			OkHttpClient okHttpClient = new OkHttpClient();
			MediaType mediaType = MediaType.parse("application/json");
			RequestBody requestBody = RequestBody.create(mediaType, pessoaString);
			Request request = new Request.Builder()
					.url("http://localhost:8080/pessoas")
					.post(requestBody).addHeader("Content-Type", "application/json")
					.addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJyaWNhcmRvZW5nZGVwY0BnbWFpbC5jb20iLCJzY29wZSI6WyJyZWFkIiwid3JpdGUiXSwibm9tZSI6IkFkbWluaXN0cmFkb3IiLCJpZCI6MSwiZXhwIjoxNjExMjQ5OTQ5LCJhdXRob3JpdGllcyI6WyJST0xFX0NBREFTVFJBUl9DQVRFR09SSUEiLCJST0xFX1BFU1FVSVNBUl9QRVNTT0EiLCJST0xFX1JFTU9WRVJfUEVTU09BIiwiUk9MRV9DQURBU1RSQVJfTEFOQ0FNRU5UTyIsIlJPTEVfUEVTUVVJU0FSX0xBTkNBTUVOVE8iLCJST0xFX1JFTU9WRVJfTEFOQ0FNRU5UTyIsIlJPTEVfQ0FEQVNUUkFSX1BFU1NPQSIsIlJPTEVfUEVTUVVJU0FSX0NBVEVHT1JJQSJdLCJqdGkiOiI2ZDgzMjczOC1kZGY5LTQyMzctODk2Yi1lNGViNWUyYTQwNTQiLCJjbGllbnRfaWQiOiJhbmd1bGFyIn0.2voCWrs7yipr31OfPYyhh6WJPY7-u6A9BAunAeJMSmw")
					.addHeader("cache-control", "no-cache").build();
			Response response = okHttpClient.newCall(request).execute();
			String body = response.body().string();
			System.out.println("Body: "+body);
			if (response.isSuccessful()) {
				LOG.info("Request is succcessful");
				LOG.info("Status:" + response.code());
			} else {
				LOG.info("Erro ao realizar o request");
				LOG.info("Status: " + response.code());
				LOG.info(response.message());
			}
			response.body().close();
			return body;
		} catch (IOException e) {
			LOG.info("Erro ao realziar o request");
			e.printStackTrace();
			throw new ApiExternaNotFoundException();
			//return null;
		}
	}
	
	private String getAlgaworks() {
		// TODO retornar response da request e retornar response code ou resposta
		try {
			OkHttpClient okHttpClient = new OkHttpClient();
			MediaType mediaType = MediaType.parse("application/json");
			Request request = new Request.Builder()
					.url("http://localhost:8080/pessoas")
					.get().addHeader("Content-Type", "application/json")
					.addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJyaWNhcmRvZW5nZGVwY0BnbWFpbC5jb20iLCJzY29wZSI6WyJyZWFkIiwid3JpdGUiXSwibm9tZSI6IkFkbWluaXN0cmFkb3IiLCJpZCI6MSwiZXhwIjoxNjExMjQ5OTQ5LCJhdXRob3JpdGllcyI6WyJST0xFX0NBREFTVFJBUl9DQVRFR09SSUEiLCJST0xFX1BFU1FVSVNBUl9QRVNTT0EiLCJST0xFX1JFTU9WRVJfUEVTU09BIiwiUk9MRV9DQURBU1RSQVJfTEFOQ0FNRU5UTyIsIlJPTEVfUEVTUVVJU0FSX0xBTkNBTUVOVE8iLCJST0xFX1JFTU9WRVJfTEFOQ0FNRU5UTyIsIlJPTEVfQ0FEQVNUUkFSX1BFU1NPQSIsIlJPTEVfUEVTUVVJU0FSX0NBVEVHT1JJQSJdLCJqdGkiOiI2ZDgzMjczOC1kZGY5LTQyMzctODk2Yi1lNGViNWUyYTQwNTQiLCJjbGllbnRfaWQiOiJhbmd1bGFyIn0.2voCWrs7yipr31OfPYyhh6WJPY7-u6A9BAunAeJMSmw")
					.addHeader("cache-control", "no-cache").build();
			Response response = okHttpClient.newCall(request).execute();
			String body = response.body().string();
			System.out.println("Body: "+body);
			if (response.isSuccessful()) {
				LOG.info("Request is succcessful");
				LOG.info("Status:" + response.code());
			} else {
				LOG.info("Erro ao realizar o request");
				LOG.info("Status: " + response.code());
				LOG.info(response.message());
			}
			response.body().close();
			return body;
		} catch (IOException e) {
			LOG.info("Erro ao realziar o request");
			e.printStackTrace();
			throw new ApiExternaNotFoundException();
			//return null;
		}
	}
	
	private String getTwitters(String tag) {
		// TODO retornar response da request e retornar response code ou resposta
		try {
			OkHttpClient okHttpClient = new OkHttpClient();
			MediaType mediaType = MediaType.parse("application/json");
			RequestBody requestBody = RequestBody.create(mediaType, "{}");
			Request request = new Request.Builder()
					.url("https://run.mocky.io/v3/"+tag)
					.post(requestBody).addHeader("Content-Type", "application/json")
					.addHeader("Authorization", "Bearer AAAAAAAAAAAAAAAAAAAAAA8CJAEAAAAA8lpG9UXV0vY135GdMU94KIim0sA%3DY38Bd6qdt1CJ4knr43NcWnCmQc3MdDjrfDoNFFFbqoPN2PDYmt")
					.addHeader("cache-control", "no-cache").build();
			Response response = okHttpClient.newCall(request).execute();
			String body = response.body().string();
			System.out.println("Body: "+body);
			if (response.isSuccessful()) {
				LOG.info("Request is succcessful");
				LOG.info("Status:" + response.code());
			} else {
				LOG.info("Erro ao realizar o request");
				LOG.info("Status: " + response.code());
				LOG.info(response.message());
			}
			response.body().close();
			return body;
		} catch (IOException e) {
			LOG.info("Erro ao realziar o request");
			e.printStackTrace();
			throw new ApiExternaNotFoundException();
			//return null;
		}
	}

}

/*
 *public List<BairroModel> getBairro(String uf, String cidadeNome){
		RestTemplate template = new RestTemplate();
		
		//.scheme("https")
		//.host("pepin.app:8081")
		
		UriComponents uri = UriComponentsBuilder.newInstance()
				.scheme(this.property.getCep().getHttp())
				.host(this.property.getCep().getHost())
				.path("api/bairro/"+uf+"/"+cidadeNome)
				//.query("")
				.build();
		ResponseEntity<BairroModel[]> bairros = template
				.getForEntity(uri.toUriString(), BairroModel[].class);
		if(!bairros.getStatusCode().is2xxSuccessful()) {
			throw new ApiExternaNotFoundException();
		}
		List<BairroModel> l = Arrays.asList(bairros.getBody());
		return l;
	}
	
	public List<LocalidadeModel> getLocalidade(String uf, String nomeCidade){
		RestTemplate template = new RestTemplate();
		
		UriComponents uri = UriComponentsBuilder.newInstance()
				.scheme(this.property.getCep().getHttp())
				.host(this.property.getCep().getHost())
				.path("api/localidade/"+uf+"/"+nomeCidade)
				//.query("")
				.build();
		ResponseEntity<LocalidadeModel[]> localidades = template
				.getForEntity(uri.toUriString(), LocalidadeModel[].class);
		if(!localidades.getStatusCode().is2xxSuccessful()) {
			throw new ApiExternaNotFoundException();
		}
		List<LocalidadeModel> l = Arrays.asList(localidades.getBody());
		return l;
	}
	
	public List<LogradouroModel> getLogradouro(String uf, String nomeCidade, String nomeRua){
		RestTemplate template = new RestTemplate();
		
		UriComponents uri = UriComponentsBuilder.newInstance()
				.scheme(this.property.getCep().getHttp())
				.host(this.property.getCep().getHost())
				.path("api/logradoro/"+uf+"/"+nomeCidade+"/"+nomeRua)
				//.query("")
				.build();
		ResponseEntity<LogradouroModel[]> logradoros = template
				.getForEntity(uri.toUriString(), LogradouroModel[].class);
		if(!logradoros.getStatusCode().is2xxSuccessful()) {
			throw new ApiExternaNotFoundException();
		}
		List<LogradouroModel> l = Arrays.asList(logradoros.getBody());
		return l;
	}
	
	public LogradouroModel getLogradouroCep(String cep){
		RestTemplate template = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		
		headers.add("Authorization", "Bearer jashnsakjaksjnakjaksjnksjkasjaksn");
		HttpEntity<HttpHeaders> request = new HttpEntity<HttpHeaders>(headers);
		
		UriComponents uri = UriComponentsBuilder.newInstance()
				.scheme(this.property.getCep().getHttp())
				.host(this.property.getCep().getHost())
				.path("api/logradoro/"+cep)
				//.query("")
				.build();
		ResponseEntity<LogradouroModel> logradoro = template
				.exchange(uri.toUriString(), HttpMethod.GET, request, LogradouroModel.class);
		if(!logradoro.getStatusCode().is2xxSuccessful()) {
			throw new ApiExternaNotFoundException();
		}
		return logradoro.getBody();
	}
 
 * 
 */
