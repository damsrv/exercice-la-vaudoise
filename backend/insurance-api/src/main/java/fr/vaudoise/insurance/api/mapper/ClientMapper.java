package fr.vaudoise.insurance.api.mapper;

import fr.vaudoise.insurance.api.model.dto.ClientDTO;
import fr.vaudoise.insurance.api.model.dto.CompanyDTO;
import fr.vaudoise.insurance.api.model.dto.PersonDTO;
import fr.vaudoise.insurance.api.model.entity.Client;
import fr.vaudoise.insurance.api.model.entity.Company;
import fr.vaudoise.insurance.api.model.entity.Person;
import org.springframework.stereotype.Component;

@Component
public class ClientMapper {

    public ClientDTO toDTO(Client client) {
        if (client instanceof Person) {
            return toPersonDTO((Person) client);
        } else if (client instanceof Company) {
            return toCompanyDTO((Company) client);
        }
        throw new IllegalArgumentException("Unknown client type");
    }

    public PersonDTO toPersonDTO(Person person) {
        PersonDTO dto = new PersonDTO();
        dto.setId(person.getId());
        dto.setName(person.getName());
        dto.setEmail(person.getEmail());
        dto.setPhone(person.getPhone());
        dto.setBirthdate(person.getBirthdate());
        return dto;
    }

    public CompanyDTO toCompanyDTO(Company company) {
        CompanyDTO dto = new CompanyDTO();
        dto.setId(company.getId());
        dto.setName(company.getName());
        dto.setEmail(company.getEmail());
        dto.setPhone(company.getPhone());
        dto.setCompanyIdentifier(company.getCompanyIdentifier());
        return dto;
    }

    public Client toEntity(ClientDTO dto) {
        if (dto instanceof PersonDTO) {
            return toPerson((PersonDTO) dto);
        } else if (dto instanceof CompanyDTO) {
            return toCompany((CompanyDTO) dto);
        }
        throw new IllegalArgumentException("Unknown DTO type");
    }

    public Person toPerson(PersonDTO dto) {
        Person person = new Person();
        person.setName(dto.getName());
        person.setEmail(dto.getEmail());
        person.setPhone(dto.getPhone());
        person.setBirthdate(dto.getBirthdate());
        return person;
    }

    public Company toCompany(CompanyDTO dto) {
        Company company = new Company();
        company.setName(dto.getName());
        company.setEmail(dto.getEmail());
        company.setPhone(dto.getPhone());
        company.setCompanyIdentifier(dto.getCompanyIdentifier());
        return company;
    }
}