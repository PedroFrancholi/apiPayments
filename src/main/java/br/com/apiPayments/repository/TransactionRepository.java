package br.com.apiPayments.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TransactionRepository extends JpaRepository<TransactionRepository, UUID> {
}
