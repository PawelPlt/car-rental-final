package com.example.carrental.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Rental {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Car car;

    @ManyToOne(optional = false)
    private Customer customer;

    @ManyToOne
    private Employee employee;

    @ManyToOne
    private Location pickupLocation;

    @ManyToOne
    private Location dropoffLocation;

    private LocalDate startDate;
    private LocalDate endDate;

    private BigDecimal totalPrice;

    @Enumerated(EnumType.STRING)
    private RentalStatus status;
}
