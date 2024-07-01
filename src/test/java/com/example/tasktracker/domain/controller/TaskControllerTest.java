package com.example.tasktracker.domain.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.tasktracker.domain.entity.Task;
import com.example.tasktracker.domain.service.TaskService;
import com.example.tasktracker.security.util.JwtUtil;

@SpringBootTest
@AutoConfigureMockMvc
public class TaskControllerTest {
	
	@Autowired
    private MockMvc mockMvc;
	
    @MockBean
    private TaskService taskService;

    @Autowired
    private JwtUtil jwtUtil;
    
    private String jwt;
    private Task task;
    
    @BeforeEach
    public void setUp() {
    	jwt = jwtUtil.generateToken(1L);
    	task = Task.builder()
    			.id(1L)
    			.title("Task title")
    			.description("task description")
    			.isFinished(false)
    			.isDeleted(false)
    			.build();
    }
    
    @Test
    public void getAllUserTasks() throws Exception {
        List<Task> emptyList = new ArrayList<>();
        when(taskService.findAllByUserId(1L)).thenReturn(emptyList);

        mockMvc.perform(get("/api/task/all")
        			.header("Authorization", "Bearer " + jwt))
        		.andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
        
        verify(taskService, times(1)).findAllByUserId(1L);
    }
    
    @Test
    public void saveTask() throws Exception {
    	when(taskService.saveTaskWithTitle("Task title", 1L)).thenReturn(task);
    	
    	mockMvc.perform(post("/api/task/create")
    			.header("Authorization", "Bearer " + jwt)
    			.param("title", task.getTitle()))
    		.andExpect(status().isCreated())
            .andExpect(jsonPath("$").value(task.getId()));
    
    	verify(taskService, times(1)).saveTaskWithTitle("Task title", 1L);
    }
    
    @Test
    public void updateTitle() throws Exception {
    	doNothing().when(taskService).updateTitle(task.getId(), "new title");
    	
    	mockMvc.perform(post("/api/task/update/title")
    			.header("Authorization", "Bearer " + jwt)
    			.param("taskId", task.getId()+"")
    			.param("title", "new title"));
    
    	verify(taskService, times(1)).updateTitle(task.getId(), "new title");
    }
    
    @Test
    public void updateDescription() throws Exception {
    	doNothing().when(taskService).updateDescription(task.getId(), "new description");
    	
    	mockMvc.perform(post("/api/task/update/description")
    			.header("Authorization", "Bearer " + jwt)
    			.param("taskId", task.getId()+"")
    			.param("description", "new description"));
    
    	verify(taskService, times(1)).updateDescription(task.getId(), "new description");
    }
    
    @Test
    public void updateState() throws Exception {
    	doNothing().when(taskService).updateIsFinished(task.getId(), true);
    	
    	mockMvc.perform(post("/api/task/update/isfinished")
    			.header("Authorization", "Bearer " + jwt)
    			.param("taskId", task.getId()+"")
    			.param("isFinished", true+""));
    
    	verify(taskService, times(1)).updateIsFinished(task.getId(), true);
    }
    
    @Test
    public void deleteTask() throws Exception {
    	doNothing().when(taskService).markAsDeleted(task.getId());
    	
    	mockMvc.perform(post("/api/task/delete")
    			.header("Authorization", "Bearer " + jwt)
    			.param("taskId", task.getId()+""));
    
    	verify(taskService, times(1)).markAsDeleted(task.getId());
    }
}


