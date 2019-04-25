package com.utopia.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.utopia.app.idao.ITicketDao;
import com.utopia.app.model.Ticket;

@Service
@Transactional
public class TicketService {
	
	@Autowired
	private ITicketDao ticketdao;
	
	public Ticket getTicketById(long ticketId) {
		return ticketdao.getOne(ticketId);
	}
	
	public List<Ticket> getTicketAll() {
		return ticketdao.findAll();
	}
	
	public void createTicket(Ticket ticket) {
		ticketdao.save(ticket);
	}
	
	public void updateTicket(Ticket ticket) {
		ticketdao.save(ticket);
	}
	
	public void deleteTicket(long ticketId) {
		ticketdao.deleteById(ticketId);
	}
	
}
