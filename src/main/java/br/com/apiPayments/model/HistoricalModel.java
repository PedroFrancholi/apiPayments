package br.com.apiPayments.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "sb_historical")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HistoricalModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idHistorical;

    @Column(nullable = false)
    private String dsHistorical;

    @Column(nullable = false)
    private String dsReversalHistorical;

    @Column(nullable = false)
    private LocalDateTime dtCreatedAt;
}
