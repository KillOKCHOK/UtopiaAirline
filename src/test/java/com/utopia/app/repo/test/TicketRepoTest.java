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

import com.utopia.app.idao.ITicketDao;
import com.utopia.app.model.Ticket;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
public class TicketRepoTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private ITicketDao trepo;
	
	@Test
	public void it_should_save_ticket() {
		Ticket t = new Ticket();
		t = entityManager.persistAndFlush(t);
		
		Ticket tTest = trepo.findById(t.getTicketId()).get();
		assertThat(tTest).isEqualTo(t);
	}
	
	@Test
	public void it_should_not_be_null() {
		Ticket t = new Ticket();
		t = entityManager.persistAndFlush(t);
		
		Ticket tTest = trepo.findById(t.getTicketId()).get();
		assertThat(tTest).isNotNull();
	}
}
