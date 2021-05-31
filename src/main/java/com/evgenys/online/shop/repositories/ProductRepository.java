package com.evgenys.online.shop.repositories;

import com.evgenys.online.shop.persistence.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {//JPA наследник PagingAndSortingRepository
    List<Product>findAllByPriceBetween(int min, int max);
}
