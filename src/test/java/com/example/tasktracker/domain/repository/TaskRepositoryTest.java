 package com.example.tasktracker.domain.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.tasktracker.domain.entity.Task;
import com.example.tasktracker.security.entity.User;
import com.example.tasktracker.security.repository.UserRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@DataJpaTest
public class TaskRepositoryTest {

	@Autowired
	private TaskRepository taskRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	@PersistenceContext
	private EntityManager entityManager;

	private List<Task> tasks;
	private User bob;
	private User sam;
	
	@BeforeEach
	public void setUp() {
		userRepo.deleteAll();
		taskRepo.deleteAll();
		
		bob = new User();
		bob.setEmail("bob@gmail.com");
		bob.setPassword("1234");
		
		sam = new User();
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
		
		tasks = List.of(task1, task2, task3);
		taskRepo.saveAll(tasks);
	}
	
	@Test
	public void findAllByUserAndIsDeletedIsFalse_shouldReturnListWithFirstTask() {
		// given
		
		// when
		List<Task> obtained = taskRepo.findAllByUserAndIsDeletedIsFalse(bob);
		
		// then
		assertNotNull(obtained);
		assertEquals(tasks.get(0).getTitle(), obtained.get(0).getTitle());
	}
	
	@Test
	public void updateTitleById() {
		// given
		String newTitle = "New title";
		Task taskToUpdate = tasks.get(0);
		
		// when
		taskRepo.updateTitleById(taskToUpdate.getId(), newTitle);
	
		// then
		Task obtained = taskRepo.findById(taskToUpdate.getId()).orElse(null);
		assertNotNull(obtained);
		System.out.println(obtained);
		assertEquals(newTitle, obtained.getTitle());
	}
	
	@Test
	public void updateDescriptionById() {
		// given
		String newDescription = "New description";
		Task taskToUpdate = tasks.get(0);
		
		// when
		taskRepo.updateDescriptionById(taskToUpdate.getId(), newDescription);
		
		// then
		Task obtained = taskRepo.findById(taskToUpdate.getId()).orElse(null);
		assertNotNull(obtained);
		assertEquals(newDescription, obtained.getDescription());
	}
	
	@Test
	public void updateIsFinishedById() {
		// given
		Task taskToUpdate = tasks.get(0);
		
		// when
		taskRepo.updateIsFinishedById(taskToUpdate.getId(), true);
		
		// then
		Task obtained = taskRepo.findById(taskToUpdate.getId()).orElse(null);
		assertNotNull(obtained);
		assertEquals(true, obtained.getIsFinished());
		assertEquals(LocalDate.now(), obtained.getFinishingDate());
	}
	
	@Test
	public void markAsDeletedById() {
		// given
		Task taskToMark = tasks.get(0);
		
		// when
		taskRepo.markAsDeletedById(taskToMark.getId());
		
		// then
		Task obtained = taskRepo.findById(taskToMark.getId()).orElse(null);
		assertNotNull(obtained);
		assertEquals(true, obtained.getIsDeleted());
	}
}
