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
	private ICityDao crepo;
	
	@Test
	public void it_should_save_city() {
		City c = new City();
		c.setCityName("Seattle");
		c.setCountry("USA");
		c = entityManager.persistAndFlush(c);
		
		assertThat(crepo.findById(c.getCityId()).get()).isEqualTo(c);
	}
	
	@Test
	public void it_should_not_be_null() {
		City c = new City();
		c.setCityName("Buffalo");
		c.setCountry("USA");
		c = entityManager.persistAndFlush(c);
		
		assertThat(crepo.findById(c.getCityId()).get()).isNotNull();
	}
}
