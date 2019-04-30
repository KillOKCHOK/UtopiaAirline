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
	public void getListOfAirportsReturn_ok() throws Exception {
		Airport airport = new Airport();
		airport.setAirportId((long)1);
		airport.setAirportCode("IAD");
		airport.setAirportName("Dulles");
		List<Airport> allAirport = new ArrayList<>();
		allAirport.add(airport);

		when(service.getAirportAll()).thenReturn(allAirport);
		
		
		mvc.perform(get("/adm/airports").accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$",hasSize(1)))
		.andExpect(jsonPath("$[0].airportName",is(airport.getAirportName())))
		.andExpect(jsonPath("$[0].airportCode",is(airport.getAirportCode())));
		
	}
	
	@Test
	public void getOneAirportReturnOk() throws Exception {
		Airport airport = new Airport();
		airport.setAirportId((long)1);
		airport.setAirportCode("IAD");
		airport.setAirportName("Dulles");
				
		when(service.getAirportById(1)).thenReturn(airport);
		
		mvc.perform(get("/adm/airport/1").accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.airportId",is(1)))
		.andExpect(jsonPath("$.airportName",is(airport.getAirportName())))
		.andExpect(jsonPath("$.airportCode",is(airport.getAirportCode())));
		
	}
	
	@Test 
	public void createNewAirportReturnCreated() throws Exception {
		Airport airport = new Airport();
		airport.setAirportCode("IAD");
		airport.setAirportName("Dulles");
		airport.setAirportId((long)1);
		City city = new City();
		city.setCityName("DC");
		city.setCountry("USA");
		airport.setCity(city);
		
		mvc.perform(post("/adm/airport")
				.accept(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(airport))				
			    .contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isCreated());
		
	}
	
	@Test
	public void updateAirportReturnAccepted() throws Exception {
		Airport airport = new Airport();
		airport.setAirportCode("IAD");
		airport.setAirportName("Dulles");
		airport.setAirportId((long)1);
		City city = new City();
		city.setCityName("DC");
		city.setCountry("USA");
		airport.setCity(city);
		
		mvc.perform(put("/adm/airport")
				.accept(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(airport))				
			    .contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isAccepted());
	}
	
	@Test
	public void deleteAirportReturnAccepted() throws Exception {
		mvc.perform(delete("/adm/airport/1")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isAccepted());
	}

}
