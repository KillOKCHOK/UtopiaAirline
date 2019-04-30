package com.utopia.app.repo.test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Calendar;
import java.util.Date;

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
	private IFlightDao frepo;
	
	@Test
	public void it_should_not_be_null() {
		Flight f = new Flight();
		f.setCapacity(200);
		f.setDepDateTime(new Date());
		f = entityManager.persistAndFlush(f);
		
		assertThat(frepo.findById(f.getFlightId()).get()).isNotNull();
	}
	
	@Test
	public void it_should_save_flight() {
		Flight f = new Flight();
		f.setCapacity(200);
		f.setDepDateTime(new Date());
		f = entityManager.persistAndFlush(f);
		
		assertThat(frepo.findById(f.getFlightId()).get()).isEqualTo(f);
	}
	
	@Test 
	public void it_should_get_list_of_flights() {		
		Date date = new Date();
		int dayDep = 2;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_YEAR, dayDep);
		Date dep = calendar.getTime();
				
		Airport a = new Airport();
		a.setAirportCode("SEA");
		a.setAirportName("SeaTac");
		a = entityManager.persistAndFlush(a);
		Airport a2 = new Airport();
		a2.setAirportCode("DCA");
		a2.setAirportName("Reagan");
		a2 = entityManager.persistAndFlush(a2);
		
		Flight f = new Flight();
		f.setCapacity(200);
		f.setDepDateTime(dep);
		f.setDepAirport(a);
		f.setArrAirport(a2);
		f = entityManager.persistAndFlush(f);
		
		long id1 = a.getAirportId();
		long id2 = a2.getAirportId();
				
		assertThat(frepo.getFlightList(dep, id1, id2).size()).isEqualTo(1);
	}
}
