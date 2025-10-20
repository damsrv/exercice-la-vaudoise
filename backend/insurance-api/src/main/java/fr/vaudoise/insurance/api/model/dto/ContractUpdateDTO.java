package fr.vaudoise.insurance.api.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(description = "Contract cost update request")
public class ContractUpdateDTO {

    @NotNull(message = "Cost amount is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Cost amount must be greater than 0")
    @Schema(description = "New contract cost amount", example = "2000.00", required = true)
    private BigDecimal costAmount;
}
