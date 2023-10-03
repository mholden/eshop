package ca.hldncatalog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.hldncatalog.catalog.CatalogItem;

public interface CatalogItemRepository extends JpaRepository<CatalogItem,Integer> {
	
}
