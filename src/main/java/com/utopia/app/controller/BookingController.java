package com.utopia.app.controller;

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

import com.utopia.app.model.Booking;
import com.utopia.app.service.BookingService;

@RestController
@RequestMapping("/adm")
public class BookingController {
	
	@Autowired
	private BookingService bookingRepo;
	
	@GetMapping("/bookings")
	public ResponseEntity<List<Booking>> getAllBooking(){
		try {
			List<Booking> bookings = bookingRepo.getBookingAll();
			return new ResponseEntity<List<Booking>>(bookings, HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<List<Booking>>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/booking/{bookingId}")
	public ResponseEntity<Booking> getBookingById(@PathVariable long bookingId){
		try {
			Booking booking = bookingRepo.getBookingById(bookingId);
			return new ResponseEntity<Booking>(booking,HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<Booking>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("/booking")
	public ResponseEntity<Booking> updateBooking(@RequestBody Booking booking){
		try {
			bookingRepo.updateBooking(booking);
			return new ResponseEntity<Booking>(HttpStatus.ACCEPTED);
		}catch(Exception e) {
			return new ResponseEntity<Booking>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/booking")
	public ResponseEntity<Booking> addBooking(@RequestBody Booking booking){
		try {
			bookingRepo.createBooking(booking);
			return new ResponseEntity<Booking>(HttpStatus.CREATED);
		}catch(Exception e) {
			return new ResponseEntity<Booking>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping("/booking/{bookingId}")
	public ResponseEntity<Booking> deleteBooking(@PathVariable long bookingId){
		try {
			bookingRepo.deleteBooking(bookingId);
			return new ResponseEntity<Booking>(HttpStatus.ACCEPTED);
		}catch(Exception e) {
			return new ResponseEntity<Booking>(HttpStatus.BAD_REQUEST);
		}
	}
	
}
