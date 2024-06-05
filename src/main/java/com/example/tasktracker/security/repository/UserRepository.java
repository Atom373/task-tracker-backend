package com.example.tasktracker.security.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.tasktracker.security.entity.User;

public interface UserRepository extends CrudRepository<User, Long>{

	Boolean existsByEmail(String email);

	User findByEmail(String email);
	
	List<User> findAll();
}