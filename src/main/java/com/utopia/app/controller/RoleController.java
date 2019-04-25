package com.utopia.app.controller;

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

import com.utopia.app.model.Role;
import com.utopia.app.service.RoleService;

@RestController
@RequestMapping("/adm")
public class RoleController {
	
	@Autowired
	private RoleService roleRepo;
	
	@GetMapping("/roles")
	public ResponseEntity<List<Role>> getAllRole(){
		try {
			List<Role> roles = roleRepo.getRoleAll();
			return new ResponseEntity<List<Role>>(roles, HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<List<Role>>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/role/{roleId}")
	public ResponseEntity<Role> getRoleById(@PathVariable long roleId){
		try {
			Role role = roleRepo.getRoleById(roleId);
			return new ResponseEntity<Role>(role,HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<Role>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("/role")
	public ResponseEntity<Role> updateRole(@RequestBody Role role){
		try {
			roleRepo.updateRole(role);
			return new ResponseEntity<Role>(HttpStatus.ACCEPTED);
		}catch(Exception e) {
			return new ResponseEntity<Role>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/role")
	public ResponseEntity<Role> addRole(@RequestBody Role role){
		try {
			roleRepo.createRole(role);
			return new ResponseEntity<Role>(HttpStatus.CREATED);
		}catch(Exception e) {
			return new ResponseEntity<Role>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping("/role/{roleId}")
	public ResponseEntity<Role> deleteRole(@PathVariable long roleId){
		try {
			roleRepo.deleteRole(roleId);
			return new ResponseEntity<Role>(HttpStatus.ACCEPTED);
		}catch(Exception e) {
			return new ResponseEntity<Role>(HttpStatus.BAD_REQUEST);
		}
	}
	
}
