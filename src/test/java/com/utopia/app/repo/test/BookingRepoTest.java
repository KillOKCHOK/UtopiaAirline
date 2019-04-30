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
	private IBookingDao brepo;
	
	@Test
	public void it_should_save_booking() {
		Booking b = new Booking();
		b.setConfirmationCode("abcde12345fghij67890");
		b.setOrderSubmit(false);
		b = entityManager.persistAndFlush(b);

		assertThat(brepo.findById(b.getBookingId()).get()).isEqualTo(b);
	}
	
	@Test
	public void it_should_get_confirmationCode_of_booking() {
		Booking b = new Booking();
		b.setConfirmationCode("abcde12345fghij67890");
		b.setOrderSubmit(false);
		b = entityManager.persistAndFlush(b);

		assertThat(brepo.findById(b.getBookingId()).get().getConfirmationCode()).isEqualTo("abcde12345fghij67890");
	}
	
	@Test
	public void it_should_not_submit_order() {
		Booking b = new Booking();
		b.setConfirmationCode("abcde12345fghij67890");
		b.setOrderSubmit(false);
		b = entityManager.persistAndFlush(b);

		assertThat(brepo.findById(b.getBookingId()).get().getOrderSubmit()).isEqualTo(false);
	}
}
