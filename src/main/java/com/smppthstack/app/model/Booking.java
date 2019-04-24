package com.smppthstack.app.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Booking {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="booking_id")
	private int bookingId;
	@ManyToOne
	@JoinColumn(name="user_	id")
	private User user;
	@Column
	private String confirmationCode;
	@Column
	private Boolean orderSubmit;
	@ManyToOne
	@JoinColumn(name="payment_id")
	private Payment payment;
	
//	@OneToMany(mappedBy="ticketId")
//	private List<Ticket> tickets;
	
	

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getConfirmationCode() {
		return confirmationCode;
	}

	public void setConfirmationCode(String confirmationCode) {
		this.confirmationCode = confirmationCode;
	}

	public Boolean getOrderSubmit() {
		return orderSubmit;
	}

	public void setOrderSubmit(Boolean orderSubmit) {
		this.orderSubmit = orderSubmit;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	public int getBookingId() {
		return bookingId;
	}

	public void setBookingId(int bookingId) {
		this.bookingId = bookingId;
	}

//	public List<Ticket> getTickets() {
//		return tickets;
//	}
//
//	public void setTickets(List<Ticket> tickets) {
//		this.tickets = tickets;
//	}
	
}
