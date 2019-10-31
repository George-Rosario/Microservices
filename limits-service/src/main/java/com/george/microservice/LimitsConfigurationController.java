package com.george.microservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.george.microservice.bean.LimitsConfiguration;

@RestController
public class LimitsConfigurationController {
	
	@Autowired
	Configuration config;
	
	@GetMapping("/limits")
	public LimitsConfiguration retriveLimitsConfiguration() {
		return new LimitsConfiguration(config.getMaximum(), config.getMinimum());
		
	}

}
