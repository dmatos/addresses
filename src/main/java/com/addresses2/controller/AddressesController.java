package com.addresses2.controller;

import com.addresses2.dto.AddressResponseDTO;
import com.addresses2.dto.UserAddressDTO;
import com.addresses2.service.AddressesService;
import com.addresses2.dto.CustomResponseDTO;
import com.addresses2.dto.UserAddressRequestDTO;
import com.addresses2.service.UserAddressService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/addresses")
public class AddressesController {

    private final AddressesService addressesService;
    private final UserAddressService userAddressService;

    public AddressesController(AddressesService addressesService, UserAddressService userAddressService) {
        this.addressesService = addressesService;
        this.userAddressService = userAddressService;
    }

    @GetMapping("/{cep}")
    public ResponseEntity<CustomResponseDTO<AddressResponseDTO>> getAddressInfoByCEP(@PathVariable("cep") String cep){
        CustomResponseDTO<AddressResponseDTO> responseDTO = addressesService.getAddress(cep);
        return new ResponseEntity<>(responseDTO, responseDTO.getStatus());
    }

    @PostMapping("/user")
    public ResponseEntity<CustomResponseDTO<String>> addUserAddress(@RequestBody UserAddressRequestDTO userAddressRequestDTO){
        CustomResponseDTO<String> responseDTO = userAddressService.addUserAddress(userAddressRequestDTO);
        return new ResponseEntity<>(responseDTO, responseDTO.getStatus());
    }

    @GetMapping("/user/{email}")
    public ResponseEntity<CustomResponseDTO<List<UserAddressDTO>>> getUserAddresses(@PathVariable("email") String email){
        CustomResponseDTO<List<UserAddressDTO>> responseDTO = userAddressService.getUserAddresses(email);
        return new ResponseEntity<>(responseDTO, responseDTO.getStatus());
    }
}
