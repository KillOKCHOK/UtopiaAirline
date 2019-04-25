package com.utopia.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.utopia.app.idao.IRoleDao;
import com.utopia.app.model.Role;

@Service
@Transactional
public class RoleService {
	
	@Autowired
	private IRoleDao roledao;
	
	public Role getRoleById(long roleId) {
		return roledao.getOne(roleId);
	}
	
	public List<Role> getRoleAll() {
		return roledao.findAll();
	}
	
	public void createRole(Role role) {
		roledao.save(role);
	}
	
	public void updateRole(Role role) {
		roledao.save(role);
	}
	
	public void deleteRole(long roleId) {
		roledao.deleteById(roleId);
	}
	
}
