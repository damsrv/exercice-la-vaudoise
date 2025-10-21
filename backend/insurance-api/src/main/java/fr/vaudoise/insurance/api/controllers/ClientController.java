package fr.vaudoise.insurance.api.controllers;

import fr.vaudoise.insurance.api.model.dto.ClientDTO;
import fr.vaudoise.insurance.api.model.dto.ClientUpdateDTO;
import fr.vaudoise.insurance.api.model.dto.CompanyDTO;
import fr.vaudoise.insurance.api.model.dto.PersonDTO;
import fr.vaudoise.insurance.api.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
@Tag(name = "Clients", description = "Client management endpoints")
public class ClientController {

    private final ClientService clientService;

    @PostMapping
    @Operation(
            summary = "Create a new client",
            description = "Creates a new client (Person or Company) in the system",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Client created successfully",
                            content = @Content(schema =@Schema(oneOf = { PersonDTO.class, CompanyDTO.class }))),
                    @ApiResponse(responseCode = "400", description = "Invalid input data")
            }
    )
    public ResponseEntity<ClientDTO> createClient(@Valid @RequestBody ClientDTO clientDTO) {
        ClientDTO created = clientService.createClient(clientDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get a client by ID",
            description = "Retrieves detailed information about a specific client",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Client found",
                            content = @Content(schema = @Schema(implementation = ClientDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Client not found")
            }
    )
    public ResponseEntity<ClientDTO> getClient(
            @Parameter(description = "Client ID", required = true)
            @PathVariable Long id) {
        ClientDTO client = clientService.getClient(id);
        return ResponseEntity.ok(client);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update a client",
            description = "Updates client information (name, email, phone). Birthdate and company identifier cannot be updated.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Client updated successfully",
                            content = @Content(schema = @Schema(implementation = ClientDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Client not found"),
                    @ApiResponse(responseCode = "400", description = "Invalid input data")
            }
    )
    public ResponseEntity<ClientDTO> updateClient(
            @Parameter(description = "Client ID", required = true)
            @PathVariable Long id,
            @Valid @RequestBody ClientUpdateDTO updateDTO) {
        ClientDTO updated = clientService.updateClient(id, updateDTO);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete a client",
            description = "Deletes a client and sets the end date of all their active contracts to the current date",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Client deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Client not found")
            }
    )
    public ResponseEntity<Void> deleteClient(
            @Parameter(description = "Client ID", required = true)
            @PathVariable Long id) {
        clientService.deleteClient(id);
        return ResponseEntity.noContent().build();
    }
}