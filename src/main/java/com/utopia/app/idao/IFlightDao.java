package com.utopia.app.idao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.utopia.app.model.Flight;

public interface IFlightDao extends JpaRepository<Flight,Long> {

	
	@Query(value = "SELECT * From flight " + 
			"where dep_airport_id =:depAirportId and arr_airport_id =:arrAirportId " + 
			"and dep_dateTime >=:date and dep_dateTime <DATE_ADD(:date, INTERVAL 1 DAY)", nativeQuery = true)
	List<Flight> getFlightList(@Param("date") Date date, @Param("depAirportId") Long depAirportId, @Param("arrAirportId") Long arrAirportId);
	
	
}
