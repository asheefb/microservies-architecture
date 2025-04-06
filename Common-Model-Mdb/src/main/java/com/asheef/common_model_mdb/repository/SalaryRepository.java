package com.asheef.common_model_mdb.repository;

import com.asheef.common_model_mdb.model.employee.Salary;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalaryRepository extends MongoRepository<Salary,String> {
}
