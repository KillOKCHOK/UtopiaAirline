package com.utopia.app.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity( name = "booking")
public class Booking {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="booking_id")
	private Long bookingId;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;
	
	@Column(name = "confirmation_num")
	private String confirmationNum;
	
	@Column
	private Boolean orderSubmit;
	
	@ManyToOne
	@JoinColumn(name="create_user_id")
	private User createUser;
	
	@ManyToOne
	@JoinColumn(name="update_user_id")
	private User updateUser;
	
	@Column(name = "create_dateTime")
    @Temporal(TemporalType.TIMESTAMP)
	private Date createDate;
	
	@Column(name = "update_dateTime")
    @Temporal(TemporalType.TIMESTAMP)
	private Date updateDate;
	
	@OneToMany(mappedBy="booking")
	private List<Ticket> tickets;
	
	@OneToMany(mappedBy="booking")
	@JsonIgnore
	private List<Payment> payment;

	public Long getBookingId() {
		return bookingId;
	}

	public void setBookingId(long bookingId) {
		this.bookingId = bookingId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getConfirmationCode() {
		return confirmationNum;
	}

	public void setConfirmationCode(String confirmationNum) {
		this.confirmationNum = confirmationNum;
	}

	public Boolean getOrderSubmit() {
		return orderSubmit;
	}

	public void setOrderSubmit(Boolean orderSubmit) {
		this.orderSubmit = orderSubmit;
	}

	public User getCreateUser() {
		return createUser;
	}

	public void setCreateUser(User createUser) {
		this.createUser = createUser;
	}

	public User getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(User updateUser) {
		this.updateUser = updateUser;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public List<Ticket> getTickets() {
		return tickets;
	}

	public void setTickets(List<Ticket> tickets) {
		this.tickets = tickets;
	}

	public List<Payment> getPayment() {
		return payment;
	}

	public void setPayment(List<Payment> payment) {
		this.payment = payment;
	}

}
