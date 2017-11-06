package com.itsure.main;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.itsure.controller.ServiceController;
import com.itsure.model.User;
import com.itsure.restclient.RestClient;
import com.itsure.service.TextToImageService;
import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@WebMvcTest(value = ServiceController.class, secure = false)
public class ServiceControllerUnitTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private RestClient restClient;
		
	@MockBean
	private TextToImageService txtImage;
	
	@Test
	public void getAssignment() throws Exception {
		User user[] = {
				new User("1","9","nesciunt iure omnis dolorem tempora et accusantium","consectetur animi nesciunt iure dolore\nenim"),
				new User("1","10","optio molestias id quia eum","quo et expedita modi cum officia vel magni\ndoloribus")
		       };		
		ResponseEntity<User[]> entity = new ResponseEntity<User[]>(user,HttpStatus.ACCEPTED);
		Mockito.when(restClient.fetchUsers()).thenReturn(entity);		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				"/assignment").accept(
				MediaType.APPLICATION_JSON);		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		String expected = "[{\"userId\":\"1\",\"id\":\"9\",\"title\":\"muitnasucca te aropmet merolod sinmo erui tnuicsen\",\"body\":\"mine\\nerolod erui tnuicsen imina rutetcesnoc\"},{\"userId\":\"1\",\"id\":\"10\",\"title\":\"mue aiuq di saitselom oitpo\",\"body\":\"subirolod\\ningam lev aiciffo muc idom atidepxe te ouq\"}]";
		assertEquals(expected, result.getResponse().getContentAsString());
	}
	
	@Test
	public void getIngest() throws Exception {		
		String expected = "{\"ImageUrl\" : \"" +  "http://localhost:1032/image/png" + "\"}";		
		Mockito.when(txtImage.saveImage(Mockito.anyString())).thenReturn("http://localhost:1032/image/png");		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.
				post("/ingest").
				content("Plain text to write in image file.").
				accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		assertEquals(expected, result.getResponse().getContentAsString());
	}
}
