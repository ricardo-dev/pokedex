package com.estudo.api.mail;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.estudo.api.model.Pokemon;
import com.estudo.api.repository.PokemonRepository;

@Component
public class Mailer {
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private TemplateEngine thymeleaf;
	
	@Autowired
	private PokemonRepository pokemonRepository;
	
	/*
	@EventListener
	protected void teste(ApplicationReadyEvent event) {
		this.enviarEmail("teste.ricardo.desenvolvimento@gmail.com", 
				Arrays.asList("ricardoengdepc@gmail.com"), 
				"Teste", 
				"Olá</br> Teste OK!");
		System.out.println("...terminado envio de email");
	}*/
	
	
	/*
	@EventListener
	protected void testeThymeleaf(ApplicationReadyEvent event) {
		this.emailAsync();
		System.out.println("...terminado envio de email");
	}*/
	
	
	@Async
	public CompletableFuture<String> emailAsync(List<String> destinatarios, List<Pokemon> list){
		String template = "mail/pokemon";
		Map<String, Object> variaveis = new HashMap<>();
		variaveis.put("pokemons", list);
		
		this.enviarEmail("teste.ricardo.desenvolvimento@gmail.com", 
				destinatarios, 
				"Novos pokemons", 
				template, variaveis);
		return CompletableFuture.completedFuture("Ok");
	} 
	
	public void enviarEmail(String remetente,
			List<String> destinatarios, 
			String assunto, 
			String template,
			Map<String, Object> variaveis) {
		Context context = new Context(new Locale("pt", "BR"));
		
		variaveis.entrySet().forEach( 
				e -> context.setVariable(e.getKey(), e.getValue()));
	
		String mensagem = thymeleaf.process(template, context);
		this.enviarEmail(remetente, destinatarios, assunto, mensagem);
	}
	
	public void enviarEmail(String remetente,
			List<String> destinatarios, String assunto, String mensagem) {
		try {
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
			helper.setFrom(remetente);
			helper.setTo(destinatarios.toArray(new String[destinatarios.size()]));
			helper.setSubject(assunto);
			helper.setText(mensagem, true);
			
			mailSender.send(mimeMessage);
		} catch (MessagingException e) {
			throw new RuntimeException("Problemas com o envio de e-mail!", e);
		}
	}

}
