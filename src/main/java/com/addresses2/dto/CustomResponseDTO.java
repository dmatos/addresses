package com.addresses2.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;

import java.time.Instant;

@Data
public class CustomResponseDTO<T> {

    @JsonProperty
    private final Instant timestamp = Instant.now();

    @Nullable
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    private HttpStatus status;

    public CustomResponseDTO(HttpStatus status, T data){
        this.status = status;
        this.data = data;
    }

    public CustomResponseDTO(HttpStatus status){
        this.status = status;
    }
}
