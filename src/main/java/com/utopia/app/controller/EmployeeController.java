//package com.utopia.app.controller;
//
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.List;
//import java.util.Map;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.utopia.app.model.Airport;
//import com.utopia.app.model.Booking;
//import com.utopia.app.model.Flight;
//import com.utopia.app.model.Payment;
//import com.utopia.app.model.User;
//import com.utopia.app.service.EmployeeService;
//
//@RestController
//@RequestMapping("/employees")
//public class EmployeeController {
//
//	@Autowired
//	EmployeeService employeeServ;
//	
//	// Get employee details
//	@GetMapping("/{employeeId}")
//	public ResponseEntity<User> getEmployee(@PathVariable("employeeId") Long employeeId) {
//		if (employeeId != null) {
//			User employee = employeeServ.getEmployee(employeeId);
//			if(employee == null) {
//				return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
//			}
//			return new ResponseEntity<User>(employee, HttpStatus.OK);
//		} else {
//			return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
//		}
//	}
//	
//	// update employee info
//	@PutMapping("/{employeeId}")
//	public ResponseEntity<User> updateEmployee(@PathVariable("employeeId") Long employeeId, @RequestBody User employee) {
//		if (employeeId != null && employee != null) {
//			employee = employeeServ.updateEmployee(employee);
//			if(employee == null) {
//				return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
//			}
//			return new ResponseEntity<User>(employee, HttpStatus.OK);
//		} else {
//			return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
//		}
//	}
//	
//	// get all booking under this employee 
//	@GetMapping("/{employeeId}/bookings")
//	public ResponseEntity<List<Booking>> getBookings(@PathVariable("employeeId") Long employeeId) {
//		if (employeeId != null) {
//			List<Booking> bookings = employeeServ.getBookings(employeeId);
//			return new ResponseEntity<List<Booking>>(bookings, HttpStatus.OK);
//		} else {
//			return new ResponseEntity<List<Booking>>(HttpStatus.BAD_REQUEST);
//		}
//	}	
//	
//
//	// For listing out airports on the main page
//	@GetMapping("/airports")
//	public ResponseEntity<List<Airport>> getAirportList(@RequestParam String name) {
//		List<Airport> airports = employeeServ.getAirportList(name);
//		if (airports != null && airports.size() > 0) {
//			return new ResponseEntity<List<Airport>>(airports, HttpStatus.OK);
//		} else {
//			return new ResponseEntity<List<Airport>>(airports, HttpStatus.BAD_REQUEST);
//		}
//	}
//
//	// For searching flights
//	// http://localhost:8000/employee/flights?sdate=2019-05-01&depAirportId=2&arrAirportId=3
//	@GetMapping("/flights")
//	public ResponseEntity<List<Flight>> getFlightList(@RequestParam String sdate, @RequestParam Long depAirportId,
//			@RequestParam Long arrAirportId) throws ParseException {
//		// Check if can pass Date from UI
//		Date date = new SimpleDateFormat("yyyy-MM-dd").parse(sdate);
//		List<Flight> flights = employeeServ.getFightList(date, depAirportId, arrAirportId);
//		if (flights != null && flights.size() > 0) {
//			return new ResponseEntity<List<Flight>>(flights, HttpStatus.OK);
//		} else {
//			return new ResponseEntity<List<Flight>>(flights, HttpStatus.BAD_REQUEST);
//		}
//	}
//
//	// Reserve tickets (decrease flights' capacities
//	@PutMapping("/flights")
//	public ResponseEntity<List<Flight>> createBookingReserv(@RequestBody List<Flight> flights,
//			@RequestParam int travelerNumber) {
//		if (flights != null && flights.size() > 0) {
//			employeeServ.createBookingReserv(flights, travelerNumber);
//			return new ResponseEntity<List<Flight>>(flights, HttpStatus.OK);
//		} else {
//			return new ResponseEntity<List<Flight>>(flights, HttpStatus.BAD_REQUEST);
//		}
//	}
//
//	// Confirm booking
//	@PostMapping("/{employeeId}/bookings")
//	public ResponseEntity<Booking> confirmBookingReserv(@PathVariable("employeeId") Long employeeId, @RequestBody Map<String, Object> bookingDetail) {
//		// @RequestBody List<User> users, @RequestBody Payment payment, @RequestBody
//		// Booking booking, @RequestBody List<Flight> flights
//		
//		// Update information in the booking (with employee)
//		ObjectMapper mapper = new ObjectMapper();
//		List<User> users = mapper.convertValue(bookingDetail.get("users"), new TypeReference<List<User>>() {});
//		Booking booking = mapper.convertValue(bookingDetail.get("booking"), Booking.class);
//		List<Flight> flights = mapper.convertValue(bookingDetail.get("flights"), new TypeReference<List<Flight>>() {});
//		Payment payment = mapper.convertValue(bookingDetail.get("payment"), Payment.class);
//		
//		if (booking != null && payment != null && users != null && users.size() > 0 &&
//				flights != null && flights.size() > 0 && payment != null && employeeId != null) {
//			employeeServ.confirmBookingReserv(users, payment, booking, flights, employeeId);
//			return new ResponseEntity<Booking>(booking, HttpStatus.OK);
//		} else {
//			return new ResponseEntity<Booking>(booking, HttpStatus.BAD_REQUEST);
//		}
//	}
//
//	// Change booking
//	@PutMapping("{employeeId}/bookings")
//	public ResponseEntity<Booking> changeBookingReserv(@PathVariable("employeeId") Long employeeId, @RequestBody Map<String, Object> bookingDetail) {
//		// @RequestBody Payment payment, @RequestBody
//		// Booking booking, @RequestBody List<Flight> flights
//		// Update information in the booking
//		ObjectMapper mapper = new ObjectMapper();
//		Booking booking = mapper.convertValue(bookingDetail.get("booking"), Booking.class);
//		List<Flight> flights = mapper.convertValue(bookingDetail.get("flights"), new TypeReference<List<Flight>>() {});
//
//		if (booking != null && flights != null && flights.size() > 0 && employeeId != null) {
//			employeeServ.changeBookingReserv(booking, flights, employeeId);
//			return new ResponseEntity<Booking>(booking, HttpStatus.OK);
//		} else {
//			return new ResponseEntity<Booking>(booking, HttpStatus.BAD_REQUEST);
//		}
//	}
//
//	// Get booking by confirmationNum and user
//	//http://localhost:8000/traveler/bookings?confirmationNum=&firstName=&lastName=
//	@GetMapping("/bookings")
//	public ResponseEntity<Booking> readBooking(@RequestParam String confirmationNum, @RequestParam String firstName,
//			@RequestParam String lastName) {
//		if (confirmationNum != null && firstName != null && lastName != null) {
//			Booking booking = employeeServ.readBooking(confirmationNum, firstName, lastName);
//			if(booking == null) {
//				return new ResponseEntity<Booking>(HttpStatus.BAD_REQUEST);
//			}
//			return new ResponseEntity<Booking>(booking, HttpStatus.OK);
//		} else {
//			return new ResponseEntity<Booking>(HttpStatus.BAD_REQUEST);
//		}
//	}
//
//	// Cancel booking
//	@DeleteMapping("/{employeeId}/bookings/{bookingId}")
//	public ResponseEntity<String> cancelBooking(@PathVariable("bookingId") Long bookingId, @PathVariable("employeeId") Long employeeId){
//		if (bookingId != null && employeeId != null) {
//			employeeServ.cancelBooking(bookingId, employeeId);
//			return new ResponseEntity<String>("Canceled", HttpStatus.OK);
//		} else {
//			return new ResponseEntity<String>("Failed", HttpStatus.BAD_REQUEST);
//		}
//	}
//}
