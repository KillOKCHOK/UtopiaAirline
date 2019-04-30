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
import com.utopia.app.curdcontroller.BookingController;
import com.utopia.app.model.Booking;
import com.utopia.app.model.User;
import com.utopia.app.service.BookingService;

@RunWith(SpringRunner.class)
@WebMvcTest(BookingController.class)
public class BookingControllerTest {

	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private BookingService service;
	
	@Autowired
    private ObjectMapper objectMapper;
	
	@Test
	public void getListOfBookingsReturnOk() throws Exception {
		Booking booking = new Booking();
		booking.setBookingId((long)1);
		booking.setConfirmationCode("abcde12345fghij67890");
		booking.setOrderSubmit(false);
		List<Booking> bookings = new ArrayList<>();
		bookings.add(booking);
		
		when(service.getBookingAll()).thenReturn(bookings);
		
		mvc.perform(get("/adm/bookings").accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$",hasSize(1)))
		.andExpect(jsonPath("$[0].confirmationCode",is(booking.getConfirmationCode())))
		.andExpect(jsonPath("$[0].orderSubmit",is(booking.getOrderSubmit())));
	}
	
	@Test
	public void getOneBookingReturnOk() throws Exception {
		Booking booking = new Booking();
		booking.setBookingId((long)1);
		booking.setConfirmationCode("abcde12345fghij67890");
		booking.setOrderSubmit(false);
		
		when(service.getBookingById(1)).thenReturn(booking);
		
		mvc.perform(get("/adm/booking/1").accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.confirmationCode",is(booking.getConfirmationCode())))
		.andExpect(jsonPath("$.orderSubmit",is(booking.getOrderSubmit())));
	}
	
	@Test
	public void createBookingReturnCreated() throws Exception {
		Booking booking = new Booking();
		booking.setBookingId((long)1);
		booking.setConfirmationCode("abcde12345fghij67890");
		booking.setOrderSubmit(false);
		booking.setCreateDate(new Date());
		User user1 = new User();
		user1.setUserId((long)1);
		user1.setUsername("Jason");
		User user2 = new User();
		user2.setUserId((long)2);
		user2.setUsername("Postman");
		booking.setCreateUser(user1);
		booking.setUser(user2);
		
		mvc.perform(post("/adm/booking")
				.accept(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(booking))				
			    .contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isCreated());
	}
	
	@Test
	public void updateBookingReturnAccepted() throws Exception{
		Booking booking = new Booking();
		booking.setBookingId((long)1);
		booking.setConfirmationCode("abcde12345fghij67890");
		booking.setOrderSubmit(true);
		booking.setCreateDate(new Date());
		User user1 = new User();
		user1.setUserId((long)1);
		user1.setUsername("Jason1");
		User user2 = new User();
		user2.setUserId((long)2);
		user2.setUsername("Postman1");
		booking.setCreateUser(user1);
		booking.setUser(user2);
		
		mvc.perform(put("/adm/booking")
				.accept(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(booking))				
			    .contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isAccepted());
	}
	
	@Test
	public void deleteBookingReturnAccepted() throws Exception {
		mvc.perform(delete("/adm/booking/1")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isAccepted());
	}
}
