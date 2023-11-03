package ca.hldnbasket.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.hldnbasket.dto.BasketItem;

public interface BasketItemRepository extends JpaRepository<BasketItem,Integer> {
	List<BasketItem> findByUserId(String userId);
}
