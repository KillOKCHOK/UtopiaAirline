package com.utopia.app.curdcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.utopia.app.model.Flight;
import com.utopia.app.service.FlightService;

@RestController
@RequestMapping("/adm")
public class FlightController {
	
	@Autowired
	private FlightService flightRepo;
	
	@GetMapping("/flights")
	public ResponseEntity<List<Flight>> getAllFlight(){
		try {
			List<Flight> flights = flightRepo.getFlightAll();
			return new ResponseEntity<List<Flight>>(flights, HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<List<Flight>>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/flight/{flightId}")
	public ResponseEntity<Flight> getFlightById(@PathVariable long flightId){
		try {
			Flight flight = flightRepo.getFlightById(flightId);
			return new ResponseEntity<Flight>(flight,HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<Flight>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("/flight")
	public ResponseEntity<Flight> updateFlight(@RequestBody Flight flight){
		try {
			flightRepo.updateFlight(flight);
			return new ResponseEntity<Flight>(HttpStatus.ACCEPTED);
		}catch(Exception e) {
			return new ResponseEntity<Flight>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/flight")
	public ResponseEntity<Flight> addFlight(@RequestBody Flight flight){
		try {
			flightRepo.createFlight(flight);
			return new ResponseEntity<Flight>(HttpStatus.CREATED);
		}catch(Exception e) {
			return new ResponseEntity<Flight>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping("/flight/{flightId}")
	public ResponseEntity<Flight> deleteFlight(@PathVariable long flightId){
		try {
			flightRepo.deleteFlight(flightId);
			return new ResponseEntity<Flight>(HttpStatus.ACCEPTED);
		}catch(Exception e) {
			return new ResponseEntity<Flight>(HttpStatus.BAD_REQUEST);
		}
	}
	
}
