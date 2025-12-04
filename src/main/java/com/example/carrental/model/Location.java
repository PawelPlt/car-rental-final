package com.example.carrental.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String city;
    private String address;

    @OneToMany(mappedBy = "location")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Car> cars;
}
