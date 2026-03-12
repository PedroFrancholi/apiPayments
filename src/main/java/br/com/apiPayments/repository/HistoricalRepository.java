package br.com.apiPayments.repository;

import br.com.apiPayments.model.HistoricalModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoricalRepository extends JpaRepository<HistoricalModel, Integer> {
}
