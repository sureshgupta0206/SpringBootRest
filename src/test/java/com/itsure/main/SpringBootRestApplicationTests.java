package com.itsure.main;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest
@ComponentScan("com.itsure")
public class SpringBootRestApplicationTests {

	@Test
	public void contextLoads() {
	}

	@Bean
    public RestTemplate geRestTemplate() {
        return new RestTemplate();
    }
}
