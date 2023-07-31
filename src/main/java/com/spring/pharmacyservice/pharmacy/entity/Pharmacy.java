package com.spring.pharmacyservice.pharmacy.entity;

import com.spring.pharmacyservice.AuditingFields;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Builder
@Getter
@ToString(callSuper = true)
@Entity(name = "pharmacy")
@NoArgsConstructor
@AllArgsConstructor
public class Pharmacy extends AuditingFields {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String pharmacyName;
    private String pharmacyAddress;
    private double latitude;
    private double longitude;

    public void chnagePharmacyAddress(String address) {
        this.pharmacyAddress = address;
    }
}
