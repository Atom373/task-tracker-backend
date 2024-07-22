package com.example.tasktracker.domain.service.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.tasktracker.domain.entity.Task;
import com.example.tasktracker.domain.repository.TaskRepository;
import com.example.tasktracker.domain.service.TaskService;
import com.example.tasktracker.security.entity.User;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

	private final EntityManager entityManager;

	private final TaskRepository taskRepo;

	@Override
	public List<Task> findAllByUserId(Long userId) {
		User userReference = entityManager.getReference(User.class, userId);
		return taskRepo.findAllByUserAndIsDeletedIsFalse(userReference);
	}

	@Override
	public Task saveTaskWithTitle(String title, Long userId) {
		User userReference = entityManager.getReference(User.class, userId);
		
		Task task = Task.builder()
						.title(title)
						.user(userReference)
						.isFinished(false)
						.isDeleted(false)
						.build();

		return taskRepo.save(task);
	}

	@Override
	public void updateTitle(Long taskId, String title) {
		taskRepo.updateTitleById(taskId, title);
	}

	@Override
	public void updateDescription(Long taskId, String description) {
		taskRepo.updateDescriptionById(taskId, description);
	}

	@Override
	public void updateIsFinished(Long taskId, Boolean isFinished) {
		taskRepo.updateIsFinishedById(taskId, isFinished);
	}

	@Override
	public void markAsDeleted(Long taskId) {
		taskRepo.markAsDeletedById(taskId);
	}
}
