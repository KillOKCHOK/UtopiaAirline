package com.utopia.app.idao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.utopia.app.model.Booking;
import com.utopia.app.model.Ticket;

public interface ITicketDao extends JpaRepository<Ticket,Long> {
	
	@Modifying
	@Query(value = "Insert into ticket (booking_id, flight_id, user_id) Values (:bookingId, :flightId, :userId)", nativeQuery = true)
	void createTicket(@Param("bookingId") Long bookingId,@Param("flightId") Long flightId,@Param("userId") Long userId );
	
	List<Ticket> findAllByBooking(@Param("booking") Booking booking);
}
