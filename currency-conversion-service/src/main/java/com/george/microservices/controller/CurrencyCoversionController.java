package com.george.microservices.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.management.RuntimeErrorException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.george.microservices.bean.CurrencyConversionBean;
import com.george.microservices.proxyservices.CurrencyExchangeProxy;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
public class CurrencyCoversionController {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	CurrencyExchangeProxy currencyExchangeProxy;
	@GetMapping("/currency-converter/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversionBean convertCurrency(@PathVariable String from,
												@PathVariable String to,
												@PathVariable BigDecimal quantity) {
		
		Map<String,String> urivariables = new HashMap<>();
		urivariables.put("from", from);
		urivariables.put("to", to);
		ResponseEntity<CurrencyConversionBean> responseEntity = new RestTemplate().getForEntity("http://localhost:8000/currency-exchange/from/{from}/to/{to}", CurrencyConversionBean.class,urivariables);
		CurrencyConversionBean response = responseEntity.getBody();
		
		return new CurrencyConversionBean(from, to, response.getId(), response.getConversionMultiple(), quantity, quantity.multiply(response.getConversionMultiple()), response.getPort());
		
	}
	
	@GetMapping("/currency-converter-feign/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversionBean convertCurrencyFeign(@PathVariable String from,
												@PathVariable String to,
												@PathVariable BigDecimal quantity) {
		
		CurrencyConversionBean response = currencyExchangeProxy.revtriveExchangeValue(from, to);
		logger.info("{}",response);
		return new CurrencyConversionBean(from, to, response.getId(), response.getConversionMultiple(), quantity, quantity.multiply(response.getConversionMultiple()), response.getPort());
		
	}
	
	//fault tolerance example
	
	@GetMapping("/fault-tolerance")
	@HystrixCommand(fallbackMethod = "faultHandled")
	public String returnThis() {
		throw new RuntimeErrorException(null);
	}
	
	public String faultHandled() {
		return "FaultIsHandled";
	}
	

}
