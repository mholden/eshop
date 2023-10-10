package ca.hldncatalog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.hldncatalog.dto.CatalogItem;

public interface CatalogItemRepository extends JpaRepository<CatalogItem,Integer> {
	
}
