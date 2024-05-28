package com.example.tasktracker.service.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.tasktracker.entity.Task;
import com.example.tasktracker.entity.User;
import com.example.tasktracker.repository.TaskRepository;
import com.example.tasktracker.repository.UserRepository;
import com.example.tasktracker.service.TaskService;
import com.example.tasktracker.service.UserService;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class TaskServiceImpl implements TaskService {

	private TaskRepository taskRepo;
	private UserService userService;
	
	@Override
	public List<Task> findAllByUserId(Long userId) {
		User user = userService.findById(userId);
		return taskRepo.findAllByUser(user);
	}

	@Override
	public Task saveTaskWithTitle(String title, Long userId) {
		User user = userService.findById(userId);
		
		Task task = new Task();
		task.setTitle(title);
		task.setIsFinished(false);
		task.setUser(user);
		
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
		
		taskRepo.save(task);
	}

	@Override
	public void delete(Long taskId) {
		taskRepo.deleteById(taskId);
	}

}
