package br.com.apiPayments.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "sb_status")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StatusModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idStatus;

    @Column(nullable = false)
    private String dsStatus;
}
