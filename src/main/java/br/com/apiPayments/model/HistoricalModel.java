package br.com.apiPayments.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "sb_historical")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistoricalModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idHistorical;

    @Column(nullable = false)
    private String dsHistorical;

    @Column(nullable = false)
    private String dsReversalHistorical;

    @Pattern(regexp = "D|C", message = "Tipo deve ser D ou C")
    @Column(nullable = false)
    private String tpMovment;

    @Column(nullable = false)
    private LocalDateTime dtCreatedAt;
}
