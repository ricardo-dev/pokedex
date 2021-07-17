package com.estudo.api.mail;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGridAPI;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

@Component
public class SendGridMailer {

    @Autowired
    private SendGridAPI sendGridAPI;
    
    private String emailFrom = "ricardopuc.computacao@gmail.com";
    
    @Autowired
	private TemplateEngine thymeleaf;
    
    @EventListener
    private void teste(ApplicationReadyEvent event) {
    	System.out.println("INICIO SG EMAIL");
    	this.sendNewPasswordSG("ricardopuc.computacao@gmail.com", "123456");
    	System.out.println("FIM SG EMAIL");
    }
    
   /* public void sendRealEstateContactSG(String destinatario) {
		String template = "mail/contactrealestate";
		Map<String, Object> variaveis = new HashMap<>();
		
		variaveis.put("immobileId", dto.getImmobileId());
		variaveis.put("name", dto.getName());
		variaveis.put("telephone", dto.getTelephone());
		variaveis.put("email", dto.getEmail());
		variaveis.put("mobile", dto.getMobile());
		variaveis.put("message", dto.getMessage());
		variaveis.put("typeContact", dto.getTypeContact());
		
		this.enviarEmailSG(
				destinatario, "Alguém quer saber mais sobre um imóvel", 
				template, variaveis);
	}*/
	
	public void sendNewPasswordSG(String email, String newPassword) {
		String template = "mail/newpassword";
		Map<String, Object> variaveis = new HashMap<>();
		variaveis.put("newpassword", newPassword);
		
		this.enviarEmailSG(email, 
				"Recuperação de senha", template, variaveis);
	}
	
	/*public void sendContactOwnerSG(String message, String email) {
		String template = "mail/sendcontactowner";
		Map<String, Object> variaveis = new HashMap<>();
		variaveis.put("message", message);
		
		this.enviarEmailSG(
				email, 
				"Novo contato", template, variaveis);
	}*/
	
	/*public void sendContactSG(String message, String email, String name) {
		String template = "mail/sendcontact";
		Map<String, Object> variaveis = new HashMap<>();
		variaveis.put("name", name);
		variaveis.put("email", email);
		variaveis.put("message", message);
		
		this.enviarEmailSG(
				this.emailFrom, 
				"Novo contato", template, variaveis);
	}*/
	
	/*public void sendNewAcountSG(String permission, String realState, String password, String email) {
		String template = "mail/newuser";
		Map<String, Object> variaveis = new HashMap<>();
		variaveis.put("permission", permission);
		variaveis.put("realState", realState);
		variaveis.put("password", password);
		
		this.enviarEmailSG( 
				email, 
				"Novo contato", template, variaveis);
	}*/
    
    public void enviarEmailSG(
			String destinatario, 
			String assunto, 
			String template,
			Map<String, Object> variaveis) {
    	Context context = new Context(new Locale("pt", "BR"));
		
		variaveis.entrySet().forEach( 
				e -> context.setVariable(e.getKey(), e.getValue()));
	
		String mensagem = thymeleaf.process(template, context);
		this.sendMail(assunto, destinatario, mensagem);
	}

    public void sendMail(String assunto, String emailTo, String conteudo) {
        Email from = new Email(this.emailFrom, "Teste SG");
        String subject = assunto;
        Email to = new Email(emailTo);
        //Content content = new Content("text/plain", "Test email with Spring");
        Content content = new Content("text/html", conteudo);
        Mail mail = new Mail(from, subject, to, content);
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sendGridAPI.api(request);
            System.out.println(response.getBody());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}