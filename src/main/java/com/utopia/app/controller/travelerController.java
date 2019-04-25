package com.utopia.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.utopia.app.model.Airport;
import com.utopia.app.service.TravelerService;

@RestController
@RequestMapping("/traveler")
public class travelerController {

	@Autowired
	TravelerService travelerServ;
	
	@GetMapping("/airports/{name}")
	public ResponseEntity<List<Airport>> getAirportList(@PathVariable String name){
		List<Airport> airports = travelerServ.getAirportList(name);
		if (airports != null && airports.size() > 0) {
			return new ResponseEntity<List<Airport>>(airports, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<Airport>>(airports, HttpStatus.BAD_REQUEST);
		}
	}
	
	
}
