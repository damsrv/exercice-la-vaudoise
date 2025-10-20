package fr.vaudoise.insurance.api.controllers;

import fr.vaudoise.insurance.api.model.dto.ClientContractSumDTO;
import fr.vaudoise.insurance.api.model.dto.ContractCreateDTO;
import fr.vaudoise.insurance.api.model.dto.ContractDTO;
import fr.vaudoise.insurance.api.model.dto.ContractUpdateDTO;
import fr.vaudoise.insurance.api.service.ContractService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/contracts")
@RequiredArgsConstructor
@Tag(name = "Contracts", description = "Contract management endpoints")
public class ContractController {

    private final ContractService contractService;

    @PostMapping
    @Operation(
            summary = "Create a new contract",
            description = "Creates a new contract for a client. Start date defaults to current date if not provided.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Contract created successfully",
                            content = @Content(schema = @Schema(implementation = ContractDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input data"),
                    @ApiResponse(responseCode = "404", description = "Client not found")
            }
    )
    public ResponseEntity<ContractDTO> createContract(@Valid @RequestBody ContractCreateDTO createDTO) {
        ContractDTO created = contractService.createContract(createDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}/cost")
    @Operation(
            summary = "Update contract cost",
            description = "Updates the cost amount of a contract. The update date is automatically set to current date.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Contract cost updated successfully",
                            content = @Content(schema = @Schema(implementation = ContractDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Contract not found"),
                    @ApiResponse(responseCode = "400", description = "Invalid input data")
            }
    )
    public ResponseEntity<ContractDTO> updateContractCost(
            @Parameter(description = "Contract ID", required = true)
            @PathVariable Long id,
            @Valid @RequestBody ContractUpdateDTO updateDTO) {
        ContractDTO updated = contractService.updateContractCost(id, updateDTO);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/client/{clientId}")
    @Operation(
            summary = "Get active contracts for a client",
            description = "Retrieves all active contracts for a specific client. Optionally filter by update date.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Contracts retrieved successfully",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ContractDTO.class)))),
                    @ApiResponse(responseCode = "404", description = "Client not found")
            }
    )
    public ResponseEntity<List<ContractDTO>> getActiveContractsForClient(

            @Parameter(description = "Client ID", required = true)
            @PathVariable
            Long clientId,

            @Parameter(description = "Filter by update date (ISO 8601 format: yyyy-MM-dd)")
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate updateDate)
    {

        List<ContractDTO> contracts = contractService.getActiveContractsForClient(clientId, updateDate);
        return ResponseEntity.ok(contracts);

    }

    @GetMapping("/client/{clientId}/sum")
    @Operation(
            summary = "Get sum of active contracts cost",
            description = "High-performance endpoint that returns the total sum of all active contracts cost for a client",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Sum calculated successfully",
                            content = @Content(schema = @Schema(implementation = ClientContractSumDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Client not found")
            }
    )
    public ResponseEntity<ClientContractSumDTO> getActiveContractsSum(
            @Parameter(description = "Client ID", required = true)
            @PathVariable Long clientId) {
        ClientContractSumDTO sum = contractService.getActiveContractsSum(clientId);
        return ResponseEntity.ok(sum);
    }
}
