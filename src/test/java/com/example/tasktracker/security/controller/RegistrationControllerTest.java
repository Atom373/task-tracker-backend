package com.example.tasktracker.security.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.tasktracker.security.dto.RegistrationRequestDto;
import com.example.tasktracker.security.service.RegistrationServiceFacade;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class RegistrationControllerTest {
	
	@Autowired
    private MockMvc mockMvc;
	
	@MockBean
	private RegistrationServiceFacade registrationService;
	
	@Autowired
    private ObjectMapper objectMapper;
	
	@Test
	public void register() throws Exception {
		RegistrationRequestDto dto = new RegistrationRequestDto();
		dto.setEmail("bob@gmail.com");
		dto.setPassword("1234");
		when(registrationService.register(dto)).thenReturn("jwt token");
		
		mockMvc.perform(post("/api/register")
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsBytes(dto)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.jwt").value("jwt token"));
		
		verify(registrationService, times(1)).register(dto);
	}

}
