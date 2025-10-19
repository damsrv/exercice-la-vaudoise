package fr.vaudoise.insurance.api.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Schema(description = "Client update request")
public class ClientUpdateDTO {

    @Schema(description = "Client name", example = "John Smith")
    private String name;

    @Email(message = "Email should be valid")
    @Schema(description = "Client email", example = "john.smith@example.com")
    private String email;

    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Phone number should be valid (10-15 digits, optional + prefix)")
    @Schema(description = "Client phone number", example = "+33612345678")
    private String phone;
}