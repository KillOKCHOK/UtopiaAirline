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
@Table(name = "city")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "cityId")
public class City {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "city_id")
	private Long cityId;

	@Column(name = "city_name")
	private String cityName;

	@Column
	private String country;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="create_by")
	private User createUser;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="update_by")
	private User updateUser;
	
	@Column(name = "create_date")
    @Temporal(TemporalType.TIMESTAMP)
	private Date createDate;
	
	@Column(name = "update_date")
    @Temporal(TemporalType.TIMESTAMP)
	private Date updateDate;

	public Long getCityId() {
		return cityId;
	}

	public void setCityId(Long cityId) {
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