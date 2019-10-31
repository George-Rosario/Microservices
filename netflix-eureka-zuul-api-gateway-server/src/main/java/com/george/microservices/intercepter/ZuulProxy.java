package com.george.microservices.intercepter;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.exception.ZuulException;

@Component
public class ZuulProxy extends ZuulFilter{
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() throws ZuulException {
		HttpServletRequest request = com.netflix.zuul.context.RequestContext.getCurrentContext().getRequest();
		logger.info("Reqeust -> {} Request URI -> {}",request,request.getRequestURI());
		return null;
	}

	@Override
	public String filterType() {
		return "pre"; // pre, post , error
	}

	@Override
	public int filterOrder() {
		return 0;
	}

}
