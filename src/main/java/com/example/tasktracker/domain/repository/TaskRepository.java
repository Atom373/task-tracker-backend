package com.example.tasktracker.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.example.tasktracker.domain.entity.Task;
import com.example.tasktracker.security.entity.User;


public interface TaskRepository extends JpaRepository<Task, Long> {

	List<Task> findAllByUserAndIsDeletedIsFalse(User user);
	
	@Modifying(clearAutomatically = true)
	@Transactional
    @Query(value = "UPDATE task SET title = :title WHERE id = :id", nativeQuery = true)
    void updateTitleById(@Param("id") Long id, @Param("title") String title);
	
	@Modifying(clearAutomatically = true)
	@Transactional
    @Query(value = "UPDATE task SET description = :description WHERE id = :id", nativeQuery = true)
    void updateDescriptionById(@Param("id") Long id, @Param("description") String description);
	
	@Modifying(clearAutomatically = true)
	@Transactional
    @Query(value = "UPDATE task SET is_finished = :isFinished, finishing_date = CURDATE() WHERE id = :id", nativeQuery = true)
    void updateIsFinishedById(@Param("id") Long id, @Param("isFinished") Boolean isFinished);
	
	@Modifying(clearAutomatically = true)
	@Transactional
    @Query(value = "UPDATE task SET is_deleted = true WHERE id = :id", nativeQuery = true)
    void markAsDeletedById(@Param("id") Long id);
}
