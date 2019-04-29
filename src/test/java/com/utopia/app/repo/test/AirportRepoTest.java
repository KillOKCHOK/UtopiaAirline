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
	private IAirportDao arepo;

	@Test
	public void it_should_save_airport() {
		Airport a = new Airport();
		a.setAirportCode("IAD");
		a = entityManager.persistAndFlush(a);

		Airport aTest = arepo.findById(a.getAirportId()).get();
		assertThat(aTest).isEqualTo(a);
	}
	
	@Test
	public void it_should_find_airport_byAirportName() {
		Airport a = new Airport();
		a.setAirportCode("IAD");
		a.setAirportName("Dulles");
		City c = new City();
		c.setCityName("Washington");
		c.setCountry("USA");
		c = entityManager.persistAndFlush(c);
		a.setCity(c);
		a=entityManager.persistAndFlush(a);
		
		List<Airport> airports = arepo.getAirportListByName("Washington");
		
		assertThat(airports.size()).isEqualTo(1);
	}
}
