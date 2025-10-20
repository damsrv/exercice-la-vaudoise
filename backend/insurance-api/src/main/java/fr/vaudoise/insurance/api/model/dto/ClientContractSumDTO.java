package fr.vaudoise.insurance.api.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Sum of active contracts cost")
public class ClientContractSumDTO {

    @Schema(description = "Client ID", example = "1")
    private Long clientId;

    @Schema(description = "Total sum of active contracts cost amount", example = "5000.00")
    private BigDecimal totalCost;
}