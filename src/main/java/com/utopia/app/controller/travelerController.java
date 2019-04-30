package com.utopia.app.controller;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.utopia.app.model.Airport;
import com.utopia.app.model.Booking;
import com.utopia.app.model.Flight;
import com.utopia.app.service.TravelerService;

@RestController
public class TravelerController {

	@Autowired
	TravelerService travelerServ;

	// For listing out airports on the main page
	@GetMapping("/airports")
	public ResponseEntity<List<Airport>> getAirportList(@RequestParam String name) {
		List<Airport> airports = travelerServ.getAirportList(name);
		if (airports != null) {
			return new ResponseEntity<List<Airport>>(airports, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<Airport>>(airports, HttpStatus.BAD_REQUEST);
		}
	}

	// For searching flights
	@GetMapping("/flights/date/{sdate}/departingAirpots/{depAirportId}/arrivingAirpots/{arrAirportId}") // PathVariable
	public ResponseEntity<List<Flight>> getFlightList(@PathVariable String sdate, @PathVariable Long depAirportId,
			@PathVariable Long arrAirportId) throws ParseException {
		List<Flight> flights = travelerServ.getFightList(sdate, depAirportId, arrAirportId);
		if (flights != null) {
			return new ResponseEntity<List<Flight>>(flights, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<Flight>>(flights, HttpStatus.BAD_REQUEST);
		}
	}

	// Reserve tickets (decrease flights' capacities
	@PutMapping("/flights")
	public ResponseEntity<List<Flight>> createBookingReserv(@RequestBody Map<String, Object> flightsReserv) {
		if (flightsReserv != null ) {
			List<Flight> flights = travelerServ.createBookingReserv(flightsReserv);
			return new ResponseEntity<List<Flight>>(flights, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<Flight>>(HttpStatus.BAD_REQUEST);
		}
	}

	// Confirm booking
	@PostMapping("/booking")
	public ResponseEntity<Booking> confirmBookingReserv(@RequestBody Map<String, Object> bookingDetail) {
		if (bookingDetail != null) {
			Booking booking = travelerServ.confirmBookingReserv(bookingDetail);
			return new ResponseEntity<Booking>(booking, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<Booking>(HttpStatus.BAD_REQUEST);
		}
	}

	// Change booking
	@PutMapping("/booking")
	public ResponseEntity<Booking> changeBookingReserv(@RequestBody Map<String, Object> bookingDetail) {
		if (bookingDetail != null) {
			Booking booking = travelerServ.changeBookingReserv(bookingDetail);
			return new ResponseEntity<Booking>(booking, HttpStatus.OK);
		} else {
			return new ResponseEntity<Booking>(HttpStatus.BAD_REQUEST);
		}
	}

	// Get booking by confirmationNum and user
	//http://localhost:8000/traveler/bookings?confirmationNum=&firstName=&lastName=
	@GetMapping("/bookings/{confirmationNum}")
	public ResponseEntity<Booking> readBooking(@PathVariable String confirmationNum) {
		if (confirmationNum != null) {
			Booking booking = travelerServ.readBooking(confirmationNum);
			if(booking == null) {
				return new ResponseEntity<Booking>(HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<Booking>(booking, HttpStatus.OK);
		} else {
			return new ResponseEntity<Booking>(HttpStatus.BAD_REQUEST);
		}
	}

	// Cancel booking
	@DeleteMapping("/bookings/{bookingId}")
	public ResponseEntity<String> cancelBooking(@PathVariable("bookingId") Long bookingId) {
		if (bookingId != null) {
			travelerServ.cancelBooking(bookingId);
			return new ResponseEntity<String>("Canceled", HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("Failed", HttpStatus.BAD_REQUEST);
		}
	}
}
