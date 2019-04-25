package com.utopia.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.utopia.app.idao.IBookingDao;
import com.utopia.app.model.Booking;

@Service
@Transactional
public class BookingService {
	
	@Autowired
	private IBookingDao bookingdao;
	
	public Booking getBookingById(long bookingId) {
		return bookingdao.getOne(bookingId);
	}
	
	public List<Booking> getBookingAll() {
		return bookingdao.findAll();
	}
	
	public void createBooking(Booking booking) {
		bookingdao.save(booking);
	}
	
	public void updateBooking(Booking booking) {
		bookingdao.save(booking);
	}
	
	public void deleteBooking(long bookingId) {
		bookingdao.deleteById(bookingId);
	}
	
}
