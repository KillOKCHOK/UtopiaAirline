package com.utopia.app.idao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.utopia.app.model.Role;

public interface IRoleDao extends JpaRepository<Role,Long> {

}
