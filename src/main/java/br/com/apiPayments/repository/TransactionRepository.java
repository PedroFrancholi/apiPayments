package br.com.apiPayments.repository;

import br.com.apiPayments.model.AccountModel;
import br.com.apiPayments.model.TransactionModel;
import org.hibernate.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<TransactionModel, UUID> {
    List<TransactionModel> findByOrigin(AccountModel origin);
    List<TransactionModel> findByDestination(AccountModel destination);
    List<TransactionModel> findByCdStatus(String cdStatus);
}
