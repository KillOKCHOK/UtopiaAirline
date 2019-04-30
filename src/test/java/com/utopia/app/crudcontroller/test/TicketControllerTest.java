package com.utopia.app.crudcontroller.test;

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
import com.utopia.app.curdcontroller.TicketController;
import com.utopia.app.model.Booking;
import com.utopia.app.model.Ticket;
import com.utopia.app.model.User;
import com.utopia.app.service.TicketService;

@RunWith(SpringRunner.class)
@WebMvcTest(TicketController.class)
public class TicketControllerTest {

	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private TicketService service;
	
	@Autowired
    private ObjectMapper objectMapper;
	
	@Test
	public void getListOfTicketsReturnOk() throws Exception {
		Ticket ticket = new Ticket();
		ticket.setTicketId((long) 1);
		List<Ticket> tickets = new ArrayList<>();
		tickets.add(ticket);
		
		when(service.getTicketAll()).thenReturn(tickets);
		
		mvc.perform(get("/adm/tickets").accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$",hasSize(1)));
	}
	
	@Test
	public void getOneTicketReturnOk() throws Exception {
		Ticket ticket = new Ticket();
		ticket.setTicketId((long) 1);
		
		when(service.getTicketById(1)).thenReturn(ticket);
		
		mvc.perform(get("/adm/ticket/1").accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
	}
	
	@Test
	public void createTicketReturnCreated() throws Exception {
		Ticket ticket = new Ticket();
		ticket.setTicketId((long) 1);
		ticket.setBooking(new Booking());
		ticket.setUser(new User());
		
		mvc.perform(post("/adm/ticket")
				.accept(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(ticket))				
			    .contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isCreated());
	}
	
	@Test
	public void updateTicketReturnAccepted() throws Exception{
		Ticket ticket = new Ticket();
		ticket.setTicketId((long) 1);
		ticket.setBooking(new Booking());
		ticket.setUser(new User());
		
		mvc.perform(put("/adm/ticket")
				.accept(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(ticket))				
			    .contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isAccepted());
	}
	
	@Test
	public void deleteTicketReturnAccepted() throws Exception {
		mvc.perform(delete("/adm/ticket/1")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isAccepted());
	}
}
