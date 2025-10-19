package fr.vaudoise.insurance.api.model.dto;

import com.fasterxml.jackson.annotation.JsonTypeName;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonTypeName("COMPANY")
@Schema(
        description = "Company client information",
        example = """
        {
          "type": "COMPANY",
          "name": "Acme Corporation",
          "email": "contact@acme.com",
          "phone": "+33698765432",
          "companyIdentifier": "abc-123"
        }
        """
)
public class CompanyDTO extends ClientDTO {

    @NotBlank(message = "Company identifier is required")
    @Pattern(regexp = "^[a-zA-Z]{3}-\\d{3}$", message = "Company identifier must follow pattern: aaa-123")
    @Schema(description = "Company identifier (format: aaa-123)", example = "abc-123", required = true)
    private String companyIdentifier;

    @Override
    public String getType() {
        return "COMPANY";
    }
}