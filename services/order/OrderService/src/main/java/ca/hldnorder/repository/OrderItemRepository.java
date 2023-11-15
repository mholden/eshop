package ca.hldnorder.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.hldnorder.dto.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem,Integer> {
	
}
