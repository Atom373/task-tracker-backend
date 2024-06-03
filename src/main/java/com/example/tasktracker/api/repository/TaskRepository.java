package com.example.tasktracker.api.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.tasktracker.api.entity.Task;
import com.example.tasktracker.security.entity.User;

public interface TaskRepository extends CrudRepository<Task, Long> {

	List<Task> findAllByUser(User user);
}
