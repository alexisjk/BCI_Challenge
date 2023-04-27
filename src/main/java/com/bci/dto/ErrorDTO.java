package com.bci.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ErrorDTO {

    private HttpStatus codigo;
    private String details;
    private List<String> errores;
    private LocalDateTime timestamp;

    public ErrorDTO(HttpStatus codigo, String details, List<String> errores, LocalDateTime timestamp) {
        this.codigo = codigo;
        this.details = details;
        this.errores = errores;
        this.timestamp = timestamp;
    }
}
