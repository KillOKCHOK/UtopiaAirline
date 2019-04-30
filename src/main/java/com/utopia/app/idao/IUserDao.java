package com.utopia.app.idao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.utopia.app.model.User;

public interface IUserDao extends JpaRepository<User,Long> {

	@Query(value = "SELECT * From user where first_name =:firstName and last_name =:lastName and email =:email order by user_id DESC", nativeQuery = true)
	List<User> getUserIdByNameAndEmail(@Param("firstName") String firstName, @Param("lastName") String lastName,
			@Param("email") String email);
	
	@Query(value = "Select * from user where user_id in "
			+ "(SELECT user_id from ticket where booking_id =:bookingId group by user_id)", nativeQuery = true)
	List<User> getUsersByBooking(@Param("bookingId") Long bookingId);
	
	
}
