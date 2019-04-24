package com.utopia.app.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.utopia.app.idao.IAirportDao;
import com.utopia.app.model.Airport;

@Service
@Transactional
public class AirportRepo {
	
	@Autowired
	private IAirportDao airportdao;
	
	public Airport getAirportById(long airportId) {
		return airportdao.getOne(airportId);
	}
	
	public List<Airport> getAirportAll() {
		return airportdao.findAll();
	}
	
	public void createAirport(Airport airport) {
		airportdao.save(airport);
	}
	
	public void updateAirport(Airport airport) {
		airportdao.save(airport);
	}
	
	public void deleteAirport(long airportId) {
		airportdao.deleteById(airportId);
	}
	
}
