package com.example.tasktracker.api.service;

import java.util.List;

import com.example.tasktracker.api.entity.Task;

public interface TaskService {

	List<Task> findAllByUserId(Long userId);
	
	Task saveTaskWithTitle(String title, Long userId);
	
	void updateTitle(Long taskId, String title);
	
	void updateDescription(Long taskId, String description);
	
	void updateIsFinished(Long taskId, Boolean isFinished);

	void markAsDeleted(Long taskId);
	
	void delete(Long taskId);
}
