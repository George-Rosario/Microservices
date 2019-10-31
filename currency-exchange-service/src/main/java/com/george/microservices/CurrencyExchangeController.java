package com.george.microservices;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.george.microservices.bean.ExchangeValue;
import com.george.microservices.repository.CurrencyExchangeRepository;

@RestController
public class CurrencyExchangeController {
	@Autowired
	Environment env;
	
	@Autowired
	CurrencyExchangeRepository currencyExchangeRepository;
	
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	public ExchangeValue revtriveExchangeValue(@PathVariable String from, @PathVariable String to) {
		//ExchangeValue exchange = new ExchangeValue(1000l, "EU", "INR", BigDecimal.valueOf(90l));
		ExchangeValue exchange = currencyExchangeRepository.findByFromAndTo(from, to);
		exchange.setPort(Integer.parseInt(env.getProperty("local.server.port")));
		logger.info("{}",exchange);
		return exchange;
	}
}
