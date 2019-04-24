package com.utopia.app.idao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.utopia.app.model.Booking;

public interface IBookingDao extends JpaRepository<Booking,Long> {

}
