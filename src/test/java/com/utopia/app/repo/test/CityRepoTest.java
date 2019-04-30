package com.utopia.app.repo.test;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.utopia.app.idao.ICityDao;
import com.utopia.app.model.City;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
public class CityRepoTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private ICityDao cityDao;
	
	@Test
	public void itShouldSaveCity() {
		City city = new City();
		city.setCityName("Seattle");
		city.setCountry("USA");
		city = entityManager.persistAndFlush(city);
		
		City expected = cityDao.findById(city.getCityId()).get();
		assertThat(expected).isEqualTo(city);
	}
	
	@Test
	public void itShouldNotBeNull() {
		City city = new City();
		city.setCityName("Buffalo");
		city.setCountry("USA");
		city = entityManager.persistAndFlush(city);
		
		City expected = cityDao.findById(city.getCityId()).get();
		assertThat(expected).isNotNull();
	}
}
