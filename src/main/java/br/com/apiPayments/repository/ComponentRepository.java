package br.com.apiPayments.repository;

import br.com.apiPayments.model.ComponentModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ComponentRepository extends JpaRepository<ComponentModel, UUID> {
    Optional<ComponentModel> findByCdDocument(String cdDocument);
}
