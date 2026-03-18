package br.com.apiPayments.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "sb_transaction")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionModel {

    @Id
    @GeneratedValue
    private UUID cdTransaction;

    @ManyToOne
    @JoinColumn(name = "originAccount_cd", nullable = false)
    private AccountModel cdOriginAccount;

    @Column(nullable = false)
    private BigDecimal vlTransaction;

    @ManyToOne
    @JoinColumn(name = "historical_cd", nullable = false)
    private HistoricalModel cdHistorical;

    @Column(nullable = false)
    private String cdStatus;

    @Column(nullable = false)
    private Boolean inReversal;

    @Column(nullable = false)
    private LocalDateTime dtCreatedAt;

    @ManyToOne
    @JoinColumn(name = "destinationAccount_cd", nullable = true)
    private AccountModel cdDestinationAccount;

    @Column(nullable = true)
    private String dsDetail;

    @Column(nullable = true)
    private LocalDateTime dtProcessAt;
}
