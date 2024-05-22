package com.example.tasktracker.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor(force=true, access=AccessLevel.PROTECTED)
public class Task {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	private final String title;
	private final String description;
	private final boolean finished;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private final User user;
	
}
