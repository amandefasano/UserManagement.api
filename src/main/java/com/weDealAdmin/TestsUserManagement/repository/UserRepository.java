package com.weDealAdmin.TestsUserManagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.weDealAdmin.TestsUserManagement.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    
	/**
	 * 
	 * @param 
	 * @return
	 */
	@Query(value = "SELECT id, username, firstname, lastname, email, pw FROM users", nativeQuery = true)
	List<User> findAll();
	
	/**
	 * @param id
	 * @return 
	 */
	@Query(value = "SELECT id, username, firstname, lastname, email, pw FROM users WHERE username = ?1", nativeQuery = true)
	User findByUsername(String username);
	
	/**
	 * @param username
	 * @param firstname
	 * @param lastname
	 * @param email
	 * @param password
	 * @return 
	 */
	@Query(value = "INSERT INTO users (username, firstname, lastname, email, pw) VALUES (?1, ?2, ?3, ?4, ?5)", nativeQuery = true)
	@Modifying
	void create(
		String username, 
		String firstname, 
		String lastname, 
		String email, 
		String password
	);
	
	/**
	 * @param id
	 * @param username
	 * @param firstname
	 * @param lastname
	 * @param email
	 * @param password
	 * @return 
	 */
	@Query(value = "UPDATE users SET username = ?2, firstname = ?3, lastname = ?4, email = ?5, pw = ?6 WHERE id = ?1", nativeQuery = true)
	@Modifying
	void update(
		Long id,
		String username, 
		String firstname, 
		String lastname, 
		String email, 
		String password
	);
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	@Query(value = "DELETE FROM users WHERE id = ?1", nativeQuery = true)
	@Modifying
	void delete(Long id);
}
