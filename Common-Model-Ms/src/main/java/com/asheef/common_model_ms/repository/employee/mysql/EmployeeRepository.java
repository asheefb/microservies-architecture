package com.asheef.common_model_ms.repository.employee.mysql;

import com.asheef.common_model_ms.employee.mysql.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Integer> {
}
