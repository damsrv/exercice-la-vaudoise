package fr.vaudoise.insurance.api.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonTypeName("PERSON")
@Schema(
        description = "Person client information",
        example = """
        {
          "type": "PERSON",
          "name": "Jean Dupont",
          "email": "jean.dupont@example.com",
          "phone": "+33612345678",
          "birthdate": "1990-05-15"
        }
        """
)
public class PersonDTO extends ClientDTO {

    @NotNull(message = "Birthdate is required")
    @Past(message = "Birthdate must be in the past")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Schema(description = "Person's birthdate (ISO 8601 format)", example = "1990-05-15", required = true)
    private LocalDate birthdate;

    @Override
    public String getType() {
        return "PERSON";
    }
}