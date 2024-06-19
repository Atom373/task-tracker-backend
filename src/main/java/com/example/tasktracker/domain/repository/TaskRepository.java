package com.example.tasktracker.domain.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.tasktracker.domain.entity.Task;
import com.example.tasktracker.security.entity.User;

public interface TaskRepository extends CrudRepository<Task, Long> {

	List<Task> findAllByUserAndIsDeletedIsFalse(User user);
}
