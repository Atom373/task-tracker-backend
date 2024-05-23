package com.example.tasktracker.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.tasktracker.entity.Task;
import com.example.tasktracker.entity.User;
import com.example.tasktracker.service.TaskService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/task")
@AllArgsConstructor
public class TaskController {

	private TaskService taskService;
	
	@GetMapping("/all")
	public ResponseEntity<List<Task>> getAllUserTasks(@AuthenticationPrincipal User user) {
		List<Task> tasks = taskService.findAllByUser(user);
		return ResponseEntity.ok(tasks);
	}
	
	@PostMapping("/create")
	public ResponseEntity<Long> saveTask(@RequestParam String title,
										 @AuthenticationPrincipal User user) {
		Task createdTask = taskService.saveTaskWithTitle(title, user);
		return new ResponseEntity<>(createdTask.getId(), HttpStatus.CREATED);
	}
	
	@PostMapping("/update/title")
	public void updateTitle(@RequestParam Long taskId, 
							@RequestParam String title) {
		taskService.updateTitle(taskId, title);
	}
	
	@PostMapping("/update/description")
	public void updatedescription(@RequestParam Long taskId, 
								  @RequestParam String description) {
		taskService.updateDescription(taskId, description);
	}
	
	@PostMapping("/update/state")
	public void updateState(@RequestParam Long taskId,
							@RequestParam Boolean state) {
		taskService.updateState(taskId, state);
	}
	
	@PostMapping("/delete")
	public void deleteTask(@RequestParam Long taskId) {
		taskService.delete(taskId);
	}
	
}
