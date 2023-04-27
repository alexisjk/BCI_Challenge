package com.bci.service.impl;

import com.bci.auth.utils.JwtUtils;
import com.bci.dto.UserDTO;
import com.bci.entity.UserEntity;
import com.bci.exception.ErrorEnum;
import com.bci.exception.UserAlreadyExistAuthenticationException;
import com.bci.mapper.UserMapper;
import com.bci.repository.UserRepository;
import com.bci.service.UserService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtTokenUtil;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtils jwtTokenUtil, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userMapper = userMapper;
    }

    @Transactional
    public UserDTO createUser(UserDTO userDTO) throws UserAlreadyExistAuthenticationException {

        Optional<UserEntity> existingUser = userRepository.findByEmail(userDTO.getEmail());
        if (existingUser.isPresent()) {
            throw new UserAlreadyExistAuthenticationException(ErrorEnum.USERALREADYEXIST.getMensaje());
        }
        prepareUserForCreation(userDTO);

        UserEntity user = userMapper.toEntity(userDTO);
        userRepository.save(user);

        return userMapper.toDto(user);
    }

    private void prepareUserForCreation(UserDTO userDTO) {

        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userDTO.setToken(jwtTokenUtil.generateToken(userDTO.getEmail()));
        userDTO.setActive(true);
    }

    @Transactional(readOnly = true)
    public UserDetails loadUserByToken(String token) throws UsernameNotFoundException {

        UserEntity user = userRepository.findByToken(token).orElseThrow(() ->
                new UsernameNotFoundException(ErrorEnum.USERNOTFOUND.getMensaje() + token));

        return new User(user.getEmail(), user.getPassword(), new ArrayList<>());
    }

    @Transactional(readOnly = true)
    public UserDTO findByEmail(String email) {

        UserEntity user = userRepository.findByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException(ErrorEnum.USERNOTFOUND.getMensaje() + email));

        return userMapper.toDto(user);
    }

    @Transactional
    public UserDTO updateJwtToken(UserDTO userDTO) {

        UserEntity user = userMapper.toEntity(userDTO);
        String newToken = jwtTokenUtil.generateToken(user.getEmail());
        user.setToken(newToken);
        UserEntity updatedUser = userRepository.save(user);
        return userMapper.toDto(updatedUser);
    }


}
