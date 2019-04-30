package com.utopia.app.repo.test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.utopia.app.idao.IFlightDao;
import com.utopia.app.model.Airport;
import com.utopia.app.model.Flight;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
public class FlightRepoTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private IFlightDao flightDao;
	
	@Test
	public void itShouldNotBeNull() {
		Flight flight = new Flight();
		flight.setCapacity(200);
		flight.setDepDateTime(new Date());
		flight = entityManager.persistAndFlush(flight);
		
		Flight expected = flightDao.findById(flight.getFlightId()).get();
		
		assertThat(expected).isNotNull();
	}
	
	@Test
	public void itShouldSaveFlight() {
		Flight flight = new Flight();
		flight.setCapacity(200);
		flight.setDepDateTime(new Date());
		flight = entityManager.persistAndFlush(flight);

		Flight fTest = flightDao.findById(flight.getFlightId()).get();
		assertThat(fTest).isEqualTo(flight);
	}
	
	@Test 
	public void itShouldGetListOfFlights() {		
		Date date = new Date();
		int days = 2;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_YEAR, days);
		Date depatureDate = calendar.getTime();
				
		Airport airport1 = new Airport();
		airport1.setAirportCode("SEA");
		airport1.setAirportName("SeaTac");
		airport1 = entityManager.persistAndFlush(airport1);
		Airport airport2 = new Airport();
		airport2.setAirportCode("DCA");
		airport2.setAirportName("Reagan");
		airport2 = entityManager.persistAndFlush(airport2);
		
		Flight flight = new Flight();
		flight.setCapacity(200);
		flight.setDepDateTime(depatureDate);
		flight.setDepAirport(airport1);
		flight.setArrAirport(airport2);
		flight = entityManager.persistAndFlush(flight);
		
		long id1 = airport1.getAirportId();
		long id2 = airport2.getAirportId();
		List<Flight> expected = flightDao.getFlightList(depatureDate, id1, id2);
		assertThat(expected.size()).isEqualTo(1);
	}
	
}
