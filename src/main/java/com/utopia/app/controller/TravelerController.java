package com.utopia.app.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.utopia.app.model.Airport;
import com.utopia.app.model.Booking;
import com.utopia.app.model.Flight;
import com.utopia.app.model.Payment;
import com.utopia.app.model.User;
import com.utopia.app.service.TravelerService;

@RestController
@RequestMapping("/traveler")
public class TravelerController {

	@Autowired
	TravelerService travelerServ;
	
	private static Logger logger = Logger.getLogger(TravelerController.class);

	// For listing out airports on the main page
	@GetMapping("/airports")
	public ResponseEntity<List<Airport>> getAirportList(@RequestParam String name, HttpServletRequest request) {
		List<Airport> airports = null;
		try {
			airports = travelerServ.getAirportList(name);
		} catch (Exception e) {
			logger.warn("Cant handle DB request with input: "+name+ "\nIP: "+request.getLocalAddr(), e.getCause());
			return new ResponseEntity<List<Airport>>(airports, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if (airports != null && airports.size() > 0) {
			return new ResponseEntity<List<Airport>>(airports, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<Airport>>(airports, HttpStatus.NOT_FOUND);
		}
	}

	// For searching flights
	// http://localhost:8000/traveler/flights?sdate=2019-05-01&depAirportId=2&arrAirportId=3
	@GetMapping("/flights")
	public ResponseEntity<List<Flight>> getFlightList(@RequestParam String sdate, @RequestParam Long depAirportId,
		@RequestParam Long arrAirportId, HttpServletRequest request) throws ParseException {
		// Check if can pass Date from UI
		List<Flight> flights = new ArrayList<Flight>();
		try {
			Date date = new SimpleDateFormat("yyyy-MM-dd").parse(sdate);
			flights = travelerServ.getFightList(date, depAirportId, arrAirportId);
//			System.out.println(flights);
		}catch(ParseException p){
			logger.error("Unable to parse the Date: "+ sdate+ "\nIP: "+request.getLocalAddr()+ p.getMessage());
			return new ResponseEntity<List<Flight>>(flights, HttpStatus.BAD_REQUEST);
		}catch(Exception e) {
			logger.error("Unable to fetch flights from DB on date: "+ sdate+" Departure AirportId: "+ depAirportId+" Arrival AirportId: "+ arrAirportId+"\nIP: "+request.getLocalAddr(), e.getCause());
			return new ResponseEntity<List<Flight>>(flights, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if (flights != null && flights.size() > 0) {
			return new ResponseEntity<List<Flight>>(flights, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<Flight>>(flights, HttpStatus.NOT_FOUND);
		}
	}

	// Reserve tickets (decrease flights' capacities
	@PutMapping("/flights")
	public ResponseEntity<List<Flight>> createBookingReserv(@RequestBody List<Flight> flights, @RequestParam int travelerNumber, HttpServletRequest request) {
		if (flights != null && flights.size() > 0) {
			try {
				travelerServ.createBookingReserv(flights, travelerNumber);
			} catch (Exception e) {
				logger.error("Unable to update flights in DB "+"\nIP: "+request.getLocalAddr(), e.getCause());
				return new ResponseEntity<List<Flight>>(flights, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			return new ResponseEntity<List<Flight>>(flights, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<Flight>>(flights, HttpStatus.BAD_REQUEST);
		}
	}
/////////////////////////////////////////////////////////////////////////////////////
	// Confirm booking
	@PostMapping("/bookings")
	public ResponseEntity<Booking> confirmBookingReserv(@RequestBody Map<String, Object> bookingDetail, HttpServletRequest request) {
		// @RequestBody List<User> users, @RequestBody Payment payment, @RequestBody
		// Booking booking, @RequestBody List<Flight> flights
		// Update information in the booking
		ObjectMapper mapper = new ObjectMapper();
		Booking booking = null;
		Payment payment = null;
		List<Flight> flights=new ArrayList<>();
		List<User> users=new ArrayList<>();
		try {
			booking = mapper.convertValue(bookingDetail.get("booking"), Booking.class);
			users = mapper.convertValue(bookingDetail.get("users"), new TypeReference<List<User>>() {});
			flights = mapper.convertValue(bookingDetail.get("flights"), new TypeReference<List<Flight>>() {});
			payment = mapper.convertValue(bookingDetail.get("payment"), Payment.class);
		}catch ( IllegalArgumentException e) {
			logger.error("Objecs do not match their declared type"+"\nIP: "+request.getLocalAddr(), e.getCause());
			return new ResponseEntity<Booking>(booking, HttpStatus.BAD_REQUEST);
		}catch (Exception e) {
			logger.error("Could not map Objects to their declared type"+"\nIP: "+request.getLocalAddr(), e.getCause());
			return new ResponseEntity<Booking>(booking, HttpStatus.BAD_REQUEST);
		}
		if (booking != null && payment != null && users != null && users.size() > 0 && flights != null && flights.size() > 0 && payment != null) {
			try{
				travelerServ.confirmBookingReserv(users, payment, booking, flights);
				return new ResponseEntity<Booking>(booking, HttpStatus.CREATED);
			}catch (Exception e) {
				logger.error("Could not confirm Booking Reservation"+"\nIP: "+request.getLocalAddr(), e.getCause());
				return new ResponseEntity<Booking>(booking, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} else {
			return new ResponseEntity<Booking>(booking, HttpStatus.BAD_REQUEST);
		}
	}

	// Change booking
	@PutMapping("/bookings")
	public ResponseEntity<Booking> changeBookingReserv(@RequestBody Map<String, Object> bookingDetail, HttpServletRequest request) {
		// @RequestBody Payment payment, @RequestBody
		// Booking booking, @RequestBody List<Flight> flights
		// Update information in the booking
		ObjectMapper mapper = new ObjectMapper();
		Booking booking = null;
		List<Flight> flights = new ArrayList<>();
		try {
			booking = mapper.convertValue(bookingDetail.get("booking"), Booking.class);
			flights = mapper.convertValue(bookingDetail.get("flights"), new TypeReference<List<Flight>>() {});
		}catch ( IllegalArgumentException e) {
			logger.error("Objecs do not match their declared type"+"\nIP: "+request.getLocalAddr(), e.getCause());
			return new ResponseEntity<Booking>(booking, HttpStatus.BAD_REQUEST);
		}catch (Exception e) {
			logger.error("Could not map Objects to their declared type"+"\nIP: "+request.getLocalAddr(), e.getCause());
			return new ResponseEntity<Booking>(booking, HttpStatus.BAD_REQUEST);
		}
		if (booking != null && flights != null && flights.size() > 0) {
			try {
				travelerServ.changeBookingReserv(booking, flights);
				return new ResponseEntity<Booking>(booking, HttpStatus.OK);
			} catch (Exception e) {
				logger.error("Could not Update Booking"+"\nIP: "+request.getLocalAddr(), e.getCause());
				return new ResponseEntity<Booking>(booking, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} else {
			return new ResponseEntity<Booking>(booking, HttpStatus.BAD_REQUEST);
		}
	}

	// Get booking by confirmationNum and user
	//http://localhost:8000/traveler/bookings?confirmationNum=&firstName=&lastName=
	@GetMapping("/bookings")
	public ResponseEntity<Booking> readBooking(@RequestParam String confirmationNum, @RequestParam String firstName,
			@RequestParam String lastName, HttpServletRequest request) {
		if (confirmationNum != null && firstName != null && lastName != null) {
			Booking booking = null;
			try{
				booking = travelerServ.readBooking(confirmationNum, firstName, lastName);
			}catch (Exception e) {
				logger.warn("Could not Find Booking"+"\nIP: "+request.getLocalAddr(), e.getCause());
				return new ResponseEntity<Booking>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
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
	public ResponseEntity<String> cancelBooking(@PathVariable("bookingId") Long bookingId, HttpServletRequest request) {
		if (bookingId != null) {
			try {
				travelerServ.cancelBooking(bookingId);
			} catch (Exception e) {
				logger.warn("Could not Delete Booking "+ bookingId+"\nIP: "+request.getLocalAddr(), e.getCause());
				return new ResponseEntity<String>("Failed", HttpStatus.INTERNAL_SERVER_ERROR);
			}
			return new ResponseEntity<String>("Canceled", HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("Failed", HttpStatus.BAD_REQUEST);
		}
	}
}
