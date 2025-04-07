package com.asheef.common_model_mdb.repository;

import com.asheef.common_model_mdb.model.employee.UserModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserModelRepository extends MongoRepository<UserModel,String> {

    @Query("{firstName:?0}")
    Optional<UserModel> findByName(String firstName);
}
