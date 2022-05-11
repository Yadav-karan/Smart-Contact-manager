package com.smart.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import com.smart.entities.Contact;
import com.smart.entities.User;
import com.smart.exceptions.NoSuchContactException;
import com.smart.exceptions.NoSuchUserException;
import com.smart.exceptions.OperationFailedException;

public interface EndUserService {

	public User addUser(User user);
	
	public List<User> findAllUser();
	
	public Contact addContact(Contact contact,int userId) throws NoSuchUserException;
	
	public List<Contact> findAllContact();
	
	public User updateUser(User user);
	
	public Contact updateContact(Contact contact);
	
	public Contact findContactById(int cId)throws NoSuchContactException;
	
	public User findUserById(int userId) throws NoSuchUserException;
	
	public void save(MultipartFile file)throws OperationFailedException;
	
	public List<Contact> getContactSorted(String field);
	
	public Page<Contact> getContactPagination(int offset,int pageNo);
	
	public User getUserByUserName(String username) throws NoSuchUserException;
	
	public List<Contact> findContactByUserId(int userId)throws NoSuchUserException;
	
	boolean deleteContactById(int contactId) throws NoSuchContactException;
}
