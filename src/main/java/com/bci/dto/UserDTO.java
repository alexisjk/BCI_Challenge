package com.bci.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class UserDTO {
    private UUID id;
    private LocalDateTime created;
    private LocalDateTime lastLogin;
    private String token;
    private boolean isActive;
    private String name;

    @NotBlank
    @Pattern(regexp = "^[\\w-]+(\\.[\\w-]+)*@[\\w-]+(\\.[\\w-]+)*(\\.[A-Za-z]{2,})$",
            message = "Email no válido")
    private String email;

    @NotBlank
    @Pattern(regexp = "^(?=.*[A-Z])(?=(?:[^0-9]*[0-9]){2})(?=(?:[^A-Z]*[A-Z]){1}[^A-Z]*$)[a-zA-Z0-9]{8,12}$",
            message = "La contraseña debe tener una longitud de 8 a 12 caracteres, una letra mayúscula y dos números")

    private String password;
    private List<PhoneDTO> phones = new ArrayList<>();

}
