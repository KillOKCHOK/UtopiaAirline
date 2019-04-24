package com.utopia.app.idao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.utopia.app.model.City;

public interface ICityDao extends JpaRepository<City,Long> {

}
