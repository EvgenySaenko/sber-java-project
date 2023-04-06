package com.evgenys.online.shop.services;

import com.evgenys.online.shop.exceptions.ResourceNotFoundException;
import com.evgenys.online.shop.persistence.entities.StorePoint;
import com.evgenys.online.shop.repositories.StorePointsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class StorePointsService {
    private final StorePointsRepository storePointsRepository;

    public StorePoint findPointsByStreet(String street){
        return storePointsRepository.findByStreet(street).orElseThrow(()->  new ResourceNotFoundException("there are no store points on such a street " + street));
    }

    public  StorePoint searchForNearestStore(String address){
        String [] deliveryAddress =  address.split(" ");
        String city = deliveryAddress[0];
        String street = deliveryAddress[1];
        Integer numberHouse = Integer.valueOf(deliveryAddress[2]);
        return findPointsByStreet(street);//ищет магазин рядом с этой улицей
    }
}
