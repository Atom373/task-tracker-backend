package com.example.tasktracker.domain.service.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Component;

import com.example.tasktracker.domain.entity.Task;
import com.example.tasktracker.domain.repository.TaskRepository;
import com.example.tasktracker.domain.service.TaskService;
import com.example.tasktracker.security.entity.User;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

	@PersistenceContext
    private EntityManager entityManager;
	
	private final TaskRepository taskRepo;
	
	@Override
	public List<Task> findAllByUserId(Long userId) {
		User userReference = entityManager.getReference(User.class, userId);
		return taskRepo.findAllByUserAndIsDeletedIsFalse(userReference);
	}
	
	@Override
	public Task saveTaskWithTitle(String title, Long userId) {
		User userReference = entityManager.getReference(User.class, userId);
		
		Task task = new Task();
		task.setTitle(title);
		task.setIsFinished(false);
		task.setUser(userReference);
		task.setIsDeleted(false);
		task.setFinishingDate(null);
		
		return taskRepo.save(task);
	}

	@Override
	public void updateTitle(Long taskId, String title) {
		Task task = taskRepo.findById(taskId).orElseThrow( () -> new RuntimeException() );
		
		task.setTitle(title);
		
		taskRepo.save(task);
	}

	@Override
	public void updateDescription(Long taskId, String description) {
		Task task = taskRepo.findById(taskId).orElseThrow( () -> new RuntimeException() );
		
		task.setDescription(description);
		
		taskRepo.save(task);

	}

	@Override
	public void updateIsFinished(Long taskId, Boolean isFinished) {
		Task task = taskRepo.findById(taskId).orElseThrow( () -> new RuntimeException() );
		
		task.setIsFinished(isFinished);
		task.setFinishingDate(LocalDate.now());
		
		taskRepo.save(task);
	}

	@Override
	public void markAsDeleted(Long taskId) {
		Task task = taskRepo.findById(taskId).orElseThrow( () -> new RuntimeException() );
		
		task.setIsDeleted(true);
		
		taskRepo.save(task);
	}
}
