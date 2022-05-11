package com.smart.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.smart.entities.Contact;
import com.smart.entities.User;
import com.smart.exceptions.NoSuchContactException;
import com.smart.exceptions.NoSuchUserException;
import com.smart.exceptions.OperationFailedException;
import com.smart.helper.ExcelHelper;
import com.smart.repository.ContactRepository;
import com.smart.repository.UserRepository;

@Service
public class EndUserServiceImpl implements EndUserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ContactRepository contactRepository;
	
	@Autowired
	private ExcelHelper excelhelper;
	
	
	@Override
	public User addUser(User user) {
		return userRepository.save(user);
	}

	@Override
	public List<User> findAllUser() {
		return userRepository.findAll();
	}

	@Override
	public Contact addContact(Contact contact,int userId) throws NoSuchUserException {
		User user = findUserById(userId);
		contact.setUser(user);
		return contactRepository.save(contact);
	}

	@Override
	public List<Contact> findAllContact() {
		return contactRepository.findAll();
	}

	@Override
	public User updateUser(User user) {
		return userRepository.save(user);
	}

	@Override
	public Contact updateContact(Contact contact) {
		return contactRepository.save(contact);
	}

	@Override
	public Contact findContactById(int cId) throws NoSuchContactException {
		try {
			Optional<Contact> contact = contactRepository.findById(cId);
			if(contact.get() != null) {
				return contact.get();
			}
		}catch (NoSuchElementException e) {
			throw new NoSuchContactException("Contact With Contact Id "+cId+" Not Found!!!");
		}
		return null;
	}

	@Override
	public User findUserById(int userId) throws NoSuchUserException {
		try {
			User user = userRepository.findById(userId).get();
			if(user != null) {
				return user;
			}
		}catch (NoSuchElementException e) {
			throw new NoSuchUserException("User With User Id "+userId+" Not Found !!!");
		}
		return null;
	}

	@Override
	public void save(MultipartFile file) throws OperationFailedException {
		List<Contact> contacts = excelhelper.convertExcelToList(file);
		contactRepository.saveAll(contacts);
	}

	@Override
	public List<Contact> getContactSorted(String field) {
		return contactRepository.findAll(Sort.by(Sort.Direction.ASC,field));
	}

	@Override
	public Page<Contact> getContactPagination(int pageNo, int pageSize) {
		return contactRepository.findAll(PageRequest.of(pageNo, pageSize));
	}

	@Override
	public User getUserByUserName(String username) throws NoSuchUserException {
		try {
			User user = userRepository.findByUserName(username);
			return user;
		} catch (Exception e) {
			throw new NoSuchUserException("User with "+username+" not found");
		}
		
	}

	@Override
	public List<Contact> findContactByUserId(int userId)throws NoSuchUserException {
		try {
			return contactRepository.findContactByUserId(userId);
		} catch (Exception e) {
			throw new NoSuchUserException("User With User Id "+userId+" Not Found !!!");
		}
	}

	@Override
	public boolean deleteContactById(int contactId) throws NoSuchContactException {
		Contact contact = findContactById(contactId);
		contactRepository.delete(contact);
		return true;
	}
	
	
	

}
