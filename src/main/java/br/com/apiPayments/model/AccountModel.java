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
@Table(name = "sb_account")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountModel {

    @Id
    @GeneratedValue
    private UUID cdAccount;

    @Column(nullable = false, unique = true)
    private Integer nrAccount;

    @Column(nullable = false)
    private Integer cdAgency;

    @Column(nullable = false)
    private BigDecimal vlAmount;

    @Column(nullable = false)
    private LocalDateTime dtCreatedAt;

    @OneToOne
    @JoinColumn(name = "component_id", nullable = false)
    private ComponentModel component;
}
