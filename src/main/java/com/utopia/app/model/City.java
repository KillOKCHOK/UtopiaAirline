package com.utopia.app.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "city")
@JsonIgnoreProperties(ignoreUnknown = true)
public class City {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "city_id")
	private Long cityId;

	@Column(name = "city_name")
	private String cityName;

	@Column
	private String country;

	public Long getCityId() {
		return cityId;
	}

	public void setCityId(long cityId) {
		this.cityId = cityId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	
}