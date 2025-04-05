package com.asheef.common_model_ms.repository.employee.mysql;

import com.asheef.common_model_ms.employee.mysql.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepository extends JpaRepository<Job,Integer> {
}
