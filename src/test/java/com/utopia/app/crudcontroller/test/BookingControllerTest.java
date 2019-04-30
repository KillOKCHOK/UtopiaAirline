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
	public void get_list_of_bookings_return_ok() throws Exception {
		Booking b = new Booking();
		b.setBookingId((long)1);
		b.setConfirmationCode("abcde12345fghij67890");
		b.setOrderSubmit(false);
		List<Booking> bookings = new ArrayList<>();
		bookings.add(b);
		
		when(service.getBookingAll()).thenReturn(bookings);
		
		mvc.perform(get("/adm/bookings").accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$",hasSize(1)))
		.andExpect(jsonPath("$[0].confirmationCode",is("abcde12345fghij67890")))
		.andExpect(jsonPath("$[0].orderSubmit",is(false)));
	}
	
	@Test
	public void get_one_booking_return_ok() throws Exception {
		Booking b = new Booking();
		b.setBookingId((long)1);
		b.setConfirmationCode("abcde12345fghij67890");
		b.setOrderSubmit(false);
		
		when(service.getBookingById(1)).thenReturn(b);
		
		mvc.perform(get("/adm/booking/1").accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.confirmationCode",is("abcde12345fghij67890")))
		.andExpect(jsonPath("$.orderSubmit",is(false)));
	}
	
	@Test
	public void create_booking_return_created() throws Exception {
		Booking b = new Booking();
		b.setBookingId((long)1);
		b.setConfirmationCode("abcde12345fghij67890");
		b.setOrderSubmit(false);
		b.setCreateDate(new Date());
		User u1 = new User();
		u1.setUserId((long)1);
		u1.setUsername("Jason");
		User u2 = new User();
		u2.setUserId((long)2);
		u2.setUsername("Postman");
		b.setCreateUser(u1);
		b.setUser(u2);
		
		mvc.perform(post("/adm/booking")
				.accept(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(b))				
			    .contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isCreated());
	}
	
	@Test
	public void update_booking_return_accepted() throws Exception{
		Booking b = new Booking();
		b.setBookingId((long)1);
		b.setConfirmationCode("abcde12345fghij67890");
		b.setOrderSubmit(true);
		b.setCreateDate(new Date());
		User u1 = new User();
		u1.setUserId((long)1);
		u1.setUsername("Jason1");
		User u2 = new User();
		u2.setUserId((long)2);
		u2.setUsername("Postman1");
		b.setCreateUser(u1);
		b.setUser(u2);
		
		mvc.perform(put("/adm/booking")
				.accept(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(b))				
			    .contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isAccepted());
	}
	
	@Test
	public void delete_booking_return_accepted() throws Exception {
		mvc.perform(delete("/adm/booking/1")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isAccepted());
	}
}
