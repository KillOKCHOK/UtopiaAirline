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
@Table(name = "airport")
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

    public Long getAirportId() {
        return airportId;
    }

    public void setAirportId(long airportId) {
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