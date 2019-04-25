package com.utopia.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.utopia.app.idao.ICityDao;
import com.utopia.app.model.City;

@Service
@Transactional
public class CityService {
	
	@Autowired
	private ICityDao citydao;
	
	public City getCityById(long cityId) {
		return citydao.getOne(cityId);
	}
	
	public List<City> getCityAll() {
		return citydao.findAll();
	}
	
	public void createCity(City city) {
		citydao.save(city);
	}
	
	public void updateCity(City city) {
		citydao.save(city);
	}
	
	public void deleteCity(long cityId) {
		System.out.print(cityId);
		citydao.deleteById(cityId);
	}
	
}
