package com.evgenys.online.shop.repositories;

import com.evgenys.online.shop.persistence.entities.StorePoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StorePointsRepository extends JpaRepository<StorePoint,Long> {
    Optional<StorePoint> findByStreet(String street);
}
