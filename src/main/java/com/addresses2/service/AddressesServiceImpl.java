package com.addresses2.service;

import com.addresses2.dto.AddressResponseDTO;
import com.addresses2.dto.CustomResponseDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AddressesServiceImpl implements AddressesService{

    @Value("${viacep.consulta.url}")
    private String viacepConsultaUrl;

    private String formatCEP(String cep){
        if (cep == null) return "";
        return cep.replaceAll("\\^[0-9]+", "");
    }

    @Override
    public CustomResponseDTO getAddress(String cep) {
        cep = formatCEP(cep);
        try {

            HttpEntity<String> request = new HttpEntity<>("");
            ResponseEntity<AddressResponseDTO> apiResponse = new RestTemplate().exchange(
                    viacepConsultaUrl,
                    HttpMethod.GET,
                    request,
                    AddressResponseDTO.class,
                    cep
            );
            AddressResponseDTO body = apiResponse.getBody();
            if (apiResponse.getStatusCode().is2xxSuccessful() &&  body != null) {
                if(body.getErro() != null) return new CustomResponseDTO<>(HttpStatus.BAD_REQUEST, "CEP " + " not found");
                return new CustomResponseDTO<>(HttpStatus.OK, body);
            }
            return new CustomResponseDTO<>(apiResponse.getStatusCode());
        }catch(Exception e){
            return new CustomResponseDTO<>(HttpStatus.BAD_REQUEST, "Invalid CEP format");
        }
    }
}
