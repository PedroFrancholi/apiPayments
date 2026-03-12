package br.com.apiPayments.repository;

import br.com.apiPayments.model.AccountModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;


public interface AccountRepository extends JpaRepository<AccountModel, UUID> {
}
