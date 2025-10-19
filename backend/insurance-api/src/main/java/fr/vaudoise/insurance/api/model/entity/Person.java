package fr.vaudoise.insurance.api.model.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@DiscriminatorValue("PERSON")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Person extends Client {

    @Column(updatable = false)
    private LocalDate birthdate;

    @Override
    public String getClientType() {
        return "PERSON";
    }


}