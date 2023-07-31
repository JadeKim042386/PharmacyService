package com.spring.pharmacyservice.pharmacy.repository;

import com.spring.pharmacyservice.pharmacy.entity.Pharmacy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PharmacyRepository extends JpaRepository<Pharmacy, Long> {
}
