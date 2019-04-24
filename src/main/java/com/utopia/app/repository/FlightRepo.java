package com.utopia.app.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.utopia.app.idao.IFlightDao;
import com.utopia.app.model.Flight;

@Service
@Transactional
public class FlightRepo {
	
	@Autowired
	private IFlightDao flightdao;
	
	public Flight getFlightById(long flightId) {
		return flightdao.getOne(flightId);
	}
	
	public List<Flight> getFlightAll() {
		return flightdao.findAll();
	}
	
	public void createFlight(Flight flight) {
		flightdao.save(flight);
	}
	
	public void updateFlight(Flight flight) {
		flightdao.save(flight);
	}
	
	public void deleteFlight(long flightId) {
		flightdao.deleteById(flightId);
	}
	
}
