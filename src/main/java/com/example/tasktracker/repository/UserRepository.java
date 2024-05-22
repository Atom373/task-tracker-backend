package com.example.tasktracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.tasktracker.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{

	Boolean existsByEmail(String email);
}