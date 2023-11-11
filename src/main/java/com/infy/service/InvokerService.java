package com.infy.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.infy.model.Climate;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class InvokerService {

	@Autowired
	RestTemplate restTemplate;
	
//	@Value("${api.url}")
	String url = "http://localhost:8052/infy";
	
	@CircuitBreaker(name = "climateCircuitBreaker", fallbackMethod = "climateFallBack" )
	public String getClimate(String city) {
		
		Climate response = restTemplate.getForObject(url+"/climates/"+city, Climate.class);
		log.info(">>>>>>>>>>>>>>>>>>>>> Response from climate is : {}", response);
		return "Successfully fetched climate details for city: "+city;
		
	}
	
	public String climateFallBack(String city, Throwable t) {
		log.info("Inside fallback method..!!!");
		
		return "Climate service is temporarily unavailable..!!";
		
	}
}
