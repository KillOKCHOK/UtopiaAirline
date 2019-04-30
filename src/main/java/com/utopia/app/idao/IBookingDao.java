package com.utopia.app.idao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.utopia.app.model.Booking;

public interface IBookingDao extends JpaRepository<Booking,Long> {
	
	@Query(value = "SELECT * From booking where confirmation_num =:confirmationCode order by booking_id DESC", nativeQuery = true)
	List<Booking> getBookingByConfirnmationNum(@Param("confirmationCode") String confirmationCode);
	
	@Query(value = "Select * from booking inner join user using(user_id) where confirmation_num =:confirmantionNum and first_name =:firstName and last_name =:lastName", nativeQuery = true)
	List<Booking> getBooking(@Param("confirmantionNum") String confirmantionNum, @Param("firstName") String firstName, @Param("lastName") String lastName);
	
//	@Modifying
//	@Query(value = "Update booking set orderSubmit = false where booking_id = :bookingId", nativeQuery=true)
//	void cancelBooking(@Param("bookingId") Long bookingId);

	@Query(value = "Select * from booking where create_user_id =: employeeId or update_user_id ==: employeeId", nativeQuery = true)
	List<Booking> getBookingByEmployee(@Param("employeeId") Long employeeId);
	
}
