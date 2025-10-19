package fr.vaudoise.insurance.api.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@DiscriminatorValue("COMPANY")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Company extends Client {

    @Column(unique = true, updatable = false)
    private String companyIdentifier;

    @Override
    public String getClientType() {
        return "COMPANY";
    }
}