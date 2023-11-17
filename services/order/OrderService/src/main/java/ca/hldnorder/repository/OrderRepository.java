package ca.hldnorder.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.hldnorder.dto.persistent.Order;

public interface OrderRepository extends JpaRepository<Order,Integer> {
	
}
