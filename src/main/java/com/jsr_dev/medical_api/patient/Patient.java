package com.jsr_dev.medical_api.patient;

import com.jsr_dev.medical_api.address.Address;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Table(name = "patients")
@Entity(name = "Patient")
public class Patient {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String avatar;
    private String email;
    private String identityDocument;
    private String phoneNumber;
    @Embedded
    private Address address;
}
