package com.utopia.app.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.utopia.app.idao.IUserDao;
import com.utopia.app.model.User;

@Service
@Transactional
public class UserRepo {
	
	@Autowired
	private IUserDao userdao;
	
	public User getUserById(long userId) {
		return userdao.getOne(userId);
	}
	
	public List<User> getUserAll() {
		return userdao.findAll();
	}
	
	public void createUser(User user) {
		userdao.save(user);
	}
	
	public void updateUser(User user) {
		userdao.save(user);
	}
	
	public void deleteUser(long userId) {
		userdao.deleteById(userId);
	}
	
}
