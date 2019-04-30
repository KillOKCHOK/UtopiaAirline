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
	private ITicketDao ticketDao;
	
	@Test
	public void itShouldSaveTicket() {
		Ticket ticket = new Ticket();
		ticket = entityManager.persistAndFlush(ticket);
		
		Ticket expected = ticketDao.findById(ticket.getTicketId()).get();
		assertThat(expected).isEqualTo(ticket);
	}
	
	@Test
	public void itShouldNotBeNull() {
		Ticket ticket = new Ticket();
		ticket = entityManager.persistAndFlush(ticket);
		
		Ticket expected = ticketDao.findById(ticket.getTicketId()).get();
		assertThat(expected).isNotNull();
	}
}
