package com.asheef.common_model_ms.repository;

import com.asheef.common_model_ms.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location,Integer> {
}
