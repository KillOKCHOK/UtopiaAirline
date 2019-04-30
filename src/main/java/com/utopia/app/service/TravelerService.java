package com.utopia.app.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.utopia.app.idao.IAirportDao;
import com.utopia.app.idao.IBookingDao;
import com.utopia.app.idao.IFlightDao;
import com.utopia.app.idao.IPaymentDao;
import com.utopia.app.idao.ITicketDao;
import com.utopia.app.idao.IUserDao;
import com.utopia.app.model.Airport;
import com.utopia.app.model.Booking;
import com.utopia.app.model.Flight;
import com.utopia.app.model.Payment;
import com.utopia.app.model.Ticket;
import com.utopia.app.model.User;
import com.utopia.app.src.RandomString;

@Service
@Transactional
public class TravelerService {

	@Autowired
	private IAirportDao airportDao;
	@Autowired
	private IFlightDao flightDao;
	@Autowired
	private IBookingDao bookingDao;
	@Autowired
	private ITicketDao ticketDao;
	@Autowired
	private IUserDao userDao;
	@Autowired
	private IPaymentDao paymentDao;
	
	private long adminId = 1;
	
	public List<Airport> getAirportList(String name) {
		String serchName = "%" + name + "%";
		try {
			return airportDao.getAirportListByName(serchName);
		} catch (Exception e) {
			
			return null;
		}
	}

	public List<Flight> getFightList(String sdate, Long depAirportId, Long arrAirportId) {
		try {
			Date date = new SimpleDateFormat("yyyy-MM-dd").parse(sdate);
			return flightDao.getFlightList(date, depAirportId, arrAirportId);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Transactional
	public List<Flight> createBookingReserv(Map<String, Object> flightsReserv) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			List<Flight> flights = mapper.convertValue(flightsReserv.get("flights"), new TypeReference<List<Flight>>() {});
			int travelerNumber = mapper.convertValue(flightsReserv.get("travelerNumber"), Integer.class);
			
			Date date = new Date();
			User admin = new User();
			admin.setUserId(adminId);
			
			// reserve seat (decrease flight capacity)
			for(int flight = 0; flight < flights.size(); flight++) {
				flightDao.decreaseFlightCapacity(travelerNumber, flights.get(flight).getFlightId(),
						admin.getUserId(), date);
			}
			return flights;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Transactional
	public Booking confirmBookingReserv(Map<String, Object> bookingDetail) {
		try {
			
			ObjectMapper mapper = new ObjectMapper();
			List<User> users = mapper.convertValue(bookingDetail.get("users"), new TypeReference<List<User>>() {});
			Booking booking = mapper.convertValue(bookingDetail.get("booking"), Booking.class);
			List<Flight> flights = mapper.convertValue(bookingDetail.get("flights"), new TypeReference<List<Flight>>() {});
			Payment payment = mapper.convertValue(bookingDetail.get("payment"), Payment.class);
			
			Date date = new Date();
			User admin = new User();
			admin.setUserId(adminId);

			// Add booking & its user
			//// add user in booking
			User user = booking.getUser();
			user.setCreateUser(admin);
			user.setCreateDate(date);
			user = userDao.save(user);
				
			//// get user id and set into booking
			booking.setUser(user);
			booking.setCreateUser(user);
			
			//// generate booking confirmationNum
			RandomString rString = new RandomString();
			String confirmationNum = rString.nextString();

			//// Setup new booking
			booking.setConfirmationNum(confirmationNum);
			booking.setOrderSubmit(true);
			booking.setCreateDate(date);
			booking = bookingDao.save(booking);

			//Payment
			payment.setBooking(booking);
			payment.setPaymentStatus(true);
			payment.setCreateDate(date);
			payment.setCreateUser(user);
			paymentDao.save(payment);
			
			//generate tickets
			//// add user in booking
			for(int travelerNum = 0; travelerNum < users.size(); travelerNum++) {
				User traveler = users.get(travelerNum);
				traveler.setCreateDate(date);
				traveler.setCreateUser(admin);
				for(int flight = 0; flight < flights.size(); flight++) {
					ticketDao.createTicket(booking.getBookingId(), flights.get(flight).getFlightId(),
							traveler.getUserId(), admin.getUserId(), date);
				}
			}
			return bookingDao.getOne(booking.getBookingId());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Booking readBooking(String confirmantionNum) {
		try {
			//get booking by confirmantionNum
			Booking booking = bookingDao.findByConfirmationNum(confirmantionNum);
			
			return booking;
		} catch (Exception e) {
			//e.printStackTrace();
			return null;
		}
	}
	
	@Transactional
	public Booking changeBookingReserv(Map<String, Object> bookingDetail) {
		try {
			
			ObjectMapper mapper = new ObjectMapper();
			Booking booking = mapper.convertValue(bookingDetail.get("booking"), Booking.class);
			Payment newPayment = mapper.convertValue(bookingDetail.get("newPayment"), Payment.class);
			List<Flight> flights = mapper.convertValue(bookingDetail.get("flights"), new TypeReference<List<Flight>>() {});
			
			Date date = new Date();
			User admin = new User();
			admin.setUserId(adminId);
			
			// update booking user info
			User user = booking.getUser();
			user.setUpdateDate(date);
			user.setUpdateUser(admin);
			userDao.save(user);
			
			// generate and update new confirmationNum
			RandomString rString = new RandomString();
			String confirmationNum = rString.nextString();

			booking.setConfirmationNum(confirmationNum);
			booking.setUpdateUser(booking.getUser());
			booking.setUpdateDate(date);
			bookingDao.save(booking);

			// cancel previous payment
			Payment oldPayment = (Payment) booking.getPayment();
			oldPayment.setPaymentStatus(false);
			oldPayment.setUpdateDate(date);
			oldPayment.setUpdateUser(admin);
			paymentDao.save(oldPayment);
			
			// create new payment
			//// need more payment info in the future
			newPayment.setBooking(booking);
			newPayment.setPaymentStatus(true);
			newPayment.setUpdateDate(date);
			newPayment.setUpdateUser(admin);
			paymentDao.save(newPayment);
			
			// cancel ticket and increase flight capacity
			Set<User> travelers = new HashSet<User>();
			for(int ticketNum = 0; ticketNum < booking.getTickets().size(); ticketNum++) {
				// get all travelers in this booking
				travelers.add(booking.getTickets().get(ticketNum).getUser());
				
				// delete tickets
				ticketDao.deleteById(booking.getTickets().get(ticketNum).getTicketId());
				
				// increase flight capacity
				flightDao.increaseFlightCapacity(1, booking.getTickets().get(ticketNum).getFlight().getFlightId(),
						admin.getUserId(), date);
			}
			
			//generate new tickets
			//// add user in booking
			for(User traveler : travelers) {
				for(int flight = 0; flight < flights.size(); flight++) {
					Ticket ticket = new Ticket();
					ticket.setBooking(booking);
					ticket.setFlight(flights.get(flight));
					ticket.setUser(traveler);
					ticket.setCreateDate(date);
					ticket.setCreateUser(admin);
					ticketDao.save(ticket);
				}
			}
			
			// get new booking info
			Booking newBooking = bookingDao.getOne(booking.getBookingId());
			return newBooking;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Transactional
	public void cancelBooking(Long bookingId) {
		try {
			
			Date date = new Date();
			User admin = new User();
			admin.setUserId(adminId);
			
			//add flight capacity back
			//// get the number of traveler
			int num = userDao.getUsersByBooking(bookingId).size();
			
			//// get flights
			List<Flight> flights = flightDao.getFlightByBookingId(bookingId);
			
			//// increase flight capacity 
			for(int i = 0; i<flights.size(); i++) {
				flightDao.increaseFlightCapacity(num, flights.get(i).getFlightId(),
						admin.getUserId(), date);
			}
			
			// update booking status(orderSubmit) to false
			Booking booking = bookingDao.getOne(bookingId);
			booking.setOrderSubmit(false);
			booking.setUpdateDate(date);
			booking.setUpdateUser(booking.getUser());
			bookingDao.save(booking);
			
			// update payment status(payment_status) to false
			Payment oldPayment = (Payment) booking.getPayment();
			oldPayment.setPaymentStatus(false);
			oldPayment.setUpdateDate(date);
			oldPayment.setUpdateUser(admin);
			paymentDao.save(oldPayment);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
