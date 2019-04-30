package com.utopia.app.idao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.utopia.app.model.Booking;
import com.utopia.app.model.Ticket;

public interface ITicketDao extends JpaRepository<Ticket,Long> {
	
	@Modifying
	@Query(value = "Insert into ticket (booking_id, flight_id, user_id, create_by, create_date) Values (:bookingId, :flightId, :travelerId, :createUserId, :createDate)", nativeQuery = true)
	void createTicket(@Param("bookingId") Long bookingId,@Param("flightId") Long flightId,@Param("travelerId") Long travelerId,
			@Param("createUserId") Long createUserId, @Param("createDate") Date createDate);
	
	List<Ticket> findAllByBooking(@Param("booking") Booking booking);
}
