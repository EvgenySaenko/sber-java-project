package com.evgenys.online.shop.repositories;


import com.evgenys.online.shop.persistence.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
    List<Order> findOrderByUserUsername(String ownerUsername);

}
