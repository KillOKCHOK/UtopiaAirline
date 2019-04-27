package com.utopia.app.idao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.utopia.app.model.Payment;

public interface IPaymentDao extends JpaRepository<Payment,Long> {
	
	@Modifying
	@Query(value = "Update payment set payment_status = false where booking_id = :bookingId", nativeQuery=true)
	void cancelBooking(@Param("bookingId") Long bookingId);
	
	@Modifying
	@Query(value = "Insert into payment (payment_status, booking_id) Values (true, :bookingId)", nativeQuery=true)
	void newPayment(@Param("bookingId") Long bookingId);
	
}
