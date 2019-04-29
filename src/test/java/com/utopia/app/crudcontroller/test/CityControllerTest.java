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
	public void get_list_of_citys_return_ok() throws Exception {
		City c = new City();
		c.setCityId((long)1);
		c.setCityName("Seattle");
		c.setCountry("USA");
		List<City> cities = new ArrayList<>();
		cities.add(c);
		
		when(service.getCityAll()).thenReturn(cities);
		
		mvc.perform(get("/adm/cities").accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$",hasSize(1)))
		.andExpect(jsonPath("$[0].cityName",is("Seattle")))
		.andExpect(jsonPath("$[0].country",is("USA")));
	}
	
	@Test
	public void get_one_city_return_ok() throws Exception {
		City c = new City();
		c.setCityId((long)1);
		c.setCityName("Seattle");
		c.setCountry("USA");
		
		when(service.getCityById(1)).thenReturn(c);
		
		mvc.perform(get("/adm/cities/1").accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.cityName",is("Seattle")))
		.andExpect(jsonPath("$.country",is("USA")));
	}
	
	@Test
	public void create_city_return_created() throws Exception {
		City c = new City();
		c.setCityId((long)1);
		c.setCityName("Seattle");
		c.setCountry("USA");
		
		mvc.perform(post("/adm/city")
				.accept(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(c))				
			    .contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isCreated());
	}
	
	@Test
	public void update_city_return_accepted() throws Exception{
		City c = new City();
		c.setCityId((long)1);
		c.setCityName("Seattle");
		c.setCountry("USA");
		
		mvc.perform(put("/adm/city")
				.accept(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(c))				
			    .contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isAccepted());
	}
	
	@Test
	public void delete_city_return_accepted() throws Exception {
		mvc.perform(delete("/adm/city/1")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isAccepted());
	}
}
