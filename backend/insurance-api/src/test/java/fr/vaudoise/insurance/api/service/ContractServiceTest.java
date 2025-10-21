package fr.vaudoise.insurance.api.service;

import fr.vaudoise.insurance.api.model.dto.ContractCreateDTO;
import fr.vaudoise.insurance.api.model.dto.ContractDTO;
import fr.vaudoise.insurance.api.model.dto.ClientContractSumDTO;
import fr.vaudoise.insurance.api.mapper.ContractMapper;
import fr.vaudoise.insurance.api.model.entity.Contract;
import fr.vaudoise.insurance.api.model.entity.Person;
import fr.vaudoise.insurance.api.repository.ClientRepository;
import fr.vaudoise.insurance.api.repository.ContractRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContractServiceTest {

    @Mock
    private ContractRepository contractRepository;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private ContractMapper contractMapper;

    @InjectMocks
    private ContractService contractService;

    private Person client;
    private Contract contract;
    private ContractDTO contractDTO;

    @BeforeEach
    void setUp() {
        client = new Person();
        client.setId(1L);
        client.setName("Jean Dupont");

        contract = Contract.builder()
                .id(1L)
                .client(client)
                .startDate(LocalDate.of(2024, 1, 1))
                .endDate(LocalDate.of(2025, 12, 31))
                .costAmount(new BigDecimal("1500.00"))
                .updateDate(LocalDate.now())
                .build();

        contractDTO = ContractDTO.builder()
                .id(1L)
                .clientId(1L)
                .startDate(LocalDate.of(2024, 1, 1))
                .endDate(LocalDate.of(2025, 12, 31))
                .costAmount(new BigDecimal("1500.00"))
                .build();
    }

    @Test
    void createContract_ShouldReturnCreatedContract() {
        // Given
        ContractCreateDTO createDTO = new ContractCreateDTO();
        createDTO.setClientId(1L);
        createDTO.setStartDate(LocalDate.of(2024, 1, 1));
        createDTO.setCostAmount(new BigDecimal("1500.00"));

        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));
        when(contractRepository.save(any(Contract.class))).thenReturn(contract);
        when(contractMapper.toDTO(contract)).thenReturn(contractDTO);

        // When
        ContractDTO result = contractService.createContract(createDTO);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getClientId()).isEqualTo(1L);
        assertThat(result.getCostAmount()).isEqualByComparingTo(new BigDecimal("1500.00"));
        verify(contractRepository, times(1)).save(any(Contract.class));
    }

    @Test
    void getActiveContractsSum_ShouldReturnCorrectSum() {
        // Given
        BigDecimal expectedSum = new BigDecimal("3500.00");
        when(clientRepository.existsById(1L)).thenReturn(true);
        when(contractRepository.sumActiveContractsCostByClientId(eq(1L)))
                .thenReturn(expectedSum);

        // When
        ClientContractSumDTO result = contractService.getActiveContractsSum(1L);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getClientId()).isEqualTo(1L);
        assertThat(result.getTotalCost()).isEqualByComparingTo(expectedSum);
        verify(contractRepository, times(1))
                .sumActiveContractsCostByClientId(eq(1L));
    }
}
