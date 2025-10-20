package fr.vaudoise.insurance.api.mapper;


import fr.vaudoise.insurance.api.model.dto.ContractDTO;
import fr.vaudoise.insurance.api.model.entity.Contract;
import org.springframework.stereotype.Component;

@Component
public class ContractMapper {

    public ContractDTO toDTO(Contract contract) {
        return ContractDTO.builder()
                .id(contract.getId())
                .clientId(contract.getClient().getId())
                .startDate(contract.getStartDate())
                .endDate(contract.getEndDate())
                .costAmount(contract.getCostAmount())
                .build();
    }
}