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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="flight")
@JsonIgnoreProperties(ignoreUnknown = true)
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
    
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="dep_airport_id")
    private Airport depAirport;
    
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="arr_airport_id")
    private Airport arrAirport;
    
    @Column
    private Integer capacity;
    
    @Column
    private Float price;
    
    
    public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

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
    
}