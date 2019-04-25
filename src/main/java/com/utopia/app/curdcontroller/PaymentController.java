package com.utopia.app.curdcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.utopia.app.model.Payment;
import com.utopia.app.service.PaymentService;

@RestController
@RequestMapping("/adm")
public class PaymentController {
	
	@Autowired
	private PaymentService paymentRepo;
	
	@GetMapping("/payments")
	public ResponseEntity<List<Payment>> getAllPayment(){
		try {
			List<Payment> payments = paymentRepo.getPaymentAll();
			return new ResponseEntity<List<Payment>>(payments, HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<List<Payment>>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/payment/{paymentId}")
	public ResponseEntity<Payment> getPaymentById(@PathVariable long paymentId){
		try {
			Payment payment = paymentRepo.getPaymentById(paymentId);
			return new ResponseEntity<Payment>(payment,HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<Payment>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("/payment")
	public ResponseEntity<Payment> updatePayment(@RequestBody Payment payment){
		try {
			paymentRepo.updatePayment(payment);
			return new ResponseEntity<Payment>(HttpStatus.ACCEPTED);
		}catch(Exception e) {
			return new ResponseEntity<Payment>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/payment")
	public ResponseEntity<Payment> addPayment(@RequestBody Payment payment){
		try {
			paymentRepo.createPayment(payment);
			return new ResponseEntity<Payment>(HttpStatus.CREATED);
		}catch(Exception e) {
			return new ResponseEntity<Payment>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping("/payment/{paymentId}")
	public ResponseEntity<Payment> deletePayment(@PathVariable long paymentId){
		try {
			paymentRepo.deletePayment(paymentId);
			return new ResponseEntity<Payment>(HttpStatus.ACCEPTED);
		}catch(Exception e) {
			return new ResponseEntity<Payment>(HttpStatus.BAD_REQUEST);
		}
	}
	
}
