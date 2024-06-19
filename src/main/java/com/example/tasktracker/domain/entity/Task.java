package com.example.tasktracker.domain.entity;

import java.time.LocalDate;

import com.example.tasktracker.security.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Task {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	private String title;
	private String description;
	private Boolean isFinished;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	private User user;
	
	private Boolean isDeleted;
	private LocalDate finishingDate;
}
