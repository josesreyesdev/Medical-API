package com.jsr_dev.medical_api.domain.patient;

import com.jsr_dev.medical_api.domain.address.Address;
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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String avatar;
    private String email;
    private String identityDocument;
    private String phoneNumber;
    @Embedded
    private Address address;
    private Boolean active;

    public void update(UpdatePatientRequest updateData) {
        if (updateData.name() != null) this.name = updateData.name();
        if (updateData.avatar() != null) this.avatar = updateData.avatar();
        if (updateData.email() != null) this.email = updateData.email();
        if (updateData.phoneNumber() != null) this.phoneNumber = updateData.phoneNumber();
        if (updateData.addAddressRequest() != null) {
            this.address.update(updateData.addAddressRequest());
        }
    }

    public void deactivate() {
        this.active = false;
    }
}
