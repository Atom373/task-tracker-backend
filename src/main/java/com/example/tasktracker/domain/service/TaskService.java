package com.example.tasktracker.domain.service;

import java.util.List;

import com.example.tasktracker.domain.entity.Task;
import com.example.tasktracker.security.entity.User;

public interface TaskService {

	List<Task> findAllByUserId(Long userId);
	
	Task saveTaskWithTitle(String title, Long userId);
	
	void updateTitle(Long taskId, String title);
	
	void updateDescription(Long taskId, String description);
	
	void updateIsFinished(Long taskId, Boolean isFinished);

	void markAsDeleted(Long taskId);
}
