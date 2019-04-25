package com.utopia.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.utopia.app.idao.IPaymentDao;
import com.utopia.app.model.Payment;

@Service
@Transactional
public class PaymentService {
	
	@Autowired
	private IPaymentDao paymentdao;
	
	public Payment getPaymentById(long paymentId) {
		return paymentdao.getOne(paymentId);
	}
	
	public List<Payment> getPaymentAll() {
		return paymentdao.findAll();
	}
	
	public void createPayment(Payment payment) {
		paymentdao.save(payment);
	}
	
	public void updatePayment(Payment payment) {
		paymentdao.save(payment);
	}
	
	public void deletePayment(long paymentId) {
		paymentdao.deleteById(paymentId);
	}
	
}
