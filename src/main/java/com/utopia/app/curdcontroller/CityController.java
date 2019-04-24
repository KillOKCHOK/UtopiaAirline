package com.utopia.app.curdcontroller;

import java.util.List;

import javax.validation.Valid;

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

import com.utopia.app.model.City;
import com.utopia.app.repository.CityRepo;

@RestController
@RequestMapping("/adm")
public class CityController {
	
	@Autowired
	private CityRepo cityRepo;
	
	@GetMapping("/cities")
	public ResponseEntity<List<City>> getAllCity(){
		try {
			List<City> citys = cityRepo.getCityAll();
			return new ResponseEntity<List<City>>(citys, HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<List<City>>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/cities/{cityId}")
	public ResponseEntity<City> getCityById(@PathVariable long cityId){
		try {
			City city = cityRepo.getCityById(cityId);
			return new ResponseEntity<City>(city,HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<City>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("/city")
	public ResponseEntity<City> updateCity(@Valid @RequestBody City city){
		try {
			cityRepo.updateCity(city);
			return new ResponseEntity<City>(city, HttpStatus.ACCEPTED);
		}catch(Exception e) {
			return new ResponseEntity<City>(city, HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/city")
	public ResponseEntity<City> addCity(@RequestBody City city){
		try {
			cityRepo.createCity(city);
			return new ResponseEntity<City>(city, HttpStatus.CREATED);
		}catch(Exception e) {
			return new ResponseEntity<City>(city, HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping("/city/{cityId}")
	public ResponseEntity<City> deleteCity(@PathVariable long cityId){
		try {
			cityRepo.deleteCity(cityId);
			return new ResponseEntity<City>(HttpStatus.ACCEPTED);
		}catch(Exception e) {
			return new ResponseEntity<City>(HttpStatus.BAD_REQUEST);
		}
	}
	
}
