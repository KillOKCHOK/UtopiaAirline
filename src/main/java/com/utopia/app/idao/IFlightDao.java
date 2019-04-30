package com.utopia.app.idao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.utopia.app.model.Flight;

public interface IFlightDao extends JpaRepository<Flight,Long> {

	
	@Query(value = "SELECT * From flight " + 
			"where dep_airport_id =:depAirportId and arr_airport_id =:arrAirportId " + 
			"and dep_dateTime >=:date and dep_dateTime <DATE_ADD(:date, INTERVAL 1 DAY) "
			+ "order by dep_dateTime", nativeQuery = true)
	List<Flight> getFlightList(@Param("date") Date date, @Param("depAirportId") Long depAirportId, @Param("arrAirportId") Long arrAirportId);
	
	@Modifying
	@Query(value = "update flight f set f.capacity = f.capacity - :num where f.flight_id = :flightId", nativeQuery = true)
	void decreaseFlightCapacity(@Param("num") int num, @Param("flightId") Long flightId);
	
	@Query(value = "Select * from flight where flight_id in "
			+ "(SELECT flight_id FROM ticket where booking_id = :bookingId Group by flight_id)", nativeQuery = true)
	List<Flight> getFlightByBookingId(@Param("bookingId") Long bookingId);
	
	@Modifying
	@Query(value = "update flight f set f.capacity = f.capacity + :num where f.flight_id = :flightId", nativeQuery = true)
	void increateFlightCapacity(@Param("num") int num, @Param("flightId") Long flightId);
}
