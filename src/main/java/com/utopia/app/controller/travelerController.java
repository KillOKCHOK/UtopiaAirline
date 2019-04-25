package com.utopia.app.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.utopia.app.model.Airport;
import com.utopia.app.model.Flight;
import com.utopia.app.service.TravelerService;
import com.utopia.app.src.RandomString;

@RestController
@RequestMapping("/traveler")
public class travelerController {

	@Autowired
	TravelerService travelerServ;
	
	private RandomString rs = new RandomString();
	
	// For listing out airports on the main page
	@GetMapping("/airports")
	public ResponseEntity<List<Airport>> getAirportList(@RequestParam String name){
		List<Airport> airports = travelerServ.getAirportList(name);
		if (airports != null && airports.size() > 0) {
			return new ResponseEntity<List<Airport>>(airports, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<Airport>>(airports, HttpStatus.BAD_REQUEST);
		}
	}
	
	// For searching flights
	@GetMapping("/flights")
	public ResponseEntity<List<Flight>> getFlightList(@RequestParam String sdate,
			@RequestParam Long depAirportId, @RequestParam Long arrAirportId) throws ParseException{
		Date date = new SimpleDateFormat("yyyy-MM-dd").parse(sdate);
		System.out.println(date);
		List<Flight> flights = travelerServ.getFightList(date, depAirportId, arrAirportId);
		if (flights != null && flights.size() > 0) {
			return new ResponseEntity<List<Flight>>(flights, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<Flight>>(flights, HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/order")
	public ResponseEntity<String> makeBooking(){
		return new ResponseEntity<String>(rs.nextString(), HttpStatus.OK);
	}
}
