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
import java.util.Date;
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
import com.utopia.app.curdcontroller.FlightController;
import com.utopia.app.model.Airport;
import com.utopia.app.model.Flight;
import com.utopia.app.service.FlightService;

@RunWith(SpringRunner.class)
@WebMvcTest(FlightController.class)
public class FlightControllerTest {

	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private FlightService service;
	
	@Autowired
    private ObjectMapper objectMapper;

	@Test
	public void getListOfFlightsReturnOk() throws Exception {
		Flight flight = new Flight();
		flight.setFlightId((long) 1);
		flight.setCapacity(200);
		flight.setDepDateTime(new Date());
		Airport airport = new Airport();
		airport.setAirportId((long) 1);
		airport.setAirportName("DCA");
		List<Flight> allFlights = new ArrayList<>();
		allFlights.add(flight);

		when(service.getFlightAll()).thenReturn(allFlights);
		
		
		mvc.perform(get("/adm/flights").accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$",hasSize(1)))
		.andExpect(jsonPath("$[0].capacity",is(flight.getCapacity())));
		
	}
	
	@Test
	public void getOneFlightReturnOk() throws Exception {
		Flight flight = new Flight();
		flight.setFlightId((long) 1);
		flight.setCapacity(200);
		Airport a = new Airport();
		a.setAirportId((long) 1);
		a.setAirportName("DCA");
				
		when(service.getFlightById(1)).thenReturn(flight);
		
		mvc.perform(get("/adm/flight/1").accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.capacity",is(flight.getCapacity())));
		
	}
	
	@Test 
	public void createNewFlightReturnCreated() throws Exception {
		Flight flight = new Flight();
		flight.setFlightId((long)1);
		flight.setPrice((float)280);
		Airport a = new Airport();
		a.setAirportId((long)1);
		a.setAirportName("DCA");
		
		mvc.perform(post("/adm/flight")
				.accept(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(flight))				
			    .contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isCreated());
		
	}
	
	@Test
	public void updateFlightReturnAccepted() throws Exception {
		Flight flight = new Flight();
		flight.setFlightId((long)1);
		flight.setPrice((float)280);
		flight.setDepDateTime(new Date());
		Airport a = new Airport();
		a.setAirportId((long)1);
		a.setAirportName("DCA");
		
		mvc.perform(put("/adm/flight")
				.accept(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(flight))				
			    .contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isAccepted());
	}
	
	@Test
	public void deleteFlightReturnAccepted() throws Exception {
		mvc.perform(delete("/adm/flight/1")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isAccepted());
	}

}

