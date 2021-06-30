package com.evgenys.online.shop.services;


import com.evgenys.online.shop.exceptions.ResourceNotFoundException;
import com.evgenys.online.shop.persistence.entities.Role;
import com.evgenys.online.shop.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Role findRoleByName(String roleName){
        return roleRepository.findRoleByName(roleName).orElseThrow(()->
                new ResourceNotFoundException("Role with this name: " + roleName + " does not exist"));
    }
}
