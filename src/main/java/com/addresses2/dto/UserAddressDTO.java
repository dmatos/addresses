package com.addresses2.dto;

import lombok.Data;

@Data
public class UserAddressDTO {
    private String email;
    private String logradouro;
    private Integer numero;
    private String bairro;
    private String cidade;
    private String uf;
    private String cep;
}
