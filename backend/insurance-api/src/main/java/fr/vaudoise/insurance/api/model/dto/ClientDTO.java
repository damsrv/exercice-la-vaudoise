package fr.vaudoise.insurance.api.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"

)
@JsonSubTypes({
        @JsonSubTypes.Type(value = PersonDTO.class, name = "PERSON"),
        @JsonSubTypes.Type(value = CompanyDTO.class, name = "COMPANY")
})
@Schema(
        description = "Client base information",
        discriminatorProperty = "type",
        oneOf = {PersonDTO.class, CompanyDTO.class}
)
public abstract class ClientDTO {

    @Schema(description = "Client ID", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @NotBlank(message = "Name is required")
    @Schema(description = "Client name", example = "John Doe", required = true)
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    @Schema(description = "Client email", example = "john.doe@example.com", required = true)
    private String email;

    @NotBlank(message = "Phone is required")
    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Phone number should be valid (10-15 digits, optional + prefix)")
    @Schema(description = "Client phone number", example = "+33612345678", required = true)
    private String phone;

    @Schema(description = "Client type", example = "PERSON", accessMode = Schema.AccessMode.READ_ONLY)
    public abstract String getType();

}
