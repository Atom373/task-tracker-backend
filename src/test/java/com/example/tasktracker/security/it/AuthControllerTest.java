package com.example.tasktracker.security.it;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

import com.example.tasktracker.security.dto.AuthenticationRequestDto;
import com.example.tasktracker.security.dto.AuthenticationResponseDto;
import com.example.tasktracker.security.dto.RegistrationRequestDto;
import com.example.tasktracker.security.entity.User;
import com.example.tasktracker.security.repository.UserRepository;
import com.example.tasktracker.security.service.RegistrationServiceFacade;
import com.example.tasktracker.security.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AuthControllerTest {
	
	@Autowired
    private MockMvc mockMvc;
	
	@Autowired
    private ObjectMapper objectMapper;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RegistrationServiceFacade registartionService;
	
	@Autowired
    private JwtUtil jwtUtil;
	
	private User bob;
	
	@BeforeEach
	public void setUp() {
		userRepository.deleteAll();
		
		bob = new User();
		bob.setEmail("bob@gmail.com");
		bob.setPassword("1234");
		
		RegistrationRequestDto dto = new RegistrationRequestDto();
		dto.setEmail(bob.getEmail());
		dto.setPassword(bob.getPassword());
		
		registartionService.register(dto);
	}
	
	@Test
	public void generateToken() throws Exception {
		// given
		AuthenticationRequestDto requestDto = new AuthenticationRequestDto();
		requestDto.setEmail(bob.getEmail());
		requestDto.setPassword(bob.getPassword());
		
		// when
		MvcResult result = mockMvc.perform(post("/api/auth")
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsBytes(requestDto)))
				.andExpect(status().isOk())
				.andReturn();
		
		// then
		String jsonResponse = result.getResponse().getContentAsString();
		AuthenticationResponseDto responseDto = 
				objectMapper.readValue(jsonResponse, AuthenticationResponseDto.class);
		String jwt = responseDto.getJwt();
		
		User user = userRepository.findByEmail(bob.getEmail());
		Long userId = jwtUtil.getIdFromToken(jwt);
		
		assertNotNull(user);
		assertEquals(userId, user.getId());
	}
}
