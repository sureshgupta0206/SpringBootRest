package com.itsure.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.itsure.model.User;
import com.itsure.restclient.RestClient;
import com.itsure.service.TextToImageService;

@RestController
public class ServiceController {
	
	@Autowired
	private RestClient restClient;
	
	@Autowired
    private Environment env;
	
	@Autowired
	private TextToImageService txtImage;
	
	@RequestMapping(value = "/assignment", method = RequestMethod.GET)
	public ResponseEntity<User[]> getAssignment() {	
		ResponseEntity<User[]> response = restClient.fetchUsers();	
		User[] users = response.getBody();		
		for(int i=0; i<users.length;i++) {
		users[i].setTitle(new StringBuffer(users[i].getTitle()).reverse().toString());  
		users[i].setBody(new StringBuffer(users[i].getBody()).reverse().toString());  
		}		
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(users);
	}
	
	@RequestMapping(value = "/ingest", method = RequestMethod.POST)
	public ResponseEntity<String> postIngest(@RequestBody String text) throws IOException  {
		String path = txtImage.saveImage(text);
		String url = "{\"ImageUrl\" : \"" +  path + "\"}";		
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(url);
	}
	
	@RequestMapping(value = "/image/png", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
	public ResponseEntity<byte[]> getImage() throws IOException {
		File imgFile = new File(env.getProperty("filePath"));
		byte[] fileContent = Files.readAllBytes(imgFile.toPath());
		return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(fileContent);
	}
 
}

