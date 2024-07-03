package com.example.tasktracker.security.it;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.example.tasktracker.security.dto.RegistrationRequestDto;
import com.example.tasktracker.security.dto.RegistrationResponseDto;
import com.example.tasktracker.security.entity.User;
import com.example.tasktracker.security.repository.UserRepository;
import com.example.tasktracker.security.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class RegistrationControllerTest {
	
	@Autowired
    private MockMvc mockMvc;
	
	@Autowired
    private ObjectMapper objectMapper;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
    private JwtUtil jwtUtil;
	
	private String email = "bob@gmail.com";
	
	@BeforeEach
	public void setUp() {
		userRepository.deleteAll();
	}
	
	@Test
	public void registerValidUser_shouldSaveNewUser() throws Exception {
		// given
		RegistrationRequestDto requestDto = new RegistrationRequestDto();
		requestDto.setEmail(email);
		requestDto.setPassword("1234");
		
		// when
		MvcResult result = mockMvc.perform(post("/api/register")
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsBytes(requestDto)))
				.andExpect(status().isOk())
				.andReturn();
		
		// then
		String jsonResponse = result.getResponse().getContentAsString();
		RegistrationResponseDto responseDto = 
				objectMapper.readValue(jsonResponse, RegistrationResponseDto.class);
		String jwt = responseDto.getJwt();
		
		User user = userRepository.findByEmail(email);
		Long userId = jwtUtil.getIdFromToken(jwt);
		
		assertNotNull(user);
		assertEquals(userId, user.getId());
	}

	@Test
	public void registerUserWithDuplicateEmail_shouldReturnErrorMessage() throws Exception {
		// given
		User bob = new User();
		bob.setEmail(email);
		bob.setPassword("1234");
		
		userRepository.save(bob);
		
		RegistrationRequestDto requestDto = new RegistrationRequestDto();
		requestDto.setEmail(email);
		requestDto.setPassword("1234");
		
		// when
		mockMvc.perform(post("/api/register")
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsBytes(requestDto)))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$").value("Эта почта уже используется"));
	}
}
