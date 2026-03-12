package br.com.apiPayments.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
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
public class TransactionModel {

    @Id
    @GeneratedValue
    private UUID cd_transaction;

    @ManyToOne
    @JoinColumn(name = "originAccount_cd", nullable = false)
    private AccountModel cdOriginAccount;

    @ManyToOne
    @JoinColumn(name = "destinationAccount_cd", nullable = false)
    private AccountModel cdDestinationAccount;

    @Column(nullable = false)
    private BigDecimal vlTransaction;

    @ManyToOne
    @JoinColumn(name = "historical_cd", nullable = false)
    private HistoricalModel cdHistorical;

    @ManyToOne
    @JoinColumn(name = "status_id", nullable = false)
    private StatusModel idStatus;

    @Column(nullable = false)
    private LocalDateTime dtCreatedAt;

    @Column(nullable = true)
    private String dsDetail;

    @Column(nullable = true)
    private LocalDateTime dtProcessAt;
}
