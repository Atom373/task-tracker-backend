 package com.example.tasktracker.domain.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.tasktracker.domain.entity.Task;
import com.example.tasktracker.security.entity.User;
import com.example.tasktracker.security.repository.UserRepository;

@DataJpaTest
public class TaskRepositoryTest {

	@Autowired
	private TaskRepository taskRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	@BeforeEach
	public void setUp() {
		taskRepo.deleteAll();
	}
	
	@Test
	public void findAllByUserAndIsDeletedIsFalse_shouldReturnListWithFirstTask() {
		// given
		User bob = new User();
		bob.setEmail("bob@gmail.com");
		bob.setPassword("1234");
		
		User sam = new User();
		sam.setEmail("sam@gmail.com");
		sam.setPassword("1234");
		
		userRepo.save(bob);
		userRepo.save(sam);
		
		Task task1 = Task.builder()
				.title("first task")
				.user(bob)
				.isDeleted(false)
				.build();
		Task task2 = Task.builder()
				.title("second task")
				.user(sam)
				.isDeleted(false)
				.build();
		Task task3 = Task.builder()
				.title("third task")
				.user(bob)
				.isDeleted(true)
				.build();
		
		taskRepo.saveAll(List.of(task1, task2, task3));
		
		// when
		List<Task> obtained = taskRepo.findAllByUserAndIsDeletedIsFalse(bob);
		
		// then
		assertNotNull(obtained);
		assertEquals(task1.getTitle(), obtained.get(0).getTitle());
	}
}
