package com.example.tasktracker.domain.it;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.example.tasktracker.domain.entity.Task;
import com.example.tasktracker.domain.repository.TaskRepository;
import com.example.tasktracker.security.entity.User;
import com.example.tasktracker.security.repository.UserRepository;
import com.example.tasktracker.security.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class TaskControllerTest {
	
	@Autowired
    private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper; 
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private TaskRepository taskRepository;
	
    @Autowired
    private JwtUtil jwtUtil;
    
    private String jwt;
    private List<Task> tasks;
    
    @BeforeEach
    public void setUp() {
    	userRepository.deleteAll();
    	taskRepository.deleteAll();
    	
    	User bob = new User();
		bob.setEmail("bob@gmail.com");
		bob.setPassword("1234");
		
		userRepository.save(bob);
		
    	jwt = jwtUtil.generateToken(bob.getId());
    	
    	Task task1 = Task.builder()
				.title("first task")
				.user(bob)
				.isDeleted(false)
				.build();
		Task task2 = Task.builder()
				.title("second task")
				.user(bob)
				.isDeleted(false)
				.build();
		Task task3 = Task.builder()
				.title("third task")
				.user(bob)
				.isDeleted(true)
				.build();
		
		tasks = List.of(task1, task2, task3);
		
		taskRepository.saveAll(tasks);
    }
    
    @Test
    public void getAllUserTasks_shouldReturnListWithTwoTasks() throws Exception {
        mockMvc.perform(get("/api/task/all")
        			.header("Authorization", "Bearer " + jwt))
        		.andExpect(status().isOk())
        		.andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].title").value("first task"))
                .andExpect(jsonPath("$[1].title").value("second task"));
        
    }
    
    @Test
    public void saveTask() throws Exception {
    	// given
    	String newTaskTitle = "new task";
    	
    	// when
    	MvcResult result = mockMvc.perform(post("/api/task")
    			.header("Authorization", "Bearer " + jwt)
    			.param("title", newTaskTitle))
    		.andExpect(status().isCreated())
            .andReturn();
    	
    	String jsonResponse = result.getResponse().getContentAsString();
    	Long createdTaskId = objectMapper.readValue(jsonResponse, Long.class);
    	
    	// then
    	Task obtained = taskRepository.findById(createdTaskId).orElse(null);
    	
    	assertNotNull(obtained);
    	assertEquals(newTaskTitle, obtained.getTitle());
    }
    
    @Test
    public void updateTitle() throws Exception {
    	// given
    	String updatedTitle = "updated title";
    	Long taskToUpdateId = tasks.get(0).getId(); 
    	
    	// when
    	mockMvc.perform(patch("/api/task/title")
    			.header("Authorization", "Bearer " + jwt)
    			.param("taskId", taskToUpdateId.toString())
    			.param("title", updatedTitle));
    	
    	// then
    	Task obtained = taskRepository.findById(taskToUpdateId).orElse(null);
    	
    	assertNotNull(obtained);
    	assertEquals(updatedTitle, obtained.getTitle());
    }
    
    @Test
    public void updateDescription() throws Exception {
    	// given
    	String updatedDescription = "updated description";
    	Long taskToUpdateId = tasks.get(0).getId(); 
    	
    	// when
    	mockMvc.perform(patch("/api/task/description")
    			.header("Authorization", "Bearer " + jwt)
    			.param("taskId", taskToUpdateId.toString())
    			.param("description", updatedDescription));
    	
    	// then
    	Task obtained = taskRepository.findById(taskToUpdateId).orElse(null);
    	
    	assertNotNull(obtained);
    	assertEquals(updatedDescription, obtained.getDescription());
    }
    
    @Test
    public void updateState() throws Exception {
    	// given
    	Boolean updatedState = true;
    	Long taskToUpdateId = tasks.get(0).getId(); 
    	
    	//when
    	mockMvc.perform(patch("/api/task/is-finished")
    			.header("Authorization", "Bearer " + jwt)
    			.param("taskId", taskToUpdateId.toString())
    			.param("isFinished", updatedState.toString()));
    	
    	// then
    	Task obtained = taskRepository.findById(taskToUpdateId).orElse(null);
    	
    	assertNotNull(obtained);
    	assertNotNull(obtained.getFinishingDate());
    	assertEquals(updatedState, obtained.getIsFinished());
    	assertEquals(LocalDate.now(), obtained.getFinishingDate());
    }
    
    @Test
    public void deleteTask() throws Exception {
    	// given
    	Long taskToDeleteId = tasks.get(0).getId(); 
    	
    	// when
    	mockMvc.perform(delete("/api/task")
    			.header("Authorization", "Bearer " + jwt)
    			.param("taskId", taskToDeleteId.toString()));
    	
    	// then
    	Task obtained = taskRepository.findById(taskToDeleteId).orElse(null);
    	
    	assertNotNull(obtained);
    	assertEquals(true, obtained.getIsDeleted());
    }
}