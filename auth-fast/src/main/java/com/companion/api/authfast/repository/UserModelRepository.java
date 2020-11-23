package com.companion.api.authfast.repository;

import com.companion.api.authfast.repository.model.UserModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserModelRepository extends CrudRepository<UserModel, Long> {

    Optional<UserModel> findByUsername(String username);
}
