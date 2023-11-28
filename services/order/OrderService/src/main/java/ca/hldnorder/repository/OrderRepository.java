package ca.hldnorder.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.hldnorder.dto.persistent.Order;

public interface OrderRepository extends JpaRepository<Order,Integer> {
	List<Order> findByUserId(String userId);
}
