package com.utopia.app.repo.test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.utopia.app.idao.IAirportDao;
import com.utopia.app.model.Airport;
import com.utopia.app.model.City;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
public class AirportRepoTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private IAirportDao airportDao;

	@Test
	public void itShouldSaveAirport() {
		Airport airport = new Airport();
		airport.setAirportCode("IAD");
		airport = entityManager.persistAndFlush(airport);

		Airport expected = airportDao.findById(airport.getAirportId()).get();
		assertThat(expected).isEqualTo(airport);
	}
	
	@Test
	public void itShouldFindAirportByAirportName() {
		Airport airport = new Airport();
		airport.setAirportCode("IAD");
		airport.setAirportName("Dulles");
		City city = new City();
		city.setCityName("Washington");
		city.setCountry("USA");
		city = entityManager.persistAndFlush(city);
		airport.setCity(city);
		airport=entityManager.persistAndFlush(airport);
		
		List<Airport> expected = airportDao.getAirportListByName("Washington");
		
		assertThat(expected.size()).isEqualTo(1);
	}
}
