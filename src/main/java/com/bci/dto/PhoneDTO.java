package com.bci.dto;

import lombok.Data;

@Data
public class PhoneDTO {

    private Long id;
    private Long number;
    private Integer citycode;
    private String contrycode;

}