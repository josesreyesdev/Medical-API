package com.jsr_dev.medical_api.physician;

import com.jsr_dev.medical_api.address.Address;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "physicians")
@Entity(name = "Physician")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Physician {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String avatar;
    private String email;
    private String phoneNumber;
    private String document;
    @Enumerated(EnumType.STRING)
    private Specialty specialty;
    @Embedded
    private Address address;


    public void update(UpdatePhysicianRequest update) {
        if (update.name() != null) this.name = update.name();
        if (update.avatar() != null) this.avatar = update.avatar();
        if (update.phoneNumber() != null) this.phoneNumber = update.phoneNumber();
        if (update.addAddressRequest() != null) {
            this.address.update(update.addAddressRequest());
        }
    }
}
