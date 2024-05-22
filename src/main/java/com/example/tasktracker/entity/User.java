package com.example.tasktracker.entity;

import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name="users")
@NoArgsConstructor(force=true, access=AccessLevel.PROTECTED)
public class User {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	private final String email;
	private final String password;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Task> tasks;
}
