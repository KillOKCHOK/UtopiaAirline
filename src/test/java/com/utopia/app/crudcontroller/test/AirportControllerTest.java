package com.utopia.app.crudcontroller.test;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
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
import com.utopia.app.curdcontroller.AirportController;
import com.utopia.app.model.Airport;
import com.utopia.app.model.City;
import com.utopia.app.service.AirportService;

@RunWith(SpringRunner.class)
@WebMvcTest(AirportController.class)
public class AirportControllerTest {

	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private AirportService service;
	
	@Autowired
    private ObjectMapper objectMapper;

	@Test
	public void get_list_of_airports_return_ok() throws Exception {
		Airport iad = new Airport();
		iad.setAirportId(1);
		iad.setAirportCode("IAD");
		iad.setAirportName("Dulles");
		List<Airport> allAirport = new ArrayList<>();
		allAirport.add(iad);

		when(service.getAirportAll()).thenReturn(allAirport);
		
		
		mvc.perform(get("/adm/airports").accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$",hasSize(1)))
		.andExpect(jsonPath("$[0].airportName",is("Dulles")))
		.andExpect(jsonPath("$[0].airportCode",is("IAD")));
		
	}
	
	@Test
	public void get_one_airport_return_ok() throws Exception {
		Airport iad = new Airport();
		iad.setAirportId(1);
		iad.setAirportCode("IAD");
		iad.setAirportName("Dulles");
				
		when(service.getAirportById(1)).thenReturn(iad);
		
		mvc.perform(get("/adm/airport/1").accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.airportId",is(1)))
		.andExpect(jsonPath("$.airportName",is("Dulles")))
		.andExpect(jsonPath("$.airportCode",is("IAD")));
		
	}
	
	@Test 
	public void create_new_airport_return_created() throws Exception {
		Airport iad = new Airport();
		iad.setAirportCode("IAD");
		iad.setAirportName("Dulles");
		iad.setAirportId(1);
		City c = new City();
		c.setCityName("DC");
		c.setCountry("USA");
		iad.setCity(c);
		
		mvc.perform(post("/adm/airport")
				.accept(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(iad))				
			    .contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isCreated());
		
	}
	
	@Test
	public void update_airport_return_accepted() throws Exception {
		Airport iad = new Airport();
		iad.setAirportCode("IAD");
		iad.setAirportName("Dulles");
		iad.setAirportId(1);
		City c = new City();
		c.setCityName("DC");
		c.setCountry("USA");
		iad.setCity(c);
		
		mvc.perform(put("/adm/airport")
				.accept(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(iad))				
			    .contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isAccepted());
	}
	
	@Test
	public void delete_airport_return_accepted() throws Exception {
		mvc.perform(delete("/adm/airport/1")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isAccepted());
	}

}
