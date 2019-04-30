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
@Table(name = "airport")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "airportId")
public class Airport {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="airport_id")
    private Long airportId;
    
    @Column(name="airport_code")
    private String airportCode;
    
    @Column(name="airport_name")
    private String airportName;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="city_id")
    private City city;
    
    @ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="create_by")
	private User createUser;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="update_by")
	private User updateUser;
	
	@Column(name = "create_date")
    @Temporal(TemporalType.TIMESTAMP)
	private Date createDate;
	
	@Column(name = "update_date")
    @Temporal(TemporalType.TIMESTAMP)
	private Date updateDate;

    public Long getAirportId() {
        return airportId;
    }

    public void setAirportId(Long airportId) {
        this.airportId = airportId;
    }

    public String getAirportCode() {
        return airportCode;
    }

    public void setAirportCode(String airportCode) {
        this.airportCode = airportCode;
    }

    public String getAirportName() {
        return airportName;
    }

    public void setAirportName(String airportName) {
        this.airportName = airportName;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }
    
}