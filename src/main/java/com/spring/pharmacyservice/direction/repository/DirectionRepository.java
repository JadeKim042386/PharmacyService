package com.spring.pharmacyservice.direction.repository;

import com.spring.pharmacyservice.direction.entity.Direction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DirectionRepository extends JpaRepository<Direction, Long> {
}
