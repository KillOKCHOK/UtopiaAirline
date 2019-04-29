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
import com.utopia.app.curdcontroller.PaymentController;
import com.utopia.app.model.Payment;
import com.utopia.app.service.PaymentService;

@RunWith(SpringRunner.class)
@WebMvcTest(PaymentController.class)
public class PaymentControllerTest {

	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private PaymentService service;
	
	@Autowired
    private ObjectMapper objectMapper;
	
	@Test
	public void get_list_of_payments_return_ok() throws Exception {
		Payment p = new Payment();
		p.setPaymentId((long) 1);
		p.setPaymentStatus(true);
		List<Payment> payments = new ArrayList<>();
		payments.add(p);
		
		when(service.getPaymentAll()).thenReturn(payments);
		
		mvc.perform(get("/adm/payments").accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$",hasSize(1)))
		.andExpect(jsonPath("$[0].paymentStatus",is(true)));
	}
	
	@Test
	public void get_one_payment_return_ok() throws Exception {
		Payment p = new Payment();
		p.setPaymentId((long) 1);
		p.setPaymentStatus(true);
		
		when(service.getPaymentById(1)).thenReturn(p);
		
		mvc.perform(get("/adm/payment/1").accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.paymentStatus",is(true)));
	}
	
	@Test
	public void create_payment_return_created() throws Exception {
		Payment p = new Payment();
		p.setPaymentId((long) 1);
		p.setPaymentStatus(true);
		
		mvc.perform(post("/adm/payment")
				.accept(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(p))				
			    .contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isCreated());
	}
	
	@Test
	public void update_payment_return_accepted() throws Exception{
		Payment p = new Payment();
		p.setPaymentId((long) 1);
		p.setPaymentStatus(true);
		
		mvc.perform(put("/adm/payment")
				.accept(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(p))				
			    .contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isAccepted());
	}
	
	@Test
	public void delete_payment_return_accepted() throws Exception {
		mvc.perform(delete("/adm/payment/1")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isAccepted());
	}
}

