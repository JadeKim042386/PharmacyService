package com.spring.pharmacyservice.pharmacy.repository;

import com.spring.pharmacyservice.pharmacy.entity.Parmacy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PharmacyRepository extends JpaRepository<Parmacy, Long> {
}
