package com.addresses2.converters;

import com.addresses2.dto.UserAddressRequestDTO;
import com.addresses2.model.UserAddress;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserAddressRequestDTOToUserAddress implements Converter<UserAddressRequestDTO, UserAddress> {


    @Override
    public UserAddress convert(UserAddressRequestDTO source) {
        if(source == null) return null;

        UserAddress userAddress = new UserAddress();
        userAddress.setBairro(source.getBairro());
        userAddress.setCep(source.getCep());
        userAddress.setCidade(source.getCidade());
        userAddress.setNumero(source.getNumero());
        userAddress.setLogradouro(source.getLogradouro());
        userAddress.setUf(source.getUf());

        return userAddress;
    }
}
