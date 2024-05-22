package com.example.tasktracker.service;

import java.util.List;

import com.example.tasktracker.entity.Task;
import com.example.tasktracker.entity.User;

public interface TaskService {

	List<Task> findAllByUser(User user);
	
	void save(Task task);
	
	void update(Task task);
	
	void delete(Task task);
}
