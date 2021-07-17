package com.estudo.api.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

//AppApplication.java - @EnableConfigurationProperties(AlgamoneyApiProperty.class)
@ConfigurationProperties("pokedex")
public class PokedexApiProperty {
	
	private final Seguranca seguranca = new Seguranca();
	
	private final Mail mail = new Mail();
	
	private String originPermitida = "http://localhost:4200";
	
	private final S3 s3 = new S3();
	
	private final Token token = new Token();

	public void setOriginPermitida(String originPermitida) {
		this.originPermitida = originPermitida;
	}

	public Seguranca getSeguranca() {
		return seguranca;
	}
	
	public Mail getMail() {
		return mail;
	}
	
	public S3 getS3() {
		return s3;
	}
	
	public String getOriginPermitida() {
		return originPermitida;
	}
	
	public Token getToken() {
		return token;
	}

	public static class Seguranca {
		private boolean enableHttps; //default false
		
		public boolean isEnableHttps() {
			return enableHttps;
		}

		public void setEnableHttps(boolean enableHttps) {
			this.enableHttps = enableHttps;
		}		
	}
	
	public static class S3 {
		private String accessKeyId;
		
		private String secretKeyId;
		
		private String bucket = "rss-algarick-arquivos";

		public String getAccessKeyId() {
			return accessKeyId;
		}

		public void setAccessKeyId(String accessKeyId) {
			this.accessKeyId = accessKeyId;
		}

		public String getSecretKeyId() {
			return secretKeyId;
		}

		public void setSecretKeyId(String secretKeyId) {
			this.secretKeyId = secretKeyId;
		}

		public String getBucket() {
			return bucket;
		}

		public void setBucket(String bucket) {
			this.bucket = bucket;
		}
	}
	
	public static class Mail {
		
		private String host;
		
		private Integer port;
		
		private String username;
		
		private String password;

		public String getHost() {
			return host;
		}

		public void setHost(String host) {
			this.host = host;
		}

		public Integer getPort() {
			return port;
		}

		public void setPort(Integer port) {
			this.port = port;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}
	}
	
	public static class Token {
		
		private String segredo;
		
		private int tokenDesktop;
		
		private int tokenRefreshDesktop;
		
		private int tokenMobile;

		public String getSegredo() {
			return segredo;
		}

		public void setSegredo(String segredo) {
			this.segredo = segredo;
		}

		public int getTokenDesktop() {
			return tokenDesktop;
		}

		public void setTokenDesktop(int tokenDesktop) {
			this.tokenDesktop = tokenDesktop;
		}

		public int getTokenRefreshDesktop() {
			return tokenRefreshDesktop;
		}

		public void setTokenRefreshDesktop(int tokenRefreshDesktop) {
			this.tokenRefreshDesktop = tokenRefreshDesktop;
		}

		public int getTokenMobile() {
			return tokenMobile;
		}

		public void setTokenMobile(int tokenMobile) {
			this.tokenMobile = tokenMobile;
		}
	}
}
