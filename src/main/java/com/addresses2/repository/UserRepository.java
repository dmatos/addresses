package com.addresses2.repository;

import com.addresses2.model.Users;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<Users, Long> {

    Optional<Users> findFirstByEmail(String email);
}
