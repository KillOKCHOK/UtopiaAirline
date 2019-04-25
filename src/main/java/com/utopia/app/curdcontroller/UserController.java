package com.utopia.app.curdcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.utopia.app.model.User;
import com.utopia.app.service.UserService;

@RestController
@RequestMapping("/adm")
public class UserController {
	
	@Autowired
	private UserService userRepo;
	
	@GetMapping("/users")
	public ResponseEntity<List<User>> getAllUser(){
		try {
			List<User> users = userRepo.getUserAll();
			return new ResponseEntity<List<User>>(users, HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<List<User>>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/user/{userId}")
	public ResponseEntity<User> getUserById(@PathVariable long userId){
		try {
			User user = userRepo.getUserById(userId);
			return new ResponseEntity<User>(user,HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("/user")
	public ResponseEntity<User> updateUser(@RequestBody User user){
		try {
			userRepo.updateUser(user);
			return new ResponseEntity<User>(HttpStatus.ACCEPTED);
		}catch(Exception e) {
			return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/user")
	public ResponseEntity<User> addUser(@RequestBody User user){
		try {
			userRepo.createUser(user);
			return new ResponseEntity<User>(HttpStatus.CREATED);
		}catch(Exception e) {
			return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping("/user/{userId}")
	public ResponseEntity<User> deleteUser(@PathVariable long userId){
		try {
			userRepo.deleteUser(userId);
			return new ResponseEntity<User>(HttpStatus.ACCEPTED);
		}catch(Exception e) {
			return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
		}
	}
	
}
