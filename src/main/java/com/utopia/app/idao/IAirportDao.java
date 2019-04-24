package com.utopia.app.idao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.utopia.app.model.Airport;

public interface IAirportDao extends JpaRepository<Airport,Long> {

}
