package com.addresses2.repository;

import com.addresses2.model.UserAddress;
import com.addresses2.model.Users;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserAddressRepository extends CrudRepository<UserAddress, Long> {
    List<UserAddress> findAllByUser(Users user);
}
