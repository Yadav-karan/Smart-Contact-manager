package com.smart.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.smart.entities.Contact;
import com.smart.entities.User;
import com.smart.exceptions.NoSuchContactException;
import com.smart.exceptions.NoSuchUserException;
import com.smart.exceptions.OperationFailedException;
import com.smart.helper.ExcelHelper;
import com.smart.service.EndUserService;

@RestController
@CrossOrigin(origins = "*",allowedHeaders = "*")
public class UserController {
	
	@Autowired
	private EndUserService userService;
	
	@Autowired
	private ExcelHelper excelHelper;
	
	@GetMapping("/get-all-users")
	public ResponseEntity<List<User>> getAllUsers(){
		List<User> result = userService.findAllUser();
		if(result != null) {
			return new ResponseEntity<>(result,HttpStatus.OK);
		}
		else { 
			return new ResponseEntity<List<User>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/add-user")
	public ResponseEntity<User> addUser(@RequestBody User user){
		User result = userService.addUser(user);
		if(result != null) {
			return new ResponseEntity<User>(result,HttpStatus.OK);
		}
		else {
			return new ResponseEntity<User>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/get-all-contacts")
	public ResponseEntity<List<Contact>> getAllContacts(){
		List<Contact> result = userService.findAllContact();
		if(result != null) {
			return new ResponseEntity<>(result,HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/add-contact/{userId}")
	public ResponseEntity<Contact> addContact(@RequestBody Contact contact,@PathVariable("userId") int userId) throws NoSuchUserException{
		Contact result = userService.addContact(contact,userId);
		if(result != null) {
			return new ResponseEntity<Contact>(result,HttpStatus.OK);
		}
		else {
			return new ResponseEntity<Contact>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/update-user")
	public ResponseEntity<User> updateUser(@RequestBody User user){
		User result = userService.updateUser(user);
		if(result !=null) {
			return new ResponseEntity<User>(result,HttpStatus.OK);
		}
		else {
			return new ResponseEntity<User>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/update-contact")
	public ResponseEntity<Contact> updateContact(@RequestBody Contact contact){
		Contact result = userService.updateContact(contact);
		if(result !=null) {
			return new ResponseEntity<>(result,HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/get-user-by-id/{userId}")
	public ResponseEntity<User> getUserById(@PathVariable("userId") int userId)throws NoSuchUserException{
		User result = userService.findUserById(userId);
		if(result != null) {
			return new ResponseEntity<>(result,HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/get-contact-by-id/{cId}")
	public ResponseEntity<Contact> getContactById(@PathVariable("cId") int cId) throws NoSuchContactException{
		Contact result = userService.findContactById(cId);
		if(result != null) {
			return new ResponseEntity<>(result,HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/upload-contact")
	public ResponseEntity<?> uploadContacts(@RequestParam("file")MultipartFile file) throws OperationFailedException, NoSuchUserException{
		if(excelHelper.checkExcelFormat(file)) {
			userService.save(file);
			return  ResponseEntity.ok(Map.of("message:","File Uploaded Successfully"));
		}else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please upload excel file ");
		}
	}
	
	@GetMapping("/sort-contact/{field}")
	public ResponseEntity<List<Contact>> getContactSorted(@PathVariable String field){
		List<Contact> result = userService.getContactSorted(field);
		if(result !=null) {
			return new ResponseEntity<>(result,HttpStatus.OK);
		}else {
			return new ResponseEntity<>(result,HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/page-contact/{pageNo}/{pageSize}")
	public ResponseEntity<Page<Contact>> getContactSorted(@PathVariable int pageNo,@PathVariable int pageSize){
		Page<Contact> result = userService.getContactPagination(pageNo,pageSize);
		if(result !=null) {
			return new ResponseEntity<>(result,HttpStatus.OK);
		}else {
			return new ResponseEntity<>(result,HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/get-user/{username}")
	public ResponseEntity<User> getUser(@PathVariable String username) throws NoSuchUserException{
		User result = userService.getUserByUserName(username);
		if(result != null) {
			return new ResponseEntity<>(result,HttpStatus.OK);
		}else {
			return new ResponseEntity<>(result,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/get-contact-by-userid/{userId}")
	public ResponseEntity<?> getContactByUserId(@PathVariable int userId) throws NoSuchUserException{
		List<Contact> result = userService.findContactByUserId(userId);
		if(result != null) {
			return new ResponseEntity<>(result,HttpStatus.OK);
		}else {
			return new ResponseEntity<>("No Contacts Available Please Add Contacts ",HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/delete-contact-by-id/{contactId}")
	public ResponseEntity<?> deleteContactById(@PathVariable int contactId) throws NoSuchContactException{
		boolean result = userService.deleteContactById(contactId);
		if(result)
			return new ResponseEntity<>("Contact deleted successfully",HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
