package br.com.apiPayments.repository;

import br.com.apiPayments.model.TransactionModel;
import org.hibernate.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TransactionRepository extends JpaRepository<TransactionModel, UUID> {
}
