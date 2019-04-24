package com.utopia.app.idao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.utopia.app.model.Flight;

public interface IFlightDao extends JpaRepository<Flight,Long> {

}
