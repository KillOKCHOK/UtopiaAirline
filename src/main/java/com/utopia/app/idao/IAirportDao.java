package com.utopia.app.idao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.utopia.app.model.Airport;

public interface IAirportDao extends JpaRepository<Airport,Long> {

	@Query(value = "SELECT * FROM city inner join airport using(city_id) "
			+ "WHERE city_name LIKE CONCAT('%',:name,'%') or country LIKE CONCAT('%',:name,'%') "
			+ "or airport_code LIKE CONCAT('%',:name,'%')", nativeQuery = true)
	List<Airport> getAirportListByName(@Param("name") String name);
	
	
}
