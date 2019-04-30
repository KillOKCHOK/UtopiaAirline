package com.utopia.app.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "ticket")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="ticket_id")
    private Long ticketId;
    
    @ManyToOne(fetch=FetchType.LAZY )
    @JoinColumn(name = "user_id" )
    private User user;
    
    @ManyToOne(fetch=FetchType.LAZY )
    @JoinColumn(name = "flight_id" )
    private Flight flight;
    
    @ManyToOne(fetch=FetchType.LAZY )
    @JoinColumn(name="booking_id")
    @JsonIgnore
    private Booking booking;
    
    

    public Booking getBooking() {
		return booking;
	}

	public void setBooking(Booking booking) {
		this.booking = booking;
	}

	public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }
    
}
