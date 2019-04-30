package com.utopia.app.crudcontroller.test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.utopia.app.curdcontroller.CityController;
import com.utopia.app.model.City;
import com.utopia.app.service.CityService;

@RunWith(SpringRunner.class)
@WebMvcTest(CityController.class)
public class CityControllerTest {

	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private CityService service;
	
	@Autowired
    private ObjectMapper objectMapper;
	
	@Test
	public void getListOfCitysReturnOk() throws Exception {
		City city = new City();
		city.setCityId((long)1);
		city.setCityName("Seattle");
		city.setCountry("USA");
		List<City> cities = new ArrayList<>();
		cities.add(city);
		
		when(service.getCityAll()).thenReturn(cities);
		
		mvc.perform(get("/adm/cities").accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$",hasSize(1)))
		.andExpect(jsonPath("$[0].cityName",is(city.getCityName())))
		.andExpect(jsonPath("$[0].country",is(city.getCountry())));
	}
	
	@Test
	public void getOneCityReturnOk() throws Exception {
		City city = new City();
		city.setCityId((long)1);
		city.setCityName("Seattle");
		city.setCountry("USA");
		
		when(service.getCityById(1)).thenReturn(city);
		
		mvc.perform(get("/adm/cities/1").accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.cityName",is(city.getCityName())))
		.andExpect(jsonPath("$.country",is(city.getCountry())));
	}
	
	@Test
	public void createCityReturnCreated() throws Exception {
		City city = new City();
		city.setCityId((long)1);
		city.setCityName("Seattle");
		city.setCountry("USA");
		
		mvc.perform(post("/adm/city")
				.accept(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(city))				
			    .contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isCreated());
	}
	
	@Test
	public void updateCityReturnAccepted() throws Exception{
		City city = new City();
		city.setCityId((long)1);
		city.setCityName("Seattle");
		city.setCountry("USA");
		
		mvc.perform(put("/adm/city")
				.accept(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(city))				
			    .contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isAccepted());
	}
	
	@Test
	public void deleteCityReturnAccepted() throws Exception {
		mvc.perform(delete("/adm/city/1")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isAccepted());
	}
}
