package com.bci.service;

import com.bci.dto.UserDTO;
import com.bci.exception.UserAlreadyExistAuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService {

    UserDTO createUser(UserDTO userDTO) throws UserAlreadyExistAuthenticationException;

    UserDetails loadUserByToken(String token) throws UsernameNotFoundException;

    UserDTO updateJwtToken(UserDTO userDTO);

    UserDTO findByEmail(String email);

}

