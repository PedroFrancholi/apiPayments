package br.com.apiPayments.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "sb_component")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComponentModel {

    @Id
    @GeneratedValue
    private UUID cdComponent;

    @Column(nullable = false)
    private String nmPerson;

    @Column(nullable = false)
    private String cdDocument;

    @Column(nullable = false, unique = true)
    private String dsEmail;

    @Column(nullable = false)
    private String dsPassword;

    @Column(nullable = false)
    private LocalDateTime dtCreatedAt;
}
