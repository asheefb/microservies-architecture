package com.asheef.common_model_mdb.repository;

import com.asheef.common_model_mdb.model.employee.UsersAudit;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAuditRepository extends MongoRepository<UsersAudit,String > {
}
