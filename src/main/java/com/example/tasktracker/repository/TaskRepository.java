package com.example.tasktracker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.tasktracker.entity.Task;
import com.example.tasktracker.entity.User;

public interface TaskRepository extends JpaRepository<Task, Long> {

	List<Task> findAllByUser(User user);
}
