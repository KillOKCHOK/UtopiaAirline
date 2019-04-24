package com.utopia.app.idao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.utopia.app.model.User;

public interface IUserDao extends JpaRepository<User,Long> {

}
