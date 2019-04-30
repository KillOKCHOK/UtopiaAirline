package com.utopia.app.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name="flight")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "flightId")
public class Flight {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="flight_id")
    private Long flightId;
    
    @Column(name = "dep_dateTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date depDateTime;
    
    @Column(name = "arr_dateTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date arrDateTime;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="dep_airport_id")
    private Airport depAirport;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="arr_airport_id")
    private Airport arrAirport;
    
    @Column
    private Integer capacity;
    
    @Column
    private Float price;
    
    @ManyToOne
	@JoinColumn(name="create_by")
	private User createUser;
	
	@ManyToOne
	@JoinColumn(name="update_by")
	private User updateUser;
	
	@Column(name = "create_date")
    @Temporal(TemporalType.TIMESTAMP)
	private Date createDate;
	
	@Column(name = "update_date")
    @Temporal(TemporalType.TIMESTAMP)
	private Date updateDate;

	public Long getFlightId() {
		return flightId;
	}

	public void setFlightId(Long flightId) {
		this.flightId = flightId;
	}

	public Date getDepDateTime() {
		return depDateTime;
	}

	public void setDepDateTime(Date depDateTime) {
		this.depDateTime = depDateTime;
	}

	public Date getArrDateTime() {
		return arrDateTime;
	}

	public void setArrDateTime(Date arrDateTime) {
		this.arrDateTime = arrDateTime;
	}

	public Airport getDepAirport() {
		return depAirport;
	}

	public void setDepAirport(Airport depAirport) {
		this.depAirport = depAirport;
	}

	public Airport getArrAirport() {
		return arrAirport;
	}

	public void setArrAirport(Airport arrAirport) {
		this.arrAirport = arrAirport;
	}

	public Integer getCapacity() {
		return capacity;
	}

	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
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
    
}