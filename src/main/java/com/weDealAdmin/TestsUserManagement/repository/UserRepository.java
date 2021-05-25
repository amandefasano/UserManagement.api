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
	@Query(value = "SELECT id, username, first_name, last_name, email, `password` FROM users", nativeQuery = true)
	List<User> findAll();
	
	/**
	 * @param id
	 * @return 
	 */
	@Query(value = "SELECT id, username, first_name, last_name, email, `password` FROM users WHERE username = ?1", nativeQuery = true)
	User findByUsername(String username);
	
	/**
	 * @param username
	 * @param firstName
	 * @param lastName
	 * @param email
	 * @param password
	 * @return 
	 */
	@Query(value = "INSERT INTO users (username, first_name, last_name, email, `password`) VALUES (?1, ?2, ?3, ?4, ?5)", nativeQuery = true)
	@Modifying
	int create(
		String username, 
		String firstName, 
		String lastName, 
		String email, 
		String password
	);
	
	/**
	 * @param id
	 * @param username
	 * @param firstName
	 * @param lastName
	 * @param email
	 * @param password
	 * @return 
	 */
	@Query(value = "UPDATE users SET username = ?2, first_name = ?3, last_name = ?4, email = ?5, `password` = ?6 WHERE id = ?1", nativeQuery = true)
	@Modifying
	int update(
		Long id,
		String username, 
		String firstName, 
		String lastName, 
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
	int delete(Long id);
}
