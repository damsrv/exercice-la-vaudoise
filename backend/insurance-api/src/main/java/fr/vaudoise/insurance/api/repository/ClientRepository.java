package fr.vaudoise.insurance.api.repository;

import fr.vaudoise.insurance.api.model.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
}