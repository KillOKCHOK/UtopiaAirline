package com.utopia.app.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.utopia.app.idao.IAirportDao;
import com.utopia.app.idao.IFlightDao;
import com.utopia.app.model.Airport;
import com.utopia.app.model.Flight;

@Service
@Transactional
public class TravelerService {
	
	@Autowired
	private IAirportDao airportDao;
	
	@Autowired
	private IFlightDao flightDao;
	
	public List<Airport> getAirportList(String name){
		String serchName = "%"+name+"%";
		try {
			return airportDao.getAirportListByName(serchName);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Flight> getFightList(Date date, Long depAirportId, Long arrAirportId){
		try {
			return flightDao.getFlightList(date,depAirportId,arrAirportId);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
}
