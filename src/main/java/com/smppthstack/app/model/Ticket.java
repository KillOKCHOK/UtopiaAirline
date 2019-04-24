package com.smppthstack.app.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity( name = "ticket")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="ticket_id")
    private Integer ticketId;
    
    @ManyToOne
    @JoinColumn(name = "user_id" )
    private User user;
    
    @ManyToOne
    @JoinColumn(name = "flight_id" )
    private Flight flight;
    
//    @ManyToOne(fetch=FetchType.LAZY )
//    @JoinColumn(name="booking_id")
//    private Booking booking;
    
    

//    public Booking getBooking() {
//		return booking;
//	}
//
//	public void setBooking(Booking booking) {
//		this.booking = booking;
//	}

	public Integer getTicketId() {
        return ticketId;
    }

    public void setTicketId(Integer ticketId) {
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
