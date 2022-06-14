package com.addresses2.service;


import com.addresses2.model.Users;
import com.addresses2.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Users findUserOrCreateOne(String email) {
        Optional<Users> userOpt = userRepository.findFirstByEmail(email);
        if(userOpt.isPresent()) return userOpt.get();
        Users user = new Users();
        user.setEmail(email);
        return userRepository.save(user);
    }

    @Override
    public Optional<Users> findOne(String email) {
        return userRepository.findFirstByEmail(email);
    }
}
