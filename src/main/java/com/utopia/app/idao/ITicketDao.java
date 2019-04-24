package com.utopia.app.idao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.utopia.app.model.Ticket;

public interface ITicketDao extends JpaRepository<Ticket,Long> {

}
