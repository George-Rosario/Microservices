package com.george.microservices.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.george.microservices.bean.ExchangeValue;


public interface CurrencyExchangeRepository extends JpaRepository<ExchangeValue, Long> {
	ExchangeValue findByFromAndTo(String from,String to);
}
