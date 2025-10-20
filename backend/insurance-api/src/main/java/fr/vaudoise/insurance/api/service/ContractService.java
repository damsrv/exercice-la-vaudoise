package fr.vaudoise.insurance.api.service;

import fr.vaudoise.insurance.api.mapper.ContractMapper;
import fr.vaudoise.insurance.api.model.dto.*;
import fr.vaudoise.insurance.api.model.entity.Client;
import fr.vaudoise.insurance.api.model.entity.Contract;
import fr.vaudoise.insurance.api.repository.ClientRepository;
import fr.vaudoise.insurance.api.repository.ContractRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContractService {

    private final ContractRepository contractRepository;
    private final ClientRepository clientRepository;
    private final ContractMapper contractMapper;

    @Transactional
    public ContractDTO createContract(ContractCreateDTO createDTO) {
        Client client = clientRepository.findById(createDTO.getClientId())
                .orElseThrow(() -> new EntityNotFoundException("Client not found with id: " + createDTO.getClientId()));

        Contract contract = Contract.builder()
                .client(client)
                .startDate(createDTO.getStartDate())
                .endDate(createDTO.getEndDate())
                .costAmount(createDTO.getCostAmount())
                .build();

        Contract savedContract = contractRepository.save(contract);
        return contractMapper.toDTO(savedContract);
    }

    @Transactional
    public ContractDTO updateContractCost(Long contractId, ContractUpdateDTO updateDTO) {

        Contract contract = contractRepository.findById(contractId)
                .orElseThrow(() -> new EntityNotFoundException("Contract not found with id: " + contractId));

        contract.setCostAmount(updateDTO.getCostAmount());
        contract.setUpdateDate(LocalDate.now());

        Contract updatedContract = contractRepository.save(contract);
        return contractMapper.toDTO(updatedContract);

    }

    @Transactional(readOnly = true)
    public List<ContractDTO> getActiveContractsForClient(Long clientId, LocalDate updateDate) {
        if (!clientRepository.existsById(clientId)) {
            throw new EntityNotFoundException("Client not found with id: " + clientId);
        }

        LocalDate now = LocalDate.now();
        List<Contract> contracts;

        if (updateDate != null) {
            contracts = contractRepository.findActiveContractsByClientIdAndUpdateDate(clientId, now, updateDate);
        } else {
            contracts = contractRepository.findActiveContractsByClientId(clientId, now);
        }

        return contracts.stream()
                .map(contractMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ClientContractSumDTO getActiveContractsSum(Long clientId) {
        if (!clientRepository.existsById(clientId)) {
            throw new EntityNotFoundException("Client not found with id: " + clientId);
        }

        LocalDate now = LocalDate.now();
        BigDecimal sum = contractRepository.sumActiveContractsCostByClientId(clientId);

        return new ClientContractSumDTO(clientId, sum);
    }
}
