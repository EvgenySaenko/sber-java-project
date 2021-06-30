package com.evgenys.online.shop.services;


import com.evgenys.online.shop.dto.UserDto;
import com.evgenys.online.shop.dto.UserDtoRegistration;
import com.evgenys.online.shop.persistence.entities.Product;
import com.evgenys.online.shop.persistence.entities.Role;
import com.evgenys.online.shop.persistence.entities.User;
import com.evgenys.online.shop.repositories.UserRepository;
import com.evgenys.online.shop.utils.converter.UserConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final UserConverter userConverter;
    private final BCryptPasswordEncoder passwordEncoder;

    public Optional<User> findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public Optional<UserDto> findUserDataDto(String username){
        return userRepository.findByUsername(username).map(userConverter::convertToUserDto);
    }

    //todo по паролю не находит(пишет что нет такого) хотя пароли кодируются верно
//    public Optional<User> findByPassword(UserDtoRegistration userDtoRegistration){
//        String password = passwordEncoder.encode(userDtoRegistration.getPassword());
//        return userRepository.findByPassword(password);

    //существует ли пользователь с таким логином телефоном и майл
    public Optional<User> findByUsernameAndPhoneAndEmail(UserDtoRegistration userDtoRegistration){
        return userRepository.findByUsernameAndPhoneAndEmail(userDtoRegistration.getUsername(),userDtoRegistration.getPhone(),userDtoRegistration.getEmail());
    }


    public User saveOrUpdate(UserDtoRegistration userDtoRegistration){
        userDtoRegistration.setPassword(passwordEncoder.encode(userDtoRegistration.getPassword()));
        User user = userConverter.convertToUser(userDtoRegistration);
        Role role = roleService.findRoleByName("ROLE_USER");
        user.setRoles(Collections.singletonList(role));
        return userRepository.save(user);
    }

    //для себя DaoAuthenticationProvider.class тут он дергает метод retrieveUser
    // где использует наш класс UserDetails loadedUser = this.getUserDetailsService().loadUserByUsername(username);
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Invalid username or password"));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

}