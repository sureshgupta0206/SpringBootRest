package com.itsure.restclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.itsure.model.User;

@Component("restClient")
public class RestClient {
	
	@Autowired
    private RestTemplate restTemplate;

	public ResponseEntity<User[]> fetchUsers() {
		 
		return restTemplate.getForEntity("https://jsonplaceholder.typicode.com/posts", User[].class);
	}

}
