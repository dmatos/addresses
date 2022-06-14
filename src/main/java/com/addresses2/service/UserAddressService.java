package com.addresses2.service;

import com.addresses2.dto.UserAddressDTO;
import com.addresses2.dto.UserAddressRequestDTO;
import com.addresses2.dto.CustomResponseDTO;

import java.util.List;

public interface UserAddressService {
    CustomResponseDTO<String> addUserAddress(UserAddressRequestDTO addressRequestDTO);
    CustomResponseDTO<List<UserAddressDTO>> getUserAddresses(String email);
}
