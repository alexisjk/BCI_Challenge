package com.bci.mapper;

import com.bci.dto.PhoneDTO;
import com.bci.dto.UserDTO;
import com.bci.entity.PhoneEntity;
import com.bci.entity.UserEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper implements Mapper<UserEntity, UserDTO> {

    @Override
    public UserDTO toDto(UserEntity user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setCreated(user.getCreated());
        userDTO.setLastLogin(user.getLastLogin());
        userDTO.setActive(user.getIsActive());
        userDTO.setPassword(user.getPassword());
        userDTO.setToken(user.getToken());

        if (!user.getPhones().isEmpty()) {
            userDTO.setPhones(toPhoneDtoList(user.getPhones()));
        }

        return userDTO;
    }

    @Override
    public UserEntity toEntity(UserDTO userDTO) {
        UserEntity user = new UserEntity();
        user.setId(userDTO.getId());
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setCreated(userDTO.getCreated());
        user.setLastLogin(userDTO.getLastLogin());
        user.setIsActive(userDTO.isActive());
        user.setPassword(userDTO.getPassword());
        user.setToken(userDTO.getToken());

        if (!userDTO.getPhones().isEmpty()) {
            user.setPhones(toPhoneEntityList(userDTO.getPhones()));
        }

        return user;
    }

    private List<PhoneDTO> toPhoneDtoList(List<PhoneEntity> phones) {
        return phones.stream()
                .map(phone -> {
                    PhoneDTO phoneDTO = new PhoneDTO();
                    phoneDTO.setNumber(phone.getNumber());
                    phoneDTO.setCitycode(phone.getCitycode());
                    phoneDTO.setContrycode(phone.getContrycode());
                    return phoneDTO;
                })
                .collect(Collectors.toList());
    }

    private List<PhoneEntity> toPhoneEntityList(List<PhoneDTO> phoneDTOs) {
        return phoneDTOs.stream()
                .map(phoneDTO -> {
                    PhoneEntity phone = new PhoneEntity();
                    phone.setNumber(phoneDTO.getNumber());
                    phone.setCitycode(phoneDTO.getCitycode());
                    phone.setContrycode(phoneDTO.getContrycode());
                    return phone;
                })
                .collect(Collectors.toList());
    }
}