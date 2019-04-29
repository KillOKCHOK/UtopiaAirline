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

@Entity
@Table(name = "payment")
public class Payment {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "payment_id")
	private Long paymentId;

	@Column(name = "payment_status")
	private Boolean paymentStatus;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "booking_id")
	private Booking booking;

	public Long getPaymentId() {
		return paymentId;
	}

	public Boolean getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(Boolean paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public Booking getBooking() {
		return booking;
	}

	public void setBooking(Booking booking) {
		this.booking = booking;
	}

	public void setPaymentId(long paymentId) {
		this.paymentId = paymentId;
	}

}
