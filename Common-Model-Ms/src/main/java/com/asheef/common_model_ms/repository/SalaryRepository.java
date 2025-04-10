package com.asheef.common_model_ms.repository;

import com.asheef.common_model_ms.model.employee.Salary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalaryRepository extends JpaRepository<Salary,Integer> {
}
