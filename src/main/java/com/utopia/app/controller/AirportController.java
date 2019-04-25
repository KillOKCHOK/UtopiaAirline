package com.utopia.app.controller;

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

import com.utopia.app.model.Airport;
import com.utopia.app.service.AirportService;

@RestController
@RequestMapping("/adm")
public class AirportController {
	
	@Autowired
	private AirportService airportServ;
	
	@GetMapping("/airports")
	public ResponseEntity<List<Airport>> getAllAirport(){
		try {
			List<Airport> airports = airportServ.getAirportAll();
			return new ResponseEntity<List<Airport>>(airports, HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<List<Airport>>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/airport/{airportId}")
	public ResponseEntity<Airport> getAirportById(@PathVariable long airportId){
		try {
			Airport airport = airportServ.getAirportById(airportId);
			return new ResponseEntity<Airport>(airport,HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<Airport>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("/airport")
	public ResponseEntity<Airport> updateAirport(@RequestBody Airport airport){
		try {
			airportServ.updateAirport(airport);
			return new ResponseEntity<Airport>(HttpStatus.ACCEPTED);
		}catch(Exception e) {
			return new ResponseEntity<Airport>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/airport")
	public ResponseEntity<Airport> addAirport(@RequestBody Airport airport){
		try {
			airportServ.createAirport(airport);
			return new ResponseEntity<Airport>(HttpStatus.CREATED);
		}catch(Exception e) {
			return new ResponseEntity<Airport>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping("/airport/{airportId}")
	public ResponseEntity<Airport> deleteAirport(@PathVariable long airportId){
		try {
			airportServ.deleteAirport(airportId);
			return new ResponseEntity<Airport>(HttpStatus.ACCEPTED);
		}catch(Exception e) {
			return new ResponseEntity<Airport>(HttpStatus.BAD_REQUEST);
		}
	}
	
}
