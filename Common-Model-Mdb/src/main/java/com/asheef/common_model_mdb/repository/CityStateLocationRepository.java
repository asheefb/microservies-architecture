package com.asheef.common_model_mdb.repository;

import com.asheef.common_model_mdb.model.CityStateLocation;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityStateLocationRepository extends MongoRepository<CityStateLocation, ObjectId> {

    @Query("{type:?0}")
    List<CityStateLocation> findByType(String type);
}
