package com.smart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.smart.entities.Contact;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Integer> {

	@Query(value="SELECT * FROM contact WHERE user_user_id=:userId",nativeQuery = true)
	public List<Contact> findContactByUserId(@Param("userId") int userId);
	
}
