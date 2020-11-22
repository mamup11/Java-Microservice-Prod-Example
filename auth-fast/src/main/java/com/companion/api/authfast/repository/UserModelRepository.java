package com.companion.api.authfast.repository;

import com.companion.api.authfast.repository.model.UserModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserModelRepository extends CrudRepository<UserModel, Long> {

}
