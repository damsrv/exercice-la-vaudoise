package fr.vaudoise.insurance.api.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Schema(description = "Contract creation request")
public class ContractCreateDTO {

    @NotNull(message = "Client ID is required")
    @Schema(description = "Client ID for whom the contract is created", example = "1", required = true)
    private Long clientId;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Schema(description = "Contract start date (ISO 8601 format). Defaults to current date if not provided",
            example = "2024-01-01")
    private LocalDate startDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Schema(description = "Contract end date (ISO 8601 format). Null for no end date",
            example = "2025-12-31")
    private LocalDate endDate;

    @NotNull(message = "Cost amount is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Cost amount must be greater than 0")
    @Schema(description = "Contract cost amount", example = "1500.50", required = true)
    private BigDecimal costAmount;
}
