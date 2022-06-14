package com.addresses2.service;

import com.addresses2.dto.AddressResponseDTO;
import com.addresses2.dto.CustomResponseDTO;

public interface AddressesService {
    CustomResponseDTO<AddressResponseDTO> getAddress(String cep);
}
