package com.example.tasktracker.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.tasktracker.entity.User;

public interface UserRepository extends CrudRepository<User, Long>{

	Boolean existsByEmail(String email);

	User findByEmail(String email);
}