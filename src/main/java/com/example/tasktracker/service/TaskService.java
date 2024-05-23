package com.example.tasktracker.service;

import java.util.List;

import com.example.tasktracker.entity.Task;
import com.example.tasktracker.entity.User;

public interface TaskService {

	List<Task> findAllByUser(User user);
	
	Task saveTaskWithTitle(String title, User user);
	
	void updateTitle(Long taskId, String title);
	
	void updateDescription(Long taskId, String description);
	
	void updateState(Long taskId, Boolean state);
	
	void delete(Long taskId);
}
