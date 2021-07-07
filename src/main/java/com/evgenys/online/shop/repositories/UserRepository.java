package com.evgenys.online.shop.repositories;

import com.evgenys.online.shop.persistence.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUsername(String username);

    Optional<User> findByUsernameAndPhoneAndEmail(String usernameReg, String phoneReg, String emailReg);

    User findByActivationCode(String code);

    //@Query("select new com.evgenys.online.shop.dto.UserDto(u) from User u")
    //@Query("select u.username as username, u.password as password from User u where username = :usernameReg and password = :passwordReg")

}
