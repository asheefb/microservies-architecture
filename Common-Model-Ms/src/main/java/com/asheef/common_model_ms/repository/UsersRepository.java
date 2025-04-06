package com.asheef.common_model_ms.repository;

import com.asheef.common_model_ms.model.employee.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<Users,Integer> {
}
