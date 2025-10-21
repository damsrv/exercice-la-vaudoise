package fr.vaudoise.insurance.api.service;

import fr.vaudoise.insurance.api.model.dto.ClientDTO;
import fr.vaudoise.insurance.api.model.dto.PersonDTO;
import fr.vaudoise.insurance.api.mapper.ClientMapper;
import fr.vaudoise.insurance.api.model.entity.Person;
import fr.vaudoise.insurance.api.repository.ClientRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private ClientMapper clientMapper;

    @InjectMocks
    private ClientService clientService;

    private Person person;
    private PersonDTO personDTO;

    @BeforeEach
    void setUp() {
        person = new Person();
        person.setId(1L);
        person.setName("Jean Dupont");
        person.setEmail("jean.dupont@example.com");
        person.setPhone("+33612345678");
        person.setBirthdate(LocalDate.of(1990, 5, 15));
        person.setContracts(new ArrayList<>());

        personDTO = new PersonDTO();
        personDTO.setId(1L);
        personDTO.setName("Jean Dupont");
        personDTO.setEmail("jean.dupont@example.com");
        personDTO.setPhone("+33612345678");
        personDTO.setBirthdate(LocalDate.of(1990, 5, 15));
    }

    @Test
    void createClient_ShouldReturnCreatedClient() {
        // Given
        when(clientMapper.toEntity(personDTO)).thenReturn(person);
        when(clientRepository.save(person)).thenReturn(person);
        when(clientMapper.toDTO(person)).thenReturn(personDTO);

        // When
        ClientDTO result = clientService.createClient(personDTO);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Jean Dupont");
        assertThat(result.getEmail()).isEqualTo("jean.dupont@example.com");
        verify(clientRepository, times(1)).save(person);
    }

    @Test
    void getClient_WithNonExistingId_ShouldThrowException() {
        // Given
        when(clientRepository.findById(999L)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> clientService.getClient(999L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Client not found with id: 999");
    }
}
