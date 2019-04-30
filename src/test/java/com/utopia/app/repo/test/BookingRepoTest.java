package com.utopia.app.repo.test;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.utopia.app.idao.IBookingDao;
import com.utopia.app.model.Booking;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
public class BookingRepoTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private IBookingDao bookingDao;
	
	@Test
	public void itShouldSaveBooking() {
		Booking booking = new Booking();
		booking.setConfirmationCode("abcde12345fghij67890");
		booking.setOrderSubmit(false);
		booking = entityManager.persistAndFlush(booking);

		Booking expected = bookingDao.findById(booking.getBookingId()).get();
		assertThat(expected).isEqualTo(booking);
	}
	
	@Test
	public void itShouldGetConfirmationCodeOfBooking() {
		Booking booking = new Booking();
		booking.setConfirmationCode("abcde12345fghij67890");
		booking.setOrderSubmit(false);
		booking = entityManager.persistAndFlush(booking);

		Booking expected = bookingDao.findById(booking.getBookingId()).get();
		assertThat(expected.getConfirmationCode()).isEqualTo("abcde12345fghij67890");
	}
	
	@Test
	public void itShouldNotSubmitOrder() {
		Booking booking = new Booking();
		booking.setConfirmationCode("abcde12345fghij67890");
		booking.setOrderSubmit(false);
		booking = entityManager.persistAndFlush(booking);

		Booking expected = bookingDao.findById(booking.getBookingId()).get();
		assertThat(expected.getOrderSubmit()).isEqualTo(false);
	}
}
