package com.utopia.app.idao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.utopia.app.model.Payment;

public interface IPaymentDao extends JpaRepository<Payment,Long> {

}
