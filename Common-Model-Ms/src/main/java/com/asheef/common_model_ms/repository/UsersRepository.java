package com.asheef.common_model_ms.repository;

import com.asheef.common_model_ms.model.employee.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users,Integer> {

    @Query("{email:?0}")
    Optional<Users> findByEmail(String email);

    @Query("{phoneNumber:?0}")
    Optional<Users> findByPhoneNumber(String phoneNumber);
}
