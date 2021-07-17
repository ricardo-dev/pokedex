package com.estudo.api.cors;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.estudo.api.config.property.PokedexApiProperty;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsFilter implements Filter{
	
	@Autowired
	private PokedexApiProperty property;

	//https://stackoverflow.com/questions/38983949/java-access-control-allow-origin-multiple-origin-domains 
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		
		resp.setHeader("Access-Control-Allow-Origin", property.getOriginPermitida());
		resp.setHeader("Access-Control-Allow-Credentials", "true");
		
		if("OPTIONS".equals(req.getMethod()) && property.getOriginPermitida().equals(req.getHeader("Origin"))) {
			resp.setHeader("Access-Control-Allow-Methods", "POST, GET, DELETE, PUT, OPTIONS");
			resp.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type, Accept");
			resp.setHeader("Access-Control-Max-Age", "3600");
			
			resp.setStatus(HttpServletResponse.SC_OK);
		} else {
			chain.doFilter(request, response);
		}
	}
}

/*
 		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		
		if(property.getOriginPermitida().equals(req.getHeader("Origin"))) {
			resp.setHeader("Access-Control-Allow-Origin", property.getOriginPermitida());
			resp.setHeader("Access-Control-Allow-Credentials", "true");
			
			if("OPTIONS".equals(req.getMethod()) && property.getOriginPermitida().equals(req.getHeader("Origin"))) {
				resp.setHeader("Access-Control-Allow-Methods", "POST, GET, DELETE, PUT, OPTIONS");
				resp.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type, Accept");
				resp.setHeader("Access-Control-Max-Age", "3600");
				
				resp.setStatus(HttpServletResponse.SC_OK);
			} else {
				chain.doFilter(request, response);
			}
		} else if(property.getOriginDoisPermitida().equals(req.getHeader("Origin"))) {
			resp.setHeader("Access-Control-Allow-Origin", property.getOriginDoisPermitida());
			resp.setHeader("Access-Control-Allow-Credentials", "true");
			
			if("OPTIONS".equals(req.getMethod()) && property.getOriginDoisPermitida().equals(req.getHeader("Origin"))) {
				resp.setHeader("Access-Control-Allow-Methods", "POST, GET, DELETE, PUT, OPTIONS");
				resp.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type, Accept");
				resp.setHeader("Access-Control-Max-Age", "3600");
				
				resp.setStatus(HttpServletResponse.SC_OK);
			} else {
				chain.doFilter(request, response);
			}
		} else {
			chain.doFilter(request, response);
		}
 */
