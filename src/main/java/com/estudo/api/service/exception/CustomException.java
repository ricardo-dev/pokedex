package com.estudo.api.service.exception;

import org.springframework.security.oauth2.common.exceptions.ClientAuthenticationException;

public class CustomException extends ClientAuthenticationException {

    /**
	 * exceção para tratamentos dentro do oauth2
	 */
	private static final long serialVersionUID = 1L;
	
	public CustomException(String msg, Throwable t) {
        super(msg, t);
    }

    public CustomException(String msg) {
        super(msg);
    }
    @Override
    public String getOAuth2ErrorCode() {
        return "my_custom_exception";
    }
}