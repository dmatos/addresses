package com.addresses2.dto;

import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserAddressRequestDTO {
    @NotNull
    private String email;
    @NotNull
    private String logradouro;
    @NotNull
    private Integer numero;
    @NotNull
    private String bairro;
    @NotNull
    private String cidade;
    @NotNull
    private String uf;
    @NotNull
    private String cep;
}
