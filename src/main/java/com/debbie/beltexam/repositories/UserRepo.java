package com.debbie.beltexam.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.debbie.beltexam.models.User;

@Repository
public interface UserRepo extends CrudRepository<User, Long> {
	User findByEmail(String email);
	
	@Query("SELECT u FROM User u")
	List<User> findAllUsers();
	
	@Modifying
	@Query("update User u set u.name = ?1, u.email = ?2, u.password = ?3, u.description = ?4, u.updatedAt = ?5 WHERE u.id = ?6")
	int updateUser(String name, String email, String password, String description, Date updatedAt, Long id);
	
}
