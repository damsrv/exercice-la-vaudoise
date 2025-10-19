package fr.vaudoise.insurance.api.service;

import fr.vaudoise.insurance.api.mapper.ClientMapper;
import fr.vaudoise.insurance.api.model.dto.ClientDTO;
import fr.vaudoise.insurance.api.model.dto.ClientUpdateDTO;
import fr.vaudoise.insurance.api.model.entity.Client;
import fr.vaudoise.insurance.api.model.entity.Contract;
import fr.vaudoise.insurance.api.repository.ClientRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    @Transactional
    public ClientDTO createClient(ClientDTO clientDTO) {
        Client client = clientMapper.toEntity(clientDTO);
        Client savedClient = clientRepository.save(client);
        return clientMapper.toDTO(savedClient);
    }

    @Transactional(readOnly = true)
    public ClientDTO getClient(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Client not found with id: " + id));
        return clientMapper.toDTO(client);
    }

    @Transactional
    public ClientDTO updateClient(Long id, ClientUpdateDTO updateDTO) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Client not found with id: " + id));

        if (updateDTO.getName() != null) {
            client.setName(updateDTO.getName());
        }
        if (updateDTO.getEmail() != null) {
            client.setEmail(updateDTO.getEmail());
        }
        if (updateDTO.getPhone() != null) {
            client.setPhone(updateDTO.getPhone());
        }

        Client updatedClient = clientRepository.save(client);
        return clientMapper.toDTO(updatedClient);
    }

    @Transactional
    public void deleteClient(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Client not found with id: " + id));

        LocalDate now = LocalDate.now();
        for (Contract contract : client.getContracts()) {
            if (contract.getEndDate() == null || contract.getEndDate().isAfter(now)) {
                contract.setEndDate(now);
            }
        }

        clientRepository.delete(client);
    }
}
