package com.utopia.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.utopia.app.idao.IAirportDao;
import com.utopia.app.model.Airport;

@Service
public class TravelerService {
	
	@Autowired
	IAirportDao airportDao;
	
	public List<Airport> getAirportList(String name){
		String serchName = "%"+name+"%";
		
		
		return null;
		
	}
}
