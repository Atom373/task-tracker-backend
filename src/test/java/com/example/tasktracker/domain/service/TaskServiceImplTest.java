package com.example.tasktracker.domain.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.tasktracker.domain.entity.Task;
import com.example.tasktracker.domain.repository.TaskRepository;
import com.example.tasktracker.domain.service.impl.TaskServiceImpl;
import com.example.tasktracker.security.entity.User;

import jakarta.persistence.EntityManager;

@ExtendWith(MockitoExtension.class)
public class TaskServiceImplTest {

	@Mock
    private EntityManager entityManager;
	
	@Mock
	private TaskRepository taskRepository;
	
	@InjectMocks
	private TaskServiceImpl taskService;
	
	private User user;
	private Task task;
	private List<Task> taskList;
	
	@BeforeEach
	public void setUp() {
		user = new User();
		user.setId(1);
		
		task = Task.builder()
				.id(1)
				.title("some title")
				.description("description")
				.user(user)
				.isFinished(false)
				.isDeleted(false)
				.build();
		
		taskList = List.of(task);
		
	}
	
	@Test
	public void findAllByUserId() {
		when(entityManager.getReference(User.class, user.getId())).thenReturn(user);
		when(taskRepository.findAllByUserAndIsDeletedIsFalse(user)).thenReturn(taskList);
		
		List<Task> obtained = taskService.findAllByUserId(user.getId());
		
		assertNotNull(obtained);
	}
	
	@Test
	public void saveTaskWithTitle() { 
		when(entityManager.getReference(User.class, user.getId())).thenReturn(user);
		when(taskRepository.save(any(Task.class))).thenReturn(task);
		
		Task obtained = taskService.saveTaskWithTitle("some task", user.getId());
		
		assertEquals(task, obtained);
	}
	
	@Test
	public void updateTitle() {
		when(taskRepository.save(any(Task.class))).thenReturn(task);
		when(taskRepository.findById(task.getId())).thenReturn(Optional.of(task));
		
		taskService.updateTitle(task.getId(), "new title");
		
		verify(taskRepository, times(1)).findById(task.getId());
		verify(taskRepository, times(1)).save(task);
	}
	
	@Test
	public void updateDescription() {
		when(taskRepository.save(any(Task.class))).thenReturn(task);
		when(taskRepository.findById(task.getId())).thenReturn(Optional.of(task));
		
		taskService.updateDescription(task.getId(), "new description");
		
		verify(taskRepository, times(1)).findById(task.getId());
		verify(taskRepository, times(1)).save(task);
	}
	
	@Test
	public void updateIsFinished() {
		when(taskRepository.save(any(Task.class))).thenReturn(task);
		when(taskRepository.findById(task.getId())).thenReturn(Optional.of(task));
		
		taskService.updateIsFinished(task.getId(), true);
		
		verify(taskRepository, times(1)).findById(task.getId());
		verify(taskRepository, times(1)).save(task);
	}
	
	@Test
	public void markAsDeleted() {
		when(taskRepository.save(any(Task.class))).thenReturn(task);
		when(taskRepository.findById(task.getId())).thenReturn(Optional.of(task));
		
		taskService.markAsDeleted(task.getId());
		
		verify(taskRepository, times(1)).findById(task.getId());
		verify(taskRepository, times(1)).save(task);
	}
}
