package com.asheef.common_model_mdb.repository;

import com.asheef.common_model_mdb.model.employee.UserModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserModelRepository extends MongoRepository<UserModel,String> {

    Optional<UserModel> findByName(String firstName);
}
