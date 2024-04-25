package ca.hldnbasket.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ca.hldnbasket.dto.persistent.Basket;

@Repository
public interface BasketRepository extends CrudRepository<Basket,String> { }
