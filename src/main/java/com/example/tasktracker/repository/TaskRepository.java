package com.example.tasktracker.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.tasktracker.entity.Task;
import com.example.tasktracker.entity.User;

public interface TaskRepository extends CrudRepository<Task, Long> {

	List<Task> findAllByUser(User user);
}
