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

import com.utopia.app.idao.IRoleDao;
import com.utopia.app.model.Role;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
public class RoleRepoTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private IRoleDao roleDao; 
	
	@Test
	public void itShouldNotBeNull() {
		Role role = new Role();
		role.setRoleName("Agent");
		role = entityManager.persistAndFlush(role);
		
		Role expected = roleDao.findById(role.getRoleId()).get();
		assertThat(expected).isNotNull();
	}
	
	@Test
	public void itShouldSaveRole() {
		Role role = new Role();
		role.setRoleName("Agent");
		role = entityManager.persistAndFlush(role);
		
		Role expected = roleDao.findById(role.getRoleId()).get();
		assertThat(expected).isEqualTo(role);
	}
}
