package com.bci.dto;

import lombok.Data;

@Data
public class ErrorResponseDTO {

    private ErrorDTO error;

    public ErrorResponseDTO(ErrorDTO error) {
        this.error = error;
    }
}
