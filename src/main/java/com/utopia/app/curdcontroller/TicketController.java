package com.utopia.app.curdcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.utopia.app.model.Ticket;
import com.utopia.app.service.TicketService;

@RestController
@RequestMapping("/adm")
public class TicketController {
	
	@Autowired
	private TicketService ticketRepo;
	
	@GetMapping("/tickets")
	public ResponseEntity<List<Ticket>> getAllTicket(){
		try {
			List<Ticket> tickets = ticketRepo.getTicketAll();
			return new ResponseEntity<List<Ticket>>(tickets, HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<List<Ticket>>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/ticket/{ticketId}")
	public ResponseEntity<Ticket> getTicketById(@PathVariable long ticketId){
		try {
			Ticket ticket = ticketRepo.getTicketById(ticketId);
			return new ResponseEntity<Ticket>(ticket,HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<Ticket>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("/ticket")
	public ResponseEntity<Ticket> updateTicket(@RequestBody Ticket ticket){
		try {
			ticketRepo.updateTicket(ticket);
			return new ResponseEntity<Ticket>(HttpStatus.ACCEPTED);
		}catch(Exception e) {
			return new ResponseEntity<Ticket>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/ticket")
	public ResponseEntity<Ticket> addTicket(@RequestBody Ticket ticket){
		try {
			ticketRepo.createTicket(ticket);
			return new ResponseEntity<Ticket>(HttpStatus.CREATED);
		}catch(Exception e) {
			return new ResponseEntity<Ticket>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping("/ticket/{ticketId}")
	public ResponseEntity<Ticket> deleteTicket(@PathVariable long ticketId){
		try {
			ticketRepo.deleteTicket(ticketId);
			return new ResponseEntity<Ticket>(HttpStatus.ACCEPTED);
		}catch(Exception e) {
			return new ResponseEntity<Ticket>(HttpStatus.BAD_REQUEST);
		}
	}
	
}
