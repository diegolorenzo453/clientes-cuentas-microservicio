package com.example.customeraccounts.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "customers")
@Getter
@Setter
@NoArgsConstructor
public class CustomerJpaEntity {

    @Id
    private String nationalId;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String firstSurname;

    @Column(nullable = false)
    private String secondSurname;

    @Column(nullable = false)
    private LocalDate birthDate;

    public CustomerJpaEntity(String nationalId, String firstName, String firstSurname, String secondSurname, LocalDate birthDate) {
        this.nationalId = nationalId;
        this.firstName = firstName;
        this.firstSurname = firstSurname;
        this.secondSurname = secondSurname;
        this.birthDate = birthDate;
    }
}


