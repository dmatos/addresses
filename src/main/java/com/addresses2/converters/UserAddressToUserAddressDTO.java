package com.addresses2.converters;

import com.addresses2.dto.UserAddressDTO;
import com.addresses2.model.UserAddress;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserAddressToUserAddressDTO implements Converter<UserAddress, UserAddressDTO> {
    @Override
    public UserAddressDTO convert(UserAddress source) {
        if(source == null) return null;

        UserAddressDTO userAddress = new UserAddressDTO();
        userAddress.setBairro(source.getBairro());
        userAddress.setCep(source.getCep());
        userAddress.setCidade(source.getCidade());
        userAddress.setNumero(source.getNumero());
        userAddress.setLogradouro(source.getLogradouro());
        userAddress.setUf(source.getUf());

        return userAddress;
    }
}
