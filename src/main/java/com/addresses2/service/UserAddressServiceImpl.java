package com.addresses2.service;

import com.addresses2.converters.UserAddressRequestDTOToUserAddress;
import com.addresses2.converters.UserAddressToUserAddressDTO;
import com.addresses2.dto.UserAddressDTO;
import com.addresses2.model.Users;
import com.addresses2.repository.UserAddressRepository;
import com.addresses2.dto.UserAddressRequestDTO;
import com.addresses2.dto.CustomResponseDTO;
import com.addresses2.model.UserAddress;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserAddressServiceImpl implements UserAddressService{

    private final UserService userService;
    private final UserAddressRepository userAddressRepository;
    private final UserAddressRequestDTOToUserAddress userAddressRequestDTOToUserAddress;
    private final UserAddressToUserAddressDTO userAddressToUserAddressDTO;

    public UserAddressServiceImpl(UserService userService, UserAddressRepository userAddressRepository, UserAddressRequestDTOToUserAddress userAddressRequestDTOToUserAddress, UserAddressToUserAddressDTO userAddressToUserAddressDTO) {
        this.userService = userService;
        this.userAddressRepository = userAddressRepository;
        this.userAddressRequestDTOToUserAddress = userAddressRequestDTOToUserAddress;
        this.userAddressToUserAddressDTO = userAddressToUserAddressDTO;
    }

    @Override
    @Transactional
    public CustomResponseDTO addUserAddress(UserAddressRequestDTO addressRequestDTO) {
        Users user = userService.findUserOrCreateOne(addressRequestDTO.getEmail());
        UserAddress userAddress = userAddressRequestDTOToUserAddress.convert(addressRequestDTO);
        if(userAddress == null) return new CustomResponseDTO<>(HttpStatus.BAD_REQUEST);
        userAddress.setUser(user);
        userAddressRepository.save(userAddress);
        return new CustomResponseDTO<>(HttpStatus.OK);
    }

    @Override
    public CustomResponseDTO getUserAddresses(String email) {
        Optional<Users> userOpt = userService.findOne(email);

        if(!userOpt.isPresent()) return new CustomResponseDTO<>(HttpStatus.NOT_FOUND, "email not found");

        List<UserAddressDTO> userAddressList = userAddressRepository.findAllByUser(userOpt.get()).stream()
                .map(userAddressToUserAddressDTO::convert).collect(Collectors.toList());

        return new CustomResponseDTO<>(HttpStatus.OK, userAddressList);
    }

}
