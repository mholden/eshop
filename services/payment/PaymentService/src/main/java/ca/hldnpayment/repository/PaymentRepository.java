package ca.hldnpayment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.hldnpayment.dto.Payment;

public interface PaymentRepository extends JpaRepository<Payment,Integer> {
	
}
