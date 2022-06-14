package com.addresses2.service;

import com.addresses2.model.Users;

import java.util.Optional;

public interface UserService {
    Users findUserOrCreateOne(String email);
    Optional<Users> findOne(String email);
}
