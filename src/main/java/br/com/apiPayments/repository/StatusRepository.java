package br.com.apiPayments.repository;

import br.com.apiPayments.model.StatusModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepository extends JpaRepository<StatusModel, Integer> {
}
