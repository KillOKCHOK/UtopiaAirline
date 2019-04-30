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

import com.utopia.app.idao.IUserDao;
import com.utopia.app.model.User;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
public class UserRepoTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private IUserDao urepo;
	
	@Test
	public void it_should_save_user() {
		User u = new User();
		u.setUserId((long) 1);
		u.setEmail("abc@gmail.com");
		u.setUsername("Daniel");
		u = entityManager.persistAndFlush(u);
		
		assertThat(urepo.findById(u.getUserId()).get()).isEqualTo(u);
	}
}
