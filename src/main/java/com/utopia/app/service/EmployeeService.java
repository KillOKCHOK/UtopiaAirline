package com.utopia.app.service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
import com.utopia.app.model.User;
import com.utopia.app.src.RandomString;

@Service
@Transactional
public class EmployeeService {

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

	public User getEmployee(Long employeeId) {
		try {
			return userDao.getOne(employeeId);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public User updateEmployee(User employee) {
		try {
			return userDao.save(employee);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Booking> getBookings(Long employeeId) {
		try {
			return bookingDao.getBookingByEmployee(employeeId);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Airport> getAirportList(String name) {
		String serchName = "%" + name + "%";
		try {
			return airportDao.getAirportListByName(serchName);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Flight> getFightList(Date date, Long depAirportId, Long arrAirportId) {
		try {
			return flightDao.getFlightList(date, depAirportId, arrAirportId);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Transactional
	public void createBookingReserv(List<Flight> flights, int travelerNumber) {
		try {
			// reserve seat (decrease flight capacity)
			for(int flight = 0; flight < flights.size(); flight++) {
				System.out.println(flights.get(flight).getDepAirport());
				flightDao.decreaseFlightCapacity(travelerNumber, flights.get(flight).getFlightId());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Transactional
	public Booking confirmBookingReserv(List<User> users, Payment payment, Booking booking, List<Flight> flights, Long employeeId) {
		try {
			
			// Add booking & its user
			//// add user in booking
			User user = booking.getUser();
			
			//// if user didn't login
			if(user.getUserId() == null) {	
				userDao.save(user);
				
				//// get user id and set into booking
				Long id = userDao.getUserIdByNameAndEmail(user.getFirstName(),user.getLastName(), user.getEmail()).get(0).getUserId();
				user.setUserId(id);
				booking.setUser(user);
			}
			
			//// set employee to creator
			User employee = new User();
			employee.setUserId(employeeId);
			booking.setCreateUser(employee);
			
			//// generate booking confirmationNum
			RandomString rString = new RandomString();
			String confirmationNum = rString.nextString();
			Date date = new Date();

			//// Setup new booking
			booking.setConfirmationCode(confirmationNum);
			booking.setOrderSubmit(true);
			booking.setCreateDate(date);
			bookingDao.save(booking);

			// Get bookingId
			booking = bookingDao.getBookingByConfirnmationNum(confirmationNum).get(0);

			//Payment
			payment.setBooking(booking);
			payment.setPaymentStatus(true);
			paymentDao.save(payment);
			
			//generate ticket
			//// add user in booking
			for(int traveler = 0; traveler < users.size(); traveler++) {
				Long userId = userDao.save(users.get(traveler)).getUserId();
				for(int flight = 0; flight < flights.size(); flight++) {
					ticketDao.createTicket(booking.getBookingId(), flights.get(flight).getFlightId(), userId);
				}
			}
			return booking;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Booking readBooking(String confirmantionNum, String firstName, String lastName) {
		try {
			//get booking by confirmantionNum
			List<Booking> bookings = bookingDao.getBooking(confirmantionNum, firstName,lastName);
			if(bookings.size() == 0) {
				return null;
			}
			//get tickets
			Booking booking = bookings.get(0);
			booking.setTickets(ticketDao.findAllByBooking(booking));
			return booking;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Transactional
	public Booking changeBookingReserv(Booking booking, List<Flight> flights, Long employeeId) {
		try {
			// update booking user info
			userDao.save(booking.getUser());
			
			// generate and update new confirmationNum
			RandomString rString = new RandomString();
			String confirmationNum = rString.nextString();
			Date date = new Date();
			
			User employee = new User();
			employee.setUserId(employeeId);

			booking.setConfirmationCode(confirmationNum);
			booking.setUpdateUser(employee);
			booking.setUpdateDate(date);
			bookingDao.save(booking);

			// cancel previous payment
			paymentDao.cancelBooking(booking.getBookingId());
			
			// create new payment
			//// need more payment info in the future
			paymentDao.newPayment(booking.getBookingId());
			
			// cancel ticket and increase flight capacity
			Set<Long> users = new HashSet<Long>();
			for(int ticketNum = 0; ticketNum < booking.getTickets().size(); ticketNum++) {
				users.add(booking.getTickets().get(ticketNum).getUser().getUserId());
				ticketDao.deleteById(booking.getTickets().get(ticketNum).getTicketId());
				System.out.println(booking.getTickets().get(ticketNum).getFlight().getFlightId());
				flightDao.increateFlightCapacity(1, booking.getTickets().get(ticketNum).getFlight().getFlightId());
			}
			
			//generate new tickets
			//// add user in booking
			for(Long userId : users) {
				for(int flight = 0; flight < flights.size(); flight++) {
					ticketDao.createTicket(booking.getBookingId(), flights.get(flight).getFlightId(), userId);
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
	public void cancelBooking(Long bookingId, Long employeeId) {
		try {
			
			//add flight capacity back
			//// get the number of traveler
			int num = userDao.getUsersByBooking(bookingId).size();
			
			//// get flights
			List<Flight> flights = flightDao.getFlightByBookingId(bookingId);
			
			//// increase flight capacity 
			for(int i = 0; i<flights.size(); i++) {
				flightDao.increateFlightCapacity(num, flights.get(i).getFlightId());
			}
			
			// update booking status(orderSubmit) to false
			Date date = new Date();
			User employee = userDao.getOne(employeeId);
			
			Booking booking = bookingDao.getOne(bookingId);
			booking.setOrderSubmit(false);
			booking.setUpdateDate(date);
			booking.setUpdateUser(employee);
			bookingDao.save(booking);
			
			// update payment status(payment_status) to false
			paymentDao.cancelBooking(bookingId);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
