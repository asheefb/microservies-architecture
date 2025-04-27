package com.asheef.common_model_mdb.repository;

import com.asheef.common_model_mdb.model.employee.UserModel;
import com.asheef.common_model_mdb.model.response.UserResponseModel;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserModelRepository extends MongoRepository<UserModel,Object> {

    @Query("{firstName:?0}")
    Optional<UserModel> findByName(String firstName);

    @Aggregation(pipeline = {

    })
    Optional<UserResponseModel> findUserBasicDetailsById(Object id);
}
